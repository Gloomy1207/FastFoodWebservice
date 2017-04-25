package com.gloomy.service.controller.basic;

import com.gloomy.beans.User;
import com.gloomy.beans.VerificationToken;
import com.gloomy.events.OnRegistrationCompleteEvent;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.impl.VerificationDAOImpl;
import com.gloomy.security.RestUserDetailService;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.controller.response.basic.JwtAuthenticationResponse;
import com.gloomy.service.controller.response.basic.RegisterResponse;
import com.gloomy.service.controller.response.basic.RegistrationConfirmResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.ServerInformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.OAUTH_API_URL)
public class AuthenticationRestController {

    private final JwtTokenUtil mJwtTokenUtil;

    private final RestUserDetailService mRestUserDetailService;

    private final UserDAOImpl mUserDAO;

    private final ApplicationEventPublisher mApplicationEventPublisher;

    private final VerificationDAOImpl mVerificationDAO;

    private final MessageSource mMessageSource;

    private BCryptPasswordEncoder mBCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationRestController(JwtTokenUtil mJwtTokenUtil, UserDAOImpl mUserDAO, RestUserDetailService mRestUserDetailService, ApplicationEventPublisher mApplicationEventPublisher, VerificationDAOImpl mVerificationDAO, MessageSource mMessageSource) {
        this.mJwtTokenUtil = mJwtTokenUtil;
        this.mUserDAO = mUserDAO;
        this.mRestUserDetailService = mRestUserDetailService;
        this.mApplicationEventPublisher = mApplicationEventPublisher;
        this.mVerificationDAO = mVerificationDAO;
        this.mMessageSource = mMessageSource;
    }

    /**
     * User login rest api
     *
     * @param username @RequestParam
     * @param password @RequestParam
     * @return access_token
     * @throws AuthenticationException Error when not match
     */
    @PostMapping(value = ApiMappingUrl.LOGIN_USER_API_URL)
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam(ApiParameter.USERNAME) String username,
                                   @RequestParam(ApiParameter.PASSWORD) String password) throws AuthenticationException {
        User user = mUserDAO.getUserByUsername(username);
        if (user == null || !mBCryptPasswordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                    .message("Username or password is incorrect")
                    .status(false)
                    .build());
        }
        UserDetails userDetails = mRestUserDetailService.loadUserByUsername(user.getUsername());
        String token = mJwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                .accessToken(token)
                .user(user)
                .status(true)
                .build());
    }

    @PostMapping(value = ApiMappingUrl.REGISTER_USER_API_URL)
    public ResponseEntity<?> register(@RequestParam(value = ApiParameter.USERNAME) String username,
                                      @RequestParam(value = ApiParameter.PASSWORD) String password,
                                      @RequestParam(value = ApiParameter.EMAIL) String email,
                                      HttpServletRequest request) {
        String userExist = mMessageSource.getMessage("message.userExist", null, request.getLocale());
        String emailExist = mMessageSource.getMessage("message.userExist", null, request.getLocale());
        String serverError = mMessageSource.getMessage("message.internalError", null, request.getLocale());
        if (mUserDAO.getUserByUsername(username) != null) {
            return ResponseEntity.ok(RegisterResponse.builder()
                    .status(false)
                    .accessToken(null)
                    .user(null)
                    .message(userExist).build());
        }
        if (mUserDAO.getUserByEmail(email) != null) {
            return ResponseEntity.ok(RegisterResponse.builder()
                    .status(false)
                    .accessToken(null)
                    .user(null)
                    .message(emailExist).build());
        }
        User user = mUserDAO.registerUser(username, password, email);
        if (user != null) {
            String success = mMessageSource.getMessage("message.sendConfirmationSuccess", null, request.getLocale());
            UserDetails userDetails = mRestUserDetailService.loadUserByUsername(username);
            String token = mJwtTokenUtil.generateToken(userDetails);
            new Thread(() -> mApplicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(request.getContextPath(), request.getLocale(), user, ServerInformationUtil.getURLWithContextPath(request)))).start();
            return ResponseEntity.ok(RegisterResponse.builder()
                    .accessToken(token)
                    .status(true)
                    .message(success)
                    .user(user)
                    .build());
        } else {
            return ResponseEntity.ok(RegisterResponse.builder()
                    .status(false)
                    .accessToken(null)
                    .user(null)
                    .message(serverError).build());
        }
    }

    /**
     * Refresh token API
     *
     * @param request HttpServletRequest
     * @return access_token
     */
    @GetMapping(value = ApiMappingUrl.REFRESH_TOKEN_URL)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        if (mJwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshToken = mJwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                    .status(false)
                    .accessToken(refreshToken)
                    .build());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * User login rest api
     *
     * @param facebookId    @RequestParam
     * @param facebookToken @RequestParam
     * @param name          @RequestParam
     * @param email         @RequestParam
     * @param avatar        @RequestParam
     * @return access_token
     * @throws AuthenticationException Error when not match
     */
    @PostMapping(value = ApiMappingUrl.LOGIN_USER_FACEBOOK_API_URL)
    @ResponseBody
    public ResponseEntity<?> loginFacebook(@RequestParam(ApiParameter.FACEBOOK_TOKEN) String facebookToken,
                                           @RequestParam(ApiParameter.FACEBOOK_ID) String facebookId,
                                           @RequestParam(ApiParameter.FULL_NAME) String name,
                                           @RequestParam(ApiParameter.EMAIL) String email,
                                           @RequestParam(ApiParameter.AVATAR) String avatar) throws AuthenticationException {
        User user = mUserDAO.getUserByFacebook(email, facebookId);
        if (user == null) {
            user = mUserDAO.registerUserByFacebook(facebookToken, facebookId, name, email, avatar);
        }
        UserDetails userDetails = mRestUserDetailService.loadUserByUsername(user.getUsername());
        String token = mJwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                .accessToken(token)
                .status(true)
                .user(user)
                .build());
    }

    /**
     * @param confirmToken @String
     * @return confirmation status
     */

    @GetMapping(value = ApiMappingUrl.REGISTRATION_CONFIRM)
    public ResponseEntity<?> registrationConfirm(@RequestParam(ApiParameter.CONFIRM_TOKEN) String confirmToken,
                                                 HttpServletRequest request) {
        VerificationToken verificationToken = mVerificationDAO.getVerificationToken(confirmToken);
        if (verificationToken == null) {
            String tokenInvalid = mMessageSource.getMessage("message.confirmationTokenInvalid", null, request.getLocale());
            return ResponseEntity.ok(RegistrationConfirmResponse.builder().message(tokenInvalid).status(false).build());
        }
        if (mVerificationDAO.isTokenExpired(verificationToken)) {
            String tokenExpired = mMessageSource.getMessage("message.confirmationTokenExpired", null, request.getLocale());
            return ResponseEntity.ok(RegistrationConfirmResponse.builder().message(tokenExpired).status(false).build());
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        mUserDAO.save(user);
        mVerificationDAO.deleteItem(verificationToken);
        String success = mMessageSource.getMessage("message.confirmationSuccess", null, request.getLocale());
        return ResponseEntity.ok(RegistrationConfirmResponse.builder().message(success).status(true).build());
    }

    /**
     * @param request @HttpServletRequest
     * @return Send Confirmation Message
     */
    @GetMapping(value = ApiMappingUrl.RESEND_CONFIRMATION)
    public ResponseEntity<?> resendConfirmation(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        User user = mUserDAO.getUserByUsername(mJwtTokenUtil.getUsernameFromToken(token));
        VerificationToken verificationToken = mVerificationDAO.getVerificationTokenByUser(user);
        verificationToken.setExpiryDate(mVerificationDAO.calculateExpiryDate(VerificationDAOImpl.EXPIRATION));
        verificationToken.setToken(mVerificationDAO.recreateVerificationToken());
        mVerificationDAO.updateValue(verificationToken);
        mApplicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(request.getContextPath(), request.getLocale(), user, ServerInformationUtil.getURLWithContextPath(request)));
        String resendMessage = mMessageSource.getMessage("message.resendConfirmationSuccess", null, request.getLocale());
        return ResponseEntity.ok(RegistrationConfirmResponse.builder().message(resendMessage).status(true).build());
    }
}

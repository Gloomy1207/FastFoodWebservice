package com.gloomy.service.controller.basic;

import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.security.RestUserDetailService;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.controller.response.ResponseMessageConstant;
import com.gloomy.service.controller.response.basic.JwtAuthenticationResponse;
import com.gloomy.service.controller.response.basic.RegisterResponse;
import com.gloomy.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    private BCryptPasswordEncoder mBCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationRestController(JwtTokenUtil mJwtTokenUtil, UserDAOImpl mUserDAO, RestUserDetailService mRestUserDetailService) {
        this.mJwtTokenUtil = mJwtTokenUtil;
        this.mUserDAO = mUserDAO;
        this.mRestUserDetailService = mRestUserDetailService;
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
                .status(true)
                .build());
    }

    @RequestMapping(value = ApiMappingUrl.REGISTER_USER_API_URL, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> register(@RequestParam(value = ApiParameter.USERNAME) String username,
                                      @RequestParam(value = ApiParameter.PASSWORD) String password) {
        if (mUserDAO.getUserByUsername(username) != null) {
            return ResponseEntity.ok(new RegisterResponse(null, null, ResponseMessageConstant.USER_EXIST_MESSAGE_EN));
        }
        User user = User.builder()
                .username(username)
                .password(mBCryptPasswordEncoder.encode(password))
                .build();
        if (mUserDAO.save(user) != null) {
            UserDetails userDetails = mRestUserDetailService.loadUserByUsername(username);
            String token = mJwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new RegisterResponse(user, token, ResponseMessageConstant.ADD_USER_SUCCESSFUL));
        } else {
            return ResponseEntity.ok(new RegisterResponse(null, null, ResponseMessageConstant.SERVER_ERROR_MESSAGE_EN));
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
                .build());
    }
}

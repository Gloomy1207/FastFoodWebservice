package com.gloomy.service.controller.basic;

import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.controller.request.EditUserRequest;
import com.gloomy.service.controller.response.authenticated.EditUserResponse;
import com.gloomy.service.storage.StorageService;
import com.gloomy.utils.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.USER_ENDPOINT)
public class UserBasicRestController {

    private final UserDAOImpl mUserDAO;
    private final MessageSource mMessageSource;
    private final StorageService mStorageService;
    private BCryptPasswordEncoder mBCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserBasicRestController(UserDAOImpl userDAOImpl, MessageSource mMessageSource, StorageService mStorageService) {
        this.mUserDAO = userDAOImpl;
        this.mMessageSource = mMessageSource;
        this.mStorageService = mStorageService;
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public Page<User> getSearchUser(HttpServletRequest request, Pageable pageable) {
        return mUserDAO.findAllPaginateOrderByPoint(request, pageable);
    }

    @GetMapping(value = ApiMappingUrl.RATING)
    public Page<User> getRatingUser(Pageable pageable, HttpServletRequest request) {
        return mUserDAO.getRatingUser(pageable, request);
    }

    @GetMapping(value = ApiMappingUrl.MY_PROFILE_ENDPOINT)
    @ResponseBody
    public ResponseEntity<?> getProfile(@RequestParam(value = ApiParameter.USERNAME, required = false) String username,
                                        HttpServletRequest request) {
        return ResponseEntity.ok(mUserDAO.getUserProfile(username, request));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = ApiMappingUrl.EDIT_PROFILE)
    public ResponseEntity<?> editProfile(@RequestParam(ApiParameter.BODY) String data,
                                         @RequestParam(value = ApiParameter.AVATAR, required = false) MultipartFile avatar,
                                         HttpServletRequest request) {
        Gson gson = new Gson();
        boolean status;
        String message;
        try {
            EditUserRequest userRequest = gson.fromJson(data, EditUserRequest.class);
            User user = mUserDAO.getUserByUsername(userRequest.getUsername());
            if (user != null) {
                user.setFullName(userRequest.getFullName());
                user.setEmail(userRequest.getEmail());
                user.setDescription(userRequest.getDescription());
                if (!TextUtils.isEmpty(userRequest.getPassword())) {
                    user.setPassword(mBCryptPasswordEncoder.encode(userRequest.getPassword()));
                }
                if (avatar != null) {
                    try {
                        mStorageService.store(avatar);
                        String pathFile = String.format("%s%s%s/%s", ApiMappingUrl.API_BASIC_URL, ApiMappingUrl.USER_ENDPOINT, ApiMappingUrl.FILE, avatar.getOriginalFilename());
                        if (TextUtils.isEmpty(user.getAvatar())) {
                            mStorageService.deleteFile(user.getAvatar().substring(user.getAvatar().lastIndexOf('/')));
                        }
                        user.setAvatar(pathFile);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                mUserDAO.saveUser(user);
            }
            status = true;
            message = mMessageSource.getMessage("message.userUpdateSuccess", null, request.getLocale());
        } catch (JsonSyntaxException exc) {
            status = false;
            message = mMessageSource.getMessage("message.dataWrongType", null, request.getLocale());
        }
        return ResponseEntity.ok(EditUserResponse.builder().message(message).status(status).build());
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = mStorageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}

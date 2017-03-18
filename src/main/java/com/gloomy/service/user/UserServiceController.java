package com.gloomy.service.user;

import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.RestServiceController;
import com.gloomy.service.response.FailureResponse;
import com.gloomy.service.response.FailureType;
import com.gloomy.service.response.SuccessType;
import com.gloomy.service.response.user.RegisterUserSuccessResponse;
import com.gloomy.utils.StringUtil;
import com.gloomy.utils.TextUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
public class UserServiceController extends RestServiceController {
    public UserServiceController(UserDAO mUserDAO) {
        super(mUserDAO);
    }

    @RequestMapping(value = ApiMappingUrl.REGISTER_USER_API_URL, method = RequestMethod.POST)
    @ResponseBody
    public String userRegister(@PathVariable(value = ApiParameter.LOCALE, required = false) String locale,
                               @RequestParam(value = ApiParameter.USERNAME, required = false) String username,
                               @RequestParam(value = ApiParameter.PASSWORD, required = false) String password) {
        // TODO: 18-Mar-17 Handle with locale later
        System.out.println(locale);
        FailureResponse failureResponse;
        String validate = validateUserParameter(username, password);
        if (!TextUtils.isEmpty(validate)) {
            failureResponse = FailureResponse.builder().message(validate).build();
            return failureResponse.toJson();
        }
        if (mUserDAO.findUserByUsername(username) != null) {
            failureResponse = FailureResponse.builder().build();
            failureResponse.addMessageFailure(FailureType.USER_EXIST);
            return failureResponse.toJson();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(StringUtil.getInstance().convertStringToMd5(password));
        if (mUserDAO.save(user) != null) {
            // TODO: 18-Mar-17 Add access_token later
            RegisterUserSuccessResponse successResponse = RegisterUserSuccessResponse.builder()
                    .type(SuccessType.ADD_USER)
                    .status(true)
                    .accessToken("abc")
                    .build();
            return successResponse.toJson();
        } else {
            failureResponse = FailureResponse.builder().build();
            failureResponse.addMessageFailure(FailureType.SERVER_ERROR);
            return failureResponse.toJson();
        }
    }

    private String validateUserParameter(String username, String password) {
        FailureResponse failureResponse = FailureResponse.builder().build();
        if (TextUtils.isEmpty(username)) {
            failureResponse.addMessageFailure(FailureType.USERNAME_EMPTY);
        }
        if (TextUtils.isEmpty(password)) {
            failureResponse.addMessageFailure(FailureType.PASSWORD_EMPTY);
        }
        return failureResponse.getMessage();
    }
}

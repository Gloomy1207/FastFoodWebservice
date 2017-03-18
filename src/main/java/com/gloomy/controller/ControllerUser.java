package com.gloomy.controller;

import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */
@Controller
@RequestMapping(value = "/user")
public class ControllerUser {

    private final UserDAOImpl mUserDAOImpl;

    @Autowired
    public ControllerUser(UserDAOImpl mUserDAOImpl) {
        this.mUserDAOImpl = mUserDAOImpl;
    }

    @RequestMapping(value = "/userDemoForm")
    public String addUserTest(Model modelAdd, Model modelSelect) {
        modelAdd.addAttribute("user", new User());
        modelSelect.addAttribute("users", mUserDAOImpl.getUsers());
        return "userDemoForm";
    }

    @RequestMapping(value = "/addUserDemo")
    public String addUserTest(@ModelAttribute User user) {
        mUserDAOImpl.addUser(user);
        return "userDemoForm";
    }

    @RequestMapping(value = "/editUser/{id}")
    public String editUser(@PathVariable String id, Model model) {
        return "userDemoForm";
    }

    @RequestMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable String id) {
        mUserDAOImpl.deleteUser(Integer.parseInt(id));
        return "redirect:/v1/api/userDemoForm";
    }
}

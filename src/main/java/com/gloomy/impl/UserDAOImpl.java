package com.gloomy.impl;

import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */
@Service
public class UserDAOImpl {
    private UserDAO mUserDAO;

    @Autowired
    public void setUserDAO(UserDAO mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    public boolean addUser(User user) {
        user.setPassword(StringUtil.getInstance().convertStringToMd5(user.getPassword()));
        return mUserDAO.save(user) != null;
    }

    public List<User> getUsers() {
        return mUserDAO.findAll();
    }

    public void deleteUser(int id) {
        mUserDAO.delete(id);
    }
}

package com.qst.crop.service.impl;

import com.qst.crop.dao.UserDao;
import com.qst.crop.entity.User;
import com.qst.crop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User selectByUserName(String userName) {
        return userDao.selectByPrimaryKey(userName);
    }
}
package com.qst.crop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.crop.dao.UserDao;
import com.qst.crop.entity.User;
import com.qst.crop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User selectByUserName(String userName) {
        return userDao.selectByPrimaryKey(userName);
    }

    @Override
    public int register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("user");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIntegral(0);
        user.setCredit(0);
        return userDao.insert(user);
    }

    @Override
    public int updateUser(User user) {
        user.setUpdateTime(new Date());
        return userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updatePassword(String userName, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userDao.updatePassword(userName, encodedPassword);
    }

    @Override
    public void delete(String userName) {
        userDao.deleteByPrimaryKey(userName);
    }

    @Override
    public PageInfo<User> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectAll();
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }
}
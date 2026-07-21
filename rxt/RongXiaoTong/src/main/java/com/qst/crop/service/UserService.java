package com.qst.crop.service;

import com.github.pagehelper.PageInfo;
import com.qst.crop.entity.User;

public interface UserService {

    User selectByUserName(String userName);

    int register(User user);

    int updateUser(User user);

    int updatePassword(String userName, String password);

    void delete(String userName);

    PageInfo<User> findPage(Integer pageNum, Integer pageSize);

}
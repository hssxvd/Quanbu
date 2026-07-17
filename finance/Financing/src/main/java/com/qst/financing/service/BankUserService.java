package com.qst.financing.service;

import com.github.pagehelper.PageInfo;
import com.qst.financing.entity.BankUser;

public interface BankUserService {

    BankUser selectByUserName(String userName);

    int register(BankUser bankUser);

    int updateUser(BankUser bankUser);

    int updatePassword(String userName, String password);

    void delete(String userName);

    PageInfo<BankUser> findPage(Integer pageNum);

}
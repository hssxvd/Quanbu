package com.qst.financing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.financing.dao.BankUserDao;
import com.qst.financing.entity.BankUser;
import com.qst.financing.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BankUserServiceImpl implements BankUserService {
    @Autowired
    private BankUserDao bankUserDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Integer pageSize = 10;

    @Override
    public BankUser selectByUserName(String userName) {
        return bankUserDao.selectByPrimaryKey(userName);
    }

    @Override
    public int register(BankUser bankUser) {
        bankUser.setPassword(passwordEncoder.encode(bankUser.getPassword()));
        bankUser.setRole("user");
        bankUser.setCreateTime(new Date());
        bankUser.setUpdateTime(new Date());
        return bankUserDao.insert(bankUser);
    }

    @Override
    public int updateUser(BankUser bankUser) {
        bankUser.setUpdateTime(new Date());
        return bankUserDao.updateByPrimaryKeySelective(bankUser);
    }

    @Override
    public int updatePassword(String userName, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return bankUserDao.updatePassword(userName, encodedPassword);
    }

    @Override
    public void delete(String userName) {
        bankUserDao.deleteByPrimaryKey(userName);
    }

    @Override
    public PageInfo<BankUser> findPage(Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<BankUser> bankUsers = bankUserDao.selectAll();
        PageInfo<BankUser> bankUserPageInfo = new PageInfo<>(bankUsers);
        return bankUserPageInfo;
    }
}
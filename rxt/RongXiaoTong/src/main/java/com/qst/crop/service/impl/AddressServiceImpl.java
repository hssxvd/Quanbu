package com.qst.crop.service.impl;

import com.qst.crop.dao.AddressDao;
import com.qst.crop.entity.Address;
import com.qst.crop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public int addAddress(Address address) {
        if (address.getIsDefault() == null) {
            address.setIsDefault(0);
        }
        if (address.getIsDefault() == 1) {
            addressDao.clearDefault(address.getOwnName());
        }
        return addressDao.insert(address);
    }

    @Override
    public List<Address> getAddressesByUserName(String userName) {
        return addressDao.selectByUserName(userName);
    }

    @Override
    public Address getAddressByAddressId(Integer addressId) {
        return addressDao.selectByAddressId(addressId);
    }

    @Override
    public int updateAddress(Address address) {
        if (address.getIsDefault() == 1) {
            addressDao.clearDefault(address.getOwnName());
        }
        return addressDao.updateByPrimaryKeySelective(address);
    }

    @Override
    public int deleteAddress(Integer addressId) {
        return addressDao.deleteByAddressId(addressId);
    }
}
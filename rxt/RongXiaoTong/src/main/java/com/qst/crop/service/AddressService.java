package com.qst.crop.service;

import com.qst.crop.entity.Address;

import java.util.List;

public interface AddressService {

    int addAddress(Address address);

    List<Address> getAddressesByUserName(String userName);

    Address getAddressByAddressId(Integer addressId);

    int updateAddress(Address address);

    int deleteAddress(Integer addressId);
}
package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Address;
import com.qst.crop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private String getCurrentUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

    @PostMapping("/add")
    public Result addAddress(@RequestBody Address address) {
        String userName = getCurrentUsername();
        address.setOwnName(userName);
        int result = addressService.addAddress(address);
        if (result > 0) {
            return new Result(true, StatusCode.OK, "添加成功");
        }
        return new Result(false, StatusCode.ERROR, "添加失败");
    }

    @GetMapping("/list")
    public Result getAddresses() {
        String userName = getCurrentUsername();
        List<Address> addresses = addressService.getAddressesByUserName(userName);
        return new Result(true, StatusCode.OK, "查询成功", addresses);
    }

    @GetMapping("/{addressId}")
    public Result getAddressById(@PathVariable("addressId") Integer addressId) {
        Address address = addressService.getAddressByAddressId(addressId);
        if (address != null) {
            return new Result(true, StatusCode.OK, "查询成功", address);
        }
        return new Result(false, StatusCode.ERROR, "地址不存在");
    }

    @PutMapping("/update")
    public Result updateAddress(@RequestBody Address address) {
        String userName = getCurrentUsername();
        address.setOwnName(userName);
        int result = addressService.updateAddress(address);
        if (result > 0) {
            return new Result(true, StatusCode.OK, "更新成功");
        }
        return new Result(false, StatusCode.ERROR, "更新失败");
    }

    @DeleteMapping("/{addressId}")
    public Result deleteAddress(@PathVariable("addressId") Integer addressId) {
        int result = addressService.deleteAddress(addressId);
        if (result > 0) {
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(false, StatusCode.ERROR, "删除失败");
    }
}
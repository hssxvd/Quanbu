package com.qst.crop.service.impl;

import com.qst.crop.dao.ShoppingcartDao;
import com.qst.crop.entity.Shoppingcart;
import com.qst.crop.model.ShoppingModel;
import com.qst.crop.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShoppingcartServiceImpl implements ShoppingcartService {
    @Autowired
    private ShoppingcartDao shoppingcartDao;

    @Override
    public void addToCart(Integer orderId) {
        String username = getCurrentUsername();
        Shoppingcart query = new Shoppingcart();
        query.setOwnName(username);
        query.setOrderId(orderId);

        List<ShoppingModel> existingItems = shoppingcartDao.selectByShopping(query);
        if (existingItems != null && !existingItems.isEmpty()) {
            ShoppingModel existing = existingItems.get(0);
            Shoppingcart update = new Shoppingcart();
            update.setShoppingId(existing.getShoppingId());
            update.setCount(existing.getCount() + 1);
            update.setUpdateTime(new Date());
            shoppingcartDao.updateByPrimaryKeySelective(update);
            return;
        }

        Shoppingcart shoppingcart = new Shoppingcart();
        shoppingcart.setOwnName(username);
        shoppingcart.setOrderId(orderId);
        shoppingcart.setCount(1);
        shoppingcart.setCreateTime(new Date());
        shoppingcart.setUpdateTime(new Date());
        shoppingcartDao.insertSelective(shoppingcart);
    }

    @Override
    public boolean delete(Integer shoppingId) {
        Shoppingcart cart = shoppingcartDao.selectByPrimaryKey(shoppingId);
        if (!isOwnedByCurrentUser(cart)) {
            return false;
        }
        shoppingcartDao.deleteByPrimaryKey(shoppingId);
        return true;
    }

    @Override
    public boolean updateCount(Integer shoppingId, Integer count) {
        if (count == null || count <= 0) {
            return false;
        }
        Shoppingcart cart = shoppingcartDao.selectByPrimaryKey(shoppingId);
        if (!isOwnedByCurrentUser(cart)) {
            return false;
        }
        Shoppingcart update = new Shoppingcart();
        update.setShoppingId(shoppingId);
        update.setCount(count);
        update.setUpdateTime(new Date());
        shoppingcartDao.updateByPrimaryKeySelective(update);
        return true;
    }

    @Override
    public List<ShoppingModel> selectByUsername() {
        Shoppingcart shoppingcart = new Shoppingcart();
        shoppingcart.setOwnName(getCurrentUsername());
        return shoppingcartDao.selectByShopping(shoppingcart);
    }

    private String getCurrentUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

    private boolean isOwnedByCurrentUser(Shoppingcart cart) {
        return cart != null && getCurrentUsername().equals(cart.getOwnName());
    }
}

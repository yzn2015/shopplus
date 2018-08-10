package com.yzn.sport.cart;

import com.yzn.sport.pojo.BuyerCart;

public interface CartService {

    public void addBuyerCartToRedis(Long sid,Integer count,String username);

    public BuyerCart getBuyerCartFromRedis(String username);
}

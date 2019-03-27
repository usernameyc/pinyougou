package com.pinyougou.service;



import com.pinyougou.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> addItemtoCarts(List<Cart> carts, Long itemId, Integer num);

    void saveCartRedis(String username, List<Cart> carts);

    List<Cart> findCartsRedis(String username);

    List<Cart> mergeCart(List<Cart> cookieCarts, List<Cart> carts);
}

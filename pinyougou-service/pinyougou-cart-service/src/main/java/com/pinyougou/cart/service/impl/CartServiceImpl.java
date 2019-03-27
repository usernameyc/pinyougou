package com.pinyougou.cart.service.impl;
import java.math.BigDecimal;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.OrderItem;
import java.util.ArrayList;

import com.pinyougou.pojo.Cart;
import com.pinyougou.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceName = "com.pinyougou.service.CartService")
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<Cart> addItemtoCarts(List<Cart> carts, Long itemId, Integer num) {
        try {
            //查询商品
            Item item = itemMapper.selectByPrimaryKey(itemId);
            String sellerId = item.getSellerId();
            //根据商家id判断购物车中是否存在该集合的购物车
            Cart cart = searchCartBySellerId(sellerId , carts);
            if (cart == null) {
                //创建新的购物车
                cart = new Cart();
                cart.setSellerId(sellerId);
                cart.setSellerName(item.getSeller());
                //创建订单明细
                OrderItem orderItem = createOrderItem(item , num);
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(orderItem);
                //为购物车设置订单明细
                cart.setOrderItems(orderItems);
                //将新的购物车对象添加到购物车集合
                carts.add(cart);
            }else{
                //购物车集合中存在该商家购物车
                OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItems(),itemId);
                if(orderItem == null){
                    //新增购物车明细
                    orderItem = createOrderItem(item , num);
                    cart.getOrderItems().add(orderItem);
                }else {
                    //如果有,在原来购物车订单明细上添加数量,修改金额
                    orderItem.setNum(orderItem.getNum()+num);
                    orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));
                    //如果订单明细的购买数小于等于0,则删除
                    if(orderItem.getNum() <= 0){
                        //删除购物车中的订单
                        cart.getOrderItems().remove(orderItem);
                    }
                    if(cart.getOrderItems().size() == 0){
                        carts.remove(cart);
                    }
                }
            }
            return carts;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveCartRedis(String username, List<Cart> carts) {
        try{
            redisTemplate.boundValueOps("cart_"+username).set(carts);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> findCartsRedis(String username) {
        try{
            List<Cart> carts = (List<Cart>) redisTemplate.boundValueOps("cart_"+username).get();
            if (carts ==null){
                carts = new ArrayList<>();
            }
            return carts;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> mergeCart(List<Cart> cookieCarts, List<Cart> carts) {
            for (Cart cart : cookieCarts){
                for (OrderItem orderItem : cart.getOrderItems()){
                        carts = addItemtoCarts(carts,orderItem.getItemId(),orderItem.getNum());
                }
            }
            return carts;
    }

    private OrderItem searchOrderItemByItemId(List<OrderItem> orderItems, Long itemId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getItemId().equals(itemId)){
                return orderItem;
            }
        }
        return null;
    }

    private OrderItem createOrderItem(Item item, Integer num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(item.getId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setNum(num);
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        orderItem.setPicPath(item.getImage());
        orderItem.setSellerId(item.getSellerId());
        return orderItem;
    }

    private Cart searchCartBySellerId(String sellerId, List<Cart> carts) {
            for(Cart cart : carts ){
            if (cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }
}

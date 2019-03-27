package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable{
    private String sellerId;
    /** 商家名称 */
    private String sellerName;
    /** 购物车明细集合 */
    private List<OrderItem> orderItems;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

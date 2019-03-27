package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.common.util.CookieUtils;
import com.pinyougou.common.util.IdWorker;
import com.pinyougou.pojo.Cart;
import com.pinyougou.service.CartService;
import com.pinyougou.service.WeixinPayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference(timeout = 10000)
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @GetMapping("/addCart")
    @CrossOrigin(origins = "http://item.pinyougou.com",allowCredentials = "true")
    public boolean addCarts(Long itemId, Integer num) {
        //设置允许访问的域名
//        response.setHeader("Access-Control-Allow-Origin","http://item.pinyougou.com");
//        //设置允许访问的Cookie
//        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            String username = request.getRemoteUser();
            List<Cart> carts = findCarts();
            carts = cartService.addItemtoCarts(carts, itemId, num);
            if (StringUtils.isNoneBlank(username)){
                cartService.saveCartRedis(username,carts);
            }else {
                CookieUtils.setCookie(request, response, CookieUtils.CookieName.PINYOUGOU_CART, JSON.toJSONString(carts), 3600 * 24, true);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/findCart")
    public List<Cart> findCarts() {
        String username = request.getRemoteUser();
        List<Cart> carts = null;
        if (StringUtils.isNoneBlank(username)) {
            carts = cartService.findCartsRedis(username);
            //cookie里面获取cart数据
            String cartStr = CookieUtils.getCookieValue(request,CookieUtils.CookieName.PINYOUGOU_CART,true);
            if (StringUtils.isNoneBlank(cartStr)) {
                List<Cart> CookieCarts = JSON.parseArray(cartStr, Cart.class);
                carts = cartService.mergeCart(CookieCarts ,carts);
                cartService.saveCartRedis(username,carts);
                CookieUtils.getCookie(request,CookieUtils.CookieName.PINYOUGOU_CART);
            }
        } else {
            String carStr = CookieUtils.getCookieValue(request, CookieUtils.CookieName.PINYOUGOU_CART, true);
            if (StringUtils.isBlank(carStr)) {
                carStr = "[]";
            }
             carts = JSON.parseArray(carStr, Cart.class);
        }
            return carts;
    }


}

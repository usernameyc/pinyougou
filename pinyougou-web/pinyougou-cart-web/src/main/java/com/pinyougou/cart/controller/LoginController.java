package com.pinyougou.cart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @GetMapping("/user/loginName")
    public Map<String , String> loginName(HttpServletRequest request) {
        String name = request.getRemoteUser();
        Map<String, String> map = new HashMap<>();
        map.put("loginName" , name);
        return map;
    }
}

package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Address;
import com.pinyougou.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class AddressController {
    @Reference(timeout = 10000)
    private AddressService addressService;
    @GetMapping("/findAddressByUser")
    public List<Address> findAddressByUser(HttpServletRequest request){
        String useId = request.getRemoteUser();
        return addressService.findAddressByUser(useId);
    }
}

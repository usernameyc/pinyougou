package com.pinyougou.mapper;

import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Seller;

import java.util.List;

/**
 * SellerMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface SellerMapper extends Mapper<Seller>{


    List<Seller> findAll(Seller seller);
}
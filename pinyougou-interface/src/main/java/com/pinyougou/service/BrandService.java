package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 品牌服务接口
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-02-25<p>
 */
public interface BrandService {

    /** 查询全部品牌 */
    PageResult findByPage(Brand brand , int page , int rows);

    void save(Brand brand);

    void update(Brand brand);

    void deleteAll(Serializable[] ids);

    List<Map<String,Object>> findAllbyIdAndName();
}

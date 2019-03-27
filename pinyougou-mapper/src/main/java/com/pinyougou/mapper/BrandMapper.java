package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-02-25<p>
 */
public interface BrandMapper extends Mapper<Brand> {

    /** 查询全部品牌 */
//    @Select("select * from tb_brand")
    List<Brand> findAll(Brand brand);

    void deleteAll(Serializable[] ids);

    @Select("select id , name as text from tb_brand order by id asc")
    List<Map<String,Object>> finAllByIdAndName();
}

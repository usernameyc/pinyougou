package com.pinyougou.mapper;

import com.pinyougou.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ItemCatMapper extends Mapper<ItemCat> {
    @Select("SELECT * FROM `tb_item_cat` WHERE parent_id = #{parentId}")
    List<ItemCat> findItemCatByParentId(Long parentId);
}

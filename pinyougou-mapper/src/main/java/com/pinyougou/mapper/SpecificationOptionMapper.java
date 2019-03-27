package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import tk.mybatis.mapper.common.Mapper;


public interface SpecificationOptionMapper extends Mapper<SpecificationOption>{
    void save(Specification specification);
}

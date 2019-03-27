package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface SpecificationService {
    PageResult findByPage(Specification specification, int page, int rows);
//    List<Specification> findAll();

    void save(Specification specification);

    List<SpecificationOption> findSpecOption(Long id);

    void update(Specification specification);

    void deleteAll(Serializable[] ids);

    List<Map<String,Object>> findAllByIdAndName();
}

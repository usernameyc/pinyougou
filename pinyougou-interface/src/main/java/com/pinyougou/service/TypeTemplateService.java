package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.TypeTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    PageResult findByPage(TypeTemplate typeTemplate, int page, int rows);

    void save(TypeTemplate typeTemplate);

    void update(TypeTemplate typeTemplate);

    void deleteAll(Serializable[] ids);

    TypeTemplate findOne(Serializable id);

     List<Map> findSpecByTemplateId(Long id);

}

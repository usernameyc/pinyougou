package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service(interfaceName = "com.pinyougou.service.TypeTemplateService")
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Override
    public PageResult findByPage(TypeTemplate typeTemplate, int page, int rows) {
        try {
            PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    typeTemplateMapper.findAll(typeTemplate);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(TypeTemplate typeTemplate) {
      typeTemplateMapper.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public void deleteAll(Serializable[] ids) {
        Example example = new Example(TypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        typeTemplateMapper.deleteByExample(example);
    }

    @Override
    public TypeTemplate findOne(Serializable id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> findSpecByTemplateId(Long id) {
        try{
             TypeTemplate typeTemplate = findOne(id);
             List<Map> specList = JSON.parseArray(typeTemplate.getSpecIds(),Map.class);
             for (Map map: specList){
                 SpecificationOption so = new SpecificationOption();
                 so.setSpecId(Long.valueOf(map.get("id").toString()));
                 List<SpecificationOption> specificationOptions = specificationOptionMapper.select(so);
                 map.put("options",specificationOptions);
             }
             return specList;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}



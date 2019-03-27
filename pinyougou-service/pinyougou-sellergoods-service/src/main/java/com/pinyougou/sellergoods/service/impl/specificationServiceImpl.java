package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Service(interfaceName = "com.pinyougou.service.SpecificationService")
@Transactional
public class specificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public PageResult findByPage(Specification specification, int page, int rows) {
        try {
            PageInfo<Specification> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    specificationMapper.findAll(specification);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Override
    public void save(Specification specification) {
        try{
            specificationMapper.insertSelective(specification);
            specificationOptionMapper.save(specification);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SpecificationOption> findSpecOption(Long id) {
        try{
            SpecificationOption op = new SpecificationOption();
            op.setSpecId(id);
            return specificationOptionMapper.select(op);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Specification specification) {
     try{
         specificationMapper.updateByPrimaryKeySelective(specification);
        SpecificationOption so = new SpecificationOption();
        so.setSpecId(specification.getId());
        specificationOptionMapper.delete(so);
        specificationOptionMapper.save(specification);
     }catch (Exception e){
         throw new RuntimeException(e);
     }
    }

    @Override
    public void deleteAll(Serializable[] ids) {
        try{
            for (Serializable id : ids ){
                SpecificationOption so = new SpecificationOption();
                so.setSpecId((Long)id);
                specificationOptionMapper.delete(so);
                specificationMapper.deleteByPrimaryKey(id);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Map<String, Object>> findAllByIdAndName() {
        try {
            return specificationMapper.findAllByIdAndName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


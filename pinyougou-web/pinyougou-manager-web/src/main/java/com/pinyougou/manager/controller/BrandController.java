package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 品牌控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-02-25<p>
 */
@RestController // @Controller + @ResponseBody
@RequestMapping("/brand")
public class BrandController {

    /**
     * 配置引用服务
     * timeout : 超时毫秒数 1000毫秒
     * */
   @Reference
    private BrandService brandService;

    // @RequestMapping(method = RequestMethod.GET)
    // [{},{}]
    @GetMapping("/findByPage")
    public PageResult findByPage(Brand brand ,Integer page , Integer rows){
        if (brand != null && StringUtils.isNoneBlank(brand.getName())){
            try{
                brand.setName(new String(brand.getName().getBytes("ISO8859-1"),"UTF-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return brandService.findByPage(brand , page , rows);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Brand brand){
        try{
            brandService.save(brand);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Brand brand){
        try{
            brandService.update(brand);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try{
            brandService.deleteAll(ids);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/findBrandList")
    public List<Map<String , Object>> findBrandList(){
        return brandService.findAllbyIdAndName();
    }
}

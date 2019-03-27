package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class findContentByCategoryId {
    @Reference(timeout = 10000)
    private ContentService contentService;
    @GetMapping("/findContentByCategoryId")
    public List<Content> findContentByCategoryId(Long categoryId){
           return contentService.findContentByCategoryId(categoryId);
    }
}

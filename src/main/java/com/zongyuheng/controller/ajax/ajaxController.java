package com.zongyuheng.controller.ajax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zongyuheng.Utils.IndexPageManager;
import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.TagService;
import com.zongyuheng.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax")
public class ajaxController {
    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    IndexPageManager<TBlog> indexPageManager;

    TypeAndTagPageManager pageTypeList;
    @Autowired
    ObjectMapper objectMapper;
/*
这个是types的ajax传递
 */
       @GetMapping("/admin/types")
    public String getTypesPagesList(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) throws JsonProcessingException {
           System.out.println(page);
           if (page <= 1) {
               pageTypeList = typeService.getPageTypeList(1);
           } else {
               pageTypeList = typeService.getPageTypeList(page);
           }
           String s = objectMapper.writeValueAsString(pageTypeList);
           return  s;
    }
    /*
这个是tags的ajax传递
 */

    @GetMapping("/admin/tags")
    public String getTagsPagesList(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) throws JsonProcessingException {
        if (page <= 1) {
            pageTypeList = tagService.getPageTagList(1);
        } else {
            pageTypeList = tagService.getPageTagList(page);
        }
        String s = objectMapper.writeValueAsString(pageTypeList);
        return  s;
    }

}

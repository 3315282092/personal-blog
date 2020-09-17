package com.zongyuheng.controller;

import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.pojo.TTags;
import com.zongyuheng.pojo.TType;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.TagService;
import com.zongyuheng.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model){
        List<TTags> tTags = tagService.listTagTop(100000);
        if (id==-1){
            id=tTags.get(0).getTid();
        }
        model.addAttribute("tags",tTags);
        model.addAttribute("page",blogService.listBlog(id,page));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}

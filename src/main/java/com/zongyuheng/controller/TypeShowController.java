package com.zongyuheng.controller;

import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.pojo.TType;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model){
        List<TType> types = typeService.listTypeTop(100000);
        if (id==-1){
            id=types.get(0).getTid();
        }
        TBlog tBlog=new TBlog();
        tBlog.setTypeid(id);
        model.addAttribute("types",types);
        System.out.println(page);
        model.addAttribute("page",blogService.listBlog(page,tBlog));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}

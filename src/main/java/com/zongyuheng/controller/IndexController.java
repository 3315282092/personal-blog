package com.zongyuheng.controller;

import com.zongyuheng.Utils.IndexPageManager;
import com.zongyuheng.config.NotFoundException;
import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.TagService;
import com.zongyuheng.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    IndexPageManager<TBlog> indexPageManager;

    @GetMapping("/")
    public String welcome(){

        return "welcomePage";
    }


    @RequestMapping("/index")
    public String index(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        if (page <= 1) {
            indexPageManager = blogService.pageListBlog(1);
        } else {
            indexPageManager = blogService.pageListBlog(page);
        }
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("page", indexPageManager);
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {

        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }





    @RequestMapping("/search")
    public String search(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "query",required = false) String query) {
        if (query == null || query == "") {
            if (page <= 1) {
                indexPageManager = blogService.pageListBlog(1);
            } else {
                indexPageManager = blogService.pageListBlog(page);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("%");
            sb.append(query);
            sb.append("%");
            String queryParam = sb.toString();
            if (page <= 1) {
                indexPageManager = blogService.listQueryBlogs(1, queryParam);
            } else {
                indexPageManager = blogService.listQueryBlogs(page, queryParam);
            }
        }
        model.addAttribute("page", indexPageManager);
        model.addAttribute("query", query);
        return "search";
    }


    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        List<TBlog> blogList = blogService.listRecommendBlogTop(3);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragment::newblogList";
    }
}

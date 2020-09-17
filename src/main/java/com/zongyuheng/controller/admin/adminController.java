package com.zongyuheng.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zongyuheng.Utils.BlogPageManager;
import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.pojo.TTags;
import com.zongyuheng.pojo.TType;
import com.zongyuheng.pojo.TUser;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.TagService;
import com.zongyuheng.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class adminController {


    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;
    @Autowired
    ObjectMapper objectMapper;

    TypeAndTagPageManager typeAndTagPageManager;

    BlogPageManager blogPageManager;

/*
自定义方法区
 */

    private void setTypeAndTag(Model model){
        model.addAttribute("tags",tagService.getAllTags());
        model.addAttribute("types", typeService.getAllTypes());
    }

    @GetMapping
    public String index() {
        return "admin/adminIndex";
    }


    /*
    下面是blog的controller
     */
    @GetMapping("/blogs")
    public String settingBlog(HttpServletResponse response,@RequestParam(value = "page", required = false, defaultValue = "1") int Page, Model model, TBlog tBlog) {
        response.addHeader("X-Frame-Options","SAMEORIGIN");
        BlogPageManager<TBlog> tBlogBlogPageManager = blogService.listBlog(Page, tBlog);
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("page", tBlogBlogPageManager);
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(value = "page", required = false, defaultValue = "1") int Page, Model model, TBlog tBlog) {

        System.out.println("本页的页数" + Page);
        BlogPageManager<TBlog> tBlogBlogPageManager = blogService.listBlog(Page, tBlog);
        model.addAttribute("page", tBlogBlogPageManager);
        return "admin/blogs :: blogList";//返回以后只会渲染这里,不会刷新
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model, @PathVariable int id, HttpServletResponse response){
        response.addHeader("X-Frame-Options","SAMEORIGIN");
        setTypeAndTag(model);
        TBlog blog=blogService.getBlog(id);
        blog.init();//将tagids进行初始化，这样子才能够渲染到前端页面
        model.addAttribute("blog",blog);
        System.out.println(blog);
        return INPUT;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/input")
    public String inputBlog(HttpServletResponse response,Model model) {
        response.addHeader("X-Frame-Options","SAMEORIGIN");
       setTypeAndTag(model);
        model.addAttribute("blog", new TBlog());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(HttpServletResponse response,TBlog blog, HttpSession session,RedirectAttributes attributes){
        response.addHeader("X-Frame-Options","SAMEORIGIN");
        if (blog.getFlag()==null ||blog.getFlag()==""){
            blog.setFlag("原创");
        }
        blog.settUser((TUser)session.getAttribute("user"));
        blog.settType(typeService.getType(blog.getTypeid().intValue()));
        blog.settTags(tagService.ListTag(blog.getTagIds()));
        Boolean aBoolean = blogService.saveBlog(blog);
        if (aBoolean){
            attributes.addFlashAttribute("message","操作成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");

        }
        return REDIRECT_LIST;
    }

    /*
    下面是分类的controller
     */
    @GetMapping("/types")
    public String type(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) throws JsonProcessingException {
        if (page <= 1) {
            typeAndTagPageManager = typeService.getPageTypeList(1);
        } else {
            typeAndTagPageManager = typeService.getPageTypeList(page);
        }
        model.addAttribute("page", typeAndTagPageManager);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input() {
        return "admin/types-input";
    }

    //如果传递的是某个值会将tname封装到对象当中
    @PostMapping("/types")
    public String post(TType tType, BindingResult result, RedirectAttributes attributes, Model model) {
        if (typeService.getTypeByName(tType.getTname()) != null) {
            model.addAttribute("sameMessage", "已存在这个类型");
            return "admin/types-input";
        }

        if (StringUtils.isEmpty(tType.getTname())) {
            model.addAttribute("errorMessage", "验证失败");
            return "admin/types-input";
        }
        Boolean flag = typeService.saveType(tType);
        if (flag) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/types";//这里重新走请求
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable int id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String editPost(TType tType, @PathVariable int id, RedirectAttributes attributes, Model model) {
        if (typeService.getTypeByName(tType.getTname()) != null) {
            model.addAttribute("sameMessage", "已存在这个类型");
            return "admin/types-input";
        }

        if (StringUtils.isEmpty(tType.getTname())) {
            model.addAttribute("errorMessage", "验证失败");
            return "admin/types-input";
        }
        System.out.println(id + tType.getTname());
        Boolean flag = typeService.updateType(id, tType.getTname());
        if (flag) {
            attributes.addFlashAttribute("message", "更新成功");
        } else {
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/types";//这里重新走请求
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes, Model model) {
        try {
            typeService.deleteType(id);
        } catch (Exception e) {
            model.addAttribute("message", "您删除的分类有具体的博客，无法删除");
            return "forward:/admin/types";
        }
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

    /*
        下面是标签的controller
         */
    @GetMapping("/tags")
    public String tag(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) throws JsonProcessingException {
        if (page <= 1) {
            typeAndTagPageManager = tagService.getPageTagList(1);
        } else {
            typeAndTagPageManager = tagService.getPageTagList(page);
        }
        model.addAttribute("page", typeAndTagPageManager);
        return "admin/tags";
    }


    @GetMapping("/tags/input")
    public String inputTags() {
        return "admin/tags-input";
    }

    //如果传递的是某个值会将tname封装到对象当中
    @PostMapping("/tags")
    public String postTags(TTags tTags, BindingResult result, RedirectAttributes attributes, Model model) {
        if (typeService.getTypeByName(tTags.getTname()) != null) {
            model.addAttribute("sameMessage", "已存在这个类型");
            return "admin/tags-input";
        }

        if (StringUtils.isEmpty(tTags.getTname())) {
            model.addAttribute("errorMessage", "验证失败");
            return "admin/tags-input";
        }
        Boolean flag = tagService.saveTag(tTags);
        if (flag) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/tags";//这里重新走请求
    }

    @GetMapping("/tags/{id}/input")
    public String editTagsInput(@PathVariable int id, Model model) {
        model.addAttribute("tags", tagService.getTag(id));
        return "admin/types-input";
    }


    @PostMapping("/tags/{id}")
    public String editTagsPost(TTags tTags, @PathVariable int id, RedirectAttributes attributes, Model model) {
        if (tagService.getTagByName(tTags.getTname()) != null) {
            model.addAttribute("sameMessage", "已存在这个类型");
            return "admin/tags-input";
        }

        if (StringUtils.isEmpty(tTags.getTname())) {
            model.addAttribute("errorMessage", "验证失败");
            return "admin/tags-input";
        }
        Boolean flag = tagService.updateTag(id, tTags.getTname());
        if (flag) {
            attributes.addFlashAttribute("message", "更新成功");
        } else {
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/tags";//这里重新走请求
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTags(@PathVariable int id, RedirectAttributes attributes, Model model) {
        try {
            tagService.deleteTag(id);
        } catch (Exception e) {
            model.addAttribute("message", "您删除的分类有具体的博客，无法删除");
            return "forward:/admin/tags";
        }
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}


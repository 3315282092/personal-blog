package com.zongyuheng.controller;

import com.zongyuheng.pojo.TComment;
import com.zongyuheng.pojo.TUser;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;

    @Value("${tComment.headicon}")
    private String headicon;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<TComment> tComments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
@PostMapping("/comments")
    public String post(TComment comment, HttpSession session){
           Long blogid=comment.gettBlog().getBid();
           comment.settBlog(blogService.getBlog(blogid.intValue()));
    TUser user= (TUser) session.getAttribute("user");
    if (user!=null){
        comment.setHeadicon(user.getUsericon());
        comment.setAdmincontext(1);
    }else {
        comment.setHeadicon(headicon);
    }
           commentService.saveComment(comment);
        return "redirect:/comments/"+blogid;
    }
}

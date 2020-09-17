package com.zongyuheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/toLogin")
public class LoginController {

@GetMapping
public String toLogin(HttpServletRequest httpServletRequest, Model model){
    String message = (String) httpServletRequest.getSession().getAttribute("message");
    model.addAttribute("message",message);
    return "admin/login";
}



}

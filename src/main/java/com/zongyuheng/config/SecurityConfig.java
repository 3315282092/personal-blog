package com.zongyuheng.config;

import com.zongyuheng.mapper.UserMapper;
import com.zongyuheng.pojo.TUser;
import com.zongyuheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests().antMatchers("/").permitAll().
                antMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/ajax/admin/**").hasRole("ADMIN");
        http.formLogin().usernameParameter("username").passwordParameter("password").loginPage("/toLogin/").failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletRequest.getSession().setAttribute("message", "请输入正确的用户名或密码");
                httpServletResponse.sendRedirect("/toLogin/");
            }
        }).loginProcessingUrl("/admin/login").successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                User principal = (User) authentication.getPrincipal();
                String username = principal.getUsername();
                TUser byUsername = userMapper.findByUsername(username);
                byUsername.setPassword(null);
                httpServletRequest.getSession().setAttribute("user", byUsername);
                httpServletResponse.sendRedirect("/admin/");
            }
        });
        http.logout().logoutSuccessUrl("/index");

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

package com.zongyuheng.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
@Bean
@ConfigurationProperties(prefix = "spring.datasource")
public DruidDataSource druidDataSource(){
    return  new DruidDataSource();
}
    @Bean
    public ServletRegistrationBean statViewServlet() {

        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

// 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet

// 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到

        Map<String, String> initParams;
        initParams = new HashMap<>();

        initParams.put("loginUsername", "admin"); //后台管理界面的登录账号

        initParams.put("loginPassword", "123456"); //后台管理界面的登录密码

//后台允许谁可以访问

//initParams.put("allow", "localhost")：表示只有本机可以访问

//initParams.put("allow", "")：为空或者为null时，表示允许所有访问

        initParams.put("allow", "");

//deny：Druid 后台拒绝谁访问

//initParams.put("kuangshen", "192.168.1.20");表示禁止此ip访问

//设置初始化参数

        bean.setInitParameters(initParams);

        return bean;
    }
    @Bean

    public FilterRegistrationBean webStatFilter() {

        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.setFilter(new WebStatFilter());

//exclusions：设置哪些请求进行过滤排除掉，从而不进行统计

        Map<String, String> initParams = new HashMap<>();

        initParams.put("exclusions", "*.js,*.css,/druid/*,/jdbc/*");

        bean.setInitParameters(initParams);

//"/*" 表示过滤所有请求

        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;

    }
}


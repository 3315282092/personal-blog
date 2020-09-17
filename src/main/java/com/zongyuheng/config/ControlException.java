package com.zongyuheng.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice//会拦截掉所有有controller注解的控制层
public class ControlException {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL: {},Exception : {}",request.getRequestURL(),e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
     //这样做的效果 出现异常之后可以将异常打印到日志当中，还可以携带错误信息到错误页面，并且可以在前端将信息显示出去
    }




}

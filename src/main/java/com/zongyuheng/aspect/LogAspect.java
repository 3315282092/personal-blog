package com.zongyuheng.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {
  private final Logger logger= LoggerFactory.getLogger(this.getClass());
  @Pointcut("execution(* com.zongyuheng.controller.*.*(..))")
  public void log(){ }
  //这里定义了一个切面，所有的controller的东西都会经过这个切面
@Before("log()")
    public void doBefore(JoinPoint joinPoint){
    ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request=attributes.getRequest();
    String url=request.getRequestURL().toString();
    String ip=request.getRemoteAddr();
    String classMethods=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();//前面是类名后面是方法名
    Object[] args = joinPoint.getArgs();
    RequestLog requestLog=new RequestLog(url,ip,classMethods,args);
    logger.info("Request : {}",requestLog);
    }
    @After("log()")
    public void doAfter(){
     logger.info("--------doAfter-----------");
    }
    //在所有的经过这个切面处理以后需要返回的结果
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
          logger.info("Result : {}",result);
    }
    private class  RequestLog {
      private String url;
      private String ip;
      private String classMethod;
      private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

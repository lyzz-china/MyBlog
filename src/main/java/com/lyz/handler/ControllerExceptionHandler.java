package com.lyz.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//会拦截所有的类上面使用Controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //ExceptionHandler标识此方法用于拦截处理异常的
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e){
        //日志记录错误异常信息
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);

        //判断，如果发生异常，不进行拦截
        //使用注解工具类的寻找注解方法，参数是异常类和异常状态类
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            try {
                throw e;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}

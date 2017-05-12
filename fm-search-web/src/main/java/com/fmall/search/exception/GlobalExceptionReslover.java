package com.fmall.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/12 15:47
 */
public class GlobalExceptionReslover implements HandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        //写日志文件
        logger.error("系统发生异常", e);

        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统发生异常，请重试");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}

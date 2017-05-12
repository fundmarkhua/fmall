package com.fmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/28 17:55
 */
@Controller
public class PageController {
    /**
     * 进入管理首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }


}

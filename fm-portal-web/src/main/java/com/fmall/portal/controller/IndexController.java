package com.fmall.portal.controller;

import com.fmall.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面跳转
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/3 18:42
 */
@Controller
public class IndexController {
    @Autowired
    ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(Model model) {
        return "index";
    }
}

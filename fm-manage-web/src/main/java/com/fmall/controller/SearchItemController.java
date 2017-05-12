package com.fmall.controller;

import com.fmall.common.pojo.FmResult;
import com.fmall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 21:25
 */
@Controller
public class SearchItemController {
    @Autowired
    SearchService searchService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public FmResult importItemIndex(){
        return searchService.importAllItems();
    }
}

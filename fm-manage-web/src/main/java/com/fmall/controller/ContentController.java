package com.fmall.controller;

import com.fmall.common.pojo.EasyUIDataGridResult;
import com.fmall.common.pojo.FmResult;
import com.fmall.content.service.ContentService;
import com.fmall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/5 17:45
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        return contentService.getContentPage(categoryId, page, rows);
    }

    @RequestMapping("/save")
    @ResponseBody
    public FmResult addContent(TbContent content) {
        return contentService.addContent(content);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public FmResult deleteContent(String ids) {
        return contentService.deleteContent(ids);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public FmResult editContent(TbContent content) {
        return contentService.updateContent(content);
    }

    @RequestMapping("/desc/{id}")
    @ResponseBody
    public FmResult getContent(@PathVariable long id) {
        return contentService.getContentById(id);
    }

}

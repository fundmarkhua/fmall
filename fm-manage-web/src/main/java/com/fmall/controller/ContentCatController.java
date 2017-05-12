package com.fmall.controller;

import com.fmall.common.pojo.EasyUITreeNode;
import com.fmall.common.pojo.FmResult;
import com.fmall.content.service.ContentCategoryService;
import com.fmall.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/4 0:07
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {
    @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public FmResult createCategory(Long parentId, String name) {
        FmResult fmResult = contentCategoryService.addContentCategory(parentId, name);
        return fmResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public FmResult updateCategory(Long id, String name) {
        return contentCategoryService.updateCategoryName(id, name);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public FmResult deleteCategory(Long id) {
        return contentCategoryService.deleteContentCategory(id);
    }

}

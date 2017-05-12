package com.fmall.controller;

import com.fmall.common.pojo.EasyUIDataGridResult;
import com.fmall.common.pojo.FmResult;
import com.fmall.pojo.TbItem;
import com.fmall.service.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/26 22:00
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    protected Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public FmResult saveItem(TbItem item, String desc) {
        return itemService.addItem(item, desc);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public FmResult updateItem(TbItem item, String desc) {
        return itemService.updateItem(item, desc);
    }

    //获取商品描述
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public FmResult getItemDesc(@PathVariable String itemId) {
        long id = Long.valueOf(itemId);
        return itemService.getItemDesc(id);
    }

    //删除商品  status 数值改为 3
    @RequestMapping("/delete")
    @ResponseBody
    public FmResult deleteItem(String ids) {
        List<Long> list = new ArrayList<>();
        String[] split = ids.split(",");

        for (String id : split) {
            list.add(Long.valueOf(id));
        }
        return itemService.updateStatus(list, 3);
    }

    //下架商品  status 数值改为 2
    @RequestMapping("/instock")
    @ResponseBody
    public FmResult instockItem(String ids) {
        List<Long> list = new ArrayList<>();
        String[] split = ids.split(",");
        for (String id : split) {
            list.add(Long.valueOf(id));
        }
        return itemService.updateStatus(list, 2);
    }

    //上架商品  status 数值改为 1
    @RequestMapping("/reshelf")
    @ResponseBody
    public FmResult reshelfItem(String ids) {
        List<Long> list = new ArrayList<>();
        String[] split = ids.split(",");
        for (String id : split) {
            list.add(Long.valueOf(id));
        }
        return itemService.updateStatus(list, 1);
    }
}

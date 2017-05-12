package com.fmall.service.impl;

import com.fmall.common.pojo.EasyUITreeNode;
import com.fmall.mapper.TbItemCatMapper;
import com.fmall.pojo.TbItemCat;
import com.fmall.pojo.TbItemCatExample;
import com.fmall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/28 23:34
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        //设置查询条件
        tbItemCatExample.createCriteria()
                .andParentIdEqualTo(parentId);
        //查询数据
        List<TbItemCat> itemCatList = tbItemCatMapper.selectByExample(tbItemCatExample);
        ArrayList<EasyUITreeNode> nodeArrayList = new ArrayList<>();
        //把列表转换为EasyUITreeNode 列表

        for (TbItemCat itemCat : itemCatList) {
            EasyUITreeNode node = new EasyUITreeNode();
            //设置属性
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent() ? "closed" : "open");
            //添加到结果集
            nodeArrayList.add(node);
        }
        return nodeArrayList;
    }
}

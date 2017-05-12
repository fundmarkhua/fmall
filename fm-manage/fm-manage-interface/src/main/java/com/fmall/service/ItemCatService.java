package com.fmall.service;

import com.fmall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/28 23:31
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}

package com.fmall.content.service;

import com.fmall.common.pojo.EasyUITreeNode;
import com.fmall.common.pojo.FmResult;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/3 23:20
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    FmResult addContentCategory(long parentId, String name);

    FmResult deleteContentCategory(long id);

    FmResult updateCategoryName(long id, String name);
}

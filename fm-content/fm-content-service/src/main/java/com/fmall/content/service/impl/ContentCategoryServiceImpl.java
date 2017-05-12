package com.fmall.content.service.impl;

import com.fmall.common.pojo.EasyUITreeNode;
import com.fmall.common.pojo.FmResult;
import com.fmall.content.service.ContentCategoryService;
import com.fmall.mapper.TbContentCategoryMapper;
import com.fmall.pojo.TbContentCategory;
import com.fmall.pojo.TbContentCategoryExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/3 23:21
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        //根据id查询子节点列表
        TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
        contentCategoryExample.createCriteria()
                .andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(contentCategoryExample);

        //列表转换成List<EasyUITreeNode>
        List<EasyUITreeNode> treeNodeList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : contentCategoryList) {
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(tbContentCategory.getId());
            treeNode.setText(tbContentCategory.getName());
            treeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            treeNodeList.add(treeNode);
        }

        return treeNodeList;
    }

    @Override
    public FmResult addContentCategory(long parentId, String name) {
        //创建一个TbContentCategory对象,并且补全数据
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //排序号  决定同级展现次序 取值大于零的整数 默认值 1
        contentCategory.setSortOrder(1);
        //状态 可选值   1 正常  2删除
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //向表中插入数据
        contentCategoryMapper.insert(contentCategory);

        //判断父节点的isParent是否为true  不是true 改为 true
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        return FmResult.ok(contentCategory);
    }

    @Override
    public FmResult deleteContentCategory(long id) {
        //取出要删除的节点
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        if (contentCategory != null) {
            Long parentId = contentCategory.getParentId();
            //判断删除的是否是父节点,是父节点返回错误代码 禁止删除
            if (contentCategory.getIsParent()) {
                return FmResult.build(500, "父节点,禁止删除");
            }
            //删除目标节点
            contentCategoryMapper.deleteByPrimaryKey(id);
            //判断父节点下是否还有子节点,如果没有父节点isParent改为false
            TbContentCategoryExample categoryExample = new TbContentCategoryExample();
            categoryExample.createCriteria()
                    .andParentIdEqualTo(parentId);
            List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(categoryExample);
            if (categoryList.size() == 0) {
                TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
                parentCategory.setIsParent(false);
                contentCategoryMapper.updateByPrimaryKey(parentCategory);
            }
            return FmResult.ok();
        }
        return FmResult.build(500, "数据库中没有所选节点");
    }

    @Override
    public FmResult updateCategoryName(long id, String name) {
        try {
            //根据id取出节点
            TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
            //更新节点数据
            category.setName(name);
            category.setUpdated(new Date());
            //存入数据库
            contentCategoryMapper.updateByPrimaryKey(category);
            return FmResult.ok();

        } catch (Exception e) {
            return FmResult.build(500, "重命名失败");
        }
    }
}


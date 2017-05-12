package com.fmall.search.mapper;

import com.fmall.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 16:08
 */
public interface ItemMapper {
    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);
}

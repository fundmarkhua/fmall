package com.fmall.search.service;

import com.fmall.common.pojo.FmResult;
import com.fmall.common.pojo.SearchResult;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 15:17
 */
public interface SearchService {
    FmResult importAllItems();

    SearchResult search(String keyWord, int page, int rows) throws Exception;
}

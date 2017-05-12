package com.fmall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/5/11 23:47
 */
public class SearchResult implements Serializable {
    private static final long serialVersionUID = -373788130396985119L;
    private long recordCount;
    private int totalPages;
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList() {
        setItemList();
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}

package com.fmall.common.pojo;

import java.io.Serializable;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/28 21:30
 */
public class EasyUITreeNode implements Serializable {

    private static final long serialVersionUID = 4206050955449979124L;
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package com.yushilei.commonapp.common.bean.net;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class Recommend {
    private String code;
    private String msg;
    private int ret;
    private List<Type> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<Type> getList() {
        return list;
    }

    public void setList(List<Type> list) {
        this.list = list;
    }
}

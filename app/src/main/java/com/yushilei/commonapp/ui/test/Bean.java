package com.yushilei.commonapp.ui.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

/**
 * @author shilei.yu
 * @since 2017/12/5
 */

public class Bean {
    @SerializedName("m.zhaopin.com/next/zpd/zpdQuestionDetail")

    private Sub detail;

    @JSONField(name = "m.zhaopin.com/next/zpd/zpdQuestionDetail")
    public Sub getDetail() {
        return detail;
    }

    @JSONField(name = "m.zhaopin.com/next/zpd/zpdQuestionDetail")
    public void setDetail(Sub detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "detail=" + detail +
                '}';
    }

    public static class Sub {
        String weex;

        public String getWeex() {
            return weex;
        }

        public void setWeex(String weex) {
            this.weex = weex;
        }
    }
}

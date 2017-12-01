package com.yushilei.commonapp.ui.weex.route;

/**
 * @author shilei.yu
 * @since 2017/12/1
 */

public class JsBean<T> {
    public String name;
    public String url;
    public T data;

    public JsBean() {
    }

    public JsBean(String name, String url, T data) {
        this.name = name;
        this.url = url;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", data=" + data +
                '}';
    }
}

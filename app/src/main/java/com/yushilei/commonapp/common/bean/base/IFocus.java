package com.yushilei.commonapp.common.bean.base;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public interface IFocus {
    public List<Focus> getData();

    public void setData(List<Focus> data);

    public long getResponseId();

    public void setResponseId(long responseId);

    public int getRet();

    public void setRet(int ret);
}

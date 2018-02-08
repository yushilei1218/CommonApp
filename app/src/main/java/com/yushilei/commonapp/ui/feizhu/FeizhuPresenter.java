package com.yushilei.commonapp.ui.feizhu;

import com.yushilei.commonapp.common.mvp.BasePresenter;

/**
 * @author shilei.yu
 * @since on 2018/2/8.
 */

public class FeizhuPresenter extends BasePresenter<FeizhuConstract.IView> implements FeizhuConstract.IPresenter {
    public FeizhuPresenter(FeizhuConstract.IView view) {
        super(view);
    }
}

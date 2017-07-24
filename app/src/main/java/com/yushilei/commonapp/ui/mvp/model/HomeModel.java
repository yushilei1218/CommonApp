package com.yushilei.commonapp.ui.mvp.model;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Recommend;
import com.yushilei.commonapp.common.bean.net.Type;
import com.yushilei.commonapp.common.item.TypeWrapper;
import com.yushilei.commonapp.common.util.SetUtil;
import com.yushilei.commonapp.ui.mvp.contract.HomeContract;

import java.util.LinkedList;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2017/7/21.
 */

public class HomeModel implements HomeContract.IModel {
    @Override
    public List<ItemWrapper> obtainItems(Recommend recommend) {
        if (recommend == null || SetUtil.isEmpty(recommend.getList()))
            return null;
        List<Type> list = recommend.getList();
        List<ItemWrapper> data = new LinkedList<>();
        for (Type t : list) {
            switch (t.getModuleType()) {
                case "guessYouLike":

                case "categoriesForShort":
                case "categoriesForLong":
                case "categoriesForExplore":
                    data.add(new TypeWrapper(t));
                    break;
            }
        }
        return data;
    }
}

package com.yushilei.commonapp.ui.mvp.model;

import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.bean.net.Data;
import com.yushilei.commonapp.common.bean.net.DiscoveryBean;
import com.yushilei.commonapp.common.bean.net.RecommendBean;
import com.yushilei.commonapp.common.bean.net.Type;
import com.yushilei.commonapp.common.item.Album2Wrapper;
import com.yushilei.commonapp.common.item.BannerWrapper;
import com.yushilei.commonapp.common.item.SquareTypeWrapper;
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
    private int mCurPageId;

    @Override
    public List<ItemWrapper> obtainItems(DiscoveryBean recommend) {
        mCurPageId = 0;
        if (recommend == null || SetUtil.isEmpty(recommend.getList())) {
            return null;
        }
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
                case "focus":
                    data.add(new BannerWrapper(t));
                    break;
                case "square":
                    data.add(new SquareTypeWrapper(t));
                    break;
            }
        }
        return data;
    }

    @Override
    public List<ItemWrapper> obtainAlbums(RecommendBean recommendBean) {
        mCurPageId++;
        if (recommendBean == null || SetUtil.isEmpty(recommendBean.getList())) {
            return null;
        }
        List<ItemWrapper> data = new LinkedList<>();
        for (Data d : recommendBean.getList()) {
            data.add(new Album2Wrapper(d));
        }
        return data;
    }

    @Override
    public int getPageId() {
        return mCurPageId;
    }

    @Override
    public int getPageSize() {
        return 20;
    }
}

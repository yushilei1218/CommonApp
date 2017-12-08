package com.yushilei.commonapp.ui.test;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter.BaseViewHolder;
import com.yushilei.commonapp.common.adapter.ItemWrapper;
import com.yushilei.commonapp.common.adapter.MultiBaseAdapter;
import com.yushilei.commonapp.common.adapter.MultiRecyclerAdapter;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.bean.BeanA;
import com.yushilei.commonapp.common.bean.BeanB;
import com.yushilei.commonapp.common.bean.BeanC;
import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.common.widget.LoadingTextView;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import okhttp3.HttpUrl;

public class TestActivity extends BaseActivity {
    private long num = 1;
    private TextView mView;
    private BigInteger mInteger = new BigInteger("1");

    @Override
    public void initView() {
        mView = (TextView) findView(R.id.num_id_1);
        setOnClick(R.id.num_id_1);
        String a = "{\n" +
                "  \"campus_home\": {\n" +
                "    \"open\": \"true\",\n" +
                "    \"url\": \"https://m.zhaopin.com/next/campus\"\n" +
                "  },\n" +
                "  \"zpdDiscoverHome\": {\n" +
                "    \"open\": \"true\",\n" +
                "    \"url\": \"https://m.zhaopin.com/next/zpd/zpdDiscoverHome\"\n" +
                "  },\n" +
                "  \"appHome\": {\n" +
                "    \"open\": \"true\",\n" +
                "    \"url\": \"https://m.zhaopin.com/next/appHome\"\n" +
                "  },\n" +
                "  \"resume\": {\n" +
                "    \"open\": \"true\",\n" +
                "    \"url\": \"https://m.zhaopin.com/next/c-cp/resume\"\n" +
                "  },\n" +
                "  \"ranking\": {\n" +
                "    \"open\": \"true\",\n" +
                "    \"url\": \"https://m.zhaopin.com/next/c-cp/objectiveRanking\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdQuestionDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdQuestionDetail.weex.9fb934.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdQuestionListCategory\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdQuestionListCategory.weex.23ad59.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/campus/companyDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/campus.companyDetail.weex.d00d2a.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdInviteOthersList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdInviteOthersList.weex.108f2d.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/campus\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/campus.weex.3ca3ed.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/LightenFaceSecrecy\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/LightenFaceSecrecy.weex.9eafbd.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdPublishSuccess\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdPublishSuccess.weex.9d44f9.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareUserPage\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareUserPage.weex.5c453f.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/visitorList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/visitorList.weex.579f0e.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyQuestionList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyQuestionList.weex.0c89cb.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdQuestionAboutCompany\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdQuestionAboutCompany.weex.aff737.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/example\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/example.weex.9bda6c.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareQuestionDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareQuestionDetail.weex.60a01f.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareActivityNext\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareActivityNext.weex.af4ad7.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyNewsList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyNewsList.weex.513534.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/personalHighlight\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/personalHighlight.weex.7201cf.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdAnsTheQueList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdAnsTheQueList.weex.c2fb29.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyFollowList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyFollowList.weex.505aea.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyFansList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyFansList.weex.b96cf3.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/positionViewHistory\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/positionViewHistory.weex.7b4833.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/campus/professionDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/campus.professionDetail.weex.bd3f29.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdPersonHome\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdPersonHome.weex.72b373.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyThumbUpList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyThumbUpList.weex.5da6a8.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/campus/search\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/campus.search.weex.ee3a48.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdAnswerQuestion\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdAnswerQuestion.weex.f7c712.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareCategory\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareCategory.weex.d2afca.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdPeerQuestionList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdPeerQuestionList.weex.a43eda.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/resume\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/resume.weex.398986.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/searchJobResult\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/searchJobResult.weex.c7a5e1.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdDiscoverHome\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdDiscoverHome.weex.deccea.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/appHome\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/appHome.weex.304f11.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/royLearn\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/royLearn.weex.0e65f9.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdHomeHotList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdHomeHotList.weex.db17dc.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareActivityLanding\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareActivityLanding.weex.e10b18.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdContactList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdContactList.weex.84d3fc.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareLandingPage\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareLandingPage.weex.13635b.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/companyDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/companyDetail.weex.fff1a1.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/testSlider\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/testSlider.weex.3e8e32.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/objectiveRanking\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/objectiveRanking.weex.665f72.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/c-cp/competiveness\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/c-cp/competiveness.weex.c12dca.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdTipOff\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdTipOff.weex.bce60d.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyCollectList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyCollectList.weex.ff4755.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdShareAnswerDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdShareAnswerDetail.weex.a17820.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdAnswerDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdAnswerDetail.weex.4c3267.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/campus_debug\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/campus.weex.2.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdMyAnswerList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdMyAnswerList.weex.7f9306.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/discover\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/discover.weex.b5b292.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdFeaturedQueList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdFeaturedQueList.weex.85f407.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdConfirmPublish\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdConfirmPublish.weex.2fe9f8.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/professionDetail\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/professionDetail.weex.5fc1c8.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdNewestQuestionList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdNewestQuestionList.weex.f2bf5a.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdCommentPage\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdCommentPage.weex.a54839.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdAskQuestion\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdAskQuestion.weex.485807.js\"\n" +
                "  },\n" +
                "  \"m.zhaopin.com/next/zpd/zpdSearchQuestionList\": {\n" +
                "    \"weex\": \"//c-m-bucket.zhaopin.cn/next/zpd/zpdSearchQuestionList.weex.433ef4.js\"\n" +
                "  }\n" +
                "}";
        Bean obj = JsonUtil.getObj(a, Bean.class);
        String s = obj.toString();
        Bean bean = JSON.parseObject(a, new TypeReference<Bean>() {
        }, Feature.InternFieldNames);
        String s1 = bean.toString();
        Bean bean1 = JSON.toJavaObject(JSON.parseObject(a), Bean.class);
        String s2 = bean1.toString();


        String url = "https://workflowy.com/s/FJKy.9G6R7GvtHw?key=111&key2=text";
        URL tag = null;
        try {
            tag = new URL(url);
            String query = tag.getQuery();
            int port = tag.getPort();
            String ref = tag.getRef();
            String authority = tag.getAuthority();
            int defaultPort = tag.getDefaultPort();
            String host = tag.getHost();
            String protocol = tag.getProtocol();
            String userInfo = tag.getUserInfo();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num_id_1:
                mInteger = mInteger.multiply(new BigInteger("10"));
                num = num * 10;
                String s = mInteger.toString();

                mView.setText(s);
                break;
        }
    }
}

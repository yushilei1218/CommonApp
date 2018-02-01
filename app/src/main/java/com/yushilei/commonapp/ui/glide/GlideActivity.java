package com.yushilei.commonapp.ui.glide;


import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class GlideActivity extends BaseActivity {

    private static final String DOU_TU_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517475444204&di=9a8f9a670ba4570f43589b5bcadeb5d2&imgtype=0&src=http%3A%2F%2Fimg0.pconline.com.cn%2Fpconline%2F1611%2F28%2F8583622_0_thumb.jpg";
    private static final String GIF = "http://img.weixinyidu.com/160118/e7c47570.jpg";

    private ImageView mImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void initView() {
        mImg = findView(R.id.act_glide_img);
        setOnClick(R.id.act_glide_btn);
        setOnClick(R.id.act_glide_btn2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_glide_btn:
                Glide.with(this)
                        .load(DOU_TU_URL)
                        .placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.errorholder)
                        .into(mImg);
                break;
            case R.id.act_glide_btn2:
                Glide.with(this)
                        .load(GIF)
                        .asGif()
                        .placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.errorholder)
                        .into(mImg);
                break;
            default:
                break;
        }
    }
}

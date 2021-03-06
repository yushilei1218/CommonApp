package com.yushilei.commonapp.ui.glide;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jakewharton.disklrucache.DiskLruCache;
import com.shileiyu.imageloader.ImageHunter;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GlideActivity extends BaseActivity {

    private static final String DOU_TU_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517475444204&di=9a8f9a670ba4570f43589b5bcadeb5d2&imgtype=0&src=http%3A%2F%2Fimg0.pconline.com.cn%2Fpconline%2F1611%2F28%2F8583622_0_thumb.jpg";
    private static final String GIF = "http://img.weixinyidu.com/160118/e7c47570.jpg";

    @BindView(R.id.act_glide_btn)
    Button mActGlideBtn;
    @BindView(R.id.act_glide_btn2)
    Button mActGlideBtn2;
    @BindView(R.id.act_glide_btn3)
    Button mActGlideBtn3;
    @BindView(R.id.act_glide_btn4)
    Button mActGlideBtn4;
    @BindView(R.id.act_glide_btn5)
    Button mActGlideBtn5;
    @BindView(R.id.act_glide_img)
    ImageView mImg;


    private DiskLruCache mOpen;

    private static final String M_KEY = "19456141555";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void initView() {
        File dir = new File(Environment.getExternalStorageDirectory(), "lru");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            mOpen = DiskLruCache.open(dir, 1, 1, 1024 * 1024 * 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void imageHunter() {
        String url = "http://c.hiphotos.baidu.com/image/pic/item/0b55b319ebc4b745b19f82c1c4fc1e178b8215d9.jpg";
        ImageHunter.with(this).url(url)
                .override(100, 100)
                .errorHolder(R.mipmap.ic_clock)
                .pleaceHolder(R.mipmap.ic_head)
                .build().into(mImg);
    }

    private void loadDiskLru() {
        try {
            InputStream inputStream = mOpen.get(M_KEY).getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            mImg.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cacheDiskLru() {
        try {

            DiskLruCache.Editor edit = mOpen.edit(M_KEY);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.marker);
            OutputStream stream = edit.newOutputStream(0);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @OnClick({R.id.act_glide_btn, R.id.act_glide_btn2, R.id.act_glide_btn3, R.id.act_glide_btn4, R.id.act_glide_btn5, R.id.act_glide_img})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.act_glide_btn3:
                cacheDiskLru();
                break;
            case R.id.act_glide_btn4:
                loadDiskLru();
                break;
            case R.id.act_glide_btn5:
                imageHunter();
                break;
            default:
                break;
        }
    }
}

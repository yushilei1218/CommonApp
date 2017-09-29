package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.util.SetUtil;

import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/9/29
 */

public class QuickSelectView extends View {
    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private List<Tag> mTags;
    private Tag mCurTag;

    private OnTagHighLightListener mHighLightListener;

    public QuickSelectView(Context context) {
        this(context, null);
    }

    public QuickSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (SetUtil.isEmpty(mTags)) return;
        float height = getHeight();
        float offset = height / mTags.size();
        int width = getWidth();
        for (int i = 0; i < mTags.size(); i++) {
            Tag tag = mTags.get(i);
            if (tag.isHighLight) {
                mTextPaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
            } else {
                mTextPaint.setColor(getContext().getResources().getColor(R.color.black));
            }
            canvas.drawText(tag.name, width / 2f, i * offset + offset / 2f, mTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (SetUtil.isEmpty(mTags)) return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Tag hitTag = getHitTag(event);
                hitTag.isHighLight = true;
                boolean isNeedCallBack = false;
                if (mCurTag == null) {
                    mCurTag = hitTag;
                    isNeedCallBack = true;
                } else {
                    if (mCurTag != hitTag) {
                        mCurTag.isHighLight = false;
                        mCurTag = hitTag;
                        isNeedCallBack = true;
                    }
                }
                if (isNeedCallBack) {
                    invalidate();
                    if (mHighLightListener != null) {
                        mHighLightListener.onTagSelected(mCurTag);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mCurTag != null) {
                    mCurTag.isHighLight = false;
                    invalidate();
                }
                if (mHighLightListener != null)
                    mHighLightListener.onTagFinish();
                break;
        }
        return true;
    }

    private Tag getHitTag(MotionEvent event) {
        int y = (int) event.getY();
        int height = getHeight();
        int offset = height / mTags.size();
        int pos = y / offset;
        if (pos < 0) pos = 0;
        if (pos > mTags.size() - 1) pos = mTags.size() - 1;
        return mTags.get(pos);
    }

    public void setHighLightListener(OnTagHighLightListener highLightListener) {
        mHighLightListener = highLightListener;
    }

    public void setTags(List<Tag> tags) {
        mTags = tags;
        postInvalidate();
    }

    public interface OnTagHighLightListener {
        void onTagSelected(Tag tag);

        void onTagFinish();
    }

    public static final class Tag {
        String name;
        boolean isHighLight;
        Object tag;

        public Tag(String name) {
            this.name = name;
        }

        public Tag(String name, Object tag) {
            this.name = name;
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isHighLight() {
            return isHighLight;
        }

        public void setHighLight(boolean highLight) {
            isHighLight = highLight;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }
    }
}

package com.yushilei.commonapp.common.widget.drop;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by huangjun on 2016/9/13.
 */
public class DropFake extends AppCompatTextView {

    public interface ITouchListener {
        void onDown();

        void onMove(float curX, float curY);

        void onUp();
    }


    private ITouchListener touchListener;

    public DropFake(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (DropManager.getInstance().isTouchable()) {
                    if (touchListener != null) {
                        DropManager.getInstance().setTouchable(false);
                        // 不允许父控件处理TouchEvent，当父控件为ListView这种本身可滑动的控件时必须要控制
                        getParent().requestDisallowInterceptTouchEvent(true);
                        touchListener.onDown();
                    }
                    return true; // eat
                }

                return false;
            case MotionEvent.ACTION_MOVE:
                if (touchListener != null) {
                    // getRaw:获取手指当前所处的相对整个屏幕的坐标
                    touchListener.onMove(event.getRawX(), event.getRawY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (touchListener != null) {
                    // 将控制权还给父控件
                    getParent().requestDisallowInterceptTouchEvent(false);
                    touchListener.onUp();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setClickListener(ITouchListener listener) {
        touchListener = listener;
    }
}

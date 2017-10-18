package com.yushilei.commonapp.common.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.yushilei.commonapp.R;

/**
 * @author shilei.yu
 * @since on 2017/7/14.
 */

public class BezierView extends View implements Animation.AnimationListener {
    private static final String TAG = "BezierView";

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * target坐标
     */
    private PointF targetPoint = new PointF();
    /**
     * touch点坐标
     */
    private PointF touchPoint = new PointF();
    /**
     * target坐标 与 touch点坐标 连线的中间点坐标 也就是control点
     */
    private PointF controlPoint = new PointF();

    /**
     * Control点与2圆的4个交点
     */
    private Point focusPointA = new Point();
    private Point focusPointB = new Point();
    private Point focusPointC = new Point();
    private Point focusPointD = new Point();
    /**
     * 目标圆半径，也是touch圆半径
     */
    private int mR;
    /**
     * 是否处于被拖拽状态
     */
    private boolean isDrag = false;
    /**
     * 是否分割目标和touch圆
     */
    private boolean isSplit = false;
    /**
     * 状态：是否清除内容
     */
    private boolean isDiscard = false;
    /**
     * 是否处于直线动画执行状态
     */
    private boolean isAnimating = false;
    /**
     * 可触发Drag的touch区域
     */
    private RectF mDragRange;
    /**
     * 作为4个交点 及control点 的闭合path
     */
    Path mPath = new Path();
    /**
     * control点 和目标中心点 距离；也是是否需要DrawPath的边界值
     * 当该距离大于mR 意味着目标圆 和touch的圆无交点需要绘制中间区域
     */
    private double needDrawClosePathDistance;
    /**
     * 可绘制的文本
     */
    private String text = "99+";

    TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 文本偏移量 （使得文本居中时需要）
     */
    private int mTextOffset;
    /**
     * 分离指数，这里以mR的为基础 ，needDrawClosePathDistance 大于  mR* mSplitRate 这2圆分离，也就是被拽开的状态
     */
    private static final float mSplitRate = 2;
    /**
     * 最大完全拽开距离
     * mSplitDistance = mSplitRate * mR;
     */
    private double mSplitDistance;
    private int touchSlop;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration config = ViewConfiguration.get(context);

        touchSlop = config.getScaledTouchSlop();
        //设置画笔
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        float dimension = context.getResources().getDimension(R.dimen.textSize);
        textPaint.setTextSize(dimension);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        //计算文本偏移
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        mTextOffset = (Math.abs(fontMetricsInt.descent) - Math.abs(fontMetricsInt.ascent)) / 2;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //已视图中心点为target点
        targetPoint.x = getWidth() / 2;
        targetPoint.y = getHeight() / 2;
        //圆半径
        mR = Math.min(getWidth(), getHeight()) / 10;
        //可触发拖拽的区域
        mDragRange = new RectF(targetPoint.x - mR, targetPoint.y - mR, targetPoint.x + mR, targetPoint.y + mR);
        //最大完全拽开距离
        mSplitDistance = mSplitRate * mR;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isDiscard) {
            if (!isSplit) {
                canvas.drawCircle(targetPoint.x, targetPoint.y, mR, paint);
            }
            if (isDrag) {
                canvas.drawCircle(touchPoint.x, touchPoint.y, mR, paint);
                if (!isSplit && needDrawClosePathDistance > mR) {
                    mPath.reset();
                    mPath.moveTo(focusPointA.x, focusPointA.y);
                    mPath.quadTo(controlPoint.x, controlPoint.y, focusPointC.x, focusPointC.y);
                    mPath.lineTo(focusPointD.x, focusPointD.y);
                    mPath.quadTo(controlPoint.x, controlPoint.y, focusPointB.x, focusPointB.y);
                    mPath.close();
                    canvas.drawPath(mPath, paint);
                }
                canvas.drawText(text, touchPoint.x, touchPoint.y - mTextOffset, textPaint);
            }
            if (!isSplit) {
                if (!TextUtils.isEmpty(text)) {
                    canvas.drawText(text, targetPoint.x, targetPoint.y - mTextOffset, textPaint);
                }
            }
        }
    }

    private float mLastX;
    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.i(TAG, "x=" + x + " ;y=" + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimating) {//是否处于动画执行状态
                    clearAnimation();//移除动画
                }
                //判断Down点是否在可拖拽范围内
                if (mDragRange.contains((int) x, (int) y)) {
                    //设置状态 并初始touch点
                    isDrag = true;
                    touchPoint.x = (int) x;
                    touchPoint.y = (int) y;
                    computeControl();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                check(x, y);
                if (requestDisallow) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                //在拖拽的情况下 对touch点 ，及4个交点
                if (isDrag) {
                    touchPoint.x = (int) x;
                    touchPoint.y = (int) y;
                    computeControl();
                }
                break;
            case MotionEvent.ACTION_UP: {
                requestDisallow = false;
                //释放时，判断已经处于完全被拽开的状态
                isDiscard = needDrawClosePathDistance >= mSplitDistance;
                //重置状态
                isDrag = false;
                isSplit = false;
                if (!isDiscard) {
                    //如果尚未被完全拽开，那么松开时开启一个弹跳的动画
                    //target点与touch点所在的直线方程 计算直线方程，计算直角三角形AB边
                    float[] xyArr = computeRightTriangleAB(targetPoint, touchPoint);
                    if (xyArr != null) {
                        //实现一个沿着 target点与touch点连线方向的直线动画效果
                        TranslateAnimation animation = new TranslateAnimation(xyArr[0], -xyArr[0], xyArr[1], -xyArr[1]);
                        animation.setInterpolator(new AccelerateDecelerateInterpolator());
                        animation.setRepeatMode(Animation.REVERSE);
                        animation.setRepeatCount(4);
                        animation.setDuration(60);
                        animation.setAnimationListener(this);
                        BezierView.this.startAnimation(animation);
                    }
                } else {
                    if (listener != null) {
                        listener.onDropComplete();
                    }
                }
                invalidate();
            }
            break;
        }
        mLastX = x;
        mLastY = y;
        //当处于Drag时 刷新视图即可
        if (isDrag) {
            invalidate();
        }
        return true;
    }

    private boolean requestDisallow = false;

    private void check(float x, float y) {
        if (requestDisallow) {
            return;
        }
        float offset = Math.abs(x - mLastX);
        if (offset > Math.abs(y - mLastY) && offset >= touchSlop) {
            requestDisallow = true;
        }
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    /**
     * 直线动画以中心点向一个方向运动的距离
     */
    private static final float TranslateAniLimited = 5;

    /**
     * 计算2点的斜率， 以TranslateAniLimited 为 C边计算直角山角形 A B边
     *
     * @return 返回的就是直角山角形AB 边
     */
    private float[] computeRightTriangleAB(PointF point1, PointF point2) {
        if (point1.x == point2.x && point1.y == point2.y) {
            return null;
        }
        float[] data = new float[2];
        if (point1.x == point2.x) {
            data[0] = 0;
            data[1] = TranslateAniLimited;
            return data;
        }
        if (point1.y == point2.y) {
            data[0] = TranslateAniLimited;
            data[1] = 0;
            return data;
        }

        float k = (point2.y - point1.y) / (point2.x - point1.x);
        float bEdge = (float) (TranslateAniLimited / (Math.sqrt(1 + k * k)));
        float aEdge = bEdge * k;
        data[0] = bEdge;
        data[1] = aEdge;

        Log.i("测试", "k=" + k + " x =" + data[0] + " y=" + data[1]);
        return data;
    }

    /**
     * 根据target 和touch点计算 control点
     * 计算needDrawClosePathDistance
     */
    public void computeControl() {
        controlPoint.x = (targetPoint.x + touchPoint.x) / 2;
        controlPoint.y = (targetPoint.y + touchPoint.y) / 2;
        needDrawClosePathDistance = Math.sqrt(Math.pow(targetPoint.x - controlPoint.x, 2) + Math.pow(targetPoint.y - controlPoint.y, 2));

        if (needDrawClosePathDistance >= mSplitDistance) {
            isSplit = true;
        }
        if (isSplit) {
            return;
        }
        double R2 = Math.sqrt(Math.pow(targetPoint.x - controlPoint.x, 2) + Math.pow(targetPoint.y - controlPoint.y, 2) - mR * mR);

        //3个圆
        double[] doubles = new CircleUtil(new Circle(targetPoint.x, targetPoint.y, mR)
                , new Circle(controlPoint.x, controlPoint.y, R2)).intersect();
        if (doubles != null && doubles.length == 4) {
            focusPointA.x = (int) doubles[0];
            focusPointA.y = (int) doubles[1];
            focusPointB.x = (int) doubles[2];
            focusPointB.y = (int) doubles[3];
        }

        double[] doubles2 = new CircleUtil(new Circle(touchPoint.x, touchPoint.y, mR)
                , new Circle(controlPoint.x, controlPoint.y, R2)).intersect();
        if (doubles2 != null && doubles2.length == 4) {
            focusPointC.x = (int) doubles2[0];
            focusPointC.y = (int) doubles2[1];
            focusPointD.x = (int) doubles2[2];
            focusPointD.y = (int) doubles2[3];
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isAnimating = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isAnimating = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private DropCompleteListener listener;

    public void setListener(DropCompleteListener listener) {
        this.listener = listener;
    }

    public interface DropCompleteListener {
        void onDropComplete();
    }
}


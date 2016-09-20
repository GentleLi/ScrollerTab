package com.ljt.scrollertab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by 李建涛 on 2016/9/19.
 */
public class XScrollerTab extends ViewGroup {
    /**
     * 滚动工具Scroller
     */
    private Scroller mScroller;
    /**
     * 判定为拖动的最小移动距离
     */
    private int mTouchSlop;
    private int mLeftBorder;
    private int mRightBorder;
    private float mDownX;
    private float mMoveX;
    private float mLastMoveX;

    public XScrollerTab(Context context) {
        this(context, null);
    }

    public XScrollerTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XScrollerTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        ViewConfiguration config = ViewConfiguration.get(context);
        /**获取view滑动翻页的最小移动距离*/
        mTouchSlop = config.getScaledTouchSlop();
        Log.d("TAG", "init: mTouchSlop ==" + mTouchSlop);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void onLayout(boolean flag, int l, int t, int r, int b) {
        Log.i("TAG", "*********onLayout**********\n left:" + l + " top:" + t + " right:" + r + " bottom:" + b);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(i * child.getMeasuredWidth(), 0, (i + 1) * getMeasuredWidth(), child.getMeasuredHeight());
        }
        mLeftBorder = getChildAt(0).getLeft();
        mRightBorder = getChildAt(childCount - 1).getRight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                Log.i("TAG", "XScrollerTab_onInterceptTouchEvent mDownX==" + mDownX);
                mLastMoveX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getRawX();
                Log.i("TAG", "XScrollerTab_onInterceptTouchEvent mMoveX==" + mMoveX);
                float disX = Math.abs(mMoveX - mDownX);
                mLastMoveX = mMoveX;
                if (disX > 10) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "XScrollerTab_onTouchEvent");
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getRawX();
                Log.i("TAG", "XScrollerTab_onTouchEvent mMoveX==" + mMoveX);
                int scrolledX = (int) (mLastMoveX - mMoveX);
                Log.i("TAG", "getScrollX()==" + getScrollX());
                if (getScrollX() + scrolledX < mLeftBorder) {
                    scrollTo(mLeftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > mRightBorder) {
                    scrollTo(mRightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mLastMoveX = mMoveX;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}

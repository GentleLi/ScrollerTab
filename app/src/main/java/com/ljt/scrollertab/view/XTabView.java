package com.ljt.scrollertab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.ljt.scrollertab.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李建涛 on 2016/9/19.
 */
public class XTabView extends LinearLayout {

    private Context mContext;
    private Scroller mScroller;
    private int mTouchSlop;
    private int mLeftBorder;
    private int mRightBorder;
    private float mLastMoveX;
    private float mDownX;
    private float mMoveX;
    private List<String> mTitles = new ArrayList<>();
    private List<Button> mListText = new ArrayList<>();
    private int mScreenWidth;
    private int mScreenHeight;
    private int mItemWidth;
    private int mItemHeight;

    public XTabView(Context context) {
        this(context, null);
    }

    public XTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);


    }

    private void init(Context context) {
        removeAllViews();
        mContext = context;
        mScroller = new Scroller(mContext);
        ViewConfiguration config = ViewConfiguration.get(mContext);
        mTouchSlop = config.getScaledTouchSlop();
        int[] screenWidthAndHeight = AppUtils.getScreenWidthAndHeight(mContext);
        mScreenWidth = screenWidthAndHeight[0];
        mScreenHeight = screenWidthAndHeight[1];
        initView();
    }

    /**
     * 设置指示器的titles
     *
     * @param titles
     */
    public void setTabTitles(List<String> titles) {
        this.mTitles = titles;
    }

    private void initView() {
        /*if (mTitles.size() == 0) {
            setEmptyView();
            return;
        }*/
        for (int i = 0; i < 30; i++) {
            Button btn = new Button(mContext);
            mItemWidth = mScreenWidth / 3;
            mItemHeight = AppUtils.dip2px(mContext, 500);
            setParamsForChildView(btn, mItemWidth, mItemHeight);
            btn.setText("测试文字" + i);
            mListText.add(btn);
            btn.setEnabled(false);
            btn.setBackgroundColor(Color.parseColor("#00000000"));
            addView(btn);

        }
    }

    /**
     * 设置每一个条目的宽度和高度
     */
    private void setParamsForChildView(TextView text, int mItemWidth, int mItemHeight) {
        text.setLayoutParams(new LayoutParams(mItemWidth, mItemHeight));
    }

    /**
     * 没有标题的情况
     */
    private void setEmptyView() {
        //TODO 李建涛 设置无title的view
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        Log.i("TAG", "sizeWidth==" + sizeWidth);
        Log.i("TAG", "sizeHeight==" + sizeHeight);
        Log.i("TAG", "modeWidth==" + modeWidth);
        Log.i("TAG", "modeHeight==" + modeHeight);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
    }

    @Override
    protected void onLayout(boolean bln, int l, int t, int r, int b) {

        Log.i("TAG", "*********onLayout**********\n left:" + l + " top:" + t + " right:" + r + " bottom:" + b);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout((i + 1) * child.getMeasuredWidth(), 0, (i + 2) * getMeasuredWidth(), child.getMeasuredHeight());
        }
        Log.d("TAG", "child.getMeasuredWidth()==" + getChildAt(0).getMeasuredWidth());
        mLeftBorder = getChildAt(0).getLeft() - mItemWidth;
        mRightBorder = (childCount + 2) * mItemWidth;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                Log.i("TAG", "onInterceptTouchEvent mDownX ==" + mDownX);
                mLastMoveX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getRawX();
                Log.i("TAG", "onInterceptTouchEvent mMoveX ==" + mMoveX);
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
                Log.i("TAG", "XTabView_onTouchEvent");
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getRawX();
                Log.i("TAG", "XTabView_onTouchEvent mMoveX==" + mMoveX);
                int scrolledX = (int) (mLastMoveX - mMoveX);
                Log.i("TAG", "getScrollX()==" + getScrollX());
                Log.i("TAG", "scrolledX==" + scrolledX);
                Log.i("TAG", "mItemWidth==" + mItemWidth);
                Log.i("TAG", "mItemWidth+scrolledX+getScrollX() ==" + (mItemWidth + scrolledX + getScrollX()));
                Log.i("TAG", "mRightBorder==" + mRightBorder);
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
                int targetIndex = (getScrollX() + mItemWidth / 2) / mItemWidth;
                int dx = targetIndex * mItemWidth - getScrollX();
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

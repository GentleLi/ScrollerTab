package com.ljt.scrollertab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by 李建涛 on 2016/9/19.
 */
public class XTextView extends TextView {

    private Context mContext;
    private Paint mPaint;

    public XTextView(Context context) {
        this(context, null);
    }

    public XTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float mDownX = event.getX();
                Log.i("TAG", "XTextView_onTouchEvent mDownX==" + mDownX);
                break;
            case MotionEvent.ACTION_MOVE:
                float mMoveX = event.getX();
                Log.i("TAG", "XTextView_onTouchEvent mMoveX==" + mMoveX);
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}

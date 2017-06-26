package com.zphuan.animationproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zphuan.animationproject.MyApplication;
import com.zphuan.animationproject.R;

/**
 * Created by PeiHuan on 2017/6/26.
 * 卫星菜单控件
 */
public class ArcMenu extends ViewGroup {
    private static final String TAG = "ArcMenu";
    private int height;

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = h;
        Log.i(TAG, "onSizeChanged: height - " + height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.measure(0, 0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 此数据为每个子视图距离第一个控件的距离
     */
    private static final int RADIUS = (int) MyApplication.getContext().getResources().getDimension(R.dimen.ArcMenu_Radius);

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child0 = getChildAt(0);
//        Log.i(TAG, "onLayout: child0.getMeasuredHeight()-"+child0.getMeasuredHeight());
        //将第一个控件摆放在左下角
        child0.layout(0, height - child0.getMeasuredHeight(), child0.getMeasuredWidth(), height);
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            double angle = Math.PI / 2 / (count - 2) * i;
            Log.i(TAG, "angle:"+angle);
            View child = getChildAt(i + 1);
            int left = (int) (child0.getLeft() + RADIUS * Math.sin(angle));
            int top = (int) (child0.getTop() - RADIUS * Math.cos(angle));
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            Log.i(TAG, "l,t,r,t:"+left+","+top+","+right+","+bottom);
            child.layout(left, top, right, bottom);
        }
    }
}

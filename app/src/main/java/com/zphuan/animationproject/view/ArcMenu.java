package com.zphuan.animationproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.zphuan.animationproject.MyApplication;
import com.zphuan.animationproject.R;

/**
 * Created by PeiHuan on 2017/6/26.
 * 卫星菜单控件
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "ArcMenu";
    private int height;
    private View child0;

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
        child0 = getChildAt(0);
        child0.setOnClickListener(this);
        //将第一个控件摆放在左下角
        child0.layout(0, height - child0.getMeasuredHeight(), child0.getMeasuredWidth(), height);
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            double angle = Math.PI / 2 / (count - 2) * i;
            View child = getChildAt(i + 1);
            int left = (int) (child0.getLeft() + RADIUS * Math.sin(angle));
            int top = (int) (child0.getTop() - RADIUS * Math.cos(angle));
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
            child.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        //1.旋转自身
        rotateChild0(v);
        //2.执行其他子视图的动画
        animateOtherChild();
    }

    private void animateOtherChild() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            AnimationSet as = new AnimationSet(true);
            final View child = getChildAt(i + 1);
            child.setVisibility(View.VISIBLE);
            int left = child.getLeft();
            TranslateAnimation ta;
            if(status==CurrentStatus.CLOSE) {
                 ta = new TranslateAnimation(
                        Animation.ABSOLUTE, -left,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, child0.getBottom() - child.getBottom(), Animation.ABSOLUTE, 0);
            }else{
                ta = new TranslateAnimation(
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, -left,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, child0.getBottom() - child.getBottom());
            }
            ta.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i(TAG, "onAnimationEnd");
                    if(status==CurrentStatus.CLOSE){
                        child.clearAnimation();
                        child.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ta.setStartOffset(200*i);
            ta.setDuration(2000);
            ta.setInterpolator(new OvershootInterpolator());
            RotateAnimation ra = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ra.setDuration(2000);
            as.addAnimation(ra);
            as.addAnimation(ta);
            as.setFillAfter(true);
            child.startAnimation(as);
        }
        changeCurrentStatus();
    }

    private void rotateChild0(View v) {
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(1000);
        v.startAnimation(ra);
    }

    private CurrentStatus status = CurrentStatus.CLOSE;

    //定义卫星菜单打开和关闭的状态
    public enum CurrentStatus {
        OPEN, CLOSE
    }

    private void changeCurrentStatus() {
        status = status == CurrentStatus.CLOSE ? CurrentStatus.OPEN : CurrentStatus.CLOSE;
    }
}

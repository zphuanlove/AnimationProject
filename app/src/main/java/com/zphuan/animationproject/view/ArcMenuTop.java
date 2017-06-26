package com.zphuan.animationproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by PeiHuan on 2017/6/26.
 */

public class ArcMenuTop extends ViewGroup implements View.OnClickListener {
    private View child0;

    public ArcMenuTop(Context context) {
        super(context);
    }

    public ArcMenuTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcMenuTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//在ViewGroup中默认是不测量孩子视图的

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量孩子视图
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(0, 0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float radius = 130f;

    //除第一个子视图外的其余子视图成弧形排列
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        child0 = getChildAt(0);
        child0.layout(0, 0, child0.getMeasuredWidth(), child0.getMeasuredHeight());
        child0.setOnClickListener(this);
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            View child = getChildAt(i + 1);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            float a = (float) (Math.PI / 2 / 4 * i);
            int childLeft = (int) (radius * Math.sin(a));
            int childTop = (int) (radius * Math.cos(a));
            int childRight = childLeft + childWidth;
            int childBottom = childTop + childHeight;

            child.layout(childLeft, childTop, childRight, childBottom);
            child.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
                rotateChild0();
                animateOtherChildren();
    }

    //子视图的动画效果处理
    //1,第0个子视图的旋转
    //2,其余子视图的自身旋转
    //3,其余子视图的平移
    private void animateOtherChildren() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View child = getChildAt(i + 1);
            child.setVisibility(View.VISIBLE);
            int childLeft = child.getLeft();
            int childTop = child.getTop();
            AnimationSet as = new AnimationSet(true);
            //移动的类型包含:ABSOULTE,RELATIVE_TO_SELF,RELATIVE_TO_PARENT
            //参数1,3:x方向的移动的类型
            //参数2,4:x方向从2到4
            //参数5,7:y方向的移动的类型
            //参数6,8:y方向从6到8
            TranslateAnimation ta = null;
            if (status == CurrentStatus.CLOSE) {
                ta = new TranslateAnimation(TranslateAnimation.ABSOLUTE, -childLeft, TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, -childTop, TranslateAnimation.ABSOLUTE, 0);
            } else {
                ta = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, -childLeft, TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, -childTop);
            }
            ta.setDuration(1500);
            //当平移完成后,再次切换状态
            ta.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                //当动画接收后的处理
                @Override
                public void onAnimationEnd(Animation animation) {
                    if(status==CurrentStatus.CLOSE){
                        child.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            //平移动画是一个一个先后执行的
            ta.setStartOffset(i * 300);
            ta.setFillAfter(true);
            //动画插入器,可以改变动画的执行效果
            //平移到终点后,超出一端距离再回到终点
            ta.setInterpolator(new OvershootInterpolator());

            RotateAnimation ra = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            ra.setDuration(3000);
            ra.setFillAfter(true);
            as.addAnimation(ra);
            as.addAnimation(ta);


            child.startAnimation(as);
        }
        changeCurrentStatus();
    }

    private void changeCurrentStatus() {
        status = status == CurrentStatus.CLOSE ? CurrentStatus.OPEN : CurrentStatus.CLOSE;
    }

    private void rotateChild0() {
        RotateAnimation ra = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);
        child0.startAnimation(ra);
    }

    private CurrentStatus status = CurrentStatus.CLOSE;

    //定义卫星菜单打开和关闭的状态
    public enum CurrentStatus {
        OPEN, CLOSE;
    }
}

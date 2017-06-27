package com.zphuan.animationproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by PeiHuan on 2017/6/27.
 */
public class Ball extends View {
    private int w;
    private int h;
    private Paint paint;
    private int length;

    public Ball(Context context) {
        this(context,null);
    }

    public Ball(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Ball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
        length =  Math.min(w, h);
        prepareAnimators();
    }
    private float radius=5;
    private int alpha=255;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha(alpha);
        canvas.drawCircle(w/2,h/2,radius,paint);
    }

    private void prepareAnimators(){
        //值动画:可以处理一个值到另一个值的变化
        ValueAnimator va1=ValueAnimator.ofFloat(5,length/2-5);
        va1.setDuration(2000);
        //动画无限执行
        va1.setRepeatCount(ValueAnimator.INFINITE);
        va1.start();
        //添加动画改变过程中的更新监听
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius= (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        ValueAnimator va2=ValueAnimator.ofInt(255,0);
        va2.setDuration(2000);
        va2.setRepeatCount(ValueAnimator.INFINITE);
        va2.start();
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }
}

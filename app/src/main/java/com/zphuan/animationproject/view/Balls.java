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
public class Balls extends View {
    private int w;
    private int h;
    private Paint paint;
    private int length;

    public Balls(Context context) {
        this(context, null);
    }

    public Balls(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Balls(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        length = Math.min(w, h);
        prepareAnimators();
    }

    private float[] radius = new float[]{5,5,5};
    private int[] alpha = new int[]{255,255,255};
    private int[] colors=new int[]{Color.RED,Color.GREEN,Color.BLUE};
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 3; i++) {
            paint.setAlpha(alpha[i]);
            paint.setColor(colors[i]);
            canvas.drawCircle(w / 2, h / 2, radius[i], paint);
        }

    }
    private long[] delays=new long[]{0,200,400};
    private void prepareAnimators() {
        for (int i = 0; i < 3; i++) {
            ValueAnimator va = ValueAnimator.ofFloat(5, length / 2 - 5);
            va.setDuration(1000);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.setStartDelay(delays[i]);
            va.start();
            final int index=i;
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    radius[index] = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            va=ValueAnimator.ofInt(255,0);
            va.setDuration(1000);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.setStartDelay(delays[i]);
            va.start();
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha[index]= (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }
    }
}

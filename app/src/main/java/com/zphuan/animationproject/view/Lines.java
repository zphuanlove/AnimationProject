package com.zphuan.animationproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by PeiHuan on 2017/6/27.
 */

public class Lines extends View {
    private Paint paint;

    public Lines(Context context) {
        this(context, null);
    }

    public Lines(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Lines(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        prepareAnimators();
    }

    private float[] scales = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth() / 11;
        int y = getHeight() / 2;
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.translate((float) ((1.5 + i * 2) * x), y);
            //利用画布缩放来实现视图的高度变化
            canvas.scale(1.0f, scales[i]);
            RectF rectF = new RectF(-x / 2, -y / 2, x / 2, y / 2);
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.restore();
        }
    }
    //效果1:左到右依次放大的效果
//	private void prepareAnimators(){
//		long[] delays=new long[]{0,100,200,300,400};
//		for (int i = 0; i < 5; i++) {
//			ValueAnimator va=ValueAnimator.ofFloat(1,0.5f);
//			va.setDuration(600);
//            va.setRepeatCount(ValueAnimator.INFINITE);
//            va.setRepeatMode(ValueAnimator.REVERSE);
//            va.setStartDelay(delays[i]);
//            va.start();
//			final int index=i;
//			va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//				@Override
//				public void onAnimationUpdate(ValueAnimator animation) {
//					scales[index]= (float) animation.getAnimatedValue();
//					invalidate();
//				}
//			});
//		}
//	}
    //效果2
	private void prepareAnimators(){
		long[] delays=new long[]{400,200,0,200,400};
		for (int i = 0; i < 5; i++) {
			ValueAnimator va=ValueAnimator.ofFloat(1,0.5f);
			va.setDuration(600);
			va.setRepeatCount(ValueAnimator.INFINITE);
			va.setRepeatMode(ValueAnimator.REVERSE);
			va.setStartDelay(delays[i]);
			va.start();
			final int index=i;
			va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					scales[index]= (float) animation.getAnimatedValue();
					invalidate();
				}
			});
		}
	}
    //卡拉ok效果

//    private void prepareAnimators() {
//        //修改动画执行一次的时间
//        long[] durations = new long[]{1260, 430, 1010, 730, 500};
//        //修改动画执行的延迟时间
//        long[] delays = new long[]{770, 290, 280, 740, 500};
//        for (int i = 0; i < 5; i++) {
//            ValueAnimator va = ValueAnimator.ofFloat(1, 0.5f);
//            va.setDuration(durations[i]);
//            va.setRepeatCount(ValueAnimator.INFINITE);
//            va.setRepeatMode(ValueAnimator.REVERSE);
//            va.setStartDelay(delays[i]);
//            va.start();
//            final int index = i;
//            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    scales[index] = (float) animation.getAnimatedValue();
//                    invalidate();
//                }
//            });
//        }
//    }
}

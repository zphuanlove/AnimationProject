package com.zphuan.animationproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by PeiHuan on 2017/6/24.
 * <p>
 * Music控件
 */
public class MusicView extends View {
    private Paint paint;
    private int length;

    public MusicView(Context context) {
        this(context, null);
    }

    public MusicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        paint = new Paint();
        //画笔颜色
        paint.setColor(Color.BLACK);
        //画笔粗细
        paint.setStrokeWidth(2);
        //抗锯齿
        paint.setAntiAlias(true);
        //不填充
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        length = Math.min(w, h);
        bigCircleRadius = length / 2;
        bigAngelRadius = length / 3;
        smallAngelRadius = length / 4;
    }

    /**
     * 大圆的半径
     */
    private float bigCircleRadius;
    /**
     * 小圆的半径
     */
    private float smallCircleRadius = 5f;
    /**
     * 两段大弧的半径
     */
    private float bigAngelRadius;
    /**
     * 两段小弧的半径
     */
    private float smallAngelRadius;

    private float startAngle1 = 0;
    private float startAngle2 = 180;
    private float sweepAngle = 60;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制两个圆
        canvas.drawCircle(bigCircleRadius, bigCircleRadius, bigCircleRadius - smallCircleRadius, paint);
        //小圆粗一些
        paint.setStrokeWidth(3);
        canvas.drawCircle(bigCircleRadius, bigCircleRadius, smallCircleRadius, paint);

        //绘制四段弧线
        //两段大弧，弧度相差180度
        RectF rectF = new RectF(bigCircleRadius-bigAngelRadius,bigCircleRadius-bigAngelRadius,bigCircleRadius+bigAngelRadius,bigCircleRadius+bigAngelRadius);
        canvas.drawArc(rectF,startAngle1,sweepAngle,false,paint);
        canvas.drawArc(rectF,startAngle2,sweepAngle,false,paint);
        //两段小弧，弧度相差180度
        RectF rectFSmaller = new RectF(bigCircleRadius-smallAngelRadius,bigCircleRadius-smallAngelRadius,bigCircleRadius+smallAngelRadius,bigCircleRadius+smallAngelRadius);
        canvas.drawArc(rectFSmaller,startAngle1,sweepAngle,false,paint);
        canvas.drawArc(rectFSmaller,startAngle2,sweepAngle,false,paint);

        startAngle1+=5;
        startAngle2+=5;
        if(!isDetached) {
            invalidate();
        }
    }

    /**
     * 自定义控件是否脱离窗体
     */
    private boolean isDetached;

    /**
     * 当自定义控件脱离窗体，即将销毁
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isDetached = true;
    }
}

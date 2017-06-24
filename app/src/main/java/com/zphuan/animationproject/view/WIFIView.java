package com.zphuan.animationproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by PeiHuan on 2017/6/24.
 * <p>
 * WIFI控件
 */
public class WIFIView extends View {

    public WIFIView(Context context) {
        this(context, null);
    }

    public WIFIView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WIFIView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;
    /**
     * 初始化准备
     */
    private void init() {
        paint = new Paint();
        //画笔颜色
        paint.setColor(Color.BLACK);
        //画笔粗细
        paint.setStrokeWidth(6);
        //抗锯齿
        paint.setAntiAlias(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this,500);
            }
        },500);
    }
    private Handler handler = new Handler();

    /**WIFI控件宽高较小一边的长度*/
    private int wifiLength;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wifiLength = Math.min(w, h);
    }

    /**
     * 开始角度
     */
    private float startAngle = -135;
    /**
     * 扇形或者弧的旋转角度
     */
    private float sweepAngle = 90;
    /**
     * 信号大小，默认4格
     */
    private int signalSize = 4;

    /**每次应该绘制的信号个数*/
    private float shouldExistSignalSize = 0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shouldExistSignalSize++;
        if(shouldExistSignalSize>4){
            shouldExistSignalSize=1;
        }
        canvas.save();
        //计算最小的扇形信号所在的圆的半径
        float signalRadius = wifiLength/2/signalSize;
        //向下平移画布,保证绘制的图形能够完全显示
        canvas.translate(0,signalRadius);
        for (int i = 0; i < signalSize; i++) {
            if(i>=signalSize-shouldExistSignalSize) {
                //定义每个信号所在圆的半径
                float radius = signalRadius * i;
                RectF rectf = new RectF(radius, radius, wifiLength - radius, wifiLength - radius);
                if (i < signalSize - 1) {
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawArc(rectf, startAngle, sweepAngle, false, paint);
                } else {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawArc(rectf, startAngle, sweepAngle, true, paint);
                }
            }
        }
        canvas.restore();
    }
}

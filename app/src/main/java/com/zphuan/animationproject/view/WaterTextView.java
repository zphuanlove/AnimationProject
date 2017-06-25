package com.zphuan.animationproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.zphuan.animationproject.R;

/**
 * Created by PeiHuan on 2017/6/25.
 *
 * 水面下降效果控件
 */

public class WaterTextView extends android.support.v7.widget.AppCompatTextView {

    private BitmapShader shader;
    private Matrix matrix;
    private int height;
    private float shaderX;
    private float shaderY;
    private int waveHeight;

    public WaterTextView(Context context) {
        this(context,null);
    }

    public WaterTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //让当前的TextView的字体为美术字体
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "Satisfy-Regular.ttf");
        setTypeface(typeface);
        //Matrix:矩阵,可以实现视图的平移旋转等效果
        matrix = new Matrix();
        //创建一个着色器
        createShader();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    private void createShader() {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wave);
        int waveWidth = originalBitmap.getWidth();
        waveHeight = originalBitmap.getHeight();
        Bitmap bitmap=Bitmap.createBitmap(waveWidth, waveHeight, originalBitmap.getConfig());
        //创建一个画布,为了将wave的图片颜色数据写入到Bitmap中
        Canvas canvas=new Canvas(bitmap);
        //设置画布的颜色从而控制文字的着色颜色
        canvas.drawColor(Color.RED);
        canvas.drawBitmap(originalBitmap,new Matrix(),getPaint());
        //CLAMP:使用原来的那张图片整体
        //REPEAT:将原来的图片复制无数份
        //MIRROR:镜像,将原来的图片镜像后,写入,再镜像...
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        getPaint().setShader(shader);
        shaderX = 0;
        shaderY = -waveHeight/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        repeatShader();
    }

    private void repeatShader() {
        shaderX +=5;
        shaderY +=0.1;
        if(shaderY >-waveHeight/2+height){
            shaderY = -waveHeight/2;
        }
        matrix.setTranslate(shaderX, shaderY);
        shader.setLocalMatrix(matrix);
        invalidate();
    }
}

package com.zphuan.animationproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zphuan.animationproject.R;

import static android.R.attr.path;
import static android.R.attr.src;


/**
 * Created by PeiHuan on 2017/6/28.
 */

public class Road extends View {

    private Paint paint;

    public Road(Context context) {
        this(context,null);
    }

    public Road(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Road(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Rect src;
    private Rect dst;
    private Path path;
    private void init() {
        paint = new Paint();
        src = new Rect();
        dst = new Rect();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Path.Direction.CW：顺时针方向
        path.addCircle(this.getWidth()/2,this.getHeight()/2,this.getWidth()/2, Path.Direction.CW);
        //画布的剪切:可以按照path去剪切出对应的图形
        canvas.clipPath(path);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.road);
        src.set(0,0,getWidth(),getHeight());
        dst.set(0,0,getWidth(),getHeight());
        canvas.drawBitmap(bitmap,src,dst,paint);
    }
}

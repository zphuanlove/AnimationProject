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
import android.util.Log;
import android.view.View;

import com.zphuan.animationproject.R;


/**
 * Created by PeiHuan on 2017/6/28.
 */
public class Road extends View {

    private static final String TAG = "huan";
    private Paint paint;
    private Bitmap bitmap;
    private int roadWidth;

    public Road(Context context) {
        this(context, null);
    }

    public Road(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Road(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Rect src;
    private Rect dst;
    private Path path;
    private Rect lastSrc;
    private Rect lastDst;

    private void init() {
        paint = new Paint();
        src = new Rect();
        dst = new Rect();
        lastSrc = new Rect();
        lastDst = new Rect();

        path = new Path();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.road);
        roadWidth = bitmap.getWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Path.Direction.CW：顺时针方向
        path.addCircle(w / 2, h / 2, w / 2, Path.Direction.CW);
    }

    private int offset;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布的剪切:可以按照path去剪切出对应的图形
        canvas.clipPath(path);

        if (offset + getWidth() <= roadWidth) {
            src.set(offset, 0, getWidth() + offset, getHeight());
            dst.set(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(bitmap, src, dst, paint);
        } else {
            src.set(offset, 0, roadWidth, getHeight());
            dst.set(0, 0, src.width(), getHeight());
            canvas.drawBitmap(bitmap, src, dst, paint);

            lastSrc.set(0, 0, getWidth() - src.width(), getHeight());
            lastDst.set(dst.width(), 0, getWidth(), getHeight());
            canvas.drawBitmap(bitmap, lastSrc, lastDst, paint);
        }
        offset += 3;
        offset %= roadWidth;
        Log.i(TAG, "offset: " + offset);
        invalidate();
    }
}

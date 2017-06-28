package com.zphuan.animationproject.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.zphuan.animationproject.R;

/**
 * Created by PeiHuan on 2017/6/28.
 */

public class CarActivity extends Activity {
    private ImageView gas;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        startTireAnimation();
        gas = (ImageView) findViewById(R.id.car_gas);
        handler.postDelayed(new MyRunnable(),300);
    }

    private void startTireAnimation() {
        ImageView front= (ImageView) findViewById(R.id.car_front_tire);
        AnimationDrawable frontAnimationDrawable= (AnimationDrawable) front.getDrawable();
        frontAnimationDrawable.start();

        ImageView back= (ImageView) findViewById(R.id.car_back_tire);
        AnimationDrawable backAnimationDrawable= (AnimationDrawable) back.getDrawable();
        backAnimationDrawable.start();
    }

    private boolean isGasVisible = false;
    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            gas.setVisibility(isGasVisible?View.INVISIBLE:View.VISIBLE);
            isGasVisible = !isGasVisible;
            handler.postDelayed(this,300);
        }
    }
}

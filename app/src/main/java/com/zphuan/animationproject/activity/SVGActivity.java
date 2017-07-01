package com.zphuan.animationproject.activity;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.zphuan.animationproject.R;

/**
 * Created by PeiHuan on 2017/6/29.
 */

public class SVGActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv= (ImageView) findViewById(R.id.iv);
                AnimatedVectorDrawable drawable= (AnimatedVectorDrawable) iv.getDrawable();
                drawable.start();
            }
        });
    }
}

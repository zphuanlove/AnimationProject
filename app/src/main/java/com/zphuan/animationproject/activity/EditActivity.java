package com.zphuan.animationproject.activity;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zphuan.animationproject.R;

import static com.zphuan.animationproject.R.id.edit1;
import static com.zphuan.animationproject.R.id.iv;

/**
 * Created by PeiHuan on 2017/7/1.
 */

public class EditActivity extends Activity implements View.OnFocusChangeListener {
    private AnimatedVectorDrawable animOut;
    private ImageView iv;
    private AnimatedVectorDrawable animGone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editext);
        iv = (ImageView) findViewById(R.id.iv);
        animOut = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_et_bg_out);
        animGone = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_et_bg_gone);

        EditText et1 = (EditText) findViewById(R.id.edit1);
        EditText et2 = (EditText) findViewById(R.id.edit2);
        et1.setOnFocusChangeListener(this);
        et2.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.edit1:
                if(hasFocus){
                    iv.setImageDrawable(animOut);
                    animOut.start();
                }
                break;
            case R.id.edit2:
                if(hasFocus){
                    iv.setImageDrawable(animGone);
                    animGone.start();
                }
                break;
        }
    }
}

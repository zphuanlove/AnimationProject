package com.zphuan.animationproject.activity;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zphuan.animationproject.R;

/**
 * Created by PeiHuan on 2017/7/1.
 */
public class EditActivity extends Activity implements View.OnFocusChangeListener, TextWatcher {
    private ImageView iv;
    private AnimatedVectorDrawable animOut;
    private AnimatedVectorDrawable animGone;
    private AnimatedVectorDrawable animRightOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editext);
        iv = (ImageView) findViewById(R.id.iv);
        animOut = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_et_bg_out);
        animGone = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_et_bg_gone);
        animRightOut = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_et_bg_right);

        EditText et1 = (EditText) findViewById(R.id.edit1);
        EditText et2 = (EditText) findViewById(R.id.edit2);
        et1.setOnFocusChangeListener(this);
        et2.setOnFocusChangeListener(this);
        et1.addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit1:
                if (hasFocus) {
                    iv.setImageDrawable(animOut);
                    animOut.start();
                }
                break;
            case R.id.edit2:
                if (hasFocus) {
                    iv.setImageDrawable(animGone);
                    animGone.start();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String result = s.toString();
        if("123".equals(result)) {
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
            iv.setImageDrawable(animRightOut);
            animRightOut.start();
        }
    }
}

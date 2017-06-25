package com.zphuan.animationproject;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zphuan.animationproject.view.MusicView;
import com.zphuan.animationproject.view.WIFIView;
import com.zphuan.animationproject.view.WaterTextView;

public class MainActivity extends AppCompatActivity {

    private String[] datas = {"WIFI_Demo","Music_Demo","Water_Demo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                    MyViewHolder viewHolder = new MyViewHolder(view);
                    return viewHolder;
                }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.tv.setText(datas[position]);
                viewHolder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertViewDialog(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return datas.length;
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                private TextView tv;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv = (TextView) itemView.findViewById(android.R.id.text1);
                }
            }
        });
    }

    /**
     * 弹出自定义对话框
     * @param position
     */
    private void alertViewDialog(int position) {
        Dialog dialog = new Dialog(this);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        View view = null;
        switch (position){
            case 0:
                view = new WIFIView(this);
                break;
            case 1:
                view = new MusicView(this);
                break;
            case 2:
                view = LayoutInflater.from(this).inflate(R.layout.water,null,false);
                break;
        }
        dialog.setContentView(view);
        if(position!=2){
            attributes.width = (int) getResources().getDimension(R.dimen.view_width);
            attributes.height = (int) getResources().getDimension(R.dimen.view_height);
        }else{
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }
        window.setAttributes(attributes);
        dialog.show();
    }
}

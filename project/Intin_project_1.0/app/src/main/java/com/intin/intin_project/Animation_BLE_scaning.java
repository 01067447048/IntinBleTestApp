package com.intin.intin_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Animation_BLE_scaning extends AppCompatActivity {
    static private ImageView animation_BLE_scaning;
    static private AnimationDrawable paly_Animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation__ble_scaning);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        animation_BLE_scaning = findViewById(R.id.animation_img);
        animation_BLE_scaning.setBackgroundResource(R.drawable.ble_scaning_animation);

        paly_Animation = (AnimationDrawable) animation_BLE_scaning.getBackground();

        animation_BLE_scaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paly_Animation.start();
            }
        });


    }

}

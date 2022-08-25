package com.intin.intin_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterChekActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_chek);

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);

        final View service_main_View = (View) findViewById(R.id.text_service);
        final TextView service_Chk = (TextView) findViewById(R.id.text_service2);
        final ViewGroup service_View = (ViewGroup) findViewById(R.id.text_service3);
        final CheckBox service_chekbox = (CheckBox) findViewById(R.id.service_chek);

        service_View.setVisibility(View.GONE);

        final View sujib_main_View = (View) findViewById(R.id.text_sujib);
        final TextView sujib_chk = (TextView) findViewById(R.id.text_sujib2);
        final ViewGroup sujib_view = (ViewGroup) findViewById(R.id.text_sujib3);
        final CheckBox sujib_chekbox = (CheckBox) findViewById(R.id.sujib_chek);


        sujib_view.setVisibility(View.GONE);


        final Button next_Button = (Button) findViewById(R.id.next_regbtn);

        final Drawable alpha1 = next_Button.getBackground();
        alpha1.setAlpha(30);

        service_Chk.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View v) {
                if( i <= 1 ){
                    service_View.setVisibility(View.VISIBLE);
                    i ++;
                }else if( i > 1 && service_chekbox.isChecked() == false) {
                    Toast.makeText(getApplicationContext(),"서비스이용 약관에 동의를 체크해 주세요.",Toast.LENGTH_SHORT).show();
                    i --;
                }else if( i > 1 || service_chekbox.isChecked() == true){
                    service_View.setVisibility(View.GONE);
                    i --;
                }

            }
        });
       service_chekbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    service_main_View.setBackgroundResource(R.drawable.text_line_nofocus);
                    alpha1.setAlpha(100);
                    service_View.setVisibility(View.GONE);
                    }
                else {
                    service_main_View.setBackgroundResource(R.drawable.text_line_focus);
                    Toast.makeText(getApplicationContext(),"서비스이용 약관에 동의를 체크해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        sujib_chk.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                if( i <= 1 ){
                    sujib_view.setVisibility(View.VISIBLE);
                    i ++;
                }else if( i > 1 && sujib_chekbox.isChecked() == false) {
                    Toast.makeText(getApplicationContext(),"개인정보 활용에 동의를 체크해 주세요.",Toast.LENGTH_SHORT).show();
                    i --;
                }else if( i > 1 || sujib_chekbox.isChecked() == true){
                    sujib_view.setVisibility(View.GONE);
                    i --;
                }

            }
        });
        sujib_chekbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    sujib_main_View.setBackgroundResource(R.drawable.text_line_nofocus);
                    alpha1.setAlpha(200);
                    sujib_view.setVisibility(View.GONE);
                    }
                else {
                    sujib_main_View.setBackgroundResource(R.drawable.text_line_focus);
                    Toast.makeText(getApplicationContext(),"개인정보 활용에 동의를 체크해 주세요.",Toast.LENGTH_SHORT).show();
                    sujib_view.setVisibility(View.GONE);
                }
            }
        });
        next_Button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if(service_chekbox.isChecked() == true && sujib_chekbox.isChecked() == true){
                    Intent register = new Intent(RegisterChekActivity.this , RegisterActivity.class);
                    RegisterChekActivity.this.startActivity(register);
                    finish();
                }else if (service_chekbox.isChecked() == true && sujib_chekbox.isChecked() == false){
                    Toast.makeText(getApplicationContext(),"개인정보 사용에 동의를 체크해 주세요.",Toast.LENGTH_SHORT).show();
                }else if (service_chekbox.isChecked() == false && sujib_chekbox.isChecked() == true){
                    Toast.makeText(getApplicationContext(),"서비스이용 약관에 동의를 체크해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });//Button.setOnClick...

    }
}

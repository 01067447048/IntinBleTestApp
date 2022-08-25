package com.intin.intin_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;


public class LoginActivity extends AppCompatActivity {
    private EditText idText, passWord;
    RegisterAdpter helper;
    private long backBtnTime = 0;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);

        if(Login_Auto_ference.getUserName(LoginActivity.this, 0).length() != 0){
            Intent intent = new Intent(getApplicationContext(), MemberFamily.class);
            startActivity(intent);
            finish();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);


        TextView title = (TextView)findViewById(R.id.page_title);
        idText =  findViewById(R.id.id_text);
        passWord = findViewById(R.id.password_text);

        TextView find_Pass = (TextView) findViewById(R.id.find_password);
        TextView register_Chk = (TextView) findViewById(R.id.regest_id);

        find_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findpasswordIntent = new Intent(LoginActivity.this, FindPassword.class);
                LoginActivity.this.startActivity(findpasswordIntent);

            }
        });
        register_Chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regsterIntent = new Intent(LoginActivity.this, RegisterChekActivity.class);
                LoginActivity.this.startActivity(regsterIntent);

            }
        });
        helper = new RegisterAdpter(this);
    }//onCreate

    public void Login(View view) {
        String userid = idText.getText().toString();
        String pass = passWord.getText().toString();
        String mData = helper.getData();

        if (userid.isEmpty() || pass.isEmpty()) {
            Message.message(getApplicationContext(), "아이디와 비밀번호를 입력해 주세요.");
        } else {
            if (!mData.contains(userid)) {
                Message.message(getApplication(),"가입된 회원이 아닙니다.");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userid).matches()) {
                Message.message(getApplication(), "아이디가 이메일 형식이 아닙니다.");
            } else {
                if (mData.contains(pass)) {
                    Message.message(getApplication(), userid + "님이 로그인 하셧습니다.");
                    Login_Auto_ference.setUserName(getApplication(), idText.getText().toString(), 0);
                    Intent login = new Intent(getApplication(), MemberFamily.class);
                    startActivity(login);
                    finish();

                }else {
                    Message.message(getApplication(), "패스워드가 틀렸습니다.");
                }
            }
        }
    }//Login..
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        if (0 <= gapTime && 2000 >= gapTime){
            super.onBackPressed();
        }
        else {
            backBtnTime = curTime;
            Message.message(getApplicationContext(),"한번 더 누르시면 종료 됩니다.");
        }
    }
}

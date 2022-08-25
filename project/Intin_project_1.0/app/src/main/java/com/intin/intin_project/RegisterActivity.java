package com.intin.intin_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    private EditText idText, passWord, passWord_chk;
    RegisterAdpter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);

        idText = findViewById(R.id.id_text);
        passWord = findViewById(R.id.password_text);
        passWord_chk = findViewById(R.id.passwordchek_text);

        final Button registerButton = (Button) findViewById(R.id.registerButton);
        final Drawable alpha1 = registerButton.getBackground();

        alpha1.setAlpha(50);

        idText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (idText.getText().toString().length() <= 0) {
                    idText.setBackgroundResource(R.drawable.text_focus_bool);
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(idText.getText().toString()).matches()) {
                    idText.setBackgroundResource(R.drawable.text_nomach_line);
                } else {
                    idText.setBackgroundResource(R.drawable.text_line_nofocus);
                }
            }
        });
        passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (passWord.getText().toString().length() <= 0) {
                    passWord.setBackgroundResource(R.drawable.text_focus_bool);
                } else {
                    passWord.setBackgroundResource(R.drawable.text_line_nofocus);
                }
            }
        });

        passWord_chk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passWord.getText().toString().equals(passWord_chk.getText().toString())) {
                    alpha1.setAlpha(200);
                    passWord_chk.setBackgroundResource(R.drawable.text_line_nofocus);
                } else if (passWord_chk.getText().toString().length() <= 0) {
                    passWord_chk.setBackgroundResource(R.drawable.text_focus_bool);
                } else {
                    passWord_chk.setBackgroundResource(R.drawable.text_nomach_line);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        helper = new RegisterAdpter(this);
    }//onCreate

    public void Sinup(View view) {
        String userid = idText.getText().toString();
        String pass = passWord.getText().toString();
        String mData = helper.getData();

        if (userid.isEmpty() || pass.isEmpty()) {
            Message.message(getApplicationContext(), "아이디와 비밀번호를 확인해 주세요.");
        } else {
            long id = helper.insterData(userid, pass);
            if (id <= 0 || mData.contentEquals(userid)) {
                Message.message(getApplicationContext(), "동일한 아이디가 존재 합니다.");
                idText.setText("");
                idText.setBackgroundResource(R.drawable.text_focus_bool);
                passWord.setText("");
                passWord.setBackgroundResource(R.drawable.text_focus_bool);
                passWord_chk.setText("");
                passWord_chk.setBackgroundResource(R.drawable.text_focus_bool);
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userid).matches()) {
                Message.message(getApplication(), "아이디가 이메일 형식이 아닙니다.");
            } else {
                Message.message(getApplicationContext(), userid + "님 회원가입에 성공 했습니다.");
                Intent login = new Intent(getApplication(), LoginActivity.class);
                startActivity(login);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}

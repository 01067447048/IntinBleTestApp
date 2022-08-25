package com.intin.intin_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberFamily extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView listView;
    Button button , add_list;
    EditText add_member;
    InputMethodManager keybord;
    private String member = "";
    private Object TextView;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_family);

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);
        add_member = findViewById(R.id.add_member);
        button = findViewById(R.id.next_btn);
        add_list = findViewById(R.id.add_list);
        final Drawable alpha1 = button.getBackground();
        alpha1.setAlpha(30);
        add_list.setVisibility(View.GONE);
        keybord = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        items = new ArrayList<String>();
        items.add("아빠");
        items.add("엄마");
        items.add("아들");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, items);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                member = listView.getItemAtPosition(position).toString();
                alpha1.setAlpha(200);
            }
        });

        add_member.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    add_list.setVisibility(View.GONE);
                }else {
                    add_list.setVisibility(View.VISIBLE);
                    add_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = add_member.getText().toString();
                            if (text.length() != 0) {
                                items.add(text);
                                add_member.setText("");
                                keybord.hideSoftInputFromWindow(add_member.getWindowToken(), 0);
                                add_member.clearFocus();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });

    }//onCreate..

    public void Next(View view) {
        String next_member = member;
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        next.putExtra("member" ,next_member);
        setResult(RESULT_OK);
        startActivity(next);
        finish();

    }
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

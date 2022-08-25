package com.intin.intin_project;

import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private View regedit, categori_view, main_view, more_view, calendarView, toolbar_2, toolbar_1, result_view, result_in_view, main_result_view;
    private TextView pass_chang, member_con, regester_del, logout, pageTitle, aram_set, howto_use, buy_item, chang_lang,
            air_moniter, air_care, result_text_view, light_inresult_view1, light_inresult_view2, light_inresult_view3,my_toorbar_mm,
            light_inresult_view4, light_inresult_view5, main_result_menu_1, main_result_menu_2, main_result_menu_3, main_result_menu_4,
            main_result_menu_5;
    public static final int REQUEST_CODE = 1000;
    public static final int REQUEST_CODE_BLE = 2000;
    private Button categori_btn, more_btn, regster_con_btn, main_btn;
    private String strColor = "#0099FF", member = "", sTime, lTime, gTime;
    private ViewGroup.LayoutParams layoutParamsSize;
    private int setText_title = 0;
    private int MOVE_VIEW_COUNT = 0;
    private long backBtnTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        member = intent.getExtras().getString("member");


        System.out.println(member);

        ImageView img = (ImageView) findViewById(R.id.my_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setCustomView(img);

        categori_btn = findViewById(R.id.categori_btn);
        more_btn = findViewById(R.id.more_btn);
        regster_con_btn = findViewById(R.id.regedit_btn);
        main_btn = findViewById(R.id.main_btn);

        regedit = findViewById(R.id.regedit_view);
        main_view = findViewById(R.id.main_view);
        categori_view = findViewById(R.id.categori_view);
        more_view = findViewById(R.id.more_view);
        calendarView = findViewById(R.id.calinder_view);
        toolbar_2 = findViewById(R.id.my_toolbar_2);
        toolbar_1 = findViewById(R.id.my_toolbar_1);
        result_view = findViewById(R.id.result_view);
        result_in_view = findViewById(R.id.result_in_view);
        main_result_view = findViewById(R.id.main_result_view);


        pageTitle = findViewById(R.id.page_title);
        pass_chang = findViewById(R.id.pass_change);
        member_con = findViewById(R.id.member_edit);
        regester_del = findViewById(R.id.regist_del);
        logout = findViewById(R.id.logout);
        aram_set = findViewById(R.id.alram_set);
        howto_use = findViewById(R.id.howto_use);
        buy_item = findViewById(R.id.buy_item);
        chang_lang = findViewById(R.id.change_lang);
        air_moniter = findViewById(R.id.air_moniter);
        air_care = findViewById(R.id.air_care);
        result_text_view = findViewById(R.id.result_textview);
        my_toorbar_mm = findViewById(R.id.my_toolbar_text);
        light_inresult_view1 = findViewById(R.id.light_inresult_view_1);
        light_inresult_view2 = findViewById(R.id.light_inresult_view_2);
        light_inresult_view3 = findViewById(R.id.light_inresult_view_3);
        light_inresult_view4 = findViewById(R.id.light_inresult_view_4);
        light_inresult_view5 = findViewById(R.id.light_inresult_view_5);
        main_result_menu_1 = findViewById(R.id.main_result_menu_1);
        main_result_menu_2 = findViewById(R.id.main_result_menu_2);
        main_result_menu_3 = findViewById(R.id.main_result_menu_3);
        main_result_menu_4 = findViewById(R.id.main_result_menu_4);
        main_result_menu_5 = findViewById(R.id.main_result_menu_5);

        main_result_menu_1.setText(member);

        regedit.setVisibility(View.VISIBLE);


        pass_chang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (pass_chang.getClass() == v.getClass()) {
                        pass_chang.setTextColor(Color.parseColor(strColor));
                        pass_chang.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (pass_chang.getClass() == v.getClass()) {
                        pass_chang.setTextColor(Color.BLACK);
                        pass_chang.setBackgroundResource(R.drawable.text_line_focus);


                    }
                }
                return true;
            }
        });

        regester_del.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (regester_del.getClass() == v.getClass()) {
                        regester_del.setTextColor(Color.parseColor(strColor));
                        regester_del.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (regester_del.getClass() == v.getClass()) {
                        regester_del.setTextColor(Color.BLACK);
                        regester_del.setBackgroundResource(R.drawable.text_line_focus);

                    }
                }
                return true;
            }
        });

        member_con.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (member_con.getClass() == v.getClass()) {
                        member_con.setTextColor(Color.parseColor(strColor));
                        member_con.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (member_con.getClass() == v.getClass()) {
                        member_con.setTextColor(Color.BLACK);
                        member_con.setBackgroundResource(R.drawable.text_line_focus);
                        Intent intent = new Intent(getApplicationContext(), MemberFamily.class);
                        startActivity(intent);
                        finish();
                    }
                }
                return true;
            }
        });

        logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (logout.getClass() == v.getClass()) {
                        logout.setTextColor(Color.parseColor(strColor));
                        logout.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (logout.getClass() == v.getClass()) {
                        logout.setTextColor(Color.BLACK);
                        logout.setBackgroundResource(R.drawable.text_line_focus);
                        Login_Auto_ference.clearUserName(getApplication());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                return true;
            }
        });
    }//onCreate


    public void moreView(View view) {
        String moreview_title = "더보기";
        more_view.setVisibility(View.VISIBLE);
        regedit.setVisibility(View.GONE);
        main_view.setVisibility(View.GONE);
        pageTitle.setVisibility(View.VISIBLE);
        categori_view.setVisibility(View.GONE);
        pageTitle.setText(moreview_title);
        more_btn.setVisibility(View.GONE);
        regster_con_btn.setVisibility(View.VISIBLE);
        toolbar_1.setVisibility(View.VISIBLE);
        toolbar_2.setVisibility(View.GONE);
        calendarView.setVisibility(View.GONE);
        result_view.setVisibility(View.GONE);
        result_in_view.setVisibility(View.GONE);
        main_result_view.setVisibility(View.GONE);


        aram_set.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (aram_set.getClass() == v.getClass()) {
                        aram_set.setTextColor(Color.parseColor(strColor));
                        aram_set.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (aram_set.getClass() == v.getClass()) {
                        aram_set.setTextColor(Color.BLACK);
                        aram_set.setBackgroundResource(R.drawable.text_line_focus);
                    }
                }
                return true;
            }
        });

        howto_use.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (howto_use.getClass() == v.getClass()) {
                        howto_use.setTextColor(Color.parseColor(strColor));
                        howto_use.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (howto_use.getClass() == v.getClass()) {
                        howto_use.setTextColor(Color.BLACK);
                        howto_use.setBackgroundResource(R.drawable.text_line_focus);
                    }
                }
                return true;
            }
        });

        buy_item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (buy_item.getClass() == v.getClass()) {
                        buy_item.setTextColor(Color.parseColor(strColor));
                        buy_item.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (buy_item.getClass() == v.getClass()) {
                        buy_item.setTextColor(Color.BLACK);
                        buy_item.setBackgroundResource(R.drawable.text_line_focus);
                    }
                }
                return true;
            }
        });

        chang_lang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (chang_lang.getClass() == v.getClass()) {
                        chang_lang.setTextColor(Color.parseColor(strColor));
                        chang_lang.setBackgroundResource(R.drawable.text_line_nofocus);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (chang_lang.getClass() == v.getClass()) {
                        chang_lang.setTextColor(Color.BLACK);
                        chang_lang.setBackgroundResource(R.drawable.text_line_focus);
                    }
                }
                return true;
            }
        });
    }//moreView..

    public void memberControl(View view) {
        String membercontrol_title = "계정관리";
        more_view.setVisibility(View.GONE);
        regedit.setVisibility(View.VISIBLE);
        pageTitle.setVisibility(View.VISIBLE);
        main_view.setVisibility(View.GONE);
        categori_view.setVisibility(View.GONE);
        pageTitle.setText(membercontrol_title);
        regster_con_btn.setVisibility(View.GONE);
        more_btn.setVisibility(View.VISIBLE);
        toolbar_1.setVisibility(View.VISIBLE);
        toolbar_2.setVisibility(View.GONE);
        calendarView.setVisibility(View.GONE);
        result_view.setVisibility(View.GONE);
        result_in_view.setVisibility(View.GONE);
        main_result_view.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cataGori(View view) {
        String categori_title = "카테고리";
        more_view.setVisibility(View.GONE);
        regedit.setVisibility(View.GONE);
        pageTitle.setVisibility(View.VISIBLE);
        main_view.setVisibility(View.GONE);
        categori_view.setVisibility(View.VISIBLE);
        pageTitle.setText(categori_title);
        categori_btn.setVisibility(View.GONE);
        main_btn.setVisibility(View.VISIBLE);
        toolbar_1.setVisibility(View.VISIBLE);
        toolbar_2.setVisibility(View.GONE);
        calendarView.setVisibility(View.GONE);
        result_view.setVisibility(View.GONE);
        result_in_view.setVisibility(View.GONE);
        main_result_view.setVisibility(View.GONE);
        String month = null;
        String day = null;
            month = new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()));
            day = new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis()));
            System.out.println(day);
            int _day = Integer.parseInt(day);
            int oneju = 7;
            if (_day <= oneju){
                ObjectAnimator.ofFloat(calendarView, "translationY", -50f).start();
            }else if (_day <= 14){
                ObjectAnimator.ofFloat(calendarView, "translationY", -100f).start();
            }else if (_day <= 21){
                ObjectAnimator.ofFloat(calendarView, "translationY", -200f).start();
            }else if (_day <= 29){
                ObjectAnimator.ofFloat(calendarView, "translationY", -250f).start();
            }


        my_toorbar_mm.setText(month + "월");

        air_care.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (air_care.getClass() == v.getClass()) {
                        air_care.setTextColor(Color.parseColor(strColor));
                        air_care.setBackgroundResource(R.drawable.text_line_nofocus);
                        call_Result_start_view();

                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (air_care.getClass() == v.getClass()) {
                        air_care.setTextColor(Color.BLACK);
                        air_care.setBackgroundResource(R.drawable.text_line_focus);
                    }
                }
                return true;
            }
        });

    }

    public void main_menu(View view) {
        String main_menu_title = "메인";
        more_view.setVisibility(View.GONE);
        pageTitle.setVisibility(View.VISIBLE);
        regedit.setVisibility(View.GONE);
        main_view.setVisibility(View.VISIBLE);
        categori_view.setVisibility(View.GONE);
        pageTitle.setText(main_menu_title);
        categori_btn.setVisibility(View.VISIBLE);
        main_btn.setVisibility(View.GONE);
        toolbar_1.setVisibility(View.VISIBLE);
        toolbar_2.setVisibility(View.GONE);
        calendarView.setVisibility(View.GONE);
        result_view.setVisibility(View.GONE);
        result_in_view.setVisibility(View.GONE);
        main_result_view.setVisibility(View.VISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void call_Result_start_view() {
        categori_view.setVisibility(View.GONE);
        pageTitle.setVisibility(View.GONE);
        toolbar_1.setVisibility(View.GONE);
        toolbar_2.setVisibility(View.VISIBLE);
        calendarView.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
        result_in_view.setVisibility(View.GONE);
        main_result_view.setVisibility(View.GONE);
        final Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.result_image_select, null);
        final Drawable _img = getApplicationContext().getResources().getDrawable(R.drawable.result_image_defult, null);


        result_text_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (result_text_view.getClass() == v.getClass()) {
                        result_text_view.setTextColor(Color.parseColor(strColor));
                        img.setBounds(0, 0, 70, 70);
                        result_text_view.setCompoundDrawables(img, null, null, null);

                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (result_text_view.getClass() == v.getClass()) {
                        result_text_view.setTextColor(Color.BLACK);
                        _img.setBounds(0, 0, 70, 70);
                        result_text_view.setCompoundDrawables(_img, null, null, null);

                        Result_start();
                    }
                }
                return true;
            }
        });

        light_inresult_view1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (light_inresult_view1.getClass() == v.getClass()) {
                        light_inresult_view1.setTextColor(Color.parseColor(strColor));
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (light_inresult_view1.getClass() == v.getClass()) {
                        light_inresult_view1.setTextColor(Color.BLACK);
                        setText_title = 1;
                        callResult_count();
                    }
                }
                return true;
            }
        });

        light_inresult_view2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (light_inresult_view2.getClass() == v.getClass()) {
                        light_inresult_view2.setTextColor(Color.parseColor(strColor));
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (light_inresult_view2.getClass() == v.getClass()) {
                        light_inresult_view2.setTextColor(Color.BLACK);
                        setText_title = 2;
                        callResult_count();
                    }
                }
                return true;
            }
        });

        light_inresult_view3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (light_inresult_view3.getClass() == v.getClass()) {
                        light_inresult_view3.setTextColor(Color.parseColor(strColor));
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (light_inresult_view3.getClass() == v.getClass()) {
                        light_inresult_view3.setTextColor(Color.BLACK);
                        setText_title = 3;
                        callResult_count();
                    }
                }
                return true;
            }
        });
        light_inresult_view4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (light_inresult_view4.getClass() == v.getClass()) {
                        light_inresult_view4.setTextColor(Color.parseColor(strColor));
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (light_inresult_view4.getClass() == v.getClass()) {
                        light_inresult_view4.setTextColor(Color.BLACK);
                        setText_title = 4;
                        callResult_count();
                    }
                }
                return true;
            }
        });
        light_inresult_view5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (light_inresult_view5.getClass() == v.getClass()) {
                        light_inresult_view5.setTextColor(Color.parseColor(strColor));
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (light_inresult_view5.getClass() == v.getClass()) {
                        light_inresult_view5.setTextColor(Color.BLACK);
                        setText_title = 5;
                        end_Result();
                    }
                }
                return true;
            }
        });
    }//call_Result_start_view

    private void Result_start() {
        categori_view.setVisibility(View.GONE);
        pageTitle.setVisibility(View.GONE);
        toolbar_1.setVisibility(View.GONE);
        main_result_view.setVisibility(View.GONE);
        toolbar_2.setVisibility(View.VISIBLE);
        calendarView.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
        result_in_view.setVisibility(View.VISIBLE);
        light_inresult_view1.setVisibility(View.VISIBLE);
        light_inresult_view2.setVisibility(View.VISIBLE);
        light_inresult_view3.setVisibility(View.VISIBLE);
        light_inresult_view4.setVisibility(View.VISIBLE);
        light_inresult_view5.setVisibility(View.VISIBLE);
        MOVE_VIEW_COUNT = 0;
        ObjectAnimator.ofFloat(result_view, "translationY", 0f).start();
//        Intent intent = new Intent(getApplicationContext(), BLE_MainActivity.class);
//        startActivityForResult(intent, REQUEST_CODE_BLE);


    }

    private void callResult_count() {
        String putTitle = null;
        switch (setText_title) {
            case 1:
                putTitle = "LED Therapy";
                break;
            case 2:
                putTitle = "Nebulizer Therapy";
                break;
            case 3:
                putTitle = "Suction Therapy";
                break;
            case 4:
                putTitle = "Spray Therapy";
                break;
            case 5:
                putTitle = null;

        }
        if (!putTitle.equals(null)) {
            main_result_menu_2.setText(putTitle);
            Intent intent = new Intent(getApplicationContext(), Result_Count.class);
            intent.putExtra("title", putTitle);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addCaliender(View view) {
        light_inresult_view1.setVisibility(View.GONE);
        light_inresult_view2.setVisibility(View.GONE);
        light_inresult_view3.setVisibility(View.GONE);
        light_inresult_view4.setVisibility(View.GONE);
        light_inresult_view5.setVisibility(View.GONE);
        String day = null;
        day = new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis()));
        int _day = Integer.parseInt(day);
        if (MOVE_VIEW_COUNT == 0) {
            ObjectAnimator.ofFloat(result_view, "translationY", +600f).start();
            ObjectAnimator.ofFloat(calendarView, "translationY", -0f).start();
            MOVE_VIEW_COUNT++;
        } else {
            ObjectAnimator.ofFloat(result_view, "translationY", 0f).start();
            MOVE_VIEW_COUNT = 0;
            if (_day <= 7){
                ObjectAnimator.ofFloat(calendarView, "translationY", -50f).start();
            }else if (_day <= 14){
                ObjectAnimator.ofFloat(calendarView, "translationY", -150f).start();
            }else if (_day <= 21){
                ObjectAnimator.ofFloat(calendarView, "translationY", -200f).start();
            }else if (_day <= 29){
                ObjectAnimator.ofFloat(calendarView, "translationY", -250f).start();
            }
        }


        System.out.println(MOVE_VIEW_COUNT);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            sTime = data.getStringExtra("startime");
            lTime = data.getStringExtra("lasttime");
            gTime = data.getStringExtra("gettime");
            main_result_menu_1.setText(member);
            end_Result();
        }
    }

    public void end_Result() {
        main_result_menu_3.setText(sTime);
        main_result_menu_4.setText(lTime);
        main_result_menu_5.setText(gTime);
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
}// MainActivity...

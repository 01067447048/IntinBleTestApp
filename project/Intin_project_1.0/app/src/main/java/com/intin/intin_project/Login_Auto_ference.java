package com.intin.intin_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Login_Auto_ference {

    static final String _USER_NAME = "username";
    static final String _PASS_WORD = "password";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public  static void setUserName(Context ctx , String userName , int i){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        if(i == 0) {
            editor.putString(_USER_NAME, userName);
            editor.commit();
        }else {
            editor.putString(_PASS_WORD, userName);
            editor.commit();
        }
    }

    public static String getUserName(Context ctx, int i){
        if (i == 0){
            return getSharedPreferences(ctx).getString(_USER_NAME,"");
        }else {
            return getSharedPreferences(ctx).getString(_PASS_WORD,"");
        }
    }

    public static void clearUserName(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}

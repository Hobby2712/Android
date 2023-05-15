package com.example.cuoiki.SharedPrefManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.cuoiki.Activity.User.LoginActivity;
import com.example.cuoiki.Model.User;

public class SharedPrefManager {
    //Khởi tạo các hằng key
    private static final String SHARE_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_FULLNAME = "keyfullname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGES = "keyimages";

    private static SharedPrefManager mIstance;
    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if (mIstance == null){
            mIstance = new SharedPrefManager(context);
        }
        return mIstance;
    }

    public void userLogin(User user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUserName());
        editor.putString(KEY_FULLNAME, user.getFullName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_IMAGES, user.getImages());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_FULLNAME, null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_IMAGES,null)
        );
    }


    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}

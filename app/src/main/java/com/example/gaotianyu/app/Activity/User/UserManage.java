package com.example.gaotianyu.app.Activity.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by GaoTianyu on 2017/11/28.
 */

public class UserManage {
    private static UserManage instance;
    private UserManage(){

    }
    public static UserManage getInstance(){
        if(instance==null){
            instance = new UserManage();
        }
        return instance;
    }
    public void saveUserinfo(Context context,String username,String password,String id){
        SharedPreferences sp = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME",username);
        editor.putString("PASSWORD",password);
        editor.putString("id",id);
        editor.apply();
    }
    public UserInfo getUserInfo (Context context){
        SharedPreferences sp = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME",""));
        userInfo.setPassword(sp.getString("PASSWORD",""));
        userInfo.setId(sp.getString("id",""));
        return userInfo;

    }
    public boolean hasUserInfo(Context context){
        UserInfo userInfo = getUserInfo(context);
        if(userInfo!=null){
            if((!TextUtils.isEmpty(userInfo.getUserName()))&&(!TextUtils.isEmpty(userInfo.getPassword()))&&(!TextUtils.isEmpty(userInfo.getId()))){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    public void clear (Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}

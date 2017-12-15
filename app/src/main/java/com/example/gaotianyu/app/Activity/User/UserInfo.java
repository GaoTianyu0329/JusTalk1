package com.example.gaotianyu.app.Activity.User;

/**
 * Created by GaoTianyu on 2017/11/28.
 */

public class UserInfo {
    private String userName;
    private String password;
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
}

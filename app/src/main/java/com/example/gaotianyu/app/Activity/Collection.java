package com.example.gaotianyu.app.Activity;

import cn.bmob.v3.BmobObject;

/**
 * Created by GaoTianyu on 2018/2/16.
 */

public class Collection extends BmobObject {
    private String userID;
    private String postID;
    public Collection(String userID, String postID){
        this.userID = userID;
        this.postID = postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostID() {
        return postID;
    }

    public String getUserID() {
        return userID;
    }
}

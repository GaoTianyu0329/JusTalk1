package com.example.gaotianyu.app.Activity.PostList;

import cn.bmob.v3.BmobObject;

/**
 * Created by GaoTianyu on 2017/12/28.
 */

public class PostList extends BmobObject {
    private String kind;

    private String uesrid;

    private String title;
    private String content;
    private String time;
    private String label;
public PostList (){

}

    public PostList(String userid,String kind,String title,String content,String time,String label){
        this.uesrid = userid;
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.time = time;
        this.label = label;

    }



    public String getContent() {
        return content;
    }


    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUesrid() {
        return uesrid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }




    public void setContent(String main) {
        this.content = main;
    }

    public void setUerid(String uerid) {
        this.uesrid = uerid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}

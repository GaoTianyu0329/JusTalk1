package com.example.gaotianyu.app.Activity.PostList;

/**
 * Created by GaoTianyu on 2017/12/28.
 */

public class PostList {
    private String id;
    private String uesrid;

    private String title;
    private String content;
    private String time;
    private String label;

public PostList(String id,String userid,String title,String content,String time,String label){
    this.id=id;
    this.uesrid = userid;
    this.title = title;
    this.content = content;
    this.time = time;
    this.label = label;

}
    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
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
}

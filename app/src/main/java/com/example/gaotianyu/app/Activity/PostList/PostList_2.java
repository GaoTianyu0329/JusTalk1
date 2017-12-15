package com.example.gaotianyu.app.Activity.PostList;

/**
 * Created by GaoTianyu on 2017/12/5.
 */

public class PostList_2 {
    private String label;
    private String time;
    private String title;
    public PostList_2(String label,String time,String title){
        this.label=label;
        this.time=time;
        this.title=title;
    }
    public String getLabel(){
        return label;
    }
    public String getTime(){
        return time;
    }
    public String getTitle(){
        return title;
    }
}

package com.example.gaotianyu.app.Activity.PostList;

/**
 * Created by GaoTianyu on 2017/12/5.
 */

public class PostList_1{
    private int id;
    private String label;
    private String time;
    private String title;
    public PostList_1(int id,String label,String time,String title){
        this.id=id;
        this.label=label;
        this.time=time;
        this.title=title;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

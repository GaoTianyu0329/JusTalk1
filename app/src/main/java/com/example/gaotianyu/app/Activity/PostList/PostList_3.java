package com.example.gaotianyu.app.Activity.PostList;

/**
 * Created by GaoTianyu on 2017/12/5.
 */

public class PostList_3 {
    private String label_1;
    private String label_2;
    private String time;
    private String title;
    public PostList_3(String label_1,String label_2,String time,String title){
        this.label_1=label_1;
        this.label_2=label_2;
        this.time=time;
        this.title=title;
    }

    public String getLabel_1() {
        return label_1;
    }

    public String getLabel_2() {
        return label_2;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public void setLabel_1(String label_1) {
        this.label_1 = label_1;
    }

    public void setLabel_2(String label_2) {
        this.label_2 = label_2;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

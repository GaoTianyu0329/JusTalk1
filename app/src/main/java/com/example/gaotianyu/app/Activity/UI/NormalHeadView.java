package com.example.gaotianyu.app.Activity.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.gaotianyu.app.R;
import com.jwenfeng.library.pulltorefresh.view.HeadView;

/**
 * Created by GaoTianyu on 2017/12/29.
 */

public class NormalHeadView extends FrameLayout implements HeadView {
    private TextView tv;



    public NormalHeadView(@NonNull Context context) {

        super(context);

        initView(context);

    }



    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_normal_head,null);

        tv = (TextView) view.findViewById(R.id.normal_head_text);

        addView(view);

    }



    @Override

    public void begin() {



    }



    @Override

    public void progress(float progress, float all) {

        if (progress >= all-10){

            tv.setText("松开刷新");

        }else{

            tv.setText("下拉加载");

        }

    }



    @Override

    public void finishing(float progress, float all) {



    }



    @Override

    public void loading() {

        tv.setText("刷新中...");

    }



    @Override

    public void normal() {

        tv.setText("下拉");

    }



    @Override

    public View getView() {

        return this;

    }
}

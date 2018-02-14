package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowActivity extends AppCompatActivity {
    private CircleImageView touxiang;
    private TextView nickName;
    private TextView time;
    private TextView title;
    private TextView label1;
    private TextView label2;
    private TextView main;
    private String kind;
    private PostList post;







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
            finish();
            break;
            default:
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        kind = intent.getStringExtra("kind");
        initView();
        post = (PostList)intent.getSerializableExtra("post_data");
        initData();

    }
    /*
    加载布局和控件
     */
    private void initView(){
        if (kind.equals("3")){
            setContentView(R.layout.activity_show3);
            label2 = (TextView) findViewById(R.id.label2);
        }else {
            setContentView(R.layout.activity_show12);
        }
        touxiang = (CircleImageView)findViewById(R.id.touxiang);
        nickName = (TextView) findViewById(R.id.nickname);
        time = (TextView) findViewById(R.id.time);
        label1 = (TextView) findViewById(R.id.label1);
        title = (TextView) findViewById(R.id.title);
        main = (TextView) findViewById(R.id.main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back);
        }
    }
    private void initData(){
        time.setText(post.getUpdatedAt());
        String label= post.getLabel();
        if (kind.equals("3")){
            for (int i=0;i<label.length();i++){
                if (label.charAt(i)=='#'){
                    label1.setText(label.substring(0,i));
                    label2.setText(label.substring(i+1));
                }
            }
        }else {
            label1.setText(label);
        }
        title.setText(post.getTitle());
        main.setText(post.getContent());


    }



}

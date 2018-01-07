package com.example.gaotianyu.app.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaotianyu.app.Activity.Fragment.GerenFragment;

import com.example.gaotianyu.app.Activity.Fragment.LifeFragment;
import com.example.gaotianyu.app.Activity.Fragment.SaishiFragment;
import com.example.gaotianyu.app.Activity.Fragment.StudyFragment;
import com.example.gaotianyu.app.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView text_exit;
    Toolbar toolbar;

private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }

/*
        ImageView button_message = (ImageView) findViewById(R.id.button_message);
        button_message.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent1);
            }
        });
        */
        ImageView button_saishi = (ImageView) findViewById(R.id.button_saishi);
        button_saishi.setOnClickListener(this);
        ImageView button_life = (ImageView) findViewById(R.id.button_life);
        button_life.setOnClickListener(this);
        ImageView button_study = (ImageView) findViewById(R.id.button_study);
        button_study.setOnClickListener(this);
        text_exit =(TextView) findViewById(R.id.text_exit);
        text_exit.setOnClickListener(this);
        button_saishi.callOnClick();
        /*ImageView button_geren = (ImageView) findViewById(R.id.button_geren);
        button_geren.setOnClickListener(this);
        button_geren.callOnClick();
        */
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View v){
        //TextView title = (TextView)findViewById(R.id.text_title);
        switch (v.getId()){
            case R.id.button_saishi:
                //title.setText("赛事");
                toolbar.setTitle("赛事");

                replaceFragment(new SaishiFragment());
                break;
            case R.id.button_life:
                //title.setText("生活助手");
                toolbar.setTitle("生活助手");
                replaceFragment(new LifeFragment());
                break;
            case R.id.button_study:
                //title.setText("学术交流");
                toolbar.setTitle("学术交流");
                replaceFragment(new StudyFragment());
                break;
            case R.id.text_exit:
                showNormalDialog();
                break;
                /*
            case R.id.button_geren:
                //title.setText("个人中心");
                replaceFragment(new GerenFragment());


           break;
           */
            default:
                break;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.button_message:
                Intent intent1 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent1);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;


        }
        return true;
    }
    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.drawable.ic_notifications_black_24dp);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("你要点击要退出吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = MainActivity.this.getSharedPreferences("userinfo", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        normalDialog.setNegativeButton("关闭", null);
        // 显示
        normalDialog.show();
    }


}

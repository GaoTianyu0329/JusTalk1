package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.gaotianyu.app.Activity.Fragment.LifeFragment;
import com.example.gaotianyu.app.Activity.Fragment.SaishiFragment;
import com.example.gaotianyu.app.Activity.Fragment.StudyFragment;
import com.example.gaotianyu.app.Activity.Tools.ButtonSlop;
import com.example.gaotianyu.app.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SaishiFragment saishiFragment;
    private LifeFragment lifeFragment;
    private StudyFragment studyFragment;
    private int nowFragment = 0;
    private final int SAISHIFRAGMENT = 0;
    private final int LIFEFRAGMENT = 1;
    private final int STUDYFRAGMNET = 2;
    private ImageView button_saishi;
    private ImageView button_life;
    private ImageView button_study;

private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
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
        button_saishi = (ImageView) findViewById(R.id.button_saishi);
        button_saishi.setOnClickListener(this);
        button_life = (ImageView) findViewById(R.id.button_life);
        button_life.setOnClickListener(this);
        button_study = (ImageView) findViewById(R.id.button_study);
        button_study.setOnClickListener(this);
        /*ImageView button_geren = (ImageView) findViewById(R.id.button_geren);
        button_geren.setOnClickListener(this);
        button_geren.callOnClick();
        */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (saishiFragment == null){
            saishiFragment = new SaishiFragment();
            transaction.add(R.id.fragment_layout, saishiFragment);
        }else {
            transaction.show(saishiFragment);
        }
        transaction.commit();
        button_saishi.setImageResource(R.mipmap.click1);
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
                if (nowFragment == SAISHIFRAGMENT){
                    return;
                }
                //title.setText("赛事");
                if (ButtonSlop.check(R.id.button_saishi)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showFragment(SAISHIFRAGMENT);
                button_saishi.setImageResource(R.mipmap.click1);
                //replaceFragment(new SaishiFragment());
                break;
            case R.id.button_life:
                if (nowFragment == LIFEFRAGMENT){
                    return;
                }
                if (ButtonSlop.check(R.id.button_life)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showFragment(LIFEFRAGMENT);
                button_life.setImageResource(R.mipmap.click2);
                //title.setText("生活助手");
                //replaceFragment(new LifeFragment());
                break;
            case R.id.button_study:
                if (nowFragment == STUDYFRAGMNET){
                    return;
                }
                if (ButtonSlop.check(R.id.button_study)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showFragment(STUDYFRAGMNET);
                button_study.setImageResource(R.mipmap.click3);
                //title.setText("学术交流");
                //replaceFragment(new StudyFragment());
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
        getMenuInflater().inflate(R.menu.toolbar_main,menu);
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
    private void showFragment(int x){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        orignalImage();
        switch (x){
            case SAISHIFRAGMENT:
                if (saishiFragment == null){
                    saishiFragment = new SaishiFragment();
                    transaction.add(R.id.fragment_layout, saishiFragment);
                }else {
                    transaction.show(saishiFragment);
                }
                break;
            case LIFEFRAGMENT:
                if (lifeFragment == null){
                    lifeFragment = new LifeFragment();
                    transaction.add(R.id.fragment_layout, lifeFragment);
                }else {
                    transaction.show(lifeFragment);
                }
                break;
            case STUDYFRAGMNET:
                if (studyFragment == null){
                    studyFragment = new StudyFragment();
                    transaction.add(R.id.fragment_layout, studyFragment);
                }else {
                    transaction.show(studyFragment);
                }
                break;
        }
        transaction.commit();
        nowFragment = x;

    }
    private void hideFragment(FragmentTransaction transaction){
        if(saishiFragment!=null){
            transaction.hide(saishiFragment);
        }
        if (lifeFragment!=null){
            transaction.hide(lifeFragment);
        }
        if (studyFragment!=null){
            transaction.hide(studyFragment);
        }
    }
    private void orignalImage(){
        button_saishi.setImageResource(R.mipmap.saishi);
        button_life.setImageResource(R.mipmap.life);
        button_study.setImageResource(R.mipmap.study);

    }

}

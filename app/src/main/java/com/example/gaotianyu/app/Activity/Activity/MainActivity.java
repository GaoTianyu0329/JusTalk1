package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        ImageView button_saishi = (ImageView) findViewById(R.id.button_saishi);
        button_saishi.setOnClickListener(this);
        ImageView button_life = (ImageView) findViewById(R.id.button_life);
        button_life.setOnClickListener(this);
        ImageView button_study = (ImageView) findViewById(R.id.button_study);
        button_study.setOnClickListener(this);
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

                replaceFragment(new SaishiFragment());
                break;
            case R.id.button_life:
                //title.setText("生活助手");
                replaceFragment(new LifeFragment());
                break;
            case R.id.button_study:
                //title.setText("学术交流");
                replaceFragment(new StudyFragment());
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

}

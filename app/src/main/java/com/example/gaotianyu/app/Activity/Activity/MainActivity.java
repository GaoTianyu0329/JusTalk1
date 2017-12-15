package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView button_message = (ImageView) findViewById(R.id.button_message);
        button_message.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent1);
            }
        });
        ImageButton button_saishi = (ImageButton) findViewById(R.id.button_saishi);
        button_saishi.setOnClickListener(this);
        ImageButton button_life = (ImageButton) findViewById(R.id.button_life);
        button_life.setOnClickListener(this);
        ImageButton button_study = (ImageButton) findViewById(R.id.button_study);
        button_study.setOnClickListener(this);
        ImageButton button_geren = (ImageButton) findViewById(R.id.button_geren);
        button_geren.setOnClickListener(this);
        button_geren.callOnClick();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }
    @Override
    public void onClick(View v){
        TextView title = (TextView)findViewById(R.id.text_title);
        switch (v.getId()){
            case R.id.button_saishi:
                title.setText("赛事");
                replaceFragment(new SaishiFragment());
                break;
            case R.id.button_life:
                title.setText("生活助手");
                replaceFragment(new LifeFragment());
                break;
            case R.id.button_study:
                title.setText("学术交流");
                replaceFragment(new StudyFragment());
                break;
            case R.id.button_geren:
                title.setText("个人中心");
                replaceFragment(new GerenFragment());
                break;
            default:
                break;
        }
    }

}

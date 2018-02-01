package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.User.UserInfo;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;

import cn.bmob.v3.Bmob;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {

   private final long time = 2000;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bmob.initialize(this, "7f9629f692893ec44d34032ddc4ad9bb");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);


        //Intent intent = new Intent(StartActivity.this, MainActivity.class);
        //startActivity(intent);
        //finish();
        try {


            if (UserManage.getInstance().hasUserInfo(this)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final UserInfo userInfo = UserManage.getInstance().getUserInfo(StartActivity.this);

                            String user_name = userInfo.getUserName();
                            String pwd = userInfo.getPassword();


                            OkHttpClient client = new OkHttpClient();
                            //上传数据
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("username", user_name)
                                    .add("password", pwd)
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://202.194.15.232:8088/App/login")//服务器网址
                                    .post(requestBody)
                                    .build();
                            try {
                                Looper.prepare();

                                //获得返回数据
                                Response response = client.newCall(request).execute();
                                String responseData = response.body().string();

                                Log.v("aaa", responseData);
                                if (responseData.equals("nosuchid")) {
                                    Toast.makeText(StartActivity.this, "该用户名不存在", Toast.LENGTH_SHORT).show();
                                    clear();
                                    handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                                        public void run() {
                                            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, time);
                                } else if (responseData.equals("false")) {
                                    Toast.makeText(StartActivity.this, "密码错误,请重新登录", Toast.LENGTH_SHORT).show();
                                    clear();
                                    handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                                        public void run() {
                                            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, time);
                                } else {

                                    Toast.makeText(StartActivity.this, "欢迎回来", Toast.LENGTH_SHORT).show();

                                    handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                                        public void run() {
                                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                            intent.putExtra("username", userInfo.getUserName());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, time);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                clear();
                                handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                                    public void run() {
                                        Toast.makeText(StartActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, time);


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            clear();

                            handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                                public void run() {
                                    Toast.makeText(StartActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, time);
                        }
                        Looper.loop();

                    }
                }).start();


            } else {

                handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                    public void run() {
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
            clear();
            handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

                public void run() {
                    Toast.makeText(StartActivity.this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, time);

        }
    }

    private void clear(){
        SharedPreferences sp = StartActivity.this.getSharedPreferences("userinfo",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
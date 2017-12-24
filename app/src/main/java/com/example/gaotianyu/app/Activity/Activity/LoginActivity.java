package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.gaotianyu.app.R.id.button_login;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText editText_user;


    private EditText editText_pwd;
    private TextView textView_signup;
    private TextView textView_forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_user = (EditText) findViewById(R.id.et_user);
        editText_pwd = (EditText) findViewById(R.id.et_pwd);
        textView_signup = (TextView) findViewById(R.id.textview_signup);
        textView_forget = (TextView) findViewById(R.id.textview_forget);
        Button button_login = (Button)findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Looper.prepare();
                            String user_name = editText_user.getText().toString();
                            String pwd = editText_pwd.getText().toString();
                            if(user_name.equals(null)||user_name.equals("")||pwd.equals(null)||pwd.equals("")){
                                Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
                            }else {
                                OkHttpClient client = new OkHttpClient();
                                //上传数据
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("username",user_name)
                                        .add("password",pwd)
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://202.194.15.232:8088/App/login")//服务器网址
                                        .post(requestBody)
                                        .build();
                                try{

                                    //获得返回数据
                                    Response response = client.newCall(request).execute();

                                    String responseData = response.body().string();
                                    Log.i(TAG, responseData );
                                    int id = 0;

                                    if (responseData.equals("nosuchid")){
                                        Toast.makeText(LoginActivity.this,"该用户名不存在",Toast.LENGTH_SHORT).show();
                                    }
                                    else if (responseData.equals("false")){
                                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        int length = responseData.length();
                                        id = Integer.parseInt(responseData.substring(4,length));
                                        Log.e(TAG, id+"" );
                                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        UserManage.getInstance().saveUserinfo(LoginActivity.this,user_name,pwd,id);
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("username",user_name);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Looper.loop();

                    }
                }).start();
            }
        });
        textView_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });

    }
}

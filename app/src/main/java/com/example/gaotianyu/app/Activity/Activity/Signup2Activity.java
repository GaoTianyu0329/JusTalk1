package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Tools.ButtonSlop;
import com.example.gaotianyu.app.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by GaoTianyu on 2018/2/21.
 */

public class Signup2Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_user;
    private EditText editText_pwd1;
    private EditText editText_pwd2;
    private EditText editText_nickname;
    private String user;
    private String pwd1;
    private String pwd2;
    private String phone;
    private String nickname;
    private Button button_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        editText_nickname = (EditText) findViewById(R.id.sign_nickname);
        editText_user = (EditText) findViewById(R.id.sign_user);
        editText_pwd1 = (EditText) findViewById(R.id.sign_pwd1);
        editText_pwd2 = (EditText) findViewById(R.id.sign_pwd2);
        nickname = editText_nickname.getText().toString();

        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_signup:
                if (ButtonSlop.check(R.id.button_signup)) {
                    Toast.makeText(Signup2Activity.this, "请稍后尝试", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = editText_user.getText().toString();
                pwd1 = editText_pwd1.getText().toString();
                pwd2 = editText_pwd2.getText().toString();
                if (user.equals(null) || user.equals("") || pwd1.equals(null) || pwd1.equals("")
                        || pwd2.equals(null) || pwd2.equals("")) {
                    Toast.makeText(Signup2Activity.this, "用户名或密码不得为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pwd1.equals(pwd2)) {
                        Toast.makeText(Signup2Activity.this, "两次输入的密码不同！", Toast.LENGTH_SHORT).show();
                    } else {
                        if (user.equals(null) || user.equals("") || pwd1.equals(null) || pwd1.equals("")
                                || pwd2.equals(null) || pwd2.equals("")) {
                            Toast.makeText(Signup2Activity.this, "用户名或密码不得为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!pwd1.equals(pwd2)) {
                                Toast.makeText(Signup2Activity.this, "两次输入的密码不同！", Toast.LENGTH_SHORT).show();
                            } else {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {


                                            OkHttpClient client = new OkHttpClient();
                                            //上传数据
                                            RequestBody requestBody = new FormBody.Builder()
                                                    .add("username", user)
                                                    .add("password", pwd1)
                                                    .add("phone", phone)
                                                    .add("nickname", nickname)
                                                    .build();
                                            Request request = new Request.Builder()
                                                    .url("http://202.194.15.232:8088/App/register")//服务器网址
                                                    .post(requestBody)
                                                    .build();
                                            try {
                                                //获得返回数据
                                                Response response = client.newCall(request).execute();
                                                String responseData = response.body().string();

                                                {
                                                    if (responseData.equals("true")) {
                                                        Toast.makeText(Signup2Activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Signup2Activity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    if (responseData.equals("false")) {
                                                        Toast.makeText(Signup2Activity.this, "用户名已被注册", Toast.LENGTH_SHORT).show();
                                                    }
                                                    if (responseData.equals("used")) {
                                                        Toast.makeText(Signup2Activity.this, "手机号已被注册", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();


                            }

                        }
                    }

                    break;
                }
        }
    }
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
}

package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Tools.ButtonSlop;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_user;
    private EditText editText_pwd1;
    private EditText editText_pwd2;
    private EditText editText_nickname;
    private Button button_signup;
    private EditText etPhoneNumber;    // 电话号码
    private Button sendmessage;  // 发送验证码
    private EditText etVerificationCode;  // 验证码
    private EventHandler eh;
    private boolean yanzheng = false;
    private String user;
    private String pwd1;
    private String pwd2;
    private String phone;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobSDK.init(this, "2309507a23889", "34656ba1883e630a8834fb48404f8225");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etVerificationCode = (EditText) findViewById(R.id.sign_message);
        editText_user = (EditText) findViewById(R.id.sign_user);
        editText_pwd1 = (EditText) findViewById(R.id.sign_pwd1);
        editText_pwd2 = (EditText) findViewById(R.id.sign_pwd2);
        etPhoneNumber = (EditText) findViewById(R.id.sign_phone);
        editText_nickname = (EditText) findViewById(R.id.sign_nickname);
        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(this);
        sendmessage = (Button) findViewById(R.id.sign_send);
        sendmessage.setOnClickListener(this);
        eh = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Looper.prepare();
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
                                                Toast.makeText(SigninActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            if (responseData.equals("false")) {
                                                Toast.makeText(SigninActivity.this, "用户名已被注册", Toast.LENGTH_SHORT).show();
                                            }
                                            if (responseData.equals("used")) {
                                                Toast.makeText(SigninActivity.this, "手机号已被注册", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Looper.loop();
                            }
                        }).start();
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(SigninActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };

        SMSSDK.registerEventHandler(eh);
    }

    protected void onDestroy()  {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_send:
                if (ButtonSlop.check(R.id.button_signup)) {
                    Toast.makeText(SigninActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                phone = etPhoneNumber.getText().toString();
                if(phone.equals("")||phone.equals(null)){
                    Toast.makeText(SigninActivity.this,"电话号不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if (phone.length()<11||phone.length()>12){
                        Toast.makeText(SigninActivity.this,"电话号格式不正确",Toast.LENGTH_SHORT).show();
                    }else {
                        SMSSDK.getVerificationCode("86",phone);
                    }
                }
                break;

            case R.id.button_signup:
                if (ButtonSlop.check(R.id.button_signup)) {
                    Toast.makeText(SigninActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }

                user = editText_user.getText().toString();
                pwd1 = editText_pwd1.getText().toString();
                pwd2 = editText_pwd2.getText().toString();
                phone = etPhoneNumber.getText().toString();
                nickname = editText_nickname.getText().toString();
                if (user.equals(null) || user.equals("")|| pwd1.equals(null) || pwd1.equals("")
                        || pwd2.equals(null) || pwd2.equals("")){
                    Toast.makeText(SigninActivity.this,"用户名或密码不得为空！",Toast.LENGTH_SHORT).show();
                }else{
                    if (!pwd1.equals(pwd2)){
                        Toast.makeText(SigninActivity.this,"两次输入的密码不同！",Toast.LENGTH_SHORT).show();
                    }else{
                        String number = etVerificationCode.getText().toString();
                        SMSSDK.submitVerificationCode("86",phone,number);
                    }
                }
                break;
        }
    };

}


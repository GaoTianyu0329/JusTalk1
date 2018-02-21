package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Tools.ButtonSlop;
import com.example.gaotianyu.app.R;
import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Signup1Activity extends AppCompatActivity implements View.OnClickListener {
    private Button button_signup;
    private EditText etPhoneNumber;    // 电话号码
    private TextView sendmessage;  // 发送验证码
    private EditText etVerificationCode;  // 验证码
    private EventHandler eh;
    private boolean yanzheng = false;

    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobSDK.init(this, "2309507a23889", "34656ba1883e630a8834fb48404f8225");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etVerificationCode = (EditText) findViewById(R.id.sign_message);

        etPhoneNumber = (EditText) findViewById(R.id.sign_phone);

        button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(this);
        sendmessage = (TextView) findViewById(R.id.sign_send);
        sendmessage.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back);
        }
        eh = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Intent intent = new Intent(Signup1Activity.this,Signup2Activity.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);

                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(Signup1Activity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
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

    protected void onDestroy()  {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_send:
                if (ButtonSlop.check(R.id.button_signup)) {
                    Toast.makeText(Signup1Activity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                phone = etPhoneNumber.getText().toString();
                if(phone.equals("")||phone.equals(null)){
                    Toast.makeText(Signup1Activity.this,"电话号不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if (phone.length()<11||phone.length()>12){
                        Toast.makeText(Signup1Activity.this,"电话号格式不正确",Toast.LENGTH_SHORT).show();
                    }else {
                        SMSSDK.getVerificationCode("86",phone);
                    }
                }
                break;

            case R.id.button_signup:
                if (ButtonSlop.check(R.id.button_signup)) {
                    Toast.makeText(Signup1Activity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etPhoneNumber.equals(null)){
                    Toast.makeText(Signup1Activity.this,"验证码为空",Toast.LENGTH_SHORT).show();
                }else {
                    phone = etPhoneNumber.getText().toString();
                    String number = etVerificationCode.getText().toString();
                    SMSSDK.submitVerificationCode("86",phone,number);
                }
                break;




        }

    }

}


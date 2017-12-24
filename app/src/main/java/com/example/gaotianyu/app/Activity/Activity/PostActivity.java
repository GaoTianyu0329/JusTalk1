package com.example.gaotianyu.app.Activity.Activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.User.UserInfo;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;

import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {
private EditText postTitle;
private EditText postMian;
private Button button_post;
private Spinner spinner1;
private Spinner spinner2;
private int id ;
private int kind;
private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         Intent intent = getIntent();
         kind = intent.getIntExtra("kind",0);
        Log.e("aaa", kind+"" );
        super.onCreate(savedInstanceState);
        switch (kind){
            case 1:
                setContentView(R.layout.activity_post1);
                spinner1 = (Spinner)findViewById(R.id.atPost_label_1);
                url="";
                break;
            case 2:
                setContentView(R.layout.activity_post2);
                spinner1 = (Spinner)findViewById(R.id.atPost_label_2);
                url="";
                break;
            case 3:
                setContentView(R.layout.activity_post3);
                spinner1 = (Spinner)findViewById(R.id.atPost_label_3);
                spinner2 = (Spinner)findViewById(R.id.atPost_label_4);
                url="";
                break;
        }

        final UserInfo userInfo = UserManage.getInstance().getUserInfo(PostActivity.this);
        final String id = userInfo.getId()+"";

        init();
        button_post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Looper.prepare();
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            String label1 = spinner1.getSelectedItem().toString();
                            String label2 = spinner2.getSelectedItem().toString();
                            String title = postTitle.getText().toString();
                            String main = postMian.getText().toString();
                            if (title.equals(null)||title.equals("")){
                                Toast.makeText(PostActivity.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                            }else if (main.equals(null)||main.equals("")){
                                Toast.makeText(PostActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                            }else {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody;
                                //上传数据
                                if(kind ==3){
                                    requestBody = new FormBody.Builder()
                                            .add("userid",id)
                                            .add("title",title)
                                            .add("main",main)
                                            .add("label1",label1)
                                            .add("label2",label2)
                                            .add("time",year+"-"+month+"-"+day)
                                            .build();
                                    Request request = new Request.Builder()
                                            .url("")//服务器网址
                                            .post(requestBody)
                                            .build();
                                }else {
                                    requestBody = new FormBody.Builder()
                                        .add("userid",id)
                                        .add("title",title)
                                        .add("main",main)
                                        .add("label",label1)
                                        .add("time",year+"-"+month+"-"+day)
                                        .build();
                                    Request request = new Request.Builder()
                                            .url("")//服务器网址
                                            .post(requestBody)
                                            .build();

                                }

                                Request request = new Request.Builder()
                                        .url("")//服务器网址
                                        .post(requestBody)
                                        .build();
                                try{

                                    //获得返回数据
                                    Response response = client.newCall(request).execute();

                                    String responseData = response.body().string();


                                    if (responseData.equals("nosuchid")){
                                        Toast.makeText(PostActivity.this,"",Toast.LENGTH_SHORT).show();
                                    }
                                    else if (responseData.equals("false")){
                                        Toast.makeText(PostActivity.this,"",Toast.LENGTH_SHORT).show();
                                    }
                                    if (responseData.equals("true")){
                                        Toast.makeText(PostActivity.this,"",Toast.LENGTH_SHORT).show();


                                        Intent intent = new Intent(PostActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }catch (Exception e){
                            e.getStackTrace();
                        }
                        Looper.loop();

                    }

                }).start();
            }

        });

    }
    private void init(){
        postTitle = (EditText)findViewById(R.id.post_title);
        postMian = (EditText)findViewById(R.id.post_main);
        button_post = (Button)findViewById(R.id.button_post);

    }
}






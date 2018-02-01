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

import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.Activity.Tools.ButtonSlop;
import com.example.gaotianyu.app.Activity.User.UserInfo;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;

import java.util.Calendar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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

private String kind;
private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
<<<<<<< HEAD

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
=======
>>>>>>> parent of 06bc97a... 将界面改为全屏，短信验证码的问题应该已经解决了，刷新的控件改为庄大佬的那个

         Intent intent = getIntent();
         kind = intent.getStringExtra("kind");
        Log.e("aaa", kind+"" );
        url= "http://202.194.15.232:8088/App/addarticle";
        super.onCreate(savedInstanceState);

        switch (Integer.parseInt(kind)){
            case 1:
                setContentView(R.layout.activity_post1);
                spinner1 = (Spinner)findViewById(R.id.atPost_label_1);
                //url="";
                break;
            case 2:
                setContentView(R.layout.activity_post2);
                spinner1 = (Spinner)findViewById(R.id.atPost_label_2);
                //url="";
                break;
            case 3:
                setContentView(R.layout.activity_post3);
                spinner1 = (Spinner)findViewById(R.id.atPost_label_3);
                spinner2 = (Spinner)findViewById(R.id.atPost_label_4);
                //url="";
                break;
        }

        final UserInfo userInfo = UserManage.getInstance().getUserInfo(PostActivity.this);
        final String userid = userInfo.getId()+"";

        init();
        button_post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (ButtonSlop.check(R.id.button_signup)) {
                    //Toast.makeText(PostActivity.this,"请稍后尝试",Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Looper.prepare();
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH)+1;
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            String label1 = spinner1.getSelectedItem().toString();

                            if (kind.equals(3)){
                                label1 +="#"+spinner2.getSelectedItem().toString();
                            }

                            String title = postTitle.getText().toString();
                            String main = postMian.getText().toString();
                            if (title.equals(null)||title.equals("")){
                                Toast.makeText(PostActivity.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                            }else if (main.equals(null)||main.equals("")){
                                Toast.makeText(PostActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                            }else {
                                PostList postList = new PostList();
                                postList.setUerid(userid);
                                postList.setKind(kind);
                                postList.setTitle(title);
                                postList.setContent(main);
                                postList.setTime(year+"-"+month+"-"+day);
                                postList.setLabel(label1);
                                postList.save(new SaveListener<String>() {

                                    @Override
                                    public void done(String objectId, BmobException e) {
                                        if(e==null){
                                            Toast.makeText(PostActivity.this,"发帖成功",Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(PostActivity.this,"发帖失败",Toast.LENGTH_SHORT).show();

                                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                        }
                                    }
                                });

                                /*
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody;
                                //上传数据
                                Log.e("YYYYY", userid );
                                Log.e("YYYYY", title );
                                Log.e("YYYYY", main );
                                Log.e("YYYYY", year+"");
                                Log.e("YYYYY", month+"" );
                                Log.e("YYYYY", day+"" );
                                Log.e("YYYYY", kind );
                                Log.e("YYYYY", label1 );
                                    requestBody = new FormBody.Builder()
                                        .add("userid",userid)
                                        .add("title",title)
                                        .add("main",main)
                                        .add("label",label1)
                                            .add("kind",kind)
                                        .add("time",year+"-"+month+"-"+day)
                                        .build();
                                    Request request = new Request.Builder()
                                            .url(url)//服务器网址
                                            .post(requestBody)
                                            .build();




                                try{

                                    //获得返回数据
                                    Response response = client.newCall(request).execute();

                                    String responseData = response.body().string();
                                    Log.e("YYYYY", responseData );


                                    if (responseData.equals("fail")){
                                        Toast.makeText(PostActivity.this,"发帖失败",Toast.LENGTH_SHORT).show();
                                    }
                                    else if (responseData.equals("success")){
                                        Toast.makeText(PostActivity.this,"发帖成功",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PostActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                */
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
        button_post = (Button)findViewById(R.id.buttonpost);

    }
}






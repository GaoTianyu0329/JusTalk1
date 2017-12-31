package com.example.gaotianyu.app.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gaotianyu.app.Activity.Activity.PostActivity;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_1;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_3;
import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.Activity.PostList.PostList_3;
import com.example.gaotianyu.app.Activity.PostList.PostList_3;
import com.example.gaotianyu.app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by GaoTianyu on 2017/12/1.
 */

public class StudyFragment extends Fragment {
    private List<PostList_3> postList = new ArrayList<>();
    PostAdapter_3 postAdapter_3;
    private Button button;
    private String url_onOreate;
    RecyclerView recyclerView_study;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){
        url_onOreate = "http://202.194.15.232:8088/App/showlist";
        View view = inflater.inflate(R.layout.fragment_study,contianer,false);
        recyclerView_study = (RecyclerView) view.findViewById(R.id.recyclerView_study);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_study.setLayoutManager(layoutManager);
        postAdapter_3 = new PostAdapter_3(postList);
        recyclerView_study.setAdapter(postAdapter_3);
        button = (Button)view.findViewById(R.id.button_post);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("kind","3");
                startActivity(intent);
            }

        });
        input();
        return view;

}
    private void input(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody;
                    //上传数据

                    requestBody = new FormBody.Builder()
                            .add("kind","3")
                            .build();
                    Request request = new Request.Builder()
                            .url(url_onOreate)//服务器网址
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        Log.e("SaishiFragment", "run: "+responseData );
                        parseJSONWithGSON(responseData);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private void parseJSONWithGSON(String jsonData){
        try {
            Gson gson = new Gson();
            List<PostList_3> mpostList = gson.fromJson(jsonData,new TypeToken<List<PostList_3>>(){}.getType());


            for (PostList_3 list : mpostList){
                Log.e("saishi", "parseJSONWithGSON: "+list );
                postList.add(list);

                //postAdapter_2.notifyDataSetChanged();

            }
            getActivity().runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    initAdapter();
                }
            });
            //postAdapter_2.notifyDataSetChanged();

        }catch (Exception e){

        }
    }
    private void initAdapter(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_study.setLayoutManager(layoutManager);
        postAdapter_3 = new PostAdapter_3(postList);
        recyclerView_study.setAdapter(postAdapter_3);

    }
}

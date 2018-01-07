package com.example.gaotianyu.app.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gaotianyu.app.Activity.Activity.PostActivity;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_2;
import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.Activity.PostList.PostList_2;
import com.example.gaotianyu.app.Activity.UI.NormalPullToRefreshLayout;
import com.example.gaotianyu.app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

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

public class LifeFragment extends Fragment {
    private List<PostList> postList = new ArrayList<>();
    PostAdapter_2 postAdapter_2;
    private Button button;
    private String url_onOreate;
    private NormalPullToRefreshLayout normalPullToRefreshLayout;
    private RecyclerView recyclerView_life;
final String TAG = " LifeFragment ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){
        url_onOreate = "http://202.194.15.232:8088/App/showlist";
        View view = inflater.inflate(R.layout.fragment_life,contianer,false);
        //normalPullToRefreshLayout = (NormalPullToRefreshLayout) view.findViewById(R.id.refreshlayout);
        recyclerView_life = (RecyclerView) view.findViewById(R.id.recyclerView_life);
        input();
        button = (Button)view.findViewById(R.id.button_post);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("kind","2");
                startActivity(intent);
            }

        });
        /*normalPullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                       normalPullToRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束加载更多
                        normalPullToRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
        */

        //postAdapter_2.notifyDataSetChanged();

        //RecyclerView recyclerView_life = (RecyclerView) view.findViewById(R.id.recyclerView_life);

        return view;
    }
    private void input(){
        /*
        PostList_2 postList1 = new PostList_2("a","2017/12/12","c","b","c","d");
        postList.add(postList1);
        PostList_2 postList12 = new PostList_2("a","2017/12/12","c","b","c","d");
        postList.add(postList12);
        PostList_2 postList13 = new PostList_2("a","2017/12/12","c","b","c","d");
        postList.add(postList13);
        */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody;
                    //上传数据

                    requestBody = new FormBody.Builder()
                            .add("kind","2")
                            .build();
                    Request request = new Request.Builder()
                            .url(url_onOreate)//服务器网址
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        Log.e(TAG, "run: "+responseData );
                        parseJSONWithGSON(responseData);

                    }catch (Exception e){
e.printStackTrace();
                    }
                }catch (Exception e){
e.printStackTrace();
                }
            }
        }).start();





       /* PostList_2 postList1 = new PostList_2("a","2017/12/12","c");
        postList.add(postList1);

        postAdapter_2.notifyDataSetChanged();
        */

    }
    private void parseJSONWithGSON(String jsonData){
        try {
            Gson gson = new Gson();
            List<PostList_2> mpostList = gson.fromJson(jsonData,new TypeToken<List<PostList_2>>(){}.getType());


            for (PostList_2 list : mpostList){
                Log.e(TAG, "parseJSONWithGSON: "+list );
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
        recyclerView_life.setLayoutManager(layoutManager);
        postAdapter_2 = new PostAdapter_2(postList);
        recyclerView_life.setAdapter(postAdapter_2);

    }

}

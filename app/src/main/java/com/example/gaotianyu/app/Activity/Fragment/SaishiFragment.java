package com.example.gaotianyu.app.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Activity.PostActivity;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_1;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_2;
import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.Activity.PostList.PostList_2;
import com.example.gaotianyu.app.Activity.RecyclerViewpack.PullRefreshLayout;
import com.example.gaotianyu.app.Activity.UI.NormalPullToRefreshLayout;
import com.example.gaotianyu.app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by GaoTianyu on 2017/12/1.
 */

public class SaishiFragment extends Fragment {
    private List<PostList> postList = new ArrayList<>();
    public PullRefreshLayout refreshLayout;
    PostAdapter_1 postAdapter_1;
    private Button button;
    private boolean isRefreshing = false;
    private boolean isLoading = false;
    private List<String> postIdList = new ArrayList<>();
    RecyclerView recyclerView_saishi;
    private Thread refreshThread, loadThread;
    private String url_onOreate;
    private NormalPullToRefreshLayout normalPullToRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){
        url_onOreate = "http://202.194.15.232:8088/App/showlist";
        View view = inflater.inflate(R.layout.fragment_saishi,contianer,false);
        normalPullToRefreshLayout = (NormalPullToRefreshLayout) view.findViewById(R.id.refreshlayout);
        recyclerView_saishi = (RecyclerView) view.findViewById(R.id.recyclerView_saishi);
        input();
        button = (Button)view.findViewById(R.id.button_post);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("kind","1");
                startActivity(intent);
            }

        });
        normalPullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
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
                            .add("kind","1")
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





       /* PostList_2 postList1 = new PostList_2("a","2017/12/12","c");
        postList.add(postList1);

        postAdapter_2.notifyDataSetChanged();
        */

    }
    private void parseJSONWithGSON(String jsonData){
        try {
            Gson gson = new Gson();
            List<PostList_1> mpostList = gson.fromJson(jsonData,new TypeToken<List<PostList_1>>(){}.getType());


            for (PostList_1 list : mpostList){
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
        recyclerView_saishi.setLayoutManager(layoutManager);
        postAdapter_1 = new PostAdapter_1(postList);
        recyclerView_saishi.setAdapter(postAdapter_1);

    }
/*
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_saishi,contianer,false);
        recyclerView_saishi = (RecyclerView) view.findViewById(R.id.recyclerView_saishi);
        refreshLayout = (PullRefreshLayout) view.findViewById(R.id.swipe_order_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_saishi.setLayoutManager(layoutManager);
        postAdapter_1 = new PostAdapter_1(postList);
        recyclerView_saishi.setAdapter(postAdapter_1);
        button = (Button)view.findViewById(R.id.button_post);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("kind","1");
                startActivity(intent);
            }

        });
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshOrder();
            }
        });

        refreshLayout.setOnLoadListener(new PullRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadOrder();
            }
        });
        input();
        return view;
    }
    private void input(){

    }
    public void removeFromOrderList(int position) {
        postList.remove(position);
        postAdapter_1.notifyDataSetChanged();
    }


    public void refreshOrder() {
        recyclerView_saishi.scrollToPosition(0);
//        orderRecyclerView.setNestedScrollingEnabled(false);


        refreshLayout.setRefreshing(true);
        isRefreshing = true;

        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response;
                Looper.prepare();
                try {






                } catch (NullPointerException e) {
                    e.printStackTrace();
                } /*catch (IOException e) {
                    e.printStackTrace();
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.handleMessage(message);
                }
                */
/*
                Looper.loop();

            }

        });
        refreshThread.start();
    }

    private void loadOrder() {
        if (postIdList.size() == 0) {
            refreshLayout.setLoading(false);
            return;
        }

        isLoading = true;
        if (postIdList.size() > 5) {
            loadOrder(postIdList.get(0), 0, 4);
        } else {
            loadOrder(postIdList.get(0), 0, postIdList.size() - 1);
        }

    }

    private void loadOrder(final String orderId, final int cur, final int des) {
        if (cur > des) {
            for (int i = 0; i <= des; i++) {
                postIdList.remove(0);
            }
            Message message = new Message();
            message.what = 2;
            message.arg1 = cur;
            message.arg2 = des;
            handler.handleMessage(message);
            return;
        }
        loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response;

                Looper.prepare();


                try {





                } catch (NullPointerException e) {
                    e.printStackTrace();
                } /*catch (IOException e) {
                    e.printStackTrace();
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.handleMessage(message);

                }
                */
/*
                Looper.loop();

            }

        });
        loadThread.start();
    }
    /**
     * 是否正在刷新订单
     */
   /* public boolean isRefreshing() {
        return isRefreshing;
    }

    /**
     * 是否正在加载订单
     */
   /*
    public boolean isLoading() {
        return isLoading;
    }
    private void fillRefreshOrderInfo(List<String> list) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        postIdList = removeSameByOrderId(list);

        /**
         * 更新最新订单信息
         */
   /*
        String latestOrderId = "";
        if (postIdList.size() > 0) {
            latestOrderId = postIdList.get(0);
            SharedPreferences.Editor editor = getContext().getSharedPreferences("order_cache",
                    Context.MODE_PRIVATE).edit();
            editor.putString("order_id", latestOrderId);
            editor.apply();

            loadOrder();
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isRefreshing) {
                        refreshLayout.setRefreshing(false);
                        isRefreshing = false;
                    }
                    postAdapter_1.notifyItemRangeRemoved(0, 0);
                }
            });
        }

    }
    private void fillLoadOrderInfo(final Message msg) {
        if (msg.arg1 > msg.arg2) {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefreshing) {
                            refreshLayout.setRefreshing(false);
                            isRefreshing = false;
                        }
                        postAdapter_1.setMpostList(postList);
                        int startPos = postAdapter_1.getItemCount();
                        postAdapter_1.notifyItemRangeInserted(startPos, postList.size());

                        //滑动到下一个
                        recyclerView_saishi.scrollToPosition(postList.size() - msg.arg2 - 1);

                        if (isLoading) {
                            refreshLayout.setLoading(false);
                        }
                        isLoading = false;
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } else {
            String json = (String) msg.obj;

            //解析JSON对象
            if (json != null && !json.equals("null")) {




            }

            if (msg.arg1 + 1 >= postIdList.size()) {
                loadOrder(postIdList.get(msg.arg1), msg.arg1 + 1, msg.arg2);
            } else {
                loadOrder(postIdList.get(msg.arg1 + 1), msg.arg1 + 1, msg.arg2);
            }

        }
    }
    private List<String> removeSameByOrderId(List<String> list) {
        List<String> stringList = list;
        int i = 0;
        while (i < stringList.size() - 1) {
            int j = i + 1;
            while (j < stringList.size()) {
                if (stringList.get(i).equals(stringList.get(j))) {
                    stringList.remove(j);
                } else {
                    j++;
                }
            }
            i++;
        }
        return stringList;
    }
    private static class MyHandler extends Handler {

        WeakReference<SaishiFragment> fragmentWeakReference;

        public MyHandler(SaishiFragment fragment) {
            this.fragmentWeakReference = new WeakReference<SaishiFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        fragmentWeakReference.get().fillRefreshOrderInfo((List<String>) msg.obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    Toast.makeText(fragmentWeakReference.get().getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    fragmentWeakReference.get().fillLoadOrderInfo(msg);
                    break;
            }
        }
    }
*/

}

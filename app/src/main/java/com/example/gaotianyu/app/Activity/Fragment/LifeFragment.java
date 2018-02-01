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
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
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
    //private NormalPullToRefreshLayout normalPullToRefreshLayout;
    private PtrClassicFrameLayout ptrLayout;
    private RecyclerView recyclerView_life;
final String TAG = " LifeFragment ";
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多

    private int limit = 10; // 每页的数据是10条
    private int curPage = 0; // 当前页的编号，从0开始
    String lastTime = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){
        url_onOreate = "http://202.194.15.232:8088/App/showlist";
        View view = inflater.inflate(R.layout.fragment_life,contianer,false);
        //normalPullToRefreshLayout = (NormalPullToRefreshLayout) view.findViewById(R.id.refreshlayout);
        ptrLayout = (PtrClassicFrameLayout)  view.findViewById(R.id.refreshlayout);
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

    @Override
    public void onResume() {
        super.onResume();


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
    private void initEvent() {
        // 为布局设置下拉刷新和上拉加载的回调事件
        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) { // 上拉加载的回调方法
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queryData(curPage, STATE_MORE);
                        postAdapter_2.notifyDataSetChanged();
                        ptrLayout.refreshComplete();
                        recyclerView_life.smoothScrollToPosition(postList.size() - 1);
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) { // 下拉刷新的回调方法
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queryData(0, STATE_REFRESH);

                        postAdapter_2.notifyDataSetChanged();
                        ptrLayout.refreshComplete();
                        recyclerView_life.smoothScrollToPosition(0);
                    }
                }, 1000);
            }
        });
    }
    private void queryData(int page, final int actionType) {
        Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:"
                + actionType);

        BmobQuery<PostList> query = new BmobQuery<>();
        // 按时间降序查询
        query.order("-createdAt");
        int count = 0;
        // 如果是加载更多
        if (actionType == STATE_MORE) {
            // 处理时间查询
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lastTime);
                Log.i("0414", date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 只查询小于等于最后一个item发表时间的数据
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            // 跳过之前页数并去掉重复数据
            query.setSkip(page * count + 1);
        } else {
            // 下拉刷新
            page = 0;
            query.setSkip(page);
        }
        // 设置每页数据个数
        query.setLimit(limit);
        // 查找数据
        query.findObjects( new FindListener<PostList>() {
            @Override
            public void done(List<PostList> list, BmobException e) {
                if(e==null){
                    if (list.size() > 0) {

                        if (actionType == STATE_REFRESH) {
                            // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
                            curPage = 0;
                            postList.clear();
                            // 获取最后时间
                            lastTime = list.get(list.size() - 1).getCreatedAt();
                        }

                        // 将本次查询的数据添加到bankCards中
                        for (PostList td : list) {
                            postList.add(td);
                        }

                        // 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
                        curPage++;
//					 showToast("第"+(page+1)+"页数据加载完成");
                    } else if (actionType == STATE_MORE) {
                        showToast("没有更多数据了");
                    } else if (actionType == STATE_REFRESH) {
                        showToast("没有数据");
                    }

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}

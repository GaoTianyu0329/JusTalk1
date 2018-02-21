package com.example.gaotianyu.app.Activity.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gaotianyu.app.Activity.Adapter.PostAdapter;
import com.example.gaotianyu.app.Activity.Collection;
import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.Activity.User.UserInfo;
import com.example.gaotianyu.app.Activity.User.UserManage;
import com.example.gaotianyu.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class CollectionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<PostList> colllectionList = new ArrayList<>();
    PostAdapter postAdapter;
    LinearLayoutManager layoutManager;
    private PtrClassicFrameLayout ptrLayout;
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多

    private int limit = 10; // 每页的数据是10条
    private int curPage = 0; // 当前页的编号，从0开始
    String lastTime = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ptrLayout = (PtrClassicFrameLayout)  findViewById(R.id.refreshlayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(CollectionActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAdapter();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initEvent();
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
    private void initAdapter(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(CollectionActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(colllectionList);
        recyclerView.setAdapter(postAdapter);

    }
    private void getCollection(){
        final UserInfo userInfo = UserManage.getInstance().getUserInfo(CollectionActivity.this);
        final String userid = userInfo.getId()+"";
        BmobQuery<Collection> query = new BmobQuery<Collection>();
        query.addWhereEqualTo("userID",userid);
        query.setLimit(30);
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> object, BmobException e) {
                if(e==null){

                    for (Collection collection : object) {
                        //获得playerName的信息
                        getPost(collection.getPostID());
                    }
                    initAdapter();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void getPost(String objectId){
        BmobQuery<PostList> query = new BmobQuery<PostList>();
        query.getObject(objectId, new QueryListener<PostList>() {

            @Override
            public void done(PostList object, BmobException e) {
                if(e==null){
                    colllectionList.add(object);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });

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
                        postAdapter.notifyDataSetChanged();
                        ptrLayout.refreshComplete();
                        recyclerView.smoothScrollToPosition(colllectionList.size() - 1);
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) { // 下拉刷新的回调方法
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queryData(0, STATE_REFRESH);
                        postAdapter.notifyDataSetChanged();
                        ptrLayout.refreshComplete();
                        recyclerView.smoothScrollToPosition(0);
                    }
                }, 1000);
            }
        });
    }
    private void queryData(int page, final int actionType) {
        Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:"
                + actionType);

        final UserInfo userInfo = UserManage.getInstance().getUserInfo(CollectionActivity.this);
        final String userid = userInfo.getId()+"";
        BmobQuery<Collection> query = new BmobQuery<Collection>();
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
        //final int finalPage = page;
        final int finalPage = page;
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if(e==null){
                    if (list.size() > 0) {

                        if (actionType == STATE_REFRESH) {
                            // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
                            curPage = 0;
                            colllectionList.clear();
                            // 获取最后时间
                            lastTime = list.get(list.size() - 1).getCreatedAt();
                        }

                        // 将本次查询的数据添加到bankCards中
                        for (Collection td : list) {
                            getPost(td.getPostID());
                        }

                        // 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
                        curPage++;

                        showToast("第"+(finalPage +1)+"页数据加载完成");
                    } else if (actionType == STATE_MORE) {
                        showToast("没有更多数据了");
                    } else if (actionType == STATE_REFRESH) {
                        showToast("没有数据");
                    }
                    ptrLayout.refreshComplete();

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    ptrLayout.refreshComplete();
                }
            }

        });

    }


    private void showToast(String msg) {
        Toast.makeText(CollectionActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

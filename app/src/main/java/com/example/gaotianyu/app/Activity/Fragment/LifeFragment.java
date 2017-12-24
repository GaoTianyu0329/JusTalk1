package com.example.gaotianyu.app.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gaotianyu.app.Activity.Activity.PostActivity;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_1;
import com.example.gaotianyu.app.Activity.Adapter.PostAdapter_2;
import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.Activity.PostList.PostList_2;
import com.example.gaotianyu.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoTianyu on 2017/12/1.
 */

public class LifeFragment extends Fragment {
    private List<PostList_2> postList = new ArrayList<>();
    PostAdapter_2 adapter_lianxiren;
    private Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contianer, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_life,contianer,false);
        RecyclerView recyclerView_saishi = (RecyclerView) view.findViewById(R.id.recyclerView_life);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_saishi.setLayoutManager(layoutManager);
        adapter_lianxiren = new PostAdapter_2(postList);
        recyclerView_saishi.setAdapter(adapter_lianxiren);
        button = (Button)view.findViewById(R.id.button_post);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("kind",2);
                startActivity(intent);
            }

        });
        input();
        return view;
    }
    private void input(){
        PostList_2 postList1 = new PostList_2("a","2017/12/12","c");
        postList.add(postList1);
        PostList_2 postList2 = new PostList_2("aaa","bbb","ccc");
        postList.add(postList2);
        PostList_2 postList3= new PostList_2("abc","bbb","cab");
        postList.add(postList3);
        adapter_lianxiren.notifyDataSetChanged();
    }
}

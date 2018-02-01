package com.example.gaotianyu.app.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.Activity.PostList.PostList_2;
import com.example.gaotianyu.app.R;

import java.util.List;

/**
 * Created by GaoTianyu on 2017/12/12.
 */

public class PostAdapter_2 extends RecyclerView.Adapter<PostAdapter_2.ViewHolder> {
    private List<PostList> postList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView label;
        TextView time;
        TextView title;
        View listView;
        public ViewHolder(View view){
            super(view);
            listView=view;
            label=(TextView) view.findViewById(R.id.postlist_label);
            time = (TextView)view.findViewById(R.id.postlist_time);
            title=(TextView)view.findViewById(R.id.postlist_title);
        }
    }
    public PostAdapter_2(List<PostList> postList){
        this.postList=postList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_postlist,parent,false);
        final PostAdapter_2.ViewHolder holder = new PostAdapter_2.ViewHolder(view);
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PostList postList2 = postList.get(position);
                //添加点击跳转

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        PostList postList_2 = postList.get(position);
        holder.label.setText("["+postList_2.getLabel()+"]");
        holder.time.setText(postList_2.getCreatedAt());
        holder.title.setText(postList_2.getTitle());
    }
    @Override
    public int getItemCount(){
        return postList.size();
    }

}

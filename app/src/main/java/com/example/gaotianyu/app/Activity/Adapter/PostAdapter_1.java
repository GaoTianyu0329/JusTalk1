package com.example.gaotianyu.app.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.R;

import java.util.List;

/**
 * Created by GaoTianyu on 2017/12/5.
 */

public class PostAdapter_1 extends RecyclerView.Adapter<PostAdapter_1.ViewHolder> {
    private List<PostList_1> postList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView label;
        TextView time;
        TextView title;
        View listView;
        public ViewHolder(View view){
            super(view);
            listView =view;
            label=(TextView) view.findViewById(R.id.postlist_label);
            time = (TextView)view.findViewById(R.id.postlist_time);
            title=(TextView)view.findViewById(R.id.postlist_title);
        }
    }
    public PostAdapter_1(List<PostList_1> postList){
        this.postList=postList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_postlist,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        PostList_1 postList_1 = postList.get(position);
        holder.label.setText(postList_1.getLabel());
        holder.time.setText(postList_1.getTime());
        holder.title.setText(postList_1.getTitle());
    }
    @Override
    public int getItemCount(){
        return postList.size();
    }

}

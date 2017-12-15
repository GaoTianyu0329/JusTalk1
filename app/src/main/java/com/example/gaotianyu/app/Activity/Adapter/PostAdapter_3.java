package com.example.gaotianyu.app.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.Activity.PostList.PostList_3;
import com.example.gaotianyu.app.R;

import java.util.List;

/**
 * Created by GaoTianyu on 2017/12/12.
 */

public class PostAdapter_3 extends RecyclerView.Adapter<PostAdapter_3.ViewHolder> {
    private List<PostList_3> postList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView label;
        TextView time;
        TextView title;
        public ViewHolder(View view){
            super(view);
            label=(TextView) view.findViewById(R.id.postlist_label);
            time = (TextView)view.findViewById(R.id.postlist_time);
            title=(TextView)view.findViewById(R.id.postlist_title);
        }
    }
    public PostAdapter_3(List<PostList_3> postList){
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
        PostList_3 postList_3 = postList.get(position);
        holder.label.setText(postList_3.getLabel_1()+" "+postList_3.getLabel_2());
        holder.time.setText(postList_3.getTime());
        holder.title.setText(postList_3.getTitle());
    }
    @Override
    public int getItemCount(){
        return postList.size();
    }

}
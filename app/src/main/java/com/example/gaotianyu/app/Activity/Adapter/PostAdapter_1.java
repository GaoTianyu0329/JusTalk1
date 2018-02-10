package com.example.gaotianyu.app.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaotianyu.app.Activity.Activity.MainActivity;
import com.example.gaotianyu.app.Activity.Activity.ShowActivity;
import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.Activity.PostList.PostList_1;
import com.example.gaotianyu.app.R;

import java.util.List;

/**
 * Created by GaoTianyu on 2017/12/5.
 */

public class PostAdapter_1 extends RecyclerView.Adapter<PostAdapter_1.ViewHolder> {
    private List<PostList> mpostList;
    private Context context;

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
    public PostAdapter_1(List<PostList> postList){
        this.mpostList=postList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_postlist,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                PostList postList = mpostList.get(position);
                context = v.getContext();
                Intent intent = new Intent(context,ShowActivity.class);
                intent.putExtra("post_data",postList);
                intent.putExtra("kind","1");
                context.startActivity(intent);
                //添加点击跳转

            }
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        PostList postList = mpostList.get(position);
        holder.label.setText("["+postList.getLabel()+"]");
        //holder.time.setText(postList.getCreatedAt());
        holder.title.setText(postList.getTitle());
    }
    @Override
    public int getItemCount(){
        return mpostList.size();
    }

    public List<PostList> getMpostList() {
        return mpostList;
    }

    public void setMpostList(List<PostList> mpostList) {
        this.mpostList = mpostList;
    }
}

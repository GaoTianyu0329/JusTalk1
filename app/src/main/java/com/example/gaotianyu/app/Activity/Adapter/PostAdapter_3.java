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
    private String label_1;
    private String label_2;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView label;
        TextView time;
        TextView title;
        View listView;
        public ViewHolder(View view){
            super(view);
            listView = view;
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
        final PostAdapter_3.ViewHolder holder = new PostAdapter_3.ViewHolder(view);
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PostList_3 postList3 = postList.get(position);
                //添加点击跳转

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        PostList_3 postList_3 = postList.get(position);
        getLabel(postList_3);
        holder.label.setText("["+label_1+"]["+label_2+"]");
        holder.time.setText(postList_3.getTime());
        holder.title.setText(postList_3.getTitle());
    }
    @Override
    public int getItemCount(){
        return postList.size();
    }
    private void getLabel(PostList_3 postList_3){
        String label= postList_3.getLabel();
        for (int i=0;i<label.length();i++){
            if (label.charAt(i)=='#'){
                label_1 = label.substring(0,i);
                label_2 = label.substring(i+1);
            }

            }
        }


}
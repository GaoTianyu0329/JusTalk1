package com.example.gaotianyu.app.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaotianyu.app.Activity.Activity.ShowActivity;
import com.example.gaotianyu.app.Activity.PostList.PostList;
import com.example.gaotianyu.app.R;

import java.util.List;

/**
 * Created by GaoTianyu on 2017/12/12.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<PostList> postList;
    private String label_1 = null;
    private String label_2 = null;
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
    public PostAdapter(List<PostList> postList){
        this.postList=postList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_postlist,parent,false);
        final PostAdapter.ViewHolder holder = new PostAdapter.ViewHolder(view);
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PostList postList3 = postList.get(position);
                context = v.getContext();
                Intent intent = new Intent(context,ShowActivity.class);
                intent.putExtra("post_data",postList3);
                intent.putExtra("kind","3");
                context.startActivity(intent);
                //添加点击跳转

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        PostList postList_3 = postList.get(position);
        getLabel(postList_3);
        if (label_2==null){
            holder.label.setText("["+label_1+"]");
        }else{
            holder.label.setText("["+label_1+"]["+label_2+"]");
        }

        holder.time.setText(postList_3.getTime());
        holder.title.setText(postList_3.getTitle());
    }
    @Override
    public int getItemCount(){
        return postList.size();
    }
    private void getLabel(PostList postList_3){
        String label= postList_3.getLabel();
        for (int i=0;i<label.length();i++){
            if (label.charAt(i)=='#'){
                label_1 = label.substring(0,i);
                label_2 = label.substring(i+1);
                return;
            }

        }
        label_1 = label;
    }


}
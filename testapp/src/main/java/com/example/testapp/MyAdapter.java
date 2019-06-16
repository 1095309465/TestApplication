package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    List<RecycleBean> list;
    Context mContext;

    public MyAdapter(Context mContext,List<RecycleBean> list) {
        this.list = list;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
      TextView tv= holder.itemView.findViewById(R.id.tv);
      tv.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class  MyHolder extends RecyclerView.ViewHolder{


        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

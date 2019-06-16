package com.example.testapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<RecycleBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recycle);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new MyItemDecoration());
//添加自定义分割线
//        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.diver));
//        recyclerView.addItemDecoration(divider);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        initData();
    }

    private void initData() {
        list.clear();
        for (int i = 0; i < 50; i++) {
            list.add(new RecycleBean(i + ""));
        }

        myAdapter.notifyDataSetChanged();
    }

    public void scroll_0(View view) {
        recyclerView.smoothScrollToPosition(0);
    }

    public void scroll_26(View view) {

        recyclerView.scrollToPosition(26);
        LinearLayoutManager mLayoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();
        mLayoutManager.scrollToPositionWithOffset(26, 0);
    }

    public void scroll_26_2(View view) {
        smoothMoveToPosition(recyclerView, 26);
    }

    //    /目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}

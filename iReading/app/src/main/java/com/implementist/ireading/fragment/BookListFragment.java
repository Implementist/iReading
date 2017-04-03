package com.implementist.ireading.fragment;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.implementist.ireading.BookData;
import com.implementist.ireading.R;
import com.implementist.ireading.RefreshViewFooter;
import com.implementist.ireading.RefreshViewHeader;
import com.implementist.ireading.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 * Book List Fragment
 */
public class BookListFragment extends BaseFragment implements XRefreshView.XRefreshViewListener {

    RecyclerView recyclerView;
    SimpleAdapter adapter;
    List<BookData> books = new ArrayList<>();
    XRefreshView xRefreshView;

    LinearLayoutManager layoutManager;
    private int mLoadCount = 0;

    @Override
    public int bindLayout() {
        return R.layout.fragment_book_list;
    }

    @Override
    public void initView(View view) {
        xRefreshView = $(R.id.refrvBookList);
        recyclerView = $(R.id.recvBookList);
        recyclerView.setHasFixedSize(true);

        initData();
        adapter = new SimpleAdapter(books, view.getContext());
        // 设置静默加载模式
        //xRefreshView1.setSilenceLoadMore();
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);

        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(0);

        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(true);

        //set custom header
        xRefreshView.setCustomHeaderView(new RefreshViewHeader(view.getContext()));

        //set custom footer
        adapter.setCustomLoadMoreView(new RefreshViewFooter(view.getContext()));

        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);

        //设置静默加载时提前加载的item个数
        //xRefreshView1.setPreLoadCount(4);
    }

    @Override
    public void setListener() {
        xRefreshView.setXRefreshViewListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        //switch (v.getId()) {
        //case R.id.button:
        //startActivity(MainActivity.class);
        //Log.i("Test", "Test Message");
        //break;
        //}
    }

    private void initData() {
        for (int i = 1; i < 6; i++) {
            BookData book = new BookData(i, "", "Untitled", 98 * i, 5 * i, i - 1);
            books.add(book);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRefreshView.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mLoadCount++;
                if (mLoadCount >= 3) {
                    //模拟没有更多数据的情况
                    xRefreshView.setLoadComplete(true);
                } else {
                    //当数据加载失败或者刷新完成必须调用此方法停止加载
                    xRefreshView.stopLoadMore(false);
                }
            }
        }, 1000);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }
}
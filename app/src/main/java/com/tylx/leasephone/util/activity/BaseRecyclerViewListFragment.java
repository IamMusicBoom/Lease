package com.tylx.leasephone.util.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.tylx.leasephone.adapter.IViewDataRecyclerAdapter;
import com.tylx.leasephone.util.recyclerViewx.GridSpaceItemDecoration;
import com.tylx.leasephone.util.recyclerViewx.SpaceItemDecoration;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/5/3.
 */

public class BaseRecyclerViewListFragment<T> extends BaseFragment implements PullToRefreshBase.OnRefreshListener2 {
    protected int page = 1;
    protected int total = 0;//必须要设置
    protected PullToRefreshRecyclerView _listview;
    protected IViewDataRecyclerAdapter baseAdapter;
    protected View _placeholder;

    protected void setAdapter(IViewDataRecyclerAdapter baseAdapter) {
        page = 1;
        this.baseAdapter = baseAdapter;
        _listview.setAdapter(this.baseAdapter);
    }

    protected void loadData(ArrayList<T> _data) {
        if (page == 1) {
            baseAdapter.clear();
        }

        if (page == 1 && (_data == null || _data.size() == 0)) {
            showListplaceholder(true);
        } else {
            showListplaceholder(false);
            baseAdapter.addInfos(_data);
            baseAdapter.notifyDataSetChanged();
        }
        _listview.onRefreshComplete();
        if (total <= baseAdapter.getInfos().size())
            _listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        else
            _listview.setMode(PullToRefreshBase.Mode.BOTH);

        page++;
    }

    public void getDataForOne() {
        page = 1;
        getListData();
    }

    public void setGridLayout(int span, int spacingInPixels) {
        _listview.getRefreshableView().addItemDecoration(new GridSpaceItemDecoration(span, spacingInPixels, false));
        _listview.getRefreshableView().setLayoutManager(new GridLayoutManager(mActivity, span, 1, false));
    }

    public void setLinearLayout(int orientation, int spacingInPixels) {
        _listview.getRefreshableView().setLayoutManager(new LinearLayoutManager(mActivity, orientation, false));
        _listview.getRefreshableView().addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }

    protected int spacingInPixels() {
        return 0;
    }

    protected int orientation() {
          return LinearLayoutManager.VERTICAL;
    }

    protected int span() {
        return 1;
    }

    enum Style {
        GRID, LINEAR
    }

    protected BaseRecyclerViewListActivity.Style style() {
        return BaseRecyclerViewListActivity.Style.LINEAR;
    }

    /**
     * listview的时候 会显示占位符
     *
     * @return
     */
    protected View Listplaceholder() {
        return null;
    }

    protected void placeholderOnClick() {
        page = 1;
        getListData();
    }

    protected void showListplaceholder(boolean show) {
        if (_placeholder == null)
            return;
        _placeholder.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected View onCreateContentView() {
        return super.onCreateContentView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _placeholder = Listplaceholder();
        if (_placeholder != null) {
            _placeholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placeholderOnClick();
                }
            });
        }
        if (_listview != null) {
            _listview.setOnRefreshListener(this);
        }

        if (_listview != null) {
            if (style() == BaseRecyclerViewListActivity.Style.LINEAR) {
                _listview.getRefreshableView().setLayoutManager(new LinearLayoutManager(mActivity, orientation(), false));
                _listview.getRefreshableView().addItemDecoration(new SpaceItemDecoration(spacingInPixels()));
            } else {
                _listview.getRefreshableView().addItemDecoration(new GridSpaceItemDecoration(span(), spacingInPixels(), false));
                _listview.getRefreshableView().setLayoutManager(new GridLayoutManager(mActivity, span(), orientation(), false));
            }
        }
        getListData();
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getListData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

        getListData();
    }

    protected void getListData() {

    }
}

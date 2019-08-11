package com.tylx.leasephone.util.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.tylx.leasephone.adapter.IRecyclerViewAdapter;
import com.tylx.leasephone.adapter.IViewDataRecyclerAdapter;
import com.tylx.leasephone.util.recyclerViewx.GridSpaceItemDecoration;
import com.tylx.leasephone.util.recyclerViewx.SpaceItemDecoration;

import java.util.ArrayList;

/**
 * Created by track on 2017/5/3.
 */

public abstract class BaseRecyclerViewListFragment2<T, E extends ViewDataBinding> extends BaseFragment implements PullToRefreshBase.OnRefreshListener2 {
    protected int page = 1;
    protected int total = 0;//必须要设置
    protected PullToRefreshRecyclerView _listview;
    public BaseIViewDataRecyclerAdapter baseAdapter;
    protected View _placeholder;
    public boolean only_from_start;

    public BaseIViewDataRecyclerAdapter getAdapter() {
        return this.baseAdapter;
    }

    public void setMode(PullToRefreshBase.Mode mode) {
        _listview.setMode(mode);
        if (mode == PullToRefreshBase.Mode.PULL_FROM_START) {
            only_from_start = true;
        }
    }

    protected void loadData(ArrayList<T> _data) {
        if (baseAdapter == null) {
            baseAdapter = new BaseIViewDataRecyclerAdapter();
            baseAdapter.setOnDeleteListener(new IRecyclerViewAdapter.OnDeleteListener() {
                @Override
                public void onDelete() {
                    showListplaceholder(true);
                }
            });
            System.out.println("setAdapter");
//            setLinearLayout();
            _listview.setAdapter(baseAdapter);
        }
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
        if (!only_from_start) {
            if (total <= baseAdapter.getInfos().size())
                _listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            else
                _listview.setMode(PullToRefreshBase.Mode.BOTH);
            if (total == 0 && page == 1) {
                baseAdapter.clear();
                baseAdapter.notifyDataSetChanged();
            }
        }
        page++;
    }

    public void resetAdapter() {
        baseAdapter = new BaseIViewDataRecyclerAdapter();
        baseAdapter.setOnDeleteListener(new IRecyclerViewAdapter.OnDeleteListener() {
            @Override
            public void onDelete() {
                showListplaceholder(true);
            }
        });
        _listview.setAdapter(baseAdapter);
        showListplaceholder(true);
    }

    public void clearData() {
        if (baseAdapter==null)
            return;
        baseAdapter.clear();
        baseAdapter.notifyDataSetChanged();
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

    public void setLinearLayout() {
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

    public abstract int getItemLayout(int viewTyoe);

    public abstract void bindItemData(E e, T info, int position);

    public class BaseIViewDataRecyclerAdapter extends IViewDataRecyclerAdapter<T, E> {

        @Override
        protected int getItemLayoutId(int viewType) {
            return getItemLayout(viewType);
        }

        @Override
        protected void bindData(E e, T info, int position) {
            bindItemData(e, info, position);
        }
    }

    public void removeIndexAnim(int index) {
        if (baseAdapter == null)
            return;
        baseAdapter.removeIndexAnim(index);
    }
}

package com.tylx.leasephone.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class IViewDataRecyclerAdapter<T, E extends ViewDataBinding> extends IRecyclerViewAdapter<T,
        IViewDataRecyclerAdapter.ViewHolder> {

    private View rootView;
    private Context mContext;
    private LayoutInflater mInflater;

    public View getRootView() {
        return rootView;
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public float width = -1;

    public void setItemWith(float width) {
        this.width = width;
    }

    @Override
    public IViewDataRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (rootView == null) {
            rootView = parent;
            mContext = rootView.getContext();
            mInflater = LayoutInflater.from(mContext);
        }
        View view = DataBindingUtil.inflate(mInflater, getItemLayoutId(viewType), parent, false).getRoot();
        if (width > -1)
            view.getLayoutParams().width = (int) width;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IViewDataRecyclerAdapter.ViewHolder holder, int position) {
        bindData((E) holder.getBinding(), getItem(position), position);
    }


    protected abstract int getItemLayoutId(int viewType);

    protected abstract void bindData(E e, T info, int position);


    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public E getBinding() {
            return DataBindingUtil.getBinding(itemView);
        }

    }


}

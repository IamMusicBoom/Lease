package com.tylx.leasephone.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class IViewDataAdapter<T, E extends ViewDataBinding> extends IBaseAdapter<T> {

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

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (rootView == null) {
            rootView = parent;
            mContext = rootView.getContext();
            mInflater = LayoutInflater.from(mContext);
        }

        E viewDataBing;
        if (convertView == null) {
            viewDataBing = DataBindingUtil.inflate(mInflater, getItemLayoutId(position), parent, false);
        } else {
            viewDataBing = DataBindingUtil.getBinding(convertView);
        }

        bindData(viewDataBing, getItem(position), position);

        return viewDataBing.getRoot();
    }


    protected abstract int getItemLayoutId(int position);

    protected abstract void bindData(E binding, T info, int position);

}

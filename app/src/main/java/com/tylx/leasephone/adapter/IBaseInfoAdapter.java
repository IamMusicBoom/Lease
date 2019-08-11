package com.tylx.leasephone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class IBaseInfoAdapter<T> extends IBaseAdapter<T> {

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (rootView == null) {
            rootView = parent;
            mContext = rootView.getContext();
            mInflater = LayoutInflater.from(mContext);
        }

        if (convertView == null) {
            convertView = createView(position, parent, mInflater);
        }
        setData(position, convertView, getItem(position));
        return convertView;
    }

    private View rootView;
    private Context mContext;
    private LayoutInflater mInflater;

    protected abstract View createView(int postion, ViewGroup parent, LayoutInflater inflater);

    protected abstract void setData(int postion, View convertView, T t);

}

package com.tylx.leasephone.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by track on 2017/1/18. 通用显示数据的适配器
 */

public class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDataList;
    private int layoutId; // 条目布局ID
    private int variableId; // DataBinding的变量ID，可通过类似R文件的BR文件来获取

    public CommonAdapter(Context context, List<T> dataList, int layoutId, int variableId) {
        this.mContext = context;
        this.mDataList = dataList;
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    public void update(List<T> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 没有ViewHolder的复用，但Databinding内部已经实现了复用
        ViewDataBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(variableId, mDataList.get(position));
        return binding.getRoot();
    }

}
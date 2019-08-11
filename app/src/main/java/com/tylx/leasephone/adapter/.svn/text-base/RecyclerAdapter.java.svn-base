package com.tylx.leasephone.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by track on 2017/3/1.
 */

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.TheViewHolder> {

    private List<T> mDatas;

    private int layoutId;

    private int brId;
    private int itemwidth;

    public RecyclerAdapter(int itemwidth, List<T> mDatas, int layoutId, int brId) {
        this.itemwidth = itemwidth;
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    public RecyclerAdapter(List<T> mDatas, int layoutId, int brId) {
        this.itemwidth = -1;
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    @Override
    public RecyclerAdapter.TheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        if (itemwidth > -1) {
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
            p.width = itemwidth;
            binding.getRoot().setLayoutParams(p);
        }

        RecyclerAdapter.TheViewHolder viewHolder = new RecyclerAdapter.TheViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.TheViewHolder holder, int position) {
//            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) holder.getBinding().getRoot().getLayoutParams();
//            p.width = itemwidth;
//            holder.getBinding().getRoot().setLayoutParams(p);
        holder.getBinding().setVariable(brId, mDatas.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class TheViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public TheViewHolder(View itemView) {
            super(itemView);
        }
    }
}


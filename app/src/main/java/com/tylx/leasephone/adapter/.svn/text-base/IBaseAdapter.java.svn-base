package com.tylx.leasephone.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class IBaseAdapter<T> extends BaseAdapter {

    private List<T> infos;

    private void initInfos() {
        infos = infos == null ? new ArrayList<T>() : infos;
    }

    public void addInfo(T info) {
        initInfos();
        this.infos.add(info);
    }

    public void addInfo(int index, T info) {
        initInfos();
        this.infos.add(index, info);
    }

    public void removeInfo(T info) {
        initInfos();
        this.infos.remove(info);
    }

    public void removeIndex(int index) {
        initInfos();
        this.infos.remove(index);
    }

    public void addInfos(List<T> infos) {
        initInfos();
        this.infos.addAll(infos);
    }

    public void addInfos(int index, List<T> infos) {
        initInfos();
        this.infos.addAll(index, infos);
    }


    public void removeInfos(List<T> infos) {
        initInfos();
        this.infos.removeAll(infos);
    }


    public void clear() {
        initInfos();
        this.infos.clear();
    }

    public List<T> getInfos() {
        return infos;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return this.infos.get(position);
    }

    @Override
    public int getCount() {
        return infos == null ? 0 : this.infos.size();
    }

}
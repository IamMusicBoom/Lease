package com.tylx.leasephone.util.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;


import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * FragmentActivity
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    private FragmentManager _fragmentManager;
    private int _index = 0;

    private ArrayList<Class<? extends BaseFragment>> _fragmentClasses = new ArrayList<>();
    private List<Fragment> _fragments = new ArrayList<>();

    protected abstract ArrayList<Class<? extends BaseFragment>> fragmentClasses();

    protected abstract int containerViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _fragmentManager = getSupportFragmentManager();
        _fragmentClasses = this.fragmentClasses();
        Assert.assertTrue("_fragmentClasses.size() == 0", _fragmentClasses.size() != 0);
        for (int i = 0; i < _fragmentClasses.size(); i++) {
            if (_fragmentManager.findFragmentByTag(i + "") != null) {
                _fragments.add(_fragmentManager.findFragmentByTag(i + ""));
            } else {
                _fragments.add(null);
            }
        }
        setFragmentSelection(getSelectedPage());
    }

    public int getSelectedPage() {
        return _index;
    }

    protected void setFragmentSelection(int index) {
        _index = index;
        FragmentTransaction transaction = _fragmentManager.beginTransaction();
        for (int i = 0; i < _fragments.size(); i++) {
            if (i == index) {
                continue;
            }
            Fragment fragment = _fragments.get(i);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }

        Fragment fragment = _fragments.get(index);

        if (fragment == null) {
            Class<? extends BaseFragment> clazz = _fragmentClasses.get(index);
            try {
                fragment = clazz.newInstance();
                Assert.assertTrue("fragment == null", fragment != null);
                transaction.add(containerViewId(), fragment, "" + index);
                _fragments.set(index, fragment);
            } catch (Exception e) {
                Assert.assertTrue("clazz.newInstance() exception", false);
            }
        } else {
            fragment = (BaseFragment) _fragmentManager.findFragmentByTag("" + _index);
            if (refreshes.get(_index, false)) {

                transaction.detach(fragment);
                transaction.attach(fragment);
            }
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();

    }

    private SparseArray<Boolean> refreshes = new SparseArray<>();

    /**
     * 设置想要刷新的fragment
     *
     * @param index   fragment的位置
     * @param refresh 是否刷新
     */
    protected void setRefresh(int index, boolean refresh) {
        refreshes.put(index, refresh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

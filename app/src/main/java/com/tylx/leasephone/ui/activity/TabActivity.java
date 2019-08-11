package com.tylx.leasephone.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tylx.leasephone.ui.fragment.LeaseFragment;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseFragment;

import java.util.List;


/**
 * Created by wangm on 2017/3/6.
 * Fragment的操作
 */

public class TabActivity extends BaseActivity {
    private List<Fragment> mFragments;
    private FragmentManager mFmanager;
    private int mContainerViewId;
    public int mCurTabIndex = -1;
    protected void init(List<Fragment> mFragments, int mContainerViewId, FragmentManager supportFragmentManager) {
        this.mFragments = mFragments;
        this.mFmanager = supportFragmentManager;
        this.mContainerViewId = mContainerViewId;

    }
    public void switchTab(int index){
        FragmentTransaction transaction = mFmanager.beginTransaction();
        Fragment fragment = mFmanager.findFragmentByTag(mFragments.get(index).getClass().getName());
        if(fragment == null){
            fragment = mFragments.get(index);
            transaction.add(mContainerViewId,fragment,fragment.getClass().getName());
        }else{
            transaction.show(fragment);
        }
        if (mCurTabIndex != -1) {// 当前fragment不为-1
            transaction.hide(mFragments.get(mCurTabIndex));// 隐藏当前fragment
        }
        transaction.commit();
        mCurTabIndex = index;
    }


}

package com.tylx.leasephone.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmetLeaseBinding;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.view.TopToolView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangm on 2017/6/15.
 * 租赁
 */

public class LeaseFragment extends TabFragment {
    public FragmetLeaseBinding binding;
    private List<Fragment> mFragments;
    public static final String PHONE_CODE = "1";
    public static final String COMPUTER_CODE = "2";
    public int mCurPos = 0;
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragmet_lease, _containerLayout, false);
        return binding.getRoot();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurPos", mCurPos);
        outState.putInt("mCurTabIndex", mCurTabIndex);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTopView();
        mFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            Fragment mPhoneLeaseFragment = getChildFragmentManager().findFragmentByTag(PhoneLeaseFragment.class.getName());
            Fragment mComputerLeaseFragment = getChildFragmentManager().findFragmentByTag(ComputerLeaseFragment.class.getName());
            mCurPos = savedInstanceState.getInt("mCurPos", -1);
            mCurTabIndex = savedInstanceState.getInt("mCurTabIndex", -1);
            if (mPhoneLeaseFragment != null) {
                mFragments.add(mPhoneLeaseFragment);
            } else {
                mFragments.add(new PhoneLeaseFragment());
            }
            if (mComputerLeaseFragment != null) {
                mFragments.add(mComputerLeaseFragment);
            } else {
                mFragments.add(new ComputerLeaseFragment());
            }

            init(mFragments, R.id.home_frameLayout, getChildFragmentManager());
        } else {
            mFragments.add(new PhoneLeaseFragment());
            mFragments.add(new ComputerLeaseFragment());
            init(mFragments, R.id.home_frameLayout, getChildFragmentManager());
            switchTab(HomeActivity.topPos);
        }

    }


    private void initTopView() {
        binding.toptoolview.setLanwidth(100);
        binding.toptoolview.addTextTab("手机租赁");
        binding.toptoolview.addTextTab("其他租赁");
        binding.toptoolview.setCurrentSelect(HomeActivity.topPos);
        binding.toptoolview.setItemSelectListener(new TopToolView.OnselectItemListener() {
            @Override
            public void selectItem(int i) {
                switchTab(i);
                mCurPos = i;
            }
        });
    }

}

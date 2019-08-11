package com.tylx.leasephone.ui.activity.lease_shop.order;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityOrderListBinding;
import com.tylx.leasephone.ui.activity.TabActivity;
import com.tylx.leasephone.ui.activity.lease_shop.detail.ImgTextFragment;
import com.tylx.leasephone.ui.activity.lease_shop.detail.ParamterFtagmet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/6/22.
 * 订单列表
 */

public class OrderListAcitivity extends TabActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{
    ActivityOrderListBinding binding;
    private RadioGroup mBottomLayout;
    public static int curOrderType ;

    public static final int LEASE = 30;
    public static final int SHOP = 31;
    public static boolean isShopCommentComplite;
    public static boolean isLeaseCommentComplite;
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_order_list,_containerLayout,false);
        return binding.getRoot();
    }
    private List<Fragment> mFragments;
    int mCurPos = 0;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurPos", mCurPos);
        outState.putInt("mCurTabIndex", mCurTabIndex);
    }
    @Override
    protected void onPostInject(Bundle savedInstanceState) {
        super.onPostInject(savedInstanceState);
        mFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            Fragment mLeaseOrderFragment = getSupportFragmentManager().findFragmentByTag(LeaseOrderFragment.class.getName());
            Fragment mShopOrderFragment = getSupportFragmentManager().findFragmentByTag(ShopOrderFragment.class.getName());
            mCurPos = savedInstanceState.getInt("mCurPos", -1);
            mCurTabIndex = savedInstanceState.getInt("mCurTabIndex", -1);
            if (mLeaseOrderFragment != null) {
                mFragments.add(mLeaseOrderFragment);
            } else {
                mFragments.add(new ImgTextFragment());
            }
            if (mShopOrderFragment != null) {
                mFragments.add(mShopOrderFragment);
            } else {
                mFragments.add(new ParamterFtagmet());
            }
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
        } else {
            mFragments.add(new LeaseOrderFragment());
            mFragments.add(new ShopOrderFragment());
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
            switchTab(0);
        }
        initViews();

    }

    private void initViews() {
        mBottomLayout = binding.bottomLayout;
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            ((RadioButton) mBottomLayout.getChildAt(i)).setOnCheckedChangeListener(this);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            if (buttonView == mBottomLayout.getChildAt(i) && isChecked) {
                switchTab(i);
                mCurPos = i;
            }
        }
    }

    public  static void into(Activity activity){
        Intent intent = new Intent(activity,OrderListAcitivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_bar_back:
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager=getSupportFragmentManager();
        for(int indext=0;indext<fragmentManager.getFragments().size();indext++)
        {
            Fragment fragment=fragmentManager.getFragments().get(indext); //找到第一层Fragment
            if(fragment==null)
                Log.w(TAG, "Activity result no fragment exists for index: 0x" + Integer.toHexString(requestCode));
            else
                handleResult(fragment,requestCode,resultCode,data);
        }
    }
    /**
     * 递归调用，对所有的子Fragment生效
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment,int requestCode,int resultCode,Intent data)
    {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if(childFragment!=null)
            for(Fragment f:childFragment)
                if(f!=null)
                {
                    handleResult(f, requestCode, resultCode, data);
                }
        if(childFragment==null)
            Log.e(TAG, "MyBaseFragmentActivity1111");
    }
}

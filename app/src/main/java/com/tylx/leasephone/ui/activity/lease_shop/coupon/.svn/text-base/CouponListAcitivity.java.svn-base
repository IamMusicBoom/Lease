package com.tylx.leasephone.ui.activity.lease_shop.coupon;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityCouponListBinding;
import com.tylx.leasephone.model.PrepareBuyModel;
import com.tylx.leasephone.model.VoucherCashModel;
import com.tylx.leasephone.ui.activity.TabActivity;
import com.tylx.leasephone.view.TopToolView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/6/22.
 * 卡券
 */

public class CouponListAcitivity extends TabActivity {
    ActivityCouponListBinding binding;
    private List<Fragment> mFragments;
    public int mCurPos = 0;
    public static int REQUEST_CODE = 231;

    public static final int ORDER_CONFIRM = 42;
    public static final int HOME = 43;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurPos", mCurPos);
        outState.putInt("mCurTabIndex", mCurTabIndex);
    }

    @Override
    protected void onPostInject(Bundle savedInstanceState) {
        super.onPostInject(savedInstanceState);
        registerBack();
        setTitle("礼券中心");
        setRightText("使用记录");
        mFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            Fragment mAvailableCoupons = getSupportFragmentManager().findFragmentByTag(AvailableCoupons.class.getName());
            Fragment mExpiredCoupons = getSupportFragmentManager().findFragmentByTag(ExpiredCoupons.class.getName());
            mCurPos = savedInstanceState.getInt("mCurPos", -1);
            mCurTabIndex = savedInstanceState.getInt("mCurTabIndex", -1);
            if (mAvailableCoupons != null) {
                mFragments.add(mAvailableCoupons);
            } else {
                mFragments.add(new AvailableCoupons());
            }
            if (mExpiredCoupons != null) {
                mFragments.add(mExpiredCoupons);
            } else {
                mFragments.add(new ExpiredCoupons());
            }
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
        } else {
            mFragments.add(new AvailableCoupons());
            mFragments.add(new ExpiredCoupons());
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
            switchTab(0);
        }
        fromType = getIntent().getIntExtra("fromType", -1);

        if(fromType == ORDER_CONFIRM){
            prepareBuyModel = (PrepareBuyModel) getIntent().getSerializableExtra("PrepareBuyModel");
        }
        initTopView();
    }
    public PrepareBuyModel prepareBuyModel;
    public static int fromType = -1;

    @Override
    protected void goNext() {
        super.goNext();
        UseRecordActivity.into(this);

    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_coupon_list, _containerLayout, false);
        return binding.getRoot();
    }

    public static void into(Activity activity, int fromType, PrepareBuyModel prepareBuyModel) {
        Intent intent = new Intent(activity, CouponListAcitivity.class);
        intent.putExtra("fromType", fromType);
        intent.putExtra("PrepareBuyModel", prepareBuyModel);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }


    private void initTopView() {
        binding.toptoolview.setLanwidth(100);
        binding.toptoolview.addTextTab("可用礼券");
        binding.toptoolview.addTextTab("过期礼券");
        binding.toptoolview.setCurrentSelect(0);
        binding.toptoolview.setItemSelectListener(new TopToolView.OnselectItemListener() {
            @Override
            public void selectItem(int i) {
                switchTab(i);
                mCurPos = i;
            }
        });
    }

    public void setResultOk(VoucherCashModel info) {
        Intent intent = new Intent();
        intent.putExtra("VoucherCashModel", info);
        setResult(RESULT_OK, intent);
        finish();

    }
}

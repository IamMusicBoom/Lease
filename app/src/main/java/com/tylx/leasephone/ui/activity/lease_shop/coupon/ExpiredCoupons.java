package com.tylx.leasephone.ui.activity.lease_shop.coupon;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmentExpiredCouponsBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.databinding.ItemVouchercashBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.VoucherCashModel;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListFragment2;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/6/23.
 * 过期卡券
 */

public class ExpiredCoupons extends BaseRecyclerViewListFragment2<VoucherCashModel, ItemVouchercashBinding> {

    FragmentExpiredCouponsBinding binding;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_expired_coupons,_containerLayout,false);
        _listview = binding.listview;
        return binding.getRoot();
    }
    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }
    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().getVoucherCashInfos(DatasStore.getCurMember().id, 3, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if(page == 1 && !binding.listview.isRefreshing())
                    showLoading();

            }

            @Override
            public void successful(Object o) {
                ArrayList<VoucherCashModel> result = (ArrayList<VoucherCashModel>) o;
                if(null != result){
                    total = result.size();
                    loadData(result);
                }
                hideLoading();

            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if(binding.listview.isRefreshing()){
                    binding.listview.onRefreshComplete();
                }
            }
        });
    }
    @Override
    public int getItemLayout(int viewTyoe) {
        return R.layout.item_vouchercash;
    }

    @Override
    public void bindItemData(ItemVouchercashBinding itemVouchercashBinding, VoucherCashModel info, int position) {
        VoucherCashModel.setDatas( (CouponListAcitivity)mActivity,itemVouchercashBinding,info,((CouponListAcitivity) mActivity).mCurPos);
    }

}

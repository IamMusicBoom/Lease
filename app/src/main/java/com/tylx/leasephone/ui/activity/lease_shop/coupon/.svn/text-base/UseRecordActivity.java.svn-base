package com.tylx.leasephone.ui.activity.lease_shop.coupon;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityUserecordBinding;
import com.tylx.leasephone.databinding.ItemUseRecordBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.VoucherCashModel;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListActivity;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/7/13.
 * 使用记录
 */

public class UseRecordActivity extends BaseRecyclerViewListActivity<VoucherCashModel,ItemUseRecordBinding> {
    ActivityUserecordBinding binding;
    public static void into(Activity mActivity){
        Intent intent = new Intent(mActivity,UseRecordActivity.class);
        mActivity.startActivity(intent);

    }
    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("使用记录");
        registerBack();
    }
    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }
    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().getVoucherCashInfoHistory(DatasStore.getCurMember().id,  new BaseModel.BaseModelIB() {
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
        return R.layout.item_use_record;
    }

    @Override
    public void bindItemData(ItemUseRecordBinding itemUseRecordBinding, VoucherCashModel info, int position) {
        itemUseRecordBinding.setModel(info);
        StringBuffer sb = new StringBuffer();
        if(null != info.goodsName){
            for (int i = 0; i < info.goodsName.size(); i++) {
                sb.append(info.goodsName.get(i));
                sb.append(",");
            }
            itemUseRecordBinding.name.setText(sb.substring(0,sb.length()-1));
        }



    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_userecord, _containerLayout, false);
        _listview = binding.listview;
        return binding.getRoot();
    }
}

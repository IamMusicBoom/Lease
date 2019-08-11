package com.tylx.leasephone.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmetComputerLeaseBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListFragment2;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/6/22.
 * 电脑租赁
 */

public class ComputerLeaseFragment extends BaseRecyclerViewListFragment2<GoodsModel, ItemHomeBinding> {
    FragmetComputerLeaseBinding binding;
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragmet_computer_lease, _containerLayout, false);
        _listview = binding.listview;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }

    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().leaseList(LeaseFragment.COMPUTER_CODE, page, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (page == 1 && !_listview.isRefreshing())
                    showLoading();
            }

            @Override
            public void successful(Object o) {
                GoodsListModel model = (GoodsListModel) o;
                total = model.total;
                loadData(model.result);
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
        return R.layout.item_home;
    }

    @Override
    public void bindItemData(final ItemHomeBinding itemHomeBinding, final GoodsModel info, int position) {
        GoodsListModel.setDatas(itemHomeBinding,info,mActivity,GoodsListModel.LEASE_COMPUTER_DATA);
    }
}

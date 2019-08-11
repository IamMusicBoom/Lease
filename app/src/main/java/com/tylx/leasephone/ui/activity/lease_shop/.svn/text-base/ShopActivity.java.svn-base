package com.tylx.leasephone.ui.activity.lease_shop;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityShopBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.fragment.LeaseFragment;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListActivity;

/**
 * Created by wangm on 2017/6/20.
 * 弃用，ShopFragment顶替
 */

public class ShopActivity extends BaseRecyclerViewListActivity<GoodsModel, ItemHomeBinding> {
    ActivityShopBinding binding;
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop, _containerLayout, false);
        _listview = binding.listview;
        return binding.getRoot();
    }

    @Override
    public int getItemLayout(int viewTyoe) {
        return R.layout.item_home;
    }

    @Override
    public void bindItemData(ItemHomeBinding itemHomeBinding, GoodsModel info, int position) {
        GoodsListModel.setDatas(itemHomeBinding, info, this,GoodsListModel.LEASE_PHONE_DATA);
    }
    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }

    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().shoppingList("", page, new BaseModel.BaseModelIB() {
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
                if(binding.listview.isRefreshing()){
                    binding.listview.onRefreshComplete();
                }
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }
}

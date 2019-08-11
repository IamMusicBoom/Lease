package com.tylx.leasephone.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityShopBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.lease_shop.ClassificationActivity;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListFragment2;


/**
 * Created by wangm on 2017/6/15.
 * 商城
 */

public class ShopFragment extends BaseRecyclerViewListFragment2<GoodsModel, ItemHomeBinding> {
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
    public void bindItemData(final ItemHomeBinding itemHomeBinding, final GoodsModel info, int position) {
        GoodsListModel.setDatas(itemHomeBinding, info, mActivity, GoodsListModel.HOME_DATA);
        itemHomeBinding.goAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.businessType == 1) {
                    GoodsDetailActivity.into(mActivity, info.id,null);
                } else if (info.businessType == 2) {
                    addShopping(itemHomeBinding, info);
                }
            }
        });
    }

    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }

    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().shoppingList(typeCode, page, new BaseModel.BaseModelIB() {
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
    private void addShopping(final ItemHomeBinding itemHomeBinding, GoodsModel info) {
        String commodityNormDetailId = "";
        if (null != info.commondityNormDetails && info.commondityNormDetails.size() > 0 && null != info.commondityNormDetails.get(0) && !TextUtils.isEmpty(info.commondityNormDetails.get(0).original) &&
                !TextUtils.isEmpty(info.commondityNormDetails.get(0).id)) {
            commodityNormDetailId = info.commondityNormDetails.get(0).id;
            new MemberModel().addShopping(mActivity,commodityNormDetailId, "1", new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {
                    showLoading(false);
                }

                @Override
                public void successful(Object o) {
                    int count = Integer.valueOf((String) o);
                    HomeActivity.count = count;
                    ((HomeActivity) mActivity).startAddAnimation(itemHomeBinding.goAddImg, count);
                    hideLoading();
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg().toString());
                }
            });
        }

    }

    private String typeCode = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ClassificationActivity.REQUEST_CODE && resultCode == mActivity.RESULT_OK) {
            if (data != null) {
                typeCode = data.getStringExtra("typeCode");
                binding.listview.setRefreshing(true);
            }
        }
    }
}

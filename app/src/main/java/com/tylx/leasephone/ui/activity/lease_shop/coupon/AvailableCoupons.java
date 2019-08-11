package com.tylx.leasephone.ui.activity.lease_shop.coupon;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmentAvailableCouponsBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.databinding.ItemVouchercashBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.PrepareBuyModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.ShoppingGoodsModel;
import com.tylx.leasephone.model.VoucherCashModel;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListFragment2;

import java.util.ArrayList;

import static com.tylx.leasephone.ui.activity.lease_shop.coupon.CouponListAcitivity.ORDER_CONFIRM;
import static com.tylx.leasephone.ui.activity.lease_shop.coupon.CouponListAcitivity.fromType;

/**
 * Created by wangm on 2017/6/22.
 * 可用卡券
 */

public class AvailableCoupons extends BaseRecyclerViewListFragment2<VoucherCashModel, ItemVouchercashBinding> {
    FragmentAvailableCouponsBinding binding;
    CommondityNormDetail curCommondityNormDetail;
    GoodsModel goodsModel;

    ArrayList<ShoppingGoodsModel> prepareBuyList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(fromType == ORDER_CONFIRM){
            PrepareBuyModel  prepareBuyModel = ((CouponListAcitivity)mActivity).prepareBuyModel;
            curCommondityNormDetail = prepareBuyModel.curCommondityNormDetail;
            goodsModel = prepareBuyModel.goodsModel;
            prepareBuyList = prepareBuyModel.prepareBuyList;
            if(null != goodsModel && null != curCommondityNormDetail){
                cardDetail = "";
                normDeatilId = curCommondityNormDetail.id;
                commondityNum = "1";
            }else if(null != prepareBuyList){
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < prepareBuyList.size(); i++) {
                    buffer.append(prepareBuyList.get(i).id + ",");
                }
                String ids = buffer.substring(0, buffer.length() - 1);
                cardDetail = ids;
                normDeatilId = "-1";
                commondityNum = "-1";
            }
            getOrderConfirmVoucher();
        }


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_available_coupons, _containerLayout, false);
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
        if (fromType == CouponListAcitivity.ORDER_CONFIRM) {
            if(TextUtils.isEmpty(commondityNum)){
                return;
            }
            getOrderConfirmVoucher();
        } else if (fromType == CouponListAcitivity.HOME) {
            getHomeVoucher();
        }


    }
    String cardDetail,normDeatilId,commondityNum;
    private void getOrderConfirmVoucher() {
        new MemberModel().getVoucherModel(cardDetail, DatasStore.getCurMember().id, normDeatilId, commondityNum, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (page == 1 && !binding.listview.isRefreshing())
                    showLoading();
            }

            @Override
            public void successful(Object o) {
                ArrayList<VoucherCashModel> result = (ArrayList<VoucherCashModel>) o;
                if (null != result) {
                    total = result.size();
                    loadData(result);
                }
                hideLoading();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if (binding.listview.isRefreshing()) {
                    binding.listview.onRefreshComplete();
                }
            }
        });
    }

    private void getHomeVoucher() {
        new MemberModel().getVoucherCashInfos(DatasStore.getCurMember().id, 1, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (page == 1 && !binding.listview.isRefreshing())
                    showLoading();

            }

            @Override
            public void successful(Object o) {
                ArrayList<VoucherCashModel> result = (ArrayList<VoucherCashModel>) o;
                if (null != result) {
                    total = result.size();
                    loadData(result);
                }
                hideLoading();

            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if (binding.listview.isRefreshing()) {
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
        VoucherCashModel.setDatas((CouponListAcitivity) mActivity, itemVouchercashBinding, info, ((CouponListAcitivity) mActivity).mCurPos);
        if(fromType == ORDER_CONFIRM){
            itemVouchercashBinding.faceTv.setText(info.arrivalAmt);
        }else{
            itemVouchercashBinding.faceTv.setText(info.amtFace);
        }
    }
}

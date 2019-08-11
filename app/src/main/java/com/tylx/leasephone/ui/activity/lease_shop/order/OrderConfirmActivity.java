package com.tylx.leasephone.ui.activity.lease_shop.order;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.tylx.leasephone.R;
import com.tylx.leasephone.adapter.IViewDataRecyclerAdapter;
import com.tylx.leasephone.databinding.ActivityOrderConfirmBinding;
import com.tylx.leasephone.databinding.DialogGoBuyBinding;
import com.tylx.leasephone.databinding.ItemOrderConfirmBinding;
import com.tylx.leasephone.model.AddressModel;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.BuyModel;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.OrderModel;
import com.tylx.leasephone.model.PrepareBuyModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.ShoppingGoodsModel;
import com.tylx.leasephone.model.VoucherCashModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.lease_shop.ShoppingCartActivity;
import com.tylx.leasephone.ui.activity.lease_shop.coupon.CouponListAcitivity;
import com.tylx.leasephone.ui.activity.login.QuickLoginActivity;
import com.tylx.leasephone.ui.activity.me.AddressActivity;
import com.tylx.leasephone.ui.activity.me.AuthenticationSelectorActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/7/17.
 * 订单确认
 */
//BaseRecyclerViewListActivity<ShoppingGoodsModel, ItemOrderConfirmBinding>
public class OrderConfirmActivity extends BaseActivity implements View.OnClickListener {
    ActivityOrderConfirmBinding binding;
    int fromType;
    MyAdapter adapter;
    /**
     * 准备购买的商品集合，（购物车过来才有）
     **/
    ArrayList<ShoppingGoodsModel> prepareBuyList;

    /**
     * 准备购买的商品，（商品详情过来才有）
     **/
    CommondityNormDetail curCommondityNormDetail;
    GoodsModel goodsModel;
    OrderModel orderModel;
    String totalMoney;
    PrepareBuyModel prepareBuyModel;
    public static void into(Activity mActivity, PrepareBuyModel prepareBuyModel, int type) {
        Intent intent = new Intent(mActivity, OrderConfirmActivity.class);
        intent.putExtra("Type", type);
        intent.putExtra("PrepareBuyModel", prepareBuyModel);
        mActivity.startActivity(intent);
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_order_confirm, _containerLayout, false);
//        _listview = binding.listview;
        return binding.getRoot();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PayActivity.isPaySuccess){
            PayActivity.isPaySuccess = false;
            finish();
        }
    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("确认订单");
        registerBack();
        adapter = new MyAdapter();
        if (TextUtils.isEmpty(DatasStore.getCurMember().id)) {
            QuickLoginActivity.into(this);
            finish();
            return;
        }
        binding.listview.setLayoutManager(new LinearLayoutManager(OrderConfirmActivity.this,LinearLayoutManager.VERTICAL,false));
        getAddress();
        fromType = getIntent().getIntExtra("Type", -1);
        prepareBuyModel = (PrepareBuyModel) getIntent().getSerializableExtra("PrepareBuyModel");
        if (fromType == PrepareBuyModel.GOODS_DETAIL) {
            curCommondityNormDetail = prepareBuyModel.curCommondityNormDetail;
            goodsModel = prepareBuyModel.goodsModel;
            GoodsListModel.setLineText(curCommondityNormDetail, binding.nowTip, goodsModel.businessType, this);
            binding.setGoodModel(goodsModel);
            binding.setCommondityNormDetail(curCommondityNormDetail);
            if(goodsModel.businessType == 1){
                binding.vouchercashLayout.setVisibility(View.GONE);
            }else{
                binding.vouchercashLayout.setVisibility(View.VISIBLE);
            }
            if(goodsModel.businessType == 1){//租赁
                String leaseTerm = "0";
                String commodityNorm = curCommondityNormDetail.commodityNorm;
                if(!TextUtils.isEmpty(commodityNorm)){
                    String[] split = commodityNorm.split(",");
                    if(split != null){
                        for (int i = 0; i < split.length; i++) {
                            if(split[i].contains("Month")){
                                String[] split1 = split[i].split("_");
                                if(split1.length>=2){
                                    leaseTerm = split1[1];
                                }
                            }
                        }
                    }
                }
                totalMoney = new BigDecimal(curCommondityNormDetail.price).multiply(new BigDecimal(leaseTerm)).toString();
            }else {//购买
                totalMoney = curCommondityNormDetail.price;
            }
            curMoney = totalMoney;
        } else if (fromType == PrepareBuyModel.SHOPPING_CART) {
            prepareBuyList = prepareBuyModel.prepareBuyList;
//            total = prepareBuyList.size();
//            loadData(prepareBuyList);
            adapter.addInfos(prepareBuyList);
            binding.listview.setAdapter(adapter);
            totalMoney = calculationMoney(prepareBuyList).toString();
            curMoney = totalMoney;

        } else if (fromType == PrepareBuyModel.ORDER_LIST) {
            orderModel = prepareBuyModel.orderModel;
            totalMoney = orderModel.payableAmt;
            adapter.addInfos(prepareBuyList);
            binding.listview.setAdapter(adapter);
            curMoney = totalMoney;
            orderId = orderModel.id;
        } else {
            showToast("去选点货吧");
            finish();
        }
        binding.setFromType(fromType);
        binding.totalMoney.setText(totalMoney + "");
    }

    /**
     * 展示订单列表
     */
//    private void showOrderDatas() {
//        ItemOrderConfirmBinding itemOrderConfirmBinding = null;
//        List<OrderModel.GoodsBean> goods = orderModel.goods;
//        for (int i = 0; i < goods.size(); i++) {
//            itemOrderConfirmBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_order_confirm, null, false);
//            View root = itemOrderConfirmBinding.getRoot();
//            ShoppingGoodsModel model = new ShoppingGoodsModel();
//            model.commondityInfoVo = new GoodsModel();
//            model.commondityInfoVo.name = goods.get(i).goodsName;
//            model.commodityNum = goods.get(i).commondityNum;
//            model.commondityInfoVo.listPic = goods.get(i).commondityImg;
//            itemOrderConfirmBinding.setModel(model);
//            itemOrderConfirmBinding.styleTv.setText(GoodsModel.formateSpecifications(goods.get(i).commondityNorm));
//            GoodsListModel.setPrice(goods.get(i).commondityPrice, "", OrderConfirmActivity.this, orderModel.orderType, null, itemOrderConfirmBinding.nowTip);
//            binding.showDatas.addView(root);
//
//            final ItemOrderConfirmBinding finalItemOrderConfirmBinding = itemOrderConfirmBinding;
//            final int finalI = i;
//            itemOrderConfirmBinding.couponLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finalItemOrderConfirmBinding.couponLayout.setTag(finalI);
//                    CouponListAcitivity.into(OrderConfirmActivity.this, CouponListAcitivity.ORDER_CONFIRM);
//                }
//            });
//        }
//    }


//    @Override
//    public int getItemLayout(int viewTyoe) {
//        return R.layout.item_order_confirm;
//    }
//
//    @Override
//    public void bindItemData(ItemOrderConfirmBinding itemOrderConfirmBinding, ShoppingGoodsModel info, int position) {
//        itemOrderConfirmBinding.setModel(info);
//        itemOrderConfirmBinding.styleTv.setText(GoodsModel.formateSpecifications(info.commodityNormDetail));
//        GoodsListModel.setLineText(info,itemOrderConfirmBinding.nowTip,info.commondityInfoVo.businessType,OrderConfirmActivity.this);
//    }

    /**
     * 获取地址
     **/
    AddressModel addressModel;

    private void getAddress() {
        new MemberModel().getMemberAddressByMemberId(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                ArrayList<AddressModel> result = (ArrayList<AddressModel>) o;
                if (null == result) {

                } else if (result.size() <= 0) {

                } else {
                    addressModel = result.get(0);
                    binding.setAddressModel(addressModel);
                }
                hideLoading();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    private String orderId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (binding.submit.getIsDoResult()) {
                    binding.submit.reset();
                } else {
                    if (addressModel==null){
                        showToast("请填写你的收货地址");
                        binding.submit.reset();
                    }else{
                        if (TextUtils.isEmpty(orderId)) {
                            goSubmit();
                        }else  {
                            if (totalMoney.equals(curMoney)) {
                                binding.submit.reset();
                                showBuyDialog();
                            } else {
                                goSubmit();
                            }

                        }
                    }

                }
                break;
            case R.id.address_layout:
                AddressActivity.into(this);
                break;
            case R.id.vouchercash_layout:
                CouponListAcitivity.into(this, CouponListAcitivity.ORDER_CONFIRM,prepareBuyModel);
                break;
        }
    }

    /**
     * 购买的对话框
     */
    private int payType = 0;
    private Dialog mGoBuyDialog;
    private DialogGoBuyBinding dialogGoBuyBinding;

    private void showBuyDialog() {
        if (null == mGoBuyDialog) {
            mGoBuyDialog = ProbjectUtil.getDialog(this, Gravity.BOTTOM);
            dialogGoBuyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_go_buy, null, false);
            mGoBuyDialog.setContentView(dialogGoBuyBinding.getRoot());
            dialogGoBuyBinding.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGoBuyDialog.dismiss();
                }
            });
            dialogGoBuyBinding.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogGoBuyBinding.submit.getIsDoResult()) {
                        dialogGoBuyBinding.submit.reset();
                    } else {
                        goBuy();
                    }
                }
            });
            dialogGoBuyBinding.cbWexin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        payType = 2;
                    } else {
                        payType = 0;
                    }
                }
            });
            dialogGoBuyBinding.cbZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        payType = 1;
                    } else {
                        payType = 0;
                    }
                }
            });
            dialogGoBuyBinding.weixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogGoBuyBinding.cbZfb.isChecked()) {
                        dialogGoBuyBinding.cbZfb.setChecked(false);
                        dialogGoBuyBinding.cbWexin.setChecked(true);
                    } else {
                        if (dialogGoBuyBinding.cbWexin.isChecked()) {
                            dialogGoBuyBinding.cbWexin.setChecked(false);
                        } else {
                            dialogGoBuyBinding.cbWexin.setChecked(true);
                        }
                    }
                }
            });
            dialogGoBuyBinding.zfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogGoBuyBinding.cbWexin.isChecked()) {
                        dialogGoBuyBinding.cbWexin.setChecked(false);
                        dialogGoBuyBinding.cbZfb.setChecked(true);
                    } else {
                        if (dialogGoBuyBinding.cbZfb.isChecked()) {
                            dialogGoBuyBinding.cbZfb.setChecked(false);
                        } else {
                            dialogGoBuyBinding.cbZfb.setChecked(true);
                        }
                    }
                }
            });
        }
        dialogGoBuyBinding.curMoney.setText("￥ " + curMoney);
        dialogGoBuyBinding.voucherMoney.setText(voucherMoney);
        dialogGoBuyBinding.dialogTotal.setText(totalMoney + "");
        mGoBuyDialog.show();
    }

    /**
     * 支付
     */
    private void goBuy() {
        if (payType <= 0) {
            showToast("请选择支付方式");
            dialogGoBuyBinding.submit.reset();
            return;
        }
        dialogGoBuyBinding.submit.setEnabled(false);
        payGoods();
    }

    /**
     * 去支付
     */
    private void payGoods() {
        new MemberModel().payOrder(orderId, curMoney, binding.styleTv.getText().toString(), DatasStore.getCurMember().id, payType + "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                String rsa = (String) o;
                if(!TextUtils.isEmpty(rsa)){
                    if(rsa.equals("SUCCESS")){
                        mGoBuyDialog.dismiss();
                        OrderListAcitivity.isLeaseCommentComplite = true;
                        OrderListAcitivity.isShopCommentComplite = true;
                        return;
                    }else{
                        dialogGoBuyBinding.submit.setEnabled(true);
                        dialogGoBuyBinding.submit.doResult(true);
                        PayActivity.orderInfo = rsa;
                        mGoBuyDialog.dismiss();
                        mContext.startActivity(new Intent(mContext, PayActivity.class));
                    }
                }

            }

            @Override
            public void failed(ResultsModel resultsModel) {
                dialogGoBuyBinding.submit.setEnabled(true);
                dialogGoBuyBinding.submit.doResult(false);
                showToast(resultsModel.getErrorMsg().toString());
            }
        });
    }

    /**
     * 提交订单
     */
    BuyModel yuYueModel;
    private void goSubmit() {
        if(fromType == PrepareBuyModel.GOODS_DETAIL && goodsModel.businessType == 1 && DatasStore.getCurMember().auditState != 3){
            showToast("请先完成实名认证");
            AuthenticationSelectorActivity.into(OrderConfirmActivity.this);
            finish();
            return;
        }




        BuyModel buyModel = new BuyModel();
        if (fromType == PrepareBuyModel.GOODS_DETAIL) {//商品详情
            if (null == goodsModel || null == curCommondityNormDetail) {
                showToast("啥都没有购买个提交个毛啊~");
                dialogGoBuyBinding.submit.doResult(false);
                return;
            }
            String price = curCommondityNormDetail.price;
            buyModel.shippingId = "";
            buyModel.commondityNormId = curCommondityNormDetail.id;
            buyModel.commondityId = "";
            buyModel.memberId = DatasStore.getCurMember().id;
            buyModel.orderType = goodsModel.businessType;
            buyModel.payType = payType;
            if(null != voucherCashModel){
                buyModel.voucherId = voucherCashModel.id;
            }else {
                buyModel.voucherId = "";
            }
            if (goodsModel.businessType == 2) {//购买
                buyModel.sumAmt = price;
                buyModel.paidAmt = curMoney;
            } else {//租赁
                String leaseTerm = "0";
                String commodityNorm = curCommondityNormDetail.commodityNorm;
                if(!TextUtils.isEmpty(commodityNorm)){
                    String[] split = commodityNorm.split(",");
                    if(split != null){
                        for (int i = 0; i < split.length; i++) {
                            if(split[i].contains("Month")){
                                String[] split1 = split[i].split("_");
                                if(split1.length>=2){
                                    leaseTerm = split1[1];
                                }
                            }
                        }
                    }
                }
//                buyModel.leaseTerm = 12 + "";
                buyModel.leaseTerm = leaseTerm;
//                buyModel.sumAmt = new BigDecimal(price).multiply(new BigDecimal("12")).toString();
//                buyModel.paidAmt = new BigDecimal(curMoney).multiply(new BigDecimal("12")).toString();
                buyModel.sumAmt = new BigDecimal(price).multiply(new BigDecimal(leaseTerm)).toString();
                buyModel.paidAmt = curMoney;
            }

            buyModel.visit = MemberModel.VISIT;
            buyModel.remark = "";
            buyModel.commondityNum = 1;
            buyModel.voucherNum = voucherMoney;
        } else if (fromType == PrepareBuyModel.SHOPPING_CART) {
            if (null == prepareBuyList || prepareBuyList.size() <= 0) {
                showToast("啥都没有购买个提交个毛啊~");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < prepareBuyList.size(); i++) {
                buffer.append(prepareBuyList.get(i).id + ",");
            }
            String ids = buffer.substring(0, buffer.length() - 1);
            String sumAmt = totalMoney;
            buyModel.shippingId = ids;
            buyModel.commondityNormId = "";
            buyModel.commondityId = "";
            buyModel.sumAmt = sumAmt;
            buyModel.memberId = DatasStore.getCurMember().id;
            buyModel.orderType = BuyModel.BUY;
            buyModel.payType = payType;
            buyModel.voucherNum = voucherMoney;
            if(null != voucherCashModel){
                buyModel.voucherId = voucherCashModel.id;
            }else {
                buyModel.voucherId = "";
            }

            buyModel.paidAmt = curMoney;
            buyModel.visit = MemberModel.VISIT;
            buyModel.remark = "";
            buyModel.commondityNum = 0;
        } else {
            showToast("去选点货吧");
            finish();
        }
        yuYueModel = buyModel;
        if(fromType == PrepareBuyModel.GOODS_DETAIL && goodsModel.businessType == 1){
            new MemberModel().authZhima(DatasStore.getCurMember().mobilePhone, DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {
                    binding.submit.setEnabled(false);
                }

                @Override
                public void successful(Object o) {
                    String url = (String) o;
                    MyWebView.into(OrderConfirmActivity.this,url);
                    binding.submit.setEnabled(true);
                    binding.submit.doResult(true);
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    binding.submit.doResult(false);
                    binding.submit.setEnabled(true);
                    hideLoading(resultsModel.getErrorMsg().toString());
                }
            });
        }else{
            new MemberModel().buy(buyModel, new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {
                    binding.submit.setEnabled(false);
                }

                @Override
                public void successful(Object o) {
                    binding.submit.setEnabled(true);
                    binding.submit.doResult(true);
                    orderId = ((String) o);
                    if(fromType == PrepareBuyModel.GOODS_DETAIL && goodsModel.businessType == 1){
                        showSuccessToast();
                        finish();
                    }else{
                        showBuyDialog();
                    }

                    if (fromType == PrepareBuyModel.SHOPPING_CART) {
                        HomeActivity.isRefresh = true;
                        ShoppingCartActivity.isRefresh = true;
                    }
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    binding.submit.doResult(false);
                    binding.submit.setEnabled(true);
                    hideLoading(resultsModel.getErrorMsg().toString());
                }
            });
        }
    }

    public BigDecimal calculationMoney(List<ShoppingGoodsModel> commondityNormDetails) {
        if (commondityNormDetails == null) {
            return new BigDecimal("0");
        }
        BigDecimal totalMoney = new BigDecimal("0");
        for (int i = 0; i < commondityNormDetails.size(); i++) {
            CommondityNormDetail commondityNormDetail = commondityNormDetails.get(i).commodityNormDetail;
            BigDecimal num = new BigDecimal(commondityNormDetails.get(i).commodityNum);
            BigDecimal price = new BigDecimal(commondityNormDetail.price);
            BigDecimal total = num.multiply(price);
            totalMoney = totalMoney.add(total);
        }
        return totalMoney;
    }

    private String curMoney = "", voucherMoney = "0";
    VoucherCashModel voucherCashModel;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddressActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            getAddress();
        } else if (requestCode == CouponListAcitivity.REQUEST_CODE && resultCode == RESULT_OK) {
            if (fromType == PrepareBuyModel.GOODS_DETAIL) {
                voucherCashModel = (VoucherCashModel) data.getSerializableExtra("VoucherCashModel");
                voucherMoney = voucherCashModel.arrivalAmt;
                BigDecimal subtract = new BigDecimal(totalMoney).subtract(new BigDecimal(voucherCashModel.arrivalAmt));
                if (subtract.compareTo(new BigDecimal("0")) <= 0) {
                    curMoney = "0";
                } else {
                    curMoney = subtract.toString();
                }
                binding.totalMoney.setText(curMoney);
                binding.voucherNameTv.setText(voucherCashModel.title);
            } else if (fromType == PrepareBuyModel.SHOPPING_CART) {
                voucherCashModel = (VoucherCashModel) data.getSerializableExtra("VoucherCashModel");
                ShoppingGoodsModel item = adapter.getItem(curPos);
                item.voucherCashModel =voucherCashModel;
                adapter.replaceItem(curPos,item);
                voucherMoney = voucherCashModel.arrivalAmt;
                BigDecimal subtract = new BigDecimal(totalMoney).subtract(new BigDecimal(voucherCashModel.arrivalAmt));
                if (subtract.compareTo(new BigDecimal("0")) <= 0) {
                    curMoney = "0";
                } else {
                    curMoney = subtract.toString();
                }
                binding.totalMoney.setText(curMoney);
            }else if(requestCode == MyWebView.REQUEST_CODE && resultCode == RESULT_OK){
                new MemberModel().buy(yuYueModel, new BaseModel.BaseModelIB() {
                    @Override
                    public void StartOp() {
                        showLoading();
                    }

                    @Override
                    public void successful(Object o) {
                       hideLoading();
                        orderId = ((String) o);
                        if(fromType == PrepareBuyModel.GOODS_DETAIL && goodsModel.businessType == 1){
                            showSuccessToast();
                            finish();
                        }else{
                            showBuyDialog();
                        }

                        if (fromType == PrepareBuyModel.SHOPPING_CART) {
                            HomeActivity.isRefresh = true;
                            ShoppingCartActivity.isRefresh = true;
                        }
                    }

                    @Override
                    public void failed(ResultsModel resultsModel) {
                        hideLoading(resultsModel.getErrorMsg().toString());
                    }
                });
            }

        }

    }
    private int curPos;
    class MyAdapter extends IViewDataRecyclerAdapter<ShoppingGoodsModel, ItemOrderConfirmBinding> {

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_order_confirm;
        }

        @Override
        protected void bindData(ItemOrderConfirmBinding itemOrderConfirmBinding, ShoppingGoodsModel info, final int position) {
            itemOrderConfirmBinding.setModel(info);
            itemOrderConfirmBinding.styleTv.setText(GoodsModel.formateSpecifications(info.commodityNormDetail));
            itemOrderConfirmBinding.setVourModel(info.voucherCashModel);
            if(null != info.commondityInfoVo){
                GoodsListModel.setLineText(info, itemOrderConfirmBinding.nowTip, info.commondityInfoVo.businessType, OrderConfirmActivity.this);
            }
            if(position == adapter.getCount()-1){
                itemOrderConfirmBinding.couponLayout.setVisibility(View.VISIBLE);
            }else{
                itemOrderConfirmBinding.couponLayout.setVisibility(View.GONE);
            }
            itemOrderConfirmBinding.couponLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPos = position;
                    CouponListAcitivity.into(OrderConfirmActivity.this,CouponListAcitivity.ORDER_CONFIRM,prepareBuyModel);
                }
            });
        }
    }
    private void showSuccessToast(){
        View view = getLayoutInflater().inflate(R.layout.toast_view,null);
        Toast toast = ProbjectUtil.getToast(this, view);
        toast.show();
    }
}

/**
 * 展示购物车
 */
//    private void showShoppingCartDatas() {
//        ItemOrderConfirmBinding itemOrderConfirmBinding = null;
//        for (int i = 0; i < prepareBuyList.size(); i++) {
//            itemOrderConfirmBinding =  DataBindingUtil.inflate(getLayoutInflater(),R.layout.item_order_confirm,null,false);
//            View root = itemOrderConfirmBinding.getRoot();
//            itemOrderConfirmBinding.setModel(prepareBuyList.get(i));
//            itemOrderConfirmBinding.styleTv.setText(GoodsModel.formateSpecifications(prepareBuyList.get(i).commodityNormDetail));
//            GoodsListModel.setLineText(prepareBuyList.get(i),itemOrderConfirmBinding.nowTip,prepareBuyList.get(i).commondityInfoVo.businessType,OrderConfirmActivity.this);
//            binding.showDatas.addView(root);
//            itemOrderConfirmBinding.couponLayout.setTag(i);
//            itemOrderConfirmBinding.couponLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CouponListAcitivity.into(OrderConfirmActivity.this,CouponListAcitivity.ORDER_CONFIRM);
//                }
//            });
//        }
//
//    }




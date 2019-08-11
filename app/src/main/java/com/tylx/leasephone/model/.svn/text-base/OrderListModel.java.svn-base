package com.tylx.leasephone.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.alipay.PayDemoActivity;
import com.tylx.leasephone.databinding.DialogGoBuyBinding;
import com.tylx.leasephone.databinding.ItemOrderBinding;
import com.tylx.leasephone.databinding.ItemOrderLeaseBinding;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderConfirmActivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderDetailActivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderListAcitivity;
import com.tylx.leasephone.ui.activity.me.CommentActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;
import com.tylx.leasephone.view.MyPayDialog;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/7/21.
 */

public class OrderListModel extends BaseModel {
    public ArrayList<OrderModel> result;
    private static OrderListAcitivity mContext;
    public static void setDatas(ItemOrderBinding itemOrderBinding, final OrderModel info, final OrderListAcitivity mActivity, final int fromType) {
        mContext = mActivity;
        if(null != info){
            if(info.orderType == 2){
                itemOrderBinding.itemOrderIdLayout.setVisibility(View.GONE);
                itemOrderBinding.itemOrderCrateTimeLayout.setVisibility(View.GONE);
            }else{
                itemOrderBinding.itemOrderIdLayout.setVisibility(View.VISIBLE);
                itemOrderBinding.itemOrderCrateTimeLayout.setVisibility(View.VISIBLE);
            }
            itemOrderBinding.itemOrderNo.setText(info.id);
            itemOrderBinding.itemOrderCreatTime.setText(ProbjectUtil.longToDateString(info.startTime,4));
        }
        if(info.goods != null){
            if(itemOrderBinding.rootView.getChildCount()>0){
                itemOrderBinding.rootView.removeAllViews();
            }
            for (int i = 0; i < info.goods.size(); i++) {//Item内部数据
                final OrderModel.GoodsBean goodsBean = info.goods.get(i);
                ItemOrderLeaseBinding itemOrderLeaseBinding;
                itemOrderLeaseBinding = DataBindingUtil.inflate(mActivity.getLayoutInflater(),R.layout.item_order_lease,null,false);
                View view = itemOrderLeaseBinding.getRoot();
                itemOrderLeaseBinding.setModel(goodsBean);
                if(i == info.goods.size()-1){
                    itemOrderLeaseBinding.lineLast.setVisibility(View.GONE);
                }else{
                    itemOrderLeaseBinding.lineLast.setVisibility(View.VISIBLE);
                }
                GoodsListModel.setPrice(goodsBean.commondityPrice,goodsBean.original,mActivity,info.orderType,itemOrderLeaseBinding.beTip,itemOrderLeaseBinding.nowTip);
                setItemDatas(fromType,itemOrderLeaseBinding,info,goodsBean);
                itemOrderLeaseBinding.grayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grayBtnOnclick(info,mActivity,goodsBean);
                    }
                });
                itemOrderLeaseBinding.redBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                itemOrderBinding.rootView.addView(view);
            }
            setItemDatas(fromType,itemOrderBinding,info,null);

        }
//        itemOrderBinding.phoneCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrderDetailActivity.into(mActivity,info);
//            }
//        });
    }

    /**
     * 设置Item数据
     * @param fromType
     * @param object
     * @param info
     */
    private static void setItemDatas(int fromType, Object object, OrderModel info, OrderModel.GoodsBean goodsBean){//设置item数据
        ItemOrderLeaseBinding itemOrderLeaseBinding = null;
        ItemOrderBinding itemOrderBinding = null;
        if(object instanceof ItemOrderLeaseBinding){
            itemOrderLeaseBinding = (ItemOrderLeaseBinding) object;
        }else if(object instanceof ItemOrderBinding){
            itemOrderBinding = (ItemOrderBinding) object;
        }
        if(itemOrderLeaseBinding != null){
            setStatusTv(fromType,itemOrderLeaseBinding,info);
            setStatusBtn(fromType,itemOrderLeaseBinding,info,goodsBean);
        }else if(itemOrderBinding != null){
            setStatusBtn(fromType,itemOrderBinding,info);
        }
    }

    /**
     * 设置状态
     * @param fromType
     * @param itemOrderLeaseBinding
     * @param info
     */
    private static void setStatusTv(int fromType,ItemOrderLeaseBinding itemOrderLeaseBinding,OrderModel info){
        if(fromType == OrderListAcitivity.LEASE){
            if(info.tradeStatus == 0){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }else if(info.tradeStatus == 2){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"申请通过",View.VISIBLE);
            }else if(info.tradeStatus == 9){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }else{
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }
        }else if(fromType == OrderListAcitivity.SHOP){
            if(info.tradeStatus == 0){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }else if(info.tradeStatus == 2){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }else if(info.tradeStatus == 3){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }else if(info.tradeStatus == 9){
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }else{
                setViewVisable(itemOrderLeaseBinding.tvStatus,"",View.GONE);
            }
        }

    }

    /**
     * 设置Item底部按钮状态
     * @param fromType
     * @param object
     * @param info
     */
    private static void setStatusBtn(int fromType,ItemOrderBinding object,OrderModel info){
        if(fromType == OrderListAcitivity.LEASE){
            if(info.tradeStatus == 0){
                object.itemBottomLayout.setVisibility(View.VISIBLE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }else if(info.tradeStatus == 2){
                object.itemBottomLayout.setVisibility(View.VISIBLE);
                setViewVisable(object.grayBtn,"查看详情",View.GONE);
                setViewVisable(object.redBtn,"确认收货",View.VISIBLE);
            }else if(info.tradeStatus == 9){
                object.itemBottomLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"评价",View.VISIBLE);
                setViewVisable(object.redBtn,"",View.GONE);
            }else{
                object.itemBottomLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }
        }else if(fromType == OrderListAcitivity.SHOP){
            if(info.tradeStatus == 0){
                object.itemBottomLayout.setVisibility(View.VISIBLE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"付款",View.VISIBLE);
            }else if(info.tradeStatus == 2){
                object.itemBottomLayout.setVisibility(View.VISIBLE);
                setViewVisable(object.grayBtn,"提醒发货",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }else if(info.tradeStatus == 3){
                object.itemBottomLayout.setVisibility(View.VISIBLE);
                setViewVisable(object.grayBtn,"查看物流",View.GONE);
                setViewVisable(object.redBtn,"确认收货",View.VISIBLE);
            }else if(info.tradeStatus == 9){
                object.itemBottomLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"评价订单",View.VISIBLE);
                setViewVisable(object.redBtn,"",View.GONE);
            }else{
                object.itemBottomLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }
        }
    }
    /**
     * 设置底部按钮状态
     * @param fromType
     * @param object
     * @param info
     */
    private static void setStatusBtn(int fromType, ItemOrderLeaseBinding object, OrderModel info, OrderModel.GoodsBean goodsBean){
        if(fromType == OrderListAcitivity.LEASE){
            if(info.tradeStatus == 0){
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }else if(info.tradeStatus == 2){
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"查看详情",View.GONE);
                setViewVisable(object.redBtn,"确认收货",View.VISIBLE);
            }else if(info.tradeStatus == 9 || info.tradeStatus == 6){//租赁可以评价的订单
                object.contentBottomBtnLayout.setVisibility(View.VISIBLE);
                if(goodsBean != null && !TextUtils.isEmpty(goodsBean.isComment) && goodsBean.isComment.equals("N")){
                    setViewVisable(object.grayBtn,"评价订单",View.VISIBLE);
                }else{
                    setViewVisable(object.grayBtn,"再次租赁",View.VISIBLE);
                }
                setViewVisable(object.redBtn,"",View.GONE);
            }else{
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }
        }else if(fromType == OrderListAcitivity.SHOP){
            if(info.tradeStatus == 0){
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"付款",View.VISIBLE);
            }else if(info.tradeStatus == 2){
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"提醒发货",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }else if(info.tradeStatus == 3){
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"查看物流",View.GONE);
                setViewVisable(object.redBtn,"确认收货",View.VISIBLE);
            }else if(info.tradeStatus == 9){
                object.contentBottomBtnLayout.setVisibility(View.VISIBLE);
                if(goodsBean != null && !TextUtils.isEmpty(goodsBean.isComment) && goodsBean.isComment.equals("N")){
                    setViewVisable(object.grayBtn,"评价订单",View.VISIBLE);
                }else{
                    setViewVisable(object.grayBtn,"再次购买",View.VISIBLE);
                }

                setViewVisable(object.redBtn,"",View.GONE);
            }else{
                object.contentBottomBtnLayout.setVisibility(View.GONE);
                setViewVisable(object.grayBtn,"",View.GONE);
                setViewVisable(object.redBtn,"",View.GONE);
            }
        }
    }
    private static void setViewVisable(TextView view,String content,int status){
        view.setVisibility(status);
        view.setText(content);
    }
    /**
     * 灰色按钮
     *
     * @param info
     * @param goodsBean
     */
    private static void grayBtnOnclick(OrderModel info, Activity mActivity, OrderModel.GoodsBean goodsBean) {
        if (info.tradeStatus == 0) {//待付款

        } else if (info.tradeStatus == 2) {//待发货

        } else if (info.tradeStatus == 3) {//待收货

        } else if (info.tradeStatus == 9) {//待评价
            if(!TextUtils.isEmpty(goodsBean.isComment)){
                if(goodsBean.isComment.equals("N")){
                    CommentActivity.into(mActivity,goodsBean);
                }else{
                    GoodsDetailActivity.into(mActivity,goodsBean.commodityId,goodsBean);
                }
            }
        } else if (info.tradeStatus == 6) {//租赁待评价
            if(!TextUtils.isEmpty(goodsBean.isComment)){
                if(goodsBean.isComment.equals("N")){
                    CommentActivity.into(mActivity,goodsBean);
                }else{
                    GoodsDetailActivity.into(mActivity,goodsBean.commodityId,goodsBean);
                }
            }
        }else {

        }
    }

    /**
     * 红色按钮
     *
     * @param info
     */
    private static void redBtnOnclick(OrderModel info) {
        if (info.tradeStatus == 0) {//待付款
//            new MyPayDialog(mActivity, info).showBuyDialog();
//            OrderConfirmActivity.into(mActivity,new PrepareBuyModel(null,null,null,info),PrepareBuyModel.ORDER_LIST);
        } else if (info.tradeStatus == 2) {//待发货

        } else if (info.tradeStatus == 3) {//待收货
//            goSure(info);
        } else if (info.tradeStatus == 9) {//待评价

        } else {

        }

    }


}

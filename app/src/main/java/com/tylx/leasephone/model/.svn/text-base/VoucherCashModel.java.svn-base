package com.tylx.leasephone.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ItemVouchercashBinding;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.lease_shop.coupon.CouponListAcitivity;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/7/13.
 */

public class VoucherCashModel extends BaseModel {
    public String id;
    public long startTime;
    public String source;
    public int status;
    public String memberId;
    public String remark;
    public String title;
    public String writeOrderId;
    public String sourceDesc;
    public String isLock;
    public String acquisitionTime;
    public long useTime;
    public String useRemark;
    public String subTitle;
    public String amtFace;
    public String amtUsed;
    public String minAmount;
    public String goodsType;
    public long endTime;
    public ArrayList<String> goodsName;
    public String arrivalAmt;

    public static void setDatas(final CouponListAcitivity mActivity, final ItemVouchercashBinding itemVouchercashBinding, final VoucherCashModel info, final int fromType) {
        itemVouchercashBinding.setModel(info);

        itemVouchercashBinding.limtTv.setText(info.goodsType.contains("-1")?"（不限商品）": "(仅限"+info.goodsType+"使用)");
        itemVouchercashBinding.remarkTv.setText(info.minAmount.contains("-1")?"全场通用":"全场满"+info.minAmount+"使用");
//        switch (info.status) {
//            case 1://1 未核销
//                itemVouchercashBinding.imgTishi.setImageResource(R.mipmap.ticket_cai);
//                break;
//            case 2://2 已核销
//                break;
//            case 3://3 已过期
//                itemVouchercashBinding.imgTishi.setImageResource(R.mipmap.ticket_gray);
//                break;
//            case 4://4 已作废
//                break;
//            case 5://5 已退款
//                break;
//        }

        switch (fromType) {
            case 0://1 可使用
                itemVouchercashBinding.imgTishi.setImageResource(R.mipmap.ticket_cai);
                itemVouchercashBinding.tvBefore.setVisibility(View.VISIBLE);
                break;
            case 1://2 已过期
                itemVouchercashBinding.imgTishi.setImageResource(R.mipmap.ticket_gray);
                itemVouchercashBinding.tvBefore.setVisibility(View.GONE);
                break;
        }
        itemVouchercashBinding.bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switch (info.status) {
//                    case 1://1 未核销
//                        if (mActivity.fromType == CouponListAcitivity.ORDER_CONFIRM) {
//                            mActivity.setResultOk(info);
//                        } else {
//                            HomeActivity.switchTo = 2;
//                            mActivity.finish();
//
//                        }
//                        break;
//                    case 2://2 已核销
//                        break;
//                    case 3://3 已过期
//                        break;
//                    case 4://4 已作废
//                        break;
//                    case 5://5 已退款
//                        break;
//                }
                switch (fromType){
                    case 0://1 可使用
                        if (mActivity.fromType == CouponListAcitivity.ORDER_CONFIRM) {
                            mActivity.setResultOk(info);
                        } else {
                            HomeActivity.switchTo = 2;
                            mActivity.finish();
                        }
                        break;
                    case 1://2 已过期

                        break;
                }
            }
        });
    }
}

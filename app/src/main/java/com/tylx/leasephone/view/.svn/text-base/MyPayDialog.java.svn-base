package com.tylx.leasephone.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;

import com.tylx.leasephone.R;
import com.tylx.leasephone.alipay.PayDemoActivity;
import com.tylx.leasephone.databinding.DialogGoBuyBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.OrderModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderConfirmActivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderListAcitivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.PayActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;

import java.math.BigDecimal;

/**
 * Created by wangm on 2017/7/24.
 */

public class MyPayDialog {
    private Activity mContext;
    private OrderModel info;
    private int payType = 0;
    private Dialog mGoBuyDialog;
    private DialogGoBuyBinding dialogGoBuyBinding;

    public MyPayDialog(Activity mContext, OrderModel info,DialogListen onDialogListen) {
        this.mContext = mContext;
        this.info = info;
        this.onDialogListen = onDialogListen;
    }

    public void showBuyDialog() {
            mGoBuyDialog = ProbjectUtil.getDialog(mContext, Gravity.BOTTOM);
            dialogGoBuyBinding = DataBindingUtil.inflate(mContext.getLayoutInflater(), R.layout.dialog_go_buy,null,false);
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
                    if(dialogGoBuyBinding.submit.getIsDoResult()){
                        dialogGoBuyBinding.submit.reset();
                    }else{
                        goBuy();
                    }
                }


            });
            dialogGoBuyBinding.cbWexin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        payType = 2;
                    }else{
                        payType = 0;
                    }
                }
            });
            dialogGoBuyBinding.cbZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        payType = 1;
                    }else{
                        payType = 0;
                    }
                }
            });
            dialogGoBuyBinding.weixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialogGoBuyBinding.cbZfb.isChecked()){
                        dialogGoBuyBinding.cbZfb.setChecked(false);
                        dialogGoBuyBinding.cbWexin.setChecked(true);
                    }else{
                        if(dialogGoBuyBinding.cbWexin.isChecked()){
                            dialogGoBuyBinding.cbWexin.setChecked(false);
                        }else{
                            dialogGoBuyBinding.cbWexin.setChecked(true);
                        }
                    }
                }
            });
            dialogGoBuyBinding.zfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialogGoBuyBinding.cbWexin.isChecked()){
                        dialogGoBuyBinding.cbWexin.setChecked(false);
                        dialogGoBuyBinding.cbZfb.setChecked(true);
                    }else{
                        if(dialogGoBuyBinding.cbZfb.isChecked()){
                            dialogGoBuyBinding.cbZfb.setChecked(false);
                        }else{
                            dialogGoBuyBinding.cbZfb.setChecked(true);
                        }
                    }
                }
            });
        vourcherMoney = new BigDecimal(info.sumAmt).subtract(new BigDecimal(info.payableAmt)).toString();
        dialogGoBuyBinding.curMoney.setText("￥ " + info.payableAmt);
        dialogGoBuyBinding.voucherMoney.setText(vourcherMoney);
        dialogGoBuyBinding.dialogTotal.setText(info.sumAmt+"");
        mGoBuyDialog.show();
    }
    private void goBuy() {
        if(payType <= 0){
            ToastUtil.showToast("请选择支付方式");
            dialogGoBuyBinding.submit.reset();
            return;
        }
        dialogGoBuyBinding.submit.setEnabled(false);

        mGoBuyDialog.dismiss();
        payGoods();
    }


    /**
     * 去支付
     */
    String orderId,curMoney,body,vourcherMoney = "0";
    private void payGoods() {
        orderId = info.id;
        curMoney = info.payableAmt;
        vourcherMoney = new BigDecimal(info.sumAmt).subtract(new BigDecimal(info.payableAmt)).toString();
        new MemberModel().payOrder(orderId, curMoney, body, DatasStore.getCurMember().id, payType + "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                String rsa = (String) o;
                onDialogListen.onSuccess();
                if(!TextUtils.isEmpty(rsa)){
                    if(rsa.equals("SUCCESS")){
                        mGoBuyDialog.dismiss();
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
                ToastUtil.showToast(resultsModel.getErrorMsg().toString());
            }
        });
    }
    DialogListen onDialogListen;
    public interface DialogListen{
        void onSuccess();
        void Failed();
    }
}

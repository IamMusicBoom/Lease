package com.tylx.leasephone.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.DialogParamterBinding;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.NormInfoVo;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;

import java.util.List;

/**
 * Created by wangm on 2017/7/27.
 */

public class MyNewParamterDialog {

    public static final int SHOPPING_CART = 22;
    public static final int GOODS_DETAIL = 33;


    private Dialog mDialog;
    private int type;
    private Activity mActivity;
    private DialogParamterBinding binding;
    private GoodsModel goodsModel;
    private CommondityNormDetail defaultCommondityNormDetail;
    private List<CommondityNormDetail> commondityNormDetails;//所有套餐
    public MyNewParamterDialog(int type, Activity mActivity, GoodsModel goodsModel, CommondityNormDetail defaultCommondityNormDetail) {
        this.type = type;
        this.mActivity = mActivity;
        this.defaultCommondityNormDetail = defaultCommondityNormDetail;
        this.goodsModel = goodsModel;
    }

    public void show() {
        init();
    }
    public void dismiss() {
        if(mDialog != null){
            mDialog.dismiss();
        }

    }
    /**
     * 根据TYPE 展示不同的dialog布局
     *
     * @param type
     * @param goodsModel
     */
    private void showDifferentView(int type, GoodsModel goodsModel) {
        if (type == SHOPPING_CART) {//购物车
            binding.imgTitleLayout.setVisibility(View.VISIBLE);
            binding.detailBuyBtn.setText("确定");
            binding.imgTitleStyle.setText(GoodsModel.formateSpecifications(goodsModel));
            binding.imgTitleName.setText(goodsModel.name);
            ProbjectUtil.loadImage(binding.imgTitle, goodsModel.listPic, R.mipmap.nofile);
            binding.detailAddBtn.setVisibility(View.GONE);
        } else if (type == GOODS_DETAIL) {//商品详情
            binding.imgTitleLayout.setVisibility(View.GONE);
            binding.detailBuyBtn.setText(goodsModel.businessType == 1 ? "确认" : "确认");
            binding.detailAddBtn.setVisibility(goodsModel.businessType == 1 ? View.GONE : View.GONE);
        }
    }
    private void init() {
        commondityNormDetails = goodsModel.commondityNormDetails;
        if (null == mDialog) {
            mDialog = ProbjectUtil.getDialog(mActivity, Gravity.BOTTOM);
            binding = DataBindingUtil.inflate(mActivity.getLayoutInflater(), R.layout.dialog_paramter, null, false);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {//Dialog消失的监听
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mDialog = null;
                }
            });
            binding.detailBuyBtn.setOnClickListener(new View.OnClickListener() {//购买Btn
                @Override
                public void onClick(View v) {
//                    CommondityNormDetail selectCommondity = null;
//                    if (null == selectList || selectList.size() <= 0) {
//                        selectCommondity = getSelectCommondity(commondityNormDetails);
//                    }else{
//                        selectCommondity = getSelectCommondity(selectList);
//                    }
//                    if (null == selectCommondity) {
//                        ToastUtil.showToast("没有该套餐~");
//                    } else {
//                        mDialog.dismiss();
//                        onBtnClickListentr.onBuyBtnClick(selectCommondity);
//                    }

                }
            });
            binding.detailAddBtn.setOnClickListener(new View.OnClickListener() {//添加购物车Btn
                @Override
                public void onClick(View v) {
//                    CommondityNormDetail selectCommondity = null;
//                    if (null == selectList || selectList.size() <= 0) {
//                        selectCommondity = getSelectCommondity(commondityNormDetails);
//                    }else{
//                        selectCommondity = getSelectCommondity(selectList);
//                    }
//                    if (null == selectCommondity) {
//                        ToastUtil.showToast("没有该套餐~");
//                    } else
//
//                    {
//                        onBtnClickListentr.onAddBtnClick(selectCommondity);
//                        mDialog.dismiss();
//                    }
                }
            });
        }
        showDifferentView(type, goodsModel);//显示不同的布局

        addCommondityNormDetails();
        mDialog.setContentView(binding.getRoot());
        mDialog.show();
    }

    /**
     * 添加套餐
     */
    private void addCommondityNormDetails() {
        View paramterView = mActivity.getLayoutInflater().inflate(R.layout.item_dialog_pramter, null);
        binding.paramterLayout.addView(paramterView);
        MyRadioGroup rp = (MyRadioGroup) paramterView.findViewById(R.id.paramters);
//            rp.addView();
    }
}

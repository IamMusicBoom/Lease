package com.tylx.leasephone.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.DialogParamterBinding;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.NormDetail;
import com.tylx.leasephone.model.NormInfoVo;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangm on 2017/7/12.
 */

public class MyParamterDialog {
    public static final int SHOPPING_CART = 22;
    public static final int GOODS_DETAIL = 33;
    private Activity mActivity;
    private GoodsModel goodsModel;
    private int type;
    private DialogParamterBinding binding;
    private Dialog mDialog;
    private CommondityNormDetail defaultCommondityNormDetail;

    private List<CommondityNormDetail> commondityNormDetails;//所有套餐

    public MyParamterDialog(Activity mActivity, GoodsModel goodsModel, CommondityNormDetail defaultCommondityNormDetail, int type) {
        this.mActivity = mActivity;
        this.goodsModel = goodsModel;
        this.type = type;
        this.defaultCommondityNormDetail = defaultCommondityNormDetail;
    }

    public void show() {
        init();
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
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
                    CommondityNormDetail selectCommondity = null;
                    if (null == selectList || selectList.size() <= 0) {
                        selectCommondity = getSelectCommondity(commondityNormDetails);
                    } else {
                        selectCommondity = getSelectCommondity(selectList);
                    }
                    if (null == selectCommondity) {
                        ToastUtil.showToast("没有该套餐~");
                    } else {
                        mDialog.dismiss();
                        onBtnClickListentr.onBuyBtnClick(selectCommondity);
                    }

                }
            });
            binding.detailAddBtn.setOnClickListener(new View.OnClickListener() {//添加购物车Btn
                @Override
                public void onClick(View v) {
                    CommondityNormDetail selectCommondity = null;
                    if (null == selectList || selectList.size() <= 0) {
                        selectCommondity = getSelectCommondity(commondityNormDetails);
                    } else {
                        selectCommondity = getSelectCommondity(selectList);
                    }
                    if (null == selectCommondity) {
                        ToastUtil.showToast("没有该套餐~");
                    } else

                    {
                        onBtnClickListentr.onAddBtnClick(selectCommondity);
                        mDialog.dismiss();
                    }
                }
            });
        }
        showDifferentView(type, goodsModel);//显示不同的布局
        List<NormInfoVo> paramters = goodsModel.normInfos;//所有参数
        if (null != paramters && paramters.size() > 0) {
            showAllParamters(paramters);//展示所有的参数
            setCanSelectParamter(commondityNormDetails);
            setCommondityNorDetail(defaultCommondityNormDetail);
            setTitleDatas(getSelectCommondity(commondityNormDetails));
        }
        mDialog.setContentView(binding.getRoot());
        mDialog.show();
    }

    /**
     * 设置可以选择的参数
     */
    private void setCanSelectParamter(List<CommondityNormDetail> canSelectParamterList) {
        for (String key : groupMap.keySet()) {
            RadioGroup radioGroup = groupMap.get(key);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                String code = paramterMap.get(radioButton.getText().toString());
                if (canSelectParamterList.size() <= 0) {
                    radioButton.setBackgroundResource(R.drawable.cannot_select_bg);
                } else {
                    for (int j = 0; j < canSelectParamterList.size(); j++) {//所有套餐包含的颜色
                        if(canSelectParamterList.get(j) != null){
                            if (!TextUtils.isEmpty(canSelectParamterList.get(j).commodityNorm)) {
                                if(!TextUtils.isEmpty(code)){
                                    if (canSelectParamterList.get(j).commodityNorm.contains(code)) {
//                        radioButton.setEnabled(true);
                                        radioButton.setBackgroundResource(R.drawable.can_select_bg);
//                        if(!radioButton.isChecked()){
//                            radioButton.setChecked(true);
//                        }
                                        break;
                                    } else {
//                        radioButton.setEnabled(false);
                                        radioButton.setBackgroundResource(R.drawable.cannot_select_bg);
//                        if(radioButton.isChecked()){
//                            radioButton.setChecked(false);
//                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置套餐
     *
     * @param commondityNormDetail
     */
    private void setCommondityNorDetail(CommondityNormDetail commondityNormDetail) {
        for (String key : groupMap.keySet()) {
            RadioGroup radioGroup = groupMap.get(key);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                String code = paramterMap.get(radioButton.getText().toString());
                if(!TextUtils.isEmpty(code)){
                    if (commondityNormDetail.commodityNorm.contains(code)) {//设置默认值
                        radioButton.setChecked(true);
                    } else {
                        radioButton.setChecked(false);
                    }
                }

            }
        }
    }

    /**
     * 展示所有参数
     *
     * @param paramters
     */
    private HashMap<String, RadioGroup> groupMap = new HashMap<>();//参数容器
    private HashMap<String, String> paramterMap = new HashMap<>();//参数
    private List<CommondityNormDetail> selectList = new ArrayList<>();//筛选后的集合


    private void showAllParamters(List<NormInfoVo> paramters) {
        for (int i = 0; i < paramters.size(); i++) {
            NormInfoVo paramter = paramters.get(i);
            View paramterView = mActivity.getLayoutInflater().inflate(R.layout.item_dialog_pramter, null);
            TextView paramterName = (TextView) paramterView.findViewById(R.id.paramter_name);
            RadioGroup nameLayout = (RadioGroup) paramterView.findViewById(R.id.paramters);
            nameLayout.setTag(paramter);
            nameLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    RadioButton ra = (RadioButton) group.findViewById(checkedId);
                    if (ra == null) {
                        return;
                    }
                    if (!ra.isPressed()) {
                        return;
                    }
                    NormInfoVo tag = (NormInfoVo) group.getTag();
                    if (selectList.size() <= 0) {//选择套餐为0
                        for (String key : groupMap.keySet()) {//遍历套餐容器
                            if (!tag.normCode.equals(key)) {//清空其他两个套餐容器中选中的按钮
                                groupMap.get(key).clearCheck();
                            }
                        }
                    }
                    String code = paramterMap.get(ra.getText().toString());//获取当前选中的套餐信息
                    if (selectList.size() <= 0) {//如果选择的套餐为0
                        selectList = filterList(code, commondityNormDetails);//在原有套餐选择
                    } else {//如果选择的套餐不为0，在现在有套餐继续筛选
                        selectList = filterList(code, selectList);
                    }
                    if (selectList.size() <= 0) {//继续筛选的套餐为0的，继续做清空操作
                        for (String key : groupMap.keySet()) {
                            if (!tag.normCode.equals(key)) {
                                groupMap.get(key).clearCheck();
                            }
                        }
                    }
                    if (selectList.size() <= 0) {//清空过后继续进行当前点击的套餐参数继续筛选
                        selectList = filterList(code, commondityNormDetails);
                    } else {
                        selectList = filterList(code, selectList);
                    }
                    setCanSelectParamter(selectList);
                    setTitleDatas(getSelectCommondity(selectList));
                }
            });
            groupMap.put(paramter.normCode, nameLayout);// 参数的Map
            if (!TextUtils.isEmpty(paramter.normValue))
                paramterName.setText(paramter.normValue);// 颜色 内存
            for (int j = 0; j < paramter.normDetails.size(); j++) {//红色 绿色 （128GB 64 GB） 等
                NormDetail normDetail = paramter.normDetails.get(j);
                paramterMap.put(normDetail.normValue, normDetail.normDetailCode);
                final RadioButton nameTv = new RadioButton(mActivity);
                nameTv.setButtonDrawable(0);
                RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.rightMargin = 20;
                lp.topMargin = 20;
                nameTv.setLayoutParams(lp);
                nameTv.setBackgroundResource(R.drawable.paramter_selector);
                nameTv.setTextColor(ContextCompat.getColorStateList(mActivity, R.color.paramter_text_selector));
                if (!TextUtils.isEmpty(normDetail.normValue))
                    nameTv.setText(normDetail.normValue);
                nameLayout.addView(nameTv);
            }
            binding.paramterLayout.addView(paramterView);
        }
    }

    private void setTitleDatas(CommondityNormDetail selectCommondity) {
        if (null != selectCommondity) {
            binding.imgTitleNum.setText("库存：" + selectCommondity.stock + " 件");
            binding.imgTitleStyle.setText(GoodsModel.formateSpecifications(selectCommondity));
        } else {
            binding.imgTitleNum.setText("库存：" + "--" + " 件");
            binding.imgTitleStyle.setText("");

        }
        binding.imgTitleName.setText(goodsModel.name);


    }

    /**
     * 获取选中的套餐
     */
    private CommondityNormDetail getSelectCommondity(List<CommondityNormDetail> list) {
        List<CommondityNormDetail> tempList = new ArrayList<>();
        List<CommondityNormDetail> needList = new ArrayList<>();
        for (String key : groupMap.keySet()) {
            RadioGroup radioGroup = groupMap.get(key);

            RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            if (null == radioButton) {
                return null;
            }
            String code = paramterMap.get(radioButton.getText().toString());
            if (tempList.size() <= 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).commodityNorm.contains(code)) {
                        tempList.add(list.get(i));
                        needList.add(list.get(i));
                    }
                }
            } else {
                if (needList.size() > 0) {
                    needList.clear();
                }
                for (int i = 0; i < tempList.size(); i++) {
                    if (tempList.get(i).commodityNorm.contains(code)) {
                        needList.add(tempList.get(i));
                    }
                }
                tempList.clear();
                tempList.addAll(needList);
            }
        }
        if (needList.size() > 0) {
            return needList.get(0);
        } else {
            return null;
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

    /**
     * 筛选集合
     */
    private List<CommondityNormDetail> filterList(String code, List<CommondityNormDetail> list) {
        List<CommondityNormDetail> tempList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).commodityNorm.contains(code)) {
                tempList.add(list.get(i));
            }
        }
        return tempList;
    }

    public OnBtnClickListentr onBtnClickListentr;

    public void setOnBtnClickListentr(OnBtnClickListentr onBtnClickListentr) {
        this.onBtnClickListentr = onBtnClickListentr;
    }

    public interface OnBtnClickListentr {
        void onAddBtnClick(CommondityNormDetail commondityNormDetail);

        void onBuyBtnClick(CommondityNormDetail commondityNormDetail);
    }

}

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
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangm on 2017/7/11.
 * 老子不得用这个
 */

public class ParamterDialog {
    public static final int SHOPPING_CART = 22;
    public static final int GOODS_DETAIL = 33;
    private Activity mActivity;
    private GoodsModel goodsModel;
    private int type;
    private DialogParamterBinding binding;
    private Dialog mDialog;
    private List<NormInfoVo> paramters;//所有参数集合
    private List<CommondityNormDetail> commondityNormDetails;//所有套餐
    private HashMap<String, RadioGroup> groupMap = new HashMap<>();//参数容器
    private HashMap<String, String> paramterMap = new HashMap<>();//参数
    private CommondityNormDetail defaultCommondity;//默认选中
        private List<CommondityNormDetail> selectList = new ArrayList<>();//筛选后的集合

    public ParamterDialog(Activity mActivity, GoodsModel goodsModel, int type) {
        this.mActivity = mActivity;
        this.goodsModel = goodsModel;
        this.type = type;
        initDialog();
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
            binding.detailBuyBtn.setText(goodsModel.businessType == 1 ? "马上预约" : "立即购买");
            binding.detailAddBtn.setVisibility(goodsModel.businessType == 1 ? View.GONE : View.VISIBLE);
        }
    }


    private void initDialog() {
        if (null == mDialog) {
            mDialog = ProbjectUtil.getDialog(mActivity, Gravity.BOTTOM);
            binding = DataBindingUtil.inflate(mActivity.getLayoutInflater(), R.layout.dialog_paramter, null, false);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {//Dialog消失的监听
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
            binding.detailBuyBtn.setOnClickListener(new View.OnClickListener() {//购买Btn
                @Override
                public void onClick(View v) {

                }
            });
            binding.detailAddBtn.setOnClickListener(new View.OnClickListener() {//添加购物车Btn
                @Override
                public void onClick(View v) {

                }
            });
        }
        showDifferentView(type, goodsModel);//显示不同的布局

        paramters = goodsModel.normInfos;//所有参数
        if (null != paramters && paramters.size() > 0) {
            commondityNormDetails = goodsModel.commondityNormDetails;//所有套餐
            showAllParamters();//展示所有的参数
            setTitleDatas();
        }
        mDialog.setContentView(binding.getRoot());
        mDialog.show();
    }

    /**
     * 展示所有参数
     */

    private void showAllParamters() {
        for (int i = 0; i < paramters.size(); i++) {
            NormInfoVo paramter = paramters.get(i);
            View paramterView = mActivity.getLayoutInflater().inflate(R.layout.item_dialog_pramter, null);
            TextView paramterName = (TextView) paramterView.findViewById(R.id.paramter_name);
            RadioGroup nameLayout = (RadioGroup) paramterView.findViewById(R.id.paramters);
            nameLayout.setTag(paramter);
            nameLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    NormInfoVo tag = (NormInfoVo) group.getTag();//获取点击时的参数实体
                    int index = group.indexOfChild(group.findViewById(checkedId));//获取点击时在容器中的下标
                    String normDetailCode = tag.normDetails.get(index).normDetailCode;//通过下标拿到具体参数实体对应的具体code
                    String normCode = tag.normDetails.get(index).normCode;//通过下标拿到具体参数实体对应的母体code
                    if (normCode.equals("CORLOR")) {//设置默认值
                        setCurDefault(normDetailCode);
                        setDefaultCanSelect();
                        setTitleDatas();
                    }
                    filterList(normCode, normDetailCode);
                    setFilterList(normCode);
                    if (null != selectList && selectList.size() > 0)
                        defaultCommondity = selectList.get(0);
                    setTitleDatas();
                }
            });
            groupMap.put(paramter.normCode, nameLayout);// 参数的Map
            if (!TextUtils.isEmpty(paramter.normValue))
                paramterName.setText(paramter.normValue);// 颜色 内存
            for (int j = 0; j < paramter.normDetails.size(); j++) {//红色 绿色 （128GB 64 GB） 等
                NormDetail normDetail = paramter.normDetails.get(j);
                paramterMap.put(normDetail.normValue, normDetail.normDetailCode);
                RadioButton nameTv = new RadioButton(mActivity);
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
                setDefault();
                setDefaultCanSelect();
            }
            binding.paramterLayout.addView(paramterView);
        }
    }

    /**
     * 设置当前默认值
     */
    private void setCurDefault(String normDetailCode) {
        List<CommondityNormDetail> tempList = new ArrayList<>();
        for (int i = 0; i < commondityNormDetails.size(); i++) {
            if (commondityNormDetails.get(i).commodityNorm.contains(normDetailCode)) {
                tempList.add(commondityNormDetails.get(i));
            }
        }
        if (null != tempList && tempList.size() > 0) {
            defaultCommondity = tempList.get(0);
        }


    }

    /**
     * 筛选List
     */
    private ArrayList<String> selsctParamterStr = new ArrayList<>();
    private String selectParamter[] = null;

    private void filterList(String normCode, String normDetailCode) {
        List<CommondityNormDetail> tempList = new ArrayList<>();
        if (normCode.contains("CORLOR")) {//如果点击时为CORLOR 容器 初始化所有选择参数（选择参数时候的标准）
            selsctParamterStr.clear();
            selectParamter = new String[groupMap.size() - 1];
            selectList.clear();
        }
        if (selsctParamterStr.size() > 0) {//如果选择参数的集合大于0
            if (selsctParamterStr.get(selsctParamterStr.size() - 1).contains(normCode)) {//如果在同一种参数中切换，替换掉原参数
                selsctParamterStr.set(selsctParamterStr.size() - 1, normDetailCode);
            } else {//如果不是在同一种参数中切换，加入参数集合
                selsctParamterStr.add(normDetailCode);
            }
        } else {//如果选择参数的集合<=0，说明是第一次点击，直接加入参数
            selsctParamterStr.add(normDetailCode);
        }
        for (int j = selsctParamterStr.size() - 1; j >= 0; j--) {//遍历选择的参数集合，取出最新的选择参数，选择参数为总参数-1
            if (selsctParamterStr.size() - 1 - j < selectParamter.length) {
                selectParamter[selsctParamterStr.size() - 1 - j] = selsctParamterStr.get(j);
            }

        }
        for (int j = 0; j < selectParamter.length; j++) {//遍历所有选择参数，筛选符合参数的集合
            Log.d("", selectParamter[j] + "-----------------------");
            if (!TextUtils.isEmpty(selectParamter[j])) {
                if (selectList.size() > 0) {//如果筛选集合>0说明已有筛选条件，所以直接从以后筛选集合中继续筛选
                    for (int k = 0; k < selectList.size(); k++) {
                        if (selectList.get(k).commodityNorm.contains(selectParamter[j])) {
                            tempList.add(selectList.get(k));
                        }
                    }
                    selectList.clear();
                    selectList.addAll(tempList);
                    tempList.clear();
                } else {////如果筛选集合<0说明第一次点击，没有筛选条件，所以在所有套餐中筛选
                    for (int k = 0; k < goodsModel.commondityNormDetails.size(); k++) {
                        if (goodsModel.commondityNormDetails.get(k).commodityNorm.contains(selectParamter[j])) {
                            tempList.add(goodsModel.commondityNormDetails.get(k));
                        }
                    }
                    selectList.clear();
                    selectList.addAll(tempList);
                    tempList.clear();
                }
            }
            Log.d("", selectList.size() + "-----------------");
        }
    }

    /**
     * 设置筛选后的List
     *
     * @param normCode
     */
    private void setFilterList(String normCode) {
        for (String key : groupMap.keySet()) {//设置值
            if (normCode.equals("CORLOR")) {//如果点击的时候CORLOR
                if (key.equals("CORLOR")) {//CORLOR参数的容器不做任何操作

                } else {//不是CORLOR的容器，设置包含参数的按钮为可点击，不包含的不可点击
                    RadioGroup radioGroup = groupMap.get(key);
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {//遍历不是CORLOR的容器
                        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                        String code = paramterMap.get(radioButton.getText().toString());
                        for (int j = 0; j < selectList.size(); j++) {
                            if (selectList.get(0).commodityNorm.contains(code)) {//设置默认选择，取筛选集合的第一条
                                radioButton.setChecked(true);
                            } else {
                                radioButton.setChecked(false);
                            }
                            if (selectList.get(j).commodityNorm.contains(code)) {//设置是否能选择
                                radioButton.setEnabled(true);
                                break;
                            } else {
                                radioButton.setEnabled(false);
                            }
                        }
                    }
                }
            } else {//如果点击的时候不是CORLOR
                if (key.equals("CORLOR")) {//不对CORLOR容器做任何操作

                } else {//设置不是CORLOR的所有容器
                    RadioGroup radioGroup = groupMap.get(key);
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                        String code = paramterMap.get(radioButton.getText().toString());
                        for (int j = 0; j < selectList.size(); j++) {
                            if (selectList.get(j).commodityNorm.contains(code)) {
                                radioButton.setEnabled(true);
                                break;
                            } else {
                                radioButton.setEnabled(false);
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * 设置默认值
     */
    private void setDefaultCanSelect() {
        for (String key : groupMap.keySet()) {//设置显示时的默认值
            if (key.equals("CORLOR")) {//如果是CORLOR容器
                RadioGroup radioGroup = groupMap.get(key);
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    String code = paramterMap.get(radioButton.getText().toString());
                    if (defaultCommondity.commodityNorm.contains(code)) {//设置默认值
                        radioButton.setChecked(true);
                    } else {
                        radioButton.setChecked(false);
                    }for (int j = 0; j < commondityNormDetails.size(); j++) {//所有套餐包含的颜色
                        if (commondityNormDetails.get(j).commodityNorm.contains(code)) {
                            radioButton.setEnabled(true);
                            break;
                        } else {
                            radioButton.setEnabled(false);
                        }
                    }

                }
            } else {//如果不是CORLOR容器
                RadioGroup radioGroup = groupMap.get(key);
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    String code = paramterMap.get(radioButton.getText().toString());
                    if (defaultCommondity.commodityNorm.contains(code)) {//设置默认值
                        radioButton.setChecked(true);
                    } else {
                        radioButton.setChecked(false);
                    }
                    if (selectList.size() > 0)
                        selectList.clear();
                    selectList.add(defaultCommondity);
                    for (int j = 0; j < selectList.size(); j++) {
                        if (selectList.get(j).commodityNorm.contains(code)) {//设置默认值包含的套餐
                            radioButton.setEnabled(true);
                            break;
                        } else {
                            radioButton.setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置一个默认值
     */
    private void setDefault() {
        if (null != goodsModel && null != goodsModel.commondityNormDetails && goodsModel.commondityNormDetails.size() > 0 && null != goodsModel.commondityNormDetails.get(0)) {
            defaultCommondity = goodsModel.commondityNormDetails.get(0);
        } else {
            defaultCommondity = goodsModel.commondityNormDetails.get(0);
        }
    }




    /**
     * 设置值
     */
    private void setTitleDatas() {
        binding.imgTitleName.setText(goodsModel.name);
        binding.imgTitleNum.setText("库存：" + defaultCommondity.stock + " 件");
        binding.imgTitleStyle.setText(GoodsModel.formateSpecifications(goodsModel));
    }
}

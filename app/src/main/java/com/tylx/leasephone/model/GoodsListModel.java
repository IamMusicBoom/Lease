package com.tylx.leasephone.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangm on 2017/7/5.
 */

public class GoodsListModel extends BaseModel {
    public static final int HOME_DATA = 1;
    public static final int LEASE_PHONE_DATA = 2;
    public static final int LEASE_COMPUTER_DATA = 3;
    public ArrayList<GoodsModel> result;

    public static void setDatas(final ItemHomeBinding itemHomeBinding, final GoodsModel info, final Activity mActivity, int type) {
        itemHomeBinding.setModel(info);
        itemHomeBinding.styleTv.setText(GoodsModel.formateSpecifications(info));
        CommondityNormDetail defaultCommondityNormDetail = GoodsModel.getDefaultCommondityNormDetail(info);

        setPriceDatas(defaultCommondityNormDetail, info.businessType, itemHomeBinding.nowTip, itemHomeBinding.beTip, mActivity);

        itemHomeBinding.phoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.into(mActivity, info.id,null);
            }
        });
        if (type == LEASE_COMPUTER_DATA || type == LEASE_PHONE_DATA) {//租赁手机或电脑
            itemHomeBinding.goAddImg.setImageResource(R.mipmap.more);
        } else if (type == HOME_DATA) {//首页数据
            if (info.businessType == 1)
                itemHomeBinding.goAddImg.setImageResource(R.mipmap.more);
            else if (info.businessType == 2)
                itemHomeBinding.goAddImg.setImageResource(R.drawable.add_img_selector);
            else
                itemHomeBinding.goAddImg.setImageResource(R.mipmap.more);
        }
    }
    public static void setLineText(Object object, TextView moneyTv, int businessType, Activity mActivity) {
        GoodsModel goodsModel = null;
        CommondityNormDetail defaultCommondityNormDetail = null;
        if (object instanceof GoodsModel) {
            goodsModel = (GoodsModel) object;
            defaultCommondityNormDetail = GoodsModel.getDefaultCommondityNormDetail(goodsModel);
        } else if (object instanceof ShoppingGoodsModel) {
            ShoppingGoodsModel shoppingGoodsModel = (ShoppingGoodsModel) object;
            defaultCommondityNormDetail = GoodsModel.getSelectCommondityNormDetail(shoppingGoodsModel);
        } else if (object instanceof CommondityNormDetail) {
            defaultCommondityNormDetail = (CommondityNormDetail) object;
        }
        setPriceDatas(defaultCommondityNormDetail,businessType,moneyTv,null,mActivity);
    }
    public static void setPriceDatas(CommondityNormDetail defaultCommondityNormDetail, int businessType, TextView nowTip, TextView beTip, Activity mActivity) {
        if(null != defaultCommondityNormDetail){
            setPrice(defaultCommondityNormDetail.price,defaultCommondityNormDetail.original,mActivity,businessType,beTip,nowTip);
        }else{
            setPrice("","",mActivity,businessType,beTip,nowTip);
        }
    }
    public static void setPrice(String price,String original,Activity mActivity,int businessType,TextView beTip,TextView nowTip) {
        String yzj = businessType == 1 ? mActivity.getResources().getString(R.string.yzj) : mActivity.getResources().getString(R.string.ysj);
        String xzj = businessType == 1 ? mActivity.getResources().getString(R.string.xzj) : mActivity.getResources().getString(R.string.xsj);
        String my = businessType == 1 ? mActivity.getResources().getString(R.string.my) : mActivity.getResources().getString(R.string.y);
        String nowP;
        String beP;
        if(!TextUtils.isEmpty(price) && null != nowTip){
            nowP = price;
        }else{
            nowP = "xxx";
        }
        SpannableString styledText2 = new SpannableString(xzj + nowP + my);
        styledText2.setSpan(new TextAppearanceSpan(mActivity, R.style.small_text2), 0, xzj.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText2.setSpan(new TextAppearanceSpan(mActivity, R.style.big_text2), xzj.length(), xzj.length() + nowP.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText2.setSpan(new TextAppearanceSpan(mActivity, R.style.small_text2), xzj.length() + nowP.length(), xzj.length() + nowP.length() + my.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if(null != nowTip){
            nowTip.setText(styledText2, TextView.BufferType.SPANNABLE);
        }
        if(!TextUtils.isEmpty(original) && null != beTip){
            beP = original;
        }else{
            beP = "xxx";
        }
        SpannableString styledText = new SpannableString(yzj + beP + my);
        styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.small_text), 0, yzj.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.big_text), yzj.length(), yzj.length() + beP.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mActivity, R.style.small_text), yzj.length() + beP.length(), yzj.length() + beP.length() + my.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if(null != beTip){
            beTip.setText(styledText, TextView.BufferType.SPANNABLE);
        }



    }

}

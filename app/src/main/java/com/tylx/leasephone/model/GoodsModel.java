package com.tylx.leasephone.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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

import com.bumptech.glide.load.data.ExifOrientationStream;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityGoodsDetailBinding;
import com.tylx.leasephone.databinding.DialogParamterBinding;
import com.tylx.leasephone.databinding.ItemShoppingCartBinding;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangm on 2017/6/19.
 */

public class GoodsModel extends BaseModel {

    public String id;

    public String typeCode;

    public int businessType;

    public String listPic;

    public String pic1;

    public String pic2;

    public String pic3;

    public String name;

    public String introducePic;

    public String introducePar;

    public String salesVolume;

    public int status;

    public long upTime;

    public int upUser;

    public long downTime;

    public int downUser;

    public long createTime;

    public long updateTime;

    public List<CommondityNormDetail> commondityNormDetails;

    public List<NormInfoVo> normInfos;

    public String remark;
    private Object datas;

    /**
     * 获取默认套餐
     * @param goodsModel
     * @return
     */
    public static CommondityNormDetail getDefaultCommondityNormDetail(GoodsModel goodsModel){
        if(null != goodsModel && null != goodsModel.commondityNormDetails && goodsModel.commondityNormDetails.size()>0){
            return goodsModel.commondityNormDetails.get(0);
        }else{
            return null;
        }

    }
    public static CommondityNormDetail getDefaultCommondityNormDetail(GoodsModel goodsModel,String id){
        if(null != goodsModel && null != goodsModel.commondityNormDetails && goodsModel.commondityNormDetails.size()>0){
            for (int i = 0; i < goodsModel.commondityNormDetails.size(); i++) {
                if(!TextUtils.isEmpty(id) && id.equals(goodsModel.commondityNormDetails.get(i).id)){
                    return goodsModel.commondityNormDetails.get(i);
                }
            }
            return goodsModel.commondityNormDetails.get(0);
        }else{
            return null;
        }

    }

    /**
     * 获取选中套餐
     * @param shoppingGoodsModel
     * @return
     */
    public static CommondityNormDetail getSelectCommondityNormDetail(ShoppingGoodsModel shoppingGoodsModel){
        if(null != shoppingGoodsModel && !TextUtils.isEmpty(shoppingGoodsModel.commodityNormDetailId)){
            if(null != shoppingGoodsModel.commondityInfoVo && null != shoppingGoodsModel.commondityInfoVo.commondityNormDetails){
                for (int i = 0; i < shoppingGoodsModel.commondityInfoVo.commondityNormDetails.size(); i++) {
                    CommondityNormDetail commondityNormDetail = shoppingGoodsModel.commondityInfoVo.commondityNormDetails.get(i);
                    if(shoppingGoodsModel.commodityNormDetailId.equals(commondityNormDetail.id)){
                        return commondityNormDetail;
                    }
                }
            }
        }
        return null;
    }
    /**
     * 通过code格式话名字
     **/
    public static String formateSpecifications(GoodsModel goodsModel) {
        if(null != goodsModel){
            return formateSpecifications(getDefaultCommondityNormDetail(goodsModel));
        }
        return "";
    }

    /**
     * 通过code格式话名字
     **/
    public static String formateSpecifications(ShoppingGoodsModel shoppingGoodsModel) {
        if(null != shoppingGoodsModel){
            return formateSpecifications(getSelectCommondityNormDetail(shoppingGoodsModel));
        }
        return "";
    }

    /**
     * 通过code格式话名字
     **/
    public static String formateSpecifications(CommondityNormDetail defaultCommondityNormDetail) {
        if(null != defaultCommondityNormDetail){
            return formateSpecifications(defaultCommondityNormDetail.commodityNorm);
        }
        return "";
    }

    public static String formateSpecifications(String formatStr) {
        if(!TextUtils.isEmpty(formatStr)){
            StringBuffer sb = new StringBuffer();
            String[] split = formatStr.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = DatasStore.formatName(split[i]);
                sb.append(s + " ");
            }
            return sb.toString();
        }
        return "";
    }
}

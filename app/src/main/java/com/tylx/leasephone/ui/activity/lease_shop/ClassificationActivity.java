package com.tylx.leasephone.ui.activity.lease_shop;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityClasssificationBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.ShopGoodsModel;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/6/21.
 * 商城列表筛选
 */

public class ClassificationActivity extends BaseActivity implements View.OnClickListener{
    ActivityClasssificationBinding binding;
    String typeCode = "";
    public static final int REQUEST_CODE = 66;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("分类筛选");
        getDatas();
    }

    private void getDatas() {
        new MemberModel().secondCommondityTypes(new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                ArrayList<ShopGoodsModel> result = (ArrayList<ShopGoodsModel>) o;
                showDatas(result);
                hideLoading();

            }
            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }


    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_classsification, _containerLayout, false);
        return binding.getRoot();
    }
    public static void into(Activity activity){
        Intent intent = new Intent(activity,ClassificationActivity.class);
        activity.startActivityForResult(intent,REQUEST_CODE);
    }


    /**
     * 展示数据
     * @param result
     */
    LinearLayout childView1,childView2;
    private void showDatas(ArrayList<ShopGoodsModel> result) {
        for (int i = 0; i < result.size(); i++) {
            if(childView1 != null)
                childView1 = null;

            if(childView2 != null)
                childView2 = null;

            final ShopGoodsModel shopGoodsModel = result.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_shop_classification,null);
            TextView nameTv = (TextView) view.findViewById(R.id.name);
            TextView moreTv = (TextView) view.findViewById(R.id.view_more);
            moreTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    typeCode = shopGoodsModel.typeCode;
                    setOk(typeCode);
                }
            });
            LinearLayout detailLayou = (LinearLayout) view.findViewById(R.id.detail_layout);
            nameTv.setText(shopGoodsModel.typeName);
            for (int j = 0; j < shopGoodsModel.commondityTypes.size(); j++) {
                if(j <= 3){
                    final ShopGoodsModel childShopModel = shopGoodsModel.commondityTypes.get(j);
                    TextView childNameTv = new TextView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
                    childNameTv.setGravity(Gravity.CENTER);
                    layoutParams.leftMargin = 5;
                    layoutParams.rightMargin = 5;
                    layoutParams.topMargin = 5;
                    layoutParams.bottomMargin = 5;
                    childNameTv.setLayoutParams(layoutParams);
                    childNameTv.setText(childShopModel.typeName);
                    childNameTv.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
                    childNameTv.setTextSize(12);
                    childNameTv.setBackgroundResource(R.drawable.classification_bg);
                    childNameTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            typeCode = childShopModel.typeCode;
                            setOk(typeCode);
                        }
                    });
                    if(j == 0){
                        childView1 = (LinearLayout) getLayoutInflater().inflate(R.layout.item_shop_classification_detail,null);
                    }else if(j == 2){
                        childView2 = (LinearLayout) getLayoutInflater().inflate(R.layout.item_shop_classification_detail,null);
                    }
                    if(j<2){
                        childView1.addView(childNameTv);
                    }else {
                        childView2.addView(childNameTv);
                    }
                }
            }
            if(shopGoodsModel.commondityTypes.size()>0){
                if(null != childView1){
                    detailLayou.addView(childView1);
                }
                if(null != childView2){
                    detailLayou.addView(childView2);
                }
            }
            binding.layout.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_classification:
                typeCode = "";
                setOk(typeCode);
                break;
        }
    }

    private void setOk(String typeCode){
        Intent intent = new Intent();
        intent.putExtra("typeCode",typeCode);
        setResult(RESULT_OK,intent);
        finish();
    }
}

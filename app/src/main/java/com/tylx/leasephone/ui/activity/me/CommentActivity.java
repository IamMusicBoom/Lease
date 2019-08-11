package com.tylx.leasephone.ui.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityCommentBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.OrderModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderListAcitivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * Created by wangm on 2017/6/20.
 * 评论
 */

public class CommentActivity extends BaseActivity {
    ActivityCommentBinding binding;
    public static final int REQUEST_CODE = 51;
    OrderModel.GoodsBean goodsBean;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("评价");
        setRightText("发布");
        goodsBean = (OrderModel.GoodsBean) getIntent().getSerializableExtra("GoodsBean");
        if(goodsBean == null){
            showToast("未获取到参数信息");
            finish();
        }
        binding.commentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = binding.commentEt.getText().toString().length();
                binding.commentNum.setText(length+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void goNext() {
        super.goNext();
       goPublish();
    }
    String orderGoodsId,content,grade,payOrderId;

    /**
     * 发表评论
     */
    private void goPublish() {
        orderGoodsId = goodsBean.id;
        payOrderId = goodsBean.orderTradeId;
        content = binding.commentEt.getText().toString();
        grade = String.valueOf(binding.commentBar.getRating());
        new MemberModel().comment(orderGoodsId, content, grade, DatasStore.getCurMember().id, payOrderId, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading(true);
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                OrderListAcitivity.isShopCommentComplite = true;
                OrderListAcitivity.isLeaseCommentComplite = true;
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_comment, _containerLayout, false);
        return binding.getRoot();
    }
    public  static void into(Activity activity,OrderModel.GoodsBean goodsBean){
        Intent intent = new Intent(activity,CommentActivity.class);
        intent.putExtra("GoodsBean",goodsBean);
        activity.startActivityForResult(intent,REQUEST_CODE);
    }
}

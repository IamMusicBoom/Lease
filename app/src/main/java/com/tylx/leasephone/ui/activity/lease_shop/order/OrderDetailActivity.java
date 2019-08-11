package com.tylx.leasephone.ui.activity.lease_shop.order;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityOrderDetailBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.OrderModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.ui.activity.me.CommentActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.view.MyPayDialog;
import com.tylx.leasephone.view.WmaOptionView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    ActivityOrderDetailBinding binding;
    OrderModel orderModel;
    private List<OrderModel.GoodsBean> goods;
    public static final int REQUEST_CODE = 981;

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_order_detail, null, false);
        return binding.getRoot();
    }

    @Override
    protected void goNext() {
        if (orderModel.orderType == 2 && orderModel.tradeStatus == 9) {
            goDelete(orderModel);
        }
    }

    private void goDelete(OrderModel orderModel) {
        new MemberModel().delOrder(orderModel.id, DatasStore.getCurMember().id + "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
        orderModel = (OrderModel) getIntent().getSerializableExtra("OrderModel");
        setTitle("订单详情");
        registerBack();
        if (orderModel == null) {
            showToast("没有找到相应订单");
            finish();
        }
        binding.setModel(orderModel);
        if (orderModel.orderType == 2 && orderModel.tradeStatus == 9) {
            setRightImage(R.mipmap.delete_write);
        }
        goods = orderModel.goods;
        if (goods != null && goods.size() > 0) {
            for (int i = 0; i < goods.size(); i++) {
                OrderModel.GoodsBean goodsBean = goods.get(i);
                View view = getLayoutInflater().inflate(R.layout.item_order_detail, null);
                ImageView img = (ImageView) view.findViewById(R.id.item_order_detail_img);
                TextView name = (TextView) view.findViewById(R.id.item_order_detail_name);
                TextView style = (TextView) view.findViewById(R.id.item_order_detail_style);
                TextView price = (TextView) view.findViewById(R.id.item_order_detail_price);
                TextView num = (TextView) view.findViewById(R.id.item_order_detail_num);
                TextView commentBtn = (TextView) view.findViewById(R.id.comment_btn);
                ProbjectUtil.loadImage(img, goodsBean.commondityImg, R.mipmap.nofile);
                name.setText(goodsBean.goodsName);
                price.setText(goodsBean.commondityPrice);
                num.setText(goodsBean.commondityNum + " 件");
                style.setText(GoodsModel.formateSpecifications(goodsBean.commondityNorm));
                if (orderModel.tradeStatus == 9 || orderModel.tradeStatus == 6) {
                    commentBtn.setVisibility(View.VISIBLE);
                    if (orderModel.goods != null && orderModel.goods.size() > 0) {
                        if (!TextUtils.isEmpty(goods.get(i).isComment) && orderModel.goods.get(0).isComment.equals("N")) {
                            commentBtn.setText("评价订单");
                        } else {
                            commentBtn.setText("再次购买");
                        }
                    }
                } else {
                    commentBtn.setVisibility(View.GONE);
                }
                final int finalI = i;
                commentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(goods.get(finalI).isComment) && orderModel.goods.get(finalI).isComment.equals("N")) {
//                            commentBtn.setText("评价订单");
                            CommentActivity.into(OrderDetailActivity.this, goods.get(finalI));
                        } else {
//                            commentBtn.setText("再次购买");
                            GoodsDetailActivity.into(OrderDetailActivity.this, goods.get(finalI).commodityId, goods.get(finalI));
                        }
                    }
                });
                binding.goodsLayout.addView(view);
            }
        }
        setPayButton();
        setVoucher();
    }

    /**
     * 设置卡券
     */
    private void setVoucher() {
        WmaOptionView orderDetailJian = binding.orderDetailJian;
        TextView valueTv = orderDetailJian.getValueTv();
        String value = new BigDecimal(orderModel.sumAmt).subtract(new BigDecimal(orderModel.payableAmt)).toString();
        valueTv.setText(value);

    }

    private void setPayButton() {
        if (orderModel == null) {
            return;
        }
        if (orderModel.orderType == 2) {//商城
            if (orderModel.tradeStatus == 0) {
                binding.orderDetailPay.setVisibility(View.VISIBLE);
                binding.orderDetailPay.setText("立即付款");
            } else if (orderModel.tradeStatus == 3) {//确认收货
                binding.orderDetailPay.setVisibility(View.VISIBLE);
                binding.orderDetailPay.setText("确认收货");
            } else {
                binding.orderDetailPay.setVisibility(View.GONE);
            }
        } else if (orderModel.orderType == 1) {//租赁
            if (orderModel.tradeStatus == 2) {//确认收货
                binding.orderDetailPay.setVisibility(View.VISIBLE);
                binding.orderDetailPay.setText("确认收货");
            } else {
                binding.orderDetailPay.setVisibility(View.GONE);
            }
        } else {
            binding.orderDetailPay.setVisibility(View.GONE);
        }
    }


    public static void into(Activity mActivity, OrderModel info) {
        Intent intent = new Intent(mActivity, OrderDetailActivity.class);
        intent.putExtra("OrderModel", info);
        mActivity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        if (orderModel == null) {
            return;
        }
        if (orderModel.orderType == 2) {//商城
            if (orderModel.tradeStatus == 0) {
                goPay(orderModel);
            } else if (orderModel.tradeStatus == 3) {//确认收货
                goSure(orderModel);
            } else {
                binding.orderDetailPay.setVisibility(View.GONE);
            }
        } else if (orderModel.orderType == 1) {//租赁
            if (orderModel.tradeStatus == 2) {//确认收货
                goSure(orderModel);
            } else {
                binding.orderDetailPay.setVisibility(View.GONE);
            }
        } else {
            binding.orderDetailPay.setVisibility(View.GONE);
        }
    }

    private void goPay(OrderModel info) {

        new MyPayDialog(OrderDetailActivity.this, info, new MyPayDialog.DialogListen() {
            @Override
            public void onSuccess() {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void Failed() {

            }
        }).showBuyDialog();
    }

    /**
     * 确认收货
     *
     * @param info
     */
    private void goSure(OrderModel info) {
        new MemberModel().confirmReceipt(info.id, DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading(true);
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }
}

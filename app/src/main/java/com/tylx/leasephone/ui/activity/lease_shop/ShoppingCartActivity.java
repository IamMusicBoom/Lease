package com.tylx.leasephone.ui.activity.lease_shop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.adapter.IRecyclerViewAdapter;
import com.tylx.leasephone.databinding.ActivityShoppingCartBinding;
import com.tylx.leasephone.databinding.ItemShoppingCartBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.BuyModel;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.NormDetail;
import com.tylx.leasephone.model.NormInfoVo;
import com.tylx.leasephone.model.PrepareBuyModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.ShoppingGoodsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderConfirmActivity;
import com.tylx.leasephone.ui.activity.login.LoginActivity;
import com.tylx.leasephone.ui.activity.me.MeActrivity;
import com.tylx.leasephone.ui.fragment.LeaseFragment;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListActivity;
import com.tylx.leasephone.view.MyParamterDialog;
import com.tylx.leasephone.view.ParamterDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/6/22.
 * 购物车
 */

public class ShoppingCartActivity extends BaseRecyclerViewListActivity<ShoppingGoodsModel, ItemShoppingCartBinding> implements View.OnClickListener{
    private boolean isEdit;
    ActivityShoppingCartBinding binding;
    ArrayList<ShoppingGoodsModel> result;
    ArrayList<ShoppingGoodsModel> prepareBuy = new ArrayList<>();
    private boolean isSelectAll;
    public static boolean isRefresh;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("购物车");
        setRightText("编辑");
        getAdapter().setOnDeleteListener(new IRecyclerViewAdapter.OnDeleteListener() {
            @Override
            public void onDelete() {
                showListplaceholder(true);
                binding.bottomLayout.setVisibility(View.GONE);
            }
        });
        binding.allCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!buttonView.isPressed()){
                    return;
                }
                if(isChecked){
                    prepareBuy.clear();
                    if(result!= null){
                        for (int i = 0; i < result.size(); i++) {
                            prepareBuy.add(result.get(i));
                        }
                        isSelectAll = true;
                    }
                }else{
                    prepareBuy.clear();
                    isSelectAll = false;
                }
                calculationMoney(prepareBuy);
                getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isRefresh){
            binding.listview.setRefreshing(true);
            isRefresh = false;
        }
    }

    @Override
    protected void goNext() {
        super.goNext();
        if (isEdit) {
            setRightText("编辑");
            isEdit = false;
        } else {
            setRightText("完成");
            isEdit = true;
        }
        prepareBuy.clear();
        isSelectAll = false;
        calculationMoney(prepareBuy);
        binding.setIsEdit(isEdit);
        getAdapter().notifyDataSetChanged();
    }


    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shopping_cart, _containerLayout, false);
        _listview = binding.listview;
        return binding.getRoot();
    }

    public static void into(Activity activity) {
        Intent intent = new Intent(activity, ShoppingCartActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }

    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().getShoppingList(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (!_listview.isRefreshing())
                    showLoading();
                if(page == 1 && _listview.isRefreshing()){
                    isSelectAll = false;
                    prepareBuy.clear();
                    binding.allCb.setChecked(false);
                    calculationMoney(prepareBuy);
                }
            }

            @Override
            public void successful(Object o) {

                result = (ArrayList<ShoppingGoodsModel>) o;
                total = result.size();
                loadData(result);
                hideLoading();
                if(result.size()<=0){
                    binding.bottomLayout.setVisibility(View.GONE);
                }else{
                    binding.bottomLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if(binding.listview.isRefreshing()){
                    binding.listview.onRefreshComplete();
                }
            }
        });
    }

    @Override
    public int getItemLayout(int viewTyoe) {
        return R.layout.item_shopping_cart;
    }

    @Override
    public void bindItemData(final ItemShoppingCartBinding itemShoppingBinding, final ShoppingGoodsModel info, final int position) {
        final MyParamterDialog myParamterDialog = new MyParamterDialog(ShoppingCartActivity.this, info.commondityInfoVo, info.commodityNormDetail, MyParamterDialog.SHOPPING_CART);
        itemShoppingBinding.setModel(info);
        itemShoppingBinding.setIsEdit(isEdit);
        itemShoppingBinding.setIsSelectAll(isSelectAll);
        itemShoppingBinding.shoppingParameter.setText(GoodsModel.formateSpecifications(info.commodityNormDetail));
        itemShoppingBinding.shoppingEidtParameter.setText(GoodsModel.formateSpecifications(info.commodityNormDetail));
        if(null != info.commondityInfoVo){
            GoodsListModel.setLineText(info, itemShoppingBinding.shoppingMoney,info.commondityInfoVo.businessType, this);
        }
        itemShoppingBinding.shoppingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除
                removreShopping(itemShoppingBinding, info, "-1", position, isEdit);
                if(prepareBuy.contains(info)){
                    prepareBuy.remove(info);
                }
                result.remove(info);
                calculationMoney(prepareBuy);
            }
        });
        itemShoppingBinding.shoppingEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除
                removreShopping(itemShoppingBinding, info, "-1", position, isEdit);
                if(prepareBuy.contains(info)){
                    prepareBuy.remove(info);
                }
                result.remove(info);
            }
        });
        itemShoppingBinding.paramterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//展示参数
                if (null != info.commondityInfoVo)
                    myParamterDialog.show();            }
        });
        myParamterDialog.setOnBtnClickListentr(new MyParamterDialog.OnBtnClickListentr() {//修改套餐
            @Override
            public void onAddBtnClick(CommondityNormDetail commondityNormDetail) {

            }

            @Override
            public void onBuyBtnClick(CommondityNormDetail commondityNormDetail) {
                modifyShopping(commondityNormDetail,info,myParamterDialog);
            }
        });
        itemShoppingBinding.shoppingAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.commodityNum == 99)
                    showToast("不能添加了");
                else {
                    showLoading(false);
                    addShopping(itemShoppingBinding, info, "1");
                }
            }
        });
        itemShoppingBinding.shoppingSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.commodityNum == 1)
                    showToast("不能减少了");
                else {
                    showLoading(false);
                    removreShopping(itemShoppingBinding, info, "1", position, isEdit);
                }
            }
        });
        if(!isEdit){
            itemShoppingBinding.shoppingCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!buttonView.isPressed()){
                        return;
                    }
//                    CommondityNormDetail commodityNormDetail = info.commodityNormDetail;
//                    commodityNormDetail.num = info.commodityNum;
                    if(isChecked){
                        prepareBuy.add(info);
                    }else{
                        if(prepareBuy.contains(info)){
                            prepareBuy.remove(info);
                        }
                    }
                    calculationMoney(prepareBuy);
                }
            });
        }

    }

    /**
     * 加入购物车
     *
     * @param info
     */
    private void addShopping(final ItemShoppingCartBinding itemShoppingBinding, final ShoppingGoodsModel info, String num) {
        String commodityNormDetailId = "";
        CommondityNormDetail selectCommondityNormDetail = info.commodityNormDetail;
        if (null != selectCommondityNormDetail && !TextUtils.isEmpty(selectCommondityNormDetail.id)) {
            commodityNormDetailId = selectCommondityNormDetail.id;
            new MemberModel().addShopping(this,commodityNormDetailId, num, new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {

                }

                @Override
                public void successful(Object o) {
                    int commodityNum = ++info.commodityNum;
                    itemShoppingBinding.shoppingEditNum.setText(String.valueOf(commodityNum));
                    HomeActivity.isRefresh = true;
                    hideLoading();
                }
                @Override
                public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg().toString());
                }
            });
        }
    }
    /**
     * 移除购物车
     * @param itemShoppingBinding
     * @param info
     * @param num
     * @param position
     * @param isEdit
     */
    private void removreShopping(final ItemShoppingCartBinding itemShoppingBinding, final ShoppingGoodsModel info,final String num, final int position, final boolean isEdit) {
        new MemberModel().removeShopping(DatasStore.getCurMember().id, info.id, num, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (isEdit) {

                } else{
                    showLoading(false);
                }

            }

            @Override
            public void successful(Object o) {
                if (isEdit) {
                    if(num.equals("-1")){
                        removeIndexAnim(position);
                    }else{
                        int commodityNum = --info.commodityNum;
                        itemShoppingBinding.shoppingEditNum.setText(String.valueOf(commodityNum));
                    }
                } else {
                    itemShoppingBinding.mSmlItem.smoothClose();
                    removeIndexAnim(position);

                }
                hideLoading();
                HomeActivity.isRefresh = true;
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_pay:
                if(isEdit){
                    if(!binding.allCb.isChecked()){
                        showToast("未选择要删除的商品");
                        return;
                    }
                    deleteAllDatas();
                }
                else
                    goPaySeleteDatas();
                break;
            case R.id.all_select:
                if(binding.allCb.isChecked())
                    binding.allCb.setChecked(false);
                else
                    binding.allCb.setChecked(true);
                break;
        }
    }

    /**
     * 结算选中数据
     */
    private void goPaySeleteDatas() {
        BigDecimal bigDecimal = calculationMoney(prepareBuy);
        if(bigDecimal.compareTo(new BigDecimal(0))<=0){
            showToast("选点东西吧~");
            return;
        }
        PrepareBuyModel prepareBuyModel = new PrepareBuyModel(prepareBuy,null,null,null);
        OrderConfirmActivity.into(this,prepareBuyModel,PrepareBuyModel.SHOPPING_CART);
    }

    /**
     * 删除所有数据
     */
    private void deleteAllDatas() {
        new MemberModel().clearShopping(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                HomeActivity.isRefresh = true;
                binding.listview.setRefreshing(true);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }
    /**
     * 修改套餐
     */
    private void modifyShopping(CommondityNormDetail commondityNormDetail, ShoppingGoodsModel info, final MyParamterDialog dialog) {
        new MemberModel().modifyShopping(DatasStore.getCurMember().id, info.id, info.commodityNum+"", commondityNormDetail.id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                binding.listview.setRefreshing(true);
                dialog.dismiss();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    /**
     * 计算总额
     * @param commondityNormDetails
     */
    public BigDecimal calculationMoney(List<ShoppingGoodsModel> commondityNormDetails){
        if (commondityNormDetails == null || result == null) {
            return new BigDecimal("0");
        }
        BigDecimal totalMoney = new BigDecimal("0");
        for (int i = 0; i < commondityNormDetails.size(); i++) {
            CommondityNormDetail  commondityNormDetail = commondityNormDetails.get(i).commodityNormDetail;
            BigDecimal num = new BigDecimal(commondityNormDetails.get(i).commodityNum);
            BigDecimal price = new BigDecimal(commondityNormDetail.price);
            BigDecimal total = num.multiply(price);
            totalMoney = totalMoney.add(total);
        }
        binding.payMoney.setText(totalMoney+"");
        if(commondityNormDetails.size() >= result.size()){
            binding.allCb.setChecked(true);
        }else{
            binding.allCb.setChecked(false);
        }
        return totalMoney;
    }
}

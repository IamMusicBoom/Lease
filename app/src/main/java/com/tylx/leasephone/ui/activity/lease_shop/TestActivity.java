package com.tylx.leasephone.ui.activity.lease_shop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.tylx.leasephone.R;
import com.tylx.leasephone.adapter.IViewDataAdapter;
import com.tylx.leasephone.databinding.ActivityTestBinding;
import com.tylx.leasephone.databinding.ItemOrderConfirmBinding;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.ShoppingGoodsModel;
import com.tylx.leasephone.model.adapter.ThtGosn;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseListActivity;
import com.tylx.leasephone.util.activity.BaseListFragment;
import com.tylx.leasephone.view.MyParamterDialog;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/6/23.
 * 测试Activity
 */

public class TestActivity extends BaseListActivity<ShoppingGoodsModel>{
//    MyParamterDialog myParamterDialog;
//    String json = "{\"message\":\"SUCCESS\",\"extendAttr\":{\"result\":[{\"id\":\"u57inhe5wtr6491doet0fbn5\",\"commodityNum\":1,\"commodityNormDetailId\":\"6xpk40xrxtrec980ku27ixza\",\"cartId\":\"fr3e92nxgrrsdit9oc9r47of\",\"commodityNormDetail\":{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_01,ME_02,SCREEN_03\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ixza\",\"commodityId\":1},\"commondityInfoVo\":{\"name\":\"华为P9\",\"id\":1,\"typeCode\":\"1\",\"status\":2,\"businessType\":2,\"remark\":null,\"createTime\":1498614039000,\"updateTime\":null,\"commondityNormDetails\":[{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_04,ME_01,SCREEN_01\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ia13\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_04,ME_02,SCREEN_01\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ia16\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_04,ME_01,SCREEN_03\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27iaza\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_02,ME_01,SCREEN_01\",\"original\":109010.00,\"price\":190.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ihcc\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_02,ME_03,SCREEN_02\",\"original\":109010.00,\"price\":190.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ihcd\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_01,ME_02,SCREEN_01\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ihza\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_01,ME_02,SCREEN_03\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku27ixza\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_01,ME_03,SCREEN_02\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrec980ku2fihza\",\"commodityId\":1},{\"remark\":null,\"stock\":100,\"commodityNorm\":\"CORLOR_02,ME_03,SCREEN_01\",\"original\":10900.00,\"price\":90.00,\"saleNum\":10,\"worth\":900000.00,\"id\":\"6xpk40xrxtrecz80ku27ihza\",\"commodityId\":1}],\"normInfos\":[{\"sort\":1,\"normCode\":\"CORLOR\",\"normValue\":\"颜色\",\"normDetails\":[{\"sort\":1,\"normCode\":\"CORLOR\",\"normDetailCode\":\"CORLOR_01\",\"normValue\":\"绿色\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"CORLOR\",\"normDetailCode\":\"CORLOR_05\",\"normValue\":\"褐色\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"CORLOR\",\"normDetailCode\":\"CORLOR_04\",\"normValue\":\"黑色\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"CORLOR\",\"normDetailCode\":\"CORLOR_03\",\"normValue\":\"土豪金色\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"CORLOR\",\"normDetailCode\":\"CORLOR_02\",\"normValue\":\"蓝色\",\"isOpen\":\"Y\"}],\"isOpen\":\"Y\",\"normMean\":\"颜色\"},{\"sort\":2,\"normCode\":\"ME\",\"normValue\":\"内存\",\"normDetails\":[{\"sort\":1,\"normCode\":\"ME\",\"normDetailCode\":\"ME_02\",\"normValue\":\"64G\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"ME\",\"normDetailCode\":\"ME_05\",\"normValue\":\"1228G\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"ME\",\"normDetailCode\":\"ME_04\",\"normValue\":\"16G\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"ME\",\"normDetailCode\":\"ME_03\",\"normValue\":\"8G\",\"isOpen\":\"Y\"},{\"sort\":2,\"normCode\":\"ME\",\"normDetailCode\":\"ME_01\",\"normValue\":\"128G\",\"isOpen\":\"Y\"}],\"isOpen\":\"Y\",\"normMean\":\"内存\"},{\"sort\":3,\"normCode\":\"SCREEN\",\"normValue\":\"屏幕尺寸\",\"normDetails\":[{\"sort\":1,\"normCode\":\"SCREEN\",\"normDetailCode\":\"SCREEN_02\",\"normValue\":\"5.5寸\",\"isOpen\":\"Y\"},{\"sort\":1,\"normCode\":\"SCREEN\",\"normDetailCode\":\"SCREEN_04\",\"normValue\":\"12.9寸\",\"isOpen\":\"Y\"},{\"sort\":1,\"normCode\":\"SCREEN\",\"normDetailCode\":\"SCREEN_03\",\"normValue\":\"4.1寸\",\"isOpen\":\"Y\"},{\"sort\":1,\"normCode\":\"SCREEN\",\"normDetailCode\":\"SCREEN_01\",\"normValue\":\"4.7寸\",\"isOpen\":\"Y\"}],\"isOpen\":\"Y\",\"normMean\":\"屏幕尺寸\"}],\"listPic\":\"temp/preImg/070515222335150860bfd.jpg\",\"pic1\":\"temp/preImg/070515222335150860bfd.jpg\",\"pic2\":\"temp/preImg/070515222335150860bfd.jpg\",\"pic3\":\"temp/preImg/0628115146980851fa8fb.jpg\",\"introducePic\":null,\"introducePar\":null,\"salesVolume\":10000,\"upTime\":null,\"upUser\":null,\"downTime\":null,\"downUser\":null}}]},\"status\":true}";
//    @Override
//    protected void onPostInject(Bundle savedInstanceState) {
//        super.onPostInject(savedInstanceState);
//        ResultsModel rsm = ResultsModel.getInstanseFromStr(json);
//        ArrayList<ShoppingGoodsModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<ShoppingGoodsModel>>() {}.getType());
//
//        ShoppingGoodsModel info = omList.get(0);
//
//        myParamterDialog = new MyParamterDialog(this, info.commondityInfoVo, info.commodityNormDetail, MyParamterDialog.SHOPPING_CART);
//        myParamterDialog.setOnBtnClickListentr(new MyParamterDialog.OnBtnClickListentr() {
//            @Override
//            public void onAddBtnClick(CommondityNormDetail commondityNormDetail) {
//
//            }
//
//            @Override
//            public void onBuyBtnClick(CommondityNormDetail commondityNormDetail) {
//
//            }
//        });
//    }
    ActivityTestBinding binding;
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_test, _containerLayout, false);
        _listview = binding.listview;
        _listview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        return binding.getRoot();
    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
        setAdapter(new MyAdapter());
        getDatas();
    }

    private void getDatas() {
        ArrayList<ShoppingGoodsModel> result = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            result.add(new ShoppingGoodsModel());
        }
        total = 30;
        loadData(result);

    }


    //    @Override
//    public void onClick(View v) {
//        myParamterDialog.show();
//    }
    class MyAdapter extends IViewDataAdapter<ShoppingGoodsModel,ItemOrderConfirmBinding>{

    @Override
    protected int getItemLayoutId(int position) {
        return R.layout.item_order_confirm;
    }

    @Override
    protected void bindData(ItemOrderConfirmBinding binding, ShoppingGoodsModel info, int position) {

    }
}
}


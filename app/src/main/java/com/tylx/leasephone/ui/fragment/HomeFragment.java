package com.tylx.leasephone.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tylx.leasephone.R;
import com.tylx.leasephone.adapter.IViewDataAdapter;
import com.tylx.leasephone.databinding.FragmentHomeBinding;
import com.tylx.leasephone.databinding.HeadHomeListBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.HomeAdModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.lease_shop.detail.GoodsDetailActivity;
import com.tylx.leasephone.util.activity.BaseListFragment;
import com.tylx.leasephone.home_view_pager.AutoPlayViewPager;
import com.tylx.leasephone.home_view_pager.BannerAdapter;
import com.tylx.leasephone.home_view_pager.ScaleInTransformer;

import java.util.ArrayList;


/**
 * Created by wangm on 2017/6/15.
 * 首页
 */

public class HomeFragment extends BaseListFragment<GoodsModel> {
    FragmentHomeBinding binding;

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, _containerLayout, false);
        _listview = binding.listview;
        return binding.getRoot();
    }

    BannerAdapter bannerAdapter;
    MyAdapter myAdapter;
    HeadHomeListBinding headHomeListBinding;
    AutoPlayViewPager autoPlayViewPage;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headHomeListBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.head_home_list, null, false);
        binding.listview.getRefreshableView().addHeaderView(headHomeListBinding.getRoot());
        headHomeListBinding.phoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) mActivity).switchFragment(1, 0);
            }
        });
        headHomeListBinding.computerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) mActivity).switchFragment(1, 1);
            }
        });
        myAdapter = new MyAdapter();
        setAdapter(myAdapter);


        bannerAdapter = new BannerAdapter(mActivity);

        getAdList();


        autoPlayViewPage = headHomeListBinding.viewPager;



        autoPlayViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) headHomeListBinding.pointGroup.getChildAt(position % mAdList.size())).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 广告Bannder
     **/
    ArrayList<String> mAdList = new ArrayList<>();
    /**
     * 获取Banner广告
     */
    private void getAdList() {
        new MemberModel().homeAdv(new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                ArrayList<HomeAdModel> result = (ArrayList<HomeAdModel>) o;
                if(result != null){
                    if(mAdList.size()>0){
                        mAdList.clear();
                    }
                    if(bannerAdapter != null){
                        bannerAdapter = null;
                        bannerAdapter = new BannerAdapter(mActivity);
                    }
                    if(headHomeListBinding.pointGroup.getChildCount()>0){
                        headHomeListBinding.pointGroup.removeAllViews();
                    }
                    for (int i = 0; i < result.size(); i++) {
                        mAdList.add(result.get(i).picUrl);
                    }
                    setBannerDatas();
                }

            }

            @Override
            public void failed(ResultsModel resultsModel) {

            }
        });
    }

    /**
     * 设置广告数据
     */
    private void setBannerDatas() {
        bannerAdapter.update(mAdList);
        for (int i = 0; i < mAdList.size(); i++) {
            RadioButton point = new RadioButton(mActivity);
            point.setButtonDrawable(R.drawable.point_selector);
            RadioGroup.LayoutParams layoutParams= new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            point.setClickable(false);
            layoutParams.leftMargin= 10;
            point.setLayoutParams(layoutParams);
            headHomeListBinding.pointGroup.addView(point);
        }
        autoPlayViewPage.setPageMargin(50);
        autoPlayViewPage.setOffscreenPageLimit(3);//>=3
        autoPlayViewPage.setAdapter(bannerAdapter);
        autoPlayViewPage.setPageTransformer(true, new ScaleInTransformer());
        autoPlayViewPage.setDirection(AutoPlayViewPager.Direction.LEFT);// 设置播放方向
        autoPlayViewPage.setCurrentItem(2000); // 设置每个Item展示的时间
        autoPlayViewPage.start(); // 开始轮播
    }


//    @Override
//    protected View Listplaceholder() {
//        return binding.placeholder;
//    }

    @Override
    protected void getListData() {
        super.getListData();
        if(_listview.isRefreshing()){
            getAdList();
        }
        new MemberModel().home(new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (!_listview.isRefreshing())
                    showLoading();
            }

            @Override
            public void successful(Object o) {
                ArrayList<GoodsModel> result = (ArrayList<GoodsModel>) o;
                total = result.size();
                loadData(result);
                hideLoading();
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

    class MyAdapter extends IViewDataAdapter<GoodsModel, ItemHomeBinding> {

        @Override
        protected int getItemLayoutId(int position) {
            return R.layout.item_home;
        }

        @Override
        protected void bindData(final ItemHomeBinding itemHomeBinding, final GoodsModel info, int position) {
            GoodsListModel.setDatas(itemHomeBinding,info,mActivity,GoodsListModel.HOME_DATA);
            itemHomeBinding.goAddImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (info.businessType == 1) {
                        GoodsDetailActivity.into(mActivity, info.id,null);
                    } else if (info.businessType == 2) {
                        addShopping(itemHomeBinding,info);
                    }
                }
            });
        }
    }
    private void addShopping(final ItemHomeBinding itemHomeBinding, GoodsModel info){
        String commodityNormDetailId = "";
        if (null != info.commondityNormDetails && info.commondityNormDetails.size() > 0 && null != info.commondityNormDetails.get(0) && !TextUtils.isEmpty(info.commondityNormDetails.get(0).original) &&
                !TextUtils.isEmpty(info.commondityNormDetails.get(0).id)) {
            commodityNormDetailId = info.commondityNormDetails.get(0).id;
            new MemberModel().addShopping(mActivity,commodityNormDetailId, "1", new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {
                    showLoading(false);
                }

                @Override
                public void successful(Object o) {
                    int count = Integer.valueOf((String)o);
                    HomeActivity.count = count;
                    ((HomeActivity) mActivity).startAddAnimation(itemHomeBinding.goAddImg,count);
                    hideLoading();
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg().toString());
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        autoPlayViewPage.stop();
    }
}

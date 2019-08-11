package com.tylx.leasephone;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.databinding.SlidingMainLayoutBinding;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.ui.activity.login.LoginActivity;
import com.tylx.leasephone.ui.activity.login.QuickLoginActivity;
import com.tylx.leasephone.ui.activity.me.AddressActivity;
import com.tylx.leasephone.ui.activity.me.CommonPproblemActivity;
import com.tylx.leasephone.ui.activity.me.MoreSettingActivity;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListActivity;

import java.util.ArrayList;

public class MainActivity extends BaseRecyclerViewListActivity<GoodsModel, ItemHomeBinding> implements View.OnClickListener{
    SlidingMenu menu;
    SlidingMainLayoutBinding binding;

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.sliding_main_layout, _containerLayout, false);
//        _listview = binding.listview;
        return binding.getRoot();
    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.55f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setBehindOffsetRes(R.dimen.dp_100);
        menu.setMenu(R.layout.menu);
        binding.navBarBack.setOnClickListener(new View.OnClickListener() {//菜单键
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        binding.navBarRight.setOnClickListener(new View.OnClickListener() {//购物车
            @Override
            public void onClick(View v) {

            }
        });

        ArrayList<View> adBannerView = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            adBannerView.add(getLayoutInflater().inflate(R.layout.banner_view, null));
        }
        binding.adBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, adBannerView)
                .setPageIndicator(new int[]{R.drawable.dot_unselected, R.drawable.dot_selected})
                .setPointViewVisible(true)
                .startTurning(5000)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
        binding.swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showToast("刷新");
                binding.swiprefresh.setRefreshing(false);
            }
        });

    }


    @Override
    public int getItemLayout(int viewTyoe) {
        return R.layout.item_home;
    }

    @Override
    public void bindItemData(ItemHomeBinding itemHomeBinding, GoodsModel info, int position) {
        itemHomeBinding.beTip.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);
    }

    /**广告Bannder**/
    public class NetworkImageHolderView implements Holder<View> {
        private View mBannerView;
        private TextView mContentTv;
        private TextView mTitleTv;
        private ImageView mImg;

        @Override
        public View createView(Context context) {
            mBannerView = getLayoutInflater().inflate(R.layout.banner_view, null);
            mContentTv = (TextView) mBannerView.findViewById(R.id.m_tv_content);
            mTitleTv = (TextView) mBannerView.findViewById(R.id.m_tv_title);
            mImg = (ImageView) mBannerView.findViewById(R.id.m_img_ad);
            return mBannerView;
        }
        @Override
        public void UpdateUI(Context context, int position, View data) {
            Glide.with(MainActivity.this).load(R.mipmap.banner_default).into(mImg);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.person_more:
                QuickLoginActivity.into(MainActivity.this);
//                menu.toggle();
                break;
            case R.id.menu_lease:
                showToast("租赁");
                break;
            case R.id.menu_market:
                showToast("商城");
                break;
            case R.id.menu_order:
                showToast("我的订单");
                break;
            case R.id.menu_ticket:
                showToast("礼券中心");
                break;
            case R.id.menu_address:
//                showToast("收货地址");
                AddressActivity.into(MainActivity.this);
//                menu.toggle();
                break;
            case R.id.menu_problem:
//                showToast("常见问题");
                CommonPproblemActivity.into(MainActivity.this);
//                menu.toggle();
                break;
            case R.id.menu_authentication:
                showToast("实名认证");
                break;
            case R.id.menu_setting:
//                showToast("更多设置");
                MoreSettingActivity.into(MainActivity.this);
//                menu.toggle();
                break;
            case R.id.menu_custom:
                showToast("客服中心");
                break;
        }
    }
    ArrayList<GoodsModel> goodsModels;
    @Override
    protected void getListData() {
        super.getListData();
//        total = 30;
        total = 1;
        goodsModels = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            goodsModels.add(new GoodsModel());
//        }
        goodsModels.add(new GoodsModel());
        loadData(goodsModels);
    }
}

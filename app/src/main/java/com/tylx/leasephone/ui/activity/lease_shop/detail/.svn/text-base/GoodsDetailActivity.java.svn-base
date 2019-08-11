package com.tylx.leasephone.ui.activity.lease_shop.detail;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityGoodsDetailBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.databinding.ItemShoppingCartBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.BuyModel;
import com.tylx.leasephone.model.CommondityNormDetail;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.NormDetail;
import com.tylx.leasephone.model.NormInfoVo;
import com.tylx.leasephone.model.OrderModel;
import com.tylx.leasephone.model.PrepareBuyModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.ShoppingGoodsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.TabActivity;
import com.tylx.leasephone.ui.activity.lease_shop.ShoppingCartActivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderConfirmActivity;
import com.tylx.leasephone.ui.activity.login.LoginActivity;
import com.tylx.leasephone.ui.activity.login.QuickLoginActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.view.MyFlowLayout;
import com.tylx.leasephone.view.MyParamterDialog;
import com.tylx.leasephone.view.ParamterDialog;
import com.tylx.leasephone.view.TopToolView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/6/22.
 * 商品详情
 */

public class GoodsDetailActivity extends TabActivity implements View.OnClickListener {
    public ActivityGoodsDetailBinding binding;
    private List<Fragment> mFragments;
    int mCurPos = 0;
    public String goodsId;
    private List<CommondityNormDetail> commondityNormDetails;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurPos", mCurPos);
        outState.putInt("mCurTabIndex", mCurTabIndex);
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_goods_detail, _containerLayout, false);
        return binding.getRoot();
    }

    OrderModel.GoodsBean goodsBean;

    @Override
    protected void onPostInject(Bundle savedInstanceState) {
        super.onPostInject(savedInstanceState);
        goodsId = getIntent().getStringExtra("goodsId");
        goodsBean = (OrderModel.GoodsBean) getIntent().getSerializableExtra("GoodsBean");
        mFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            Fragment mImgTextFragment = getSupportFragmentManager().findFragmentByTag(ImgTextFragment.class.getName());
            Fragment mParamterFtagmet = getSupportFragmentManager().findFragmentByTag(ParamterFtagmet.class.getName());
            Fragment mCommentFragment = getSupportFragmentManager().findFragmentByTag(CommentFragment.class.getName());
            mCurPos = savedInstanceState.getInt("mCurPos", -1);
            mCurTabIndex = savedInstanceState.getInt("mCurTabIndex", -1);
            if (mImgTextFragment != null) {
                mFragments.add(mImgTextFragment);
            } else {
                mFragments.add(new ImgTextFragment());
            }
            if (mParamterFtagmet != null) {
                mFragments.add(mParamterFtagmet);
            } else {
                mFragments.add(new ParamterFtagmet());
            }
            if (mCommentFragment != null) {
                mFragments.add(mCommentFragment);
            } else {
                mFragments.add(new CommentFragment());
            }

            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
        } else {
            mFragments.add(new ImgTextFragment());
            mFragments.add(new ParamterFtagmet());
            mFragments.add(new CommentFragment());
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
            switchTab(0);
        }
        initTopView();
        binding.navBarTitle.setText("产品详情");
        getDatas();

    }

    GoodsModel goodsModel;

    private void getDatas() {
        new MemberModel().getCommondityInfoDetail(goodsId, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                goodsModel = (GoodsModel) o;
                hideLoading();
                if (null != goodsModel) {
                    if (goodsBean != null) {
                        curCommondityNormDetail = GoodsModel.getDefaultCommondityNormDetail(goodsModel, goodsBean.commondityNormId);
                    } else {
                        curCommondityNormDetail = GoodsModel.getDefaultCommondityNormDetail(goodsModel);
                    }
                    ImgTextFragment imgTextFragment = (com.tylx.leasephone.ui.activity.lease_shop.detail.ImgTextFragment) getSupportFragmentManager().findFragmentByTag(ImgTextFragment.class.getName());
                    if(null != imgTextFragment){
                        String s = changeImgWidth(goodsModel.introducePic);
                        if(s != null){
                            imgTextFragment.webView.loadDataWithBaseURL(null, s, "text/html; charset=UTF-8", null, null);
                        }
//                        if(goodsModel.introducePic.contains("/web-lease/")){
//                            String replace = goodsModel.introducePic.replace("/web-lease/", "http://api.bookcoins.tk:804/web-lease");
//                            imgTextFragment.webView.loadDataWithBaseURL(null, replace, "text/html; charset=UTF-8", null, null);
//                        }else{
//
//                        }
                    }

                    setStyleTv(curCommondityNormDetail);
                    showDatas();
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.toString());
            }
        });
    }

    /**
     * @param htmlContent 原来的html
     * @return 改变img标签宽度以后的html
     */
    public static String changeImgWidth(String htmlContent){
        if (TextUtils.isEmpty(htmlContent)) {
            return "";
        }
        Document doc_Dis = Jsoup.parse(htmlContent);
        Elements ele_Img = doc_Dis.getElementsByTag("img");
        if (ele_Img.size() != 0){
            for (Element e_Img : ele_Img) {
                e_Img.attr("style", "width:100%");
            }
        }
        String newHtmlContent=doc_Dis.toString();
        return newHtmlContent;
    }
    /**
     * 展示数据
     */
    private void showDatas() {
        binding.setModel(goodsModel);
        /**Banner广告开始**/
        if (!TextUtils.isEmpty(goodsModel.pic1))
            mAdList.add(goodsModel.pic1);
        if (!TextUtils.isEmpty(goodsModel.pic2))
            mAdList.add(goodsModel.pic2);
        if (!TextUtils.isEmpty(goodsModel.pic3))
            mAdList.add(goodsModel.pic3);
        ArrayList<View> adBannerView = new ArrayList<>();
        for (int i = 0; i < mAdList.size(); i++) {
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
        /**Banner广告结束**/


        if (goodsModel.commondityNormDetails.size() > 0)
            binding.setDetali(goodsModel.commondityNormDetails.get(0));
    }

    /**
     * 广告Bannder
     **/
    ArrayList<String> mAdList = new ArrayList<>();
    MyParamterDialog myParamterDialog;
    CommondityNormDetail curCommondityNormDetail;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_add_btn:
//                showToast("加入购物车");
//                startAddAnimation(binding.detailAddBtn);
                if (null != curCommondityNormDetail) {
                    addShopping(curCommondityNormDetail, "1");
                } else {
                    showToast("套餐为空");
                }

                break;
            case R.id.detail_buy_btn:
//                showToast("立即购买");
                if (TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(GoodsDetailActivity.this);
                } else {
                    if (null != goodsModel) {
                        if (null != curCommondityNormDetail) {
                            goBuy(curCommondityNormDetail);
                        } else {
                            showToast("套餐为空");
                        }

                    } else {
                        showToast("商品为空");
                    }
                }
                break;
            case R.id.detail_parameter:
//                showToast("选择参数");
                if (null != goodsModel) {
                    CommondityNormDetail defaultCommondityNormDetail = GoodsModel.getDefaultCommondityNormDetail(goodsModel);
                    myParamterDialog = new MyParamterDialog(this, goodsModel, defaultCommondityNormDetail, MyParamterDialog.GOODS_DETAIL);
                    myParamterDialog.setOnBtnClickListentr(new MyParamterDialog.OnBtnClickListentr() {
                        @Override
                        public void onAddBtnClick(CommondityNormDetail commondityNormDetail) {
                            curCommondityNormDetail = commondityNormDetail;
                            setStyleTv(curCommondityNormDetail);
//                            addShopping(curCommondityNormDetail,"1");
                        }

                        @Override
                        public void onBuyBtnClick(CommondityNormDetail commondityNormDetail) {
                            curCommondityNormDetail = commondityNormDetail;
                            setStyleTv(curCommondityNormDetail);
//                            goBuy(curCommondityNormDetail);
                        }
                    });
                    myParamterDialog.show();
                }
                break;
            case R.id.nav_bar_back:
                finish();
                break;
            case R.id.nav_bar_right:
                if (TextUtils.isEmpty(DatasStore.getCurMember().id))
                    QuickLoginActivity.into(this);
                else
                    ShoppingCartActivity.into(GoodsDetailActivity.this);
//                showToast("购物车");
                break;
        }
    }

    /**
     * 购买
     *
     * @param curCommondityNormDetail
     */
    private void goBuy(CommondityNormDetail curCommondityNormDetail) {
        PrepareBuyModel prepareBuyModel = new PrepareBuyModel(null, goodsModel, curCommondityNormDetail, null);

        OrderConfirmActivity.into(this, prepareBuyModel, PrepareBuyModel.GOODS_DETAIL);

//        if(curCommondityNormDetail == null){
//            return;
//        }
//        String sumAmt = curCommondityNormDetail.price;
//        BuyModel buyModel = new BuyModel();
//        buyModel.shippingId = "";
//        buyModel.commondityNormId = curCommondityNormDetail.id;
//        buyModel.commondityId = goodsId;
//        buyModel.sumAmt = sumAmt.toString();
//        buyModel.memberId = DatasStore.getCurMember().id;
//        buyModel.orderType = goodsModel.businessType;
//        buyModel.payType = 0;
//        buyModel.voucherId = "";
//        buyModel.leaseTerm = 0+"";
//        buyModel.paidAmt = sumAmt;
//        buyModel.visit = MemberModel.VISIT;
//        buyModel.remark = "";
//        buyModel.commondityNum = 1;
//        new MemberModel().buy(buyModel, new BaseModel.BaseModelIB() {
//            @Override
//            public void StartOp() {
//                showLoading(true);
//            }
//
//            @Override
//            public void successful(Object o) {
//                hideLoading();
//            }
//
//            @Override
//            public void failed(ResultsModel resultsModel) {
//                hideLoading(resultsModel.getErrorMsg().toString());
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (HomeActivity.isRefresh) {
            getPersonInfo();
            HomeActivity.isRefresh = false;
        } else {
            makeRedIsGone(HomeActivity.count);
        }
    }

    private void startAddAnimation(View view, int count) {
        int[] location1 = new int[2];
        view.getLocationOnScreen(location1);
        int x1 = location1[0];
        int y1 = location1[1];
        int[] location2 = new int[2];
        binding.navRedCircle.getLocationOnScreen(location2);
        int x2 = location2[0];
        int y2 = location2[1];
        ObjectAnimator moveX = ObjectAnimator.ofFloat(binding.navRedCircle, "translationX", -(x2 - x1), 0);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(binding.navRedCircle, "translationY", -(y2 - y1), 0);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(moveX, moveY);
        animSet.setDuration(300);
        final int finalCount = count;
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                binding.navRedCircle.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (finalCount >= 99) {
                    binding.navRedCircle.setText(99 + "+");
                } else {
                    binding.navRedCircle.setText(finalCount + "");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animSet.start();
    }

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
            if (mAdList.size() > 0) {
                ProbjectUtil.loadImage(mImg, mAdList.get(position), R.mipmap.banner_default);
            }
        }
    }

    public static void into(Activity activity, String id, OrderModel.GoodsBean goodsBean) {
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        intent.putExtra("goodsId", id);
        intent.putExtra("GoodsBean", goodsBean);
        activity.startActivity(intent);
    }


    private void initTopView() {
        binding.toptoolview.setLanwidth(100);
        binding.toptoolview.addTextTab("图文详情");
        binding.toptoolview.addTextTab("产品参数");
        binding.toptoolview.addTextTab("用户评价");
        binding.toptoolview.setCurrentSelect(0);
        binding.toptoolview.setItemSelectListener(new TopToolView.OnselectItemListener() {
            @Override
            public void selectItem(int i) {
                switchTab(i);
                mCurPos = i;
            }
        });
    }

    private void getPersonInfo() {
        new MemberModel().getPersonInfo(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                MemberModel m = (MemberModel) o;
                HomeActivity.count = m.shippingTotal;
                makeRedIsGone(HomeActivity.count);
            }


            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    private void makeRedIsGone(int count) {
        if (count > 0) {
            binding.navRedCircle.setVisibility(View.VISIBLE);
            if (count > 99) {
                binding.navRedCircle.setText(99 + "+");
            } else {
                binding.navRedCircle.setText(count + "");
            }
        } else {
            binding.navRedCircle.setVisibility(View.GONE);
        }
        if (mCurTabIndex == 1) {
            binding.navRedCircle.setVisibility(View.GONE);
        }
    }

    /**
     * 加入购物车
     *
     * @param selectCommondityNormDetail
     */
    private void addShopping(CommondityNormDetail selectCommondityNormDetail, String num) {
        String commodityNormDetailId = "";
        if (null != selectCommondityNormDetail && !TextUtils.isEmpty(selectCommondityNormDetail.id)) {
            commodityNormDetailId = selectCommondityNormDetail.id;
            new MemberModel().addShopping(this, commodityNormDetailId, num, new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {

                }

                @Override
                public void successful(Object o) {
                    int count = Integer.valueOf((String) o);
                    HomeActivity.count = count;
                    startAddAnimation(binding.detailAddBtn, count);
                    hideLoading();
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg().toString());
                }
            });
        }
    }

    private void setStyleTv(CommondityNormDetail curCommondityNormDetail) {
        if (curCommondityNormDetail != null) {
            binding.remrkTv.setText(curCommondityNormDetail.remark);
            binding.priceTv.setText(curCommondityNormDetail.price);
            binding.styleTv.setText(GoodsModel.formateSpecifications(curCommondityNormDetail.commodityNorm));
            binding.tvWorth.setText(curCommondityNormDetail.worth + "元");
        } else {
            binding.remrkTv.setText("xxx");
            binding.priceTv.setText("xxx");
            binding.styleTv.setText("xxx");
            binding.tvWorth.setText("xxx");
        }


    }
}

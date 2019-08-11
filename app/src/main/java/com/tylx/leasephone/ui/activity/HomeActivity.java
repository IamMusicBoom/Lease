package com.tylx.leasephone.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityHomeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.lease_shop.ClassificationActivity;
import com.tylx.leasephone.ui.activity.lease_shop.ShoppingCartActivity;
import com.tylx.leasephone.ui.activity.lease_shop.coupon.CouponListAcitivity;
import com.tylx.leasephone.ui.activity.lease_shop.order.OrderListAcitivity;
import com.tylx.leasephone.ui.activity.login.QuickLoginActivity;
import com.tylx.leasephone.ui.activity.me.AddressActivity;
import com.tylx.leasephone.ui.activity.me.AuthenticationSelectorActivity;
import com.tylx.leasephone.ui.activity.me.CommonPproblemActivity;
import com.tylx.leasephone.ui.activity.me.CustomerServiceActivity;
import com.tylx.leasephone.ui.activity.me.MapActivity;
import com.tylx.leasephone.ui.activity.me.MeActrivity;
import com.tylx.leasephone.ui.activity.me.MoreSettingActivity;
import com.tylx.leasephone.ui.fragment.HomeFragment;
import com.tylx.leasephone.ui.fragment.LeaseFragment;
import com.tylx.leasephone.ui.fragment.ShopFragment;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/6/20.
 * 主界面 搭载 首页，商城，租赁
 */

public class HomeActivity extends TabActivity implements View.OnClickListener {
    private List<Fragment> mFragments;
    int mCurPos = 0;
    ActivityHomeBinding binding;
    TextView mNickName, mPhone, mPhoneTv, mRedCircle;
    RoundImageView mHeadImg;
    public static boolean isRefresh;
    TextView mTitle;
    View mSearch, mRightRoot;
    public static int switchTo = -1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurPos", mCurPos);
        outState.putInt("mCurTabIndex", mCurTabIndex);
    }

    public static void into(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRefresh) {
            getPersonInfo();
            isRefresh = false;
        }else {
            makeRedIsGone(count);
        }
        if(switchTo != -1){
            switchFragment(switchTo);
            switchTo = -1;
        }
    }

    private void makeRedIsGone(int count) {
        if (count > 0) {
            mRedCircle.setVisibility(View.VISIBLE);
            if (count > 99) {
                mRedCircle.setText(99 + "+");
            } else {
                mRedCircle.setText(count + "");
            }
        } else {
            mRedCircle.setVisibility(View.GONE);
        }
        if(mCurTabIndex == 1){
            mRedCircle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPostInject(Bundle savedInstanceState) {
        super.onPostInject(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_home, _containerLayout, false);
        setContentView(R.layout.activity_home);
        mTitle = (TextView) findViewById(R.id.nav_bar_title);
        mSearch = findViewById(R.id.nav_bar_search);
        mRightRoot = findViewById(R.id.nav_right_root);
        initMenu();
        mFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            Fragment firstFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            Fragment secondFragment = getSupportFragmentManager().findFragmentByTag(LeaseFragment.class.getName());
            Fragment thirdFragment = getSupportFragmentManager().findFragmentByTag(ShopFragment.class.getName());
            mCurPos = savedInstanceState.getInt("mCurPos", -1);
            mCurTabIndex = savedInstanceState.getInt("mCurTabIndex", -1);
            if (firstFragment != null) {
                mFragments.add(firstFragment);
            } else {
                mFragments.add(new HomeFragment());
            }
            if (secondFragment != null) {
                mFragments.add(secondFragment);
            } else {
                mFragments.add(new LeaseFragment());
            }
            if (thirdFragment != null) {
                mFragments.add(thirdFragment);
            } else {
                mFragments.add(new ShopFragment());
            }
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
        } else {
            mFragments.add(new HomeFragment());
            mFragments.add(new LeaseFragment());
            mFragments.add(new ShopFragment());
            init(mFragments, R.id.home_frameLayout, getSupportFragmentManager());
            switchTab(0);

        }
    }

    private void setMemberInfo(MemberModel m) {
        mNickName.setText(TextUtils.isEmpty(m.nickName) ? "" : m.nickName);
        mPhone.setText(TextUtils.isEmpty(m.mobilePhone) ? "" : m.mobilePhone);
        mPhoneTv.setVisibility(View.VISIBLE);
        ProbjectUtil.loadImage(mHeadImg, m.headImg, R.mipmap.default_face);
    }

    SlidingMenu menu;

    private void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setFadeDegree(0.55f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setBehindOffsetRes(R.dimen.dp_100);
        menu.setMenu(R.layout.menu);
        mNickName = (TextView) findViewById(R.id.menu_nickname);
        mPhone = (TextView) findViewById(R.id.menu_phone);
        mPhoneTv = (TextView) findViewById(R.id.menu_phone_tv);
        mHeadImg = (RoundImageView) findViewById(R.id.menu_headimg);
        mRedCircle = (TextView) findViewById(R.id.nav_red_circle);
        setMemberInfo(DatasStore.getCurMember());
        count = DatasStore.getCurMember().shippingTotal;
        makeRedIsGone(count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_bar_back:
                menu.toggle();
                break;
            case R.id.nav_bar_search:
//                showToast("分类搜索");
//                menu.toggle();
                ClassificationActivity.into(HomeActivity.this);
                break;
            case R.id.nav_bar_right:
//                showToast("购物车");
                if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id))
                    QuickLoginActivity.into(this);
                else
                    ShoppingCartActivity.into(HomeActivity.this);
//                menu.toggle();
                break;
            case R.id.person_more:
                Log.d("",DatasStore.getCurMember()+"--------------------------DatasStore.getCurMember()");
                Log.d("",DatasStore.getCurMember().id+"--------------------------DatasStore.getCurMember().id");

                if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(HomeActivity.this);
                } else {
                    MeActrivity.into(HomeActivity.this);
                }
                break;
            case R.id.menu_home:
                switchFragment(0);
                break;
            case R.id.menu_lease:
//                showToast("租赁");
                switchFragment(1);
                break;
            case R.id.menu_market:
//                showToast("商城");
                switchFragment(2);
                break;
            case R.id.menu_order:
//                showToast("我的订单");
                if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(HomeActivity.this);
                } else {
                    OrderListAcitivity.into(HomeActivity.this);
                }

                break;
            case R.id.menu_ticket:
//                showToast("礼券中心");
                if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(HomeActivity.this);
                } else {
                    CouponListAcitivity.into(HomeActivity.this,CouponListAcitivity.HOME,null);
                }
                break;
            case R.id.menu_address:
//                showToast("收货地址");
                if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(HomeActivity.this);
                } else {
                    AddressActivity.into(HomeActivity.this);
                }
//                menu.toggle();
                break;
            case R.id.menu_problem:
//                showToast("常见问题");
                CommonPproblemActivity.into(HomeActivity.this);
//                menu.toggle();
                break;
            case R.id.menu_authentication:
//                showToast("实名认证");
                if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(HomeActivity.this);
                } else {
                    AuthenticationSelectorActivity.into(HomeActivity.this);
                }
                break;
            case R.id.menu_setting:
//                showToast("更多设置");
                MoreSettingActivity.into(HomeActivity.this);
//                menu.toggle();
                break;
            case R.id.menu_custom:
//                showToast("客服中心");
                CustomerServiceActivity.into(HomeActivity.this);


//                goCallPhone();

                break;
            case R.id.menu_shop_address:
//                showToast("门店地址");
                MapActivity.into(HomeActivity.this);
                break;
        }
    }

    private void goCallPhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:4008070807"));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 授权
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:4008070807"));
                startActivity(intent);

            } else {
                // 未授权
                showToast("未获取到权限");
            }
        }
    }

    public void switchFragment(int pos) {
        if (pos == mCurPos) {
            menu.toggle();
        } else {
            switchTab(pos);
            if (menu.isMenuShowing()) {
                menu.toggle();
            }
        }

        if (pos == 0) {
            mSearch.setVisibility(View.GONE);
            mRightRoot.setVisibility(View.VISIBLE);
            makeRedIsGone(count);
            mTitle.setText("");
        } else if (pos == 1) {
            mSearch.setVisibility(View.GONE);
            mRightRoot.setVisibility(View.GONE);
            mRedCircle.setVisibility(View.GONE);
            mTitle.setText("租赁");
        } else if (pos == 2) {
            mSearch.setVisibility(View.VISIBLE);
            mRightRoot.setVisibility(View.VISIBLE);
            makeRedIsGone(count);
            mTitle.setText("商城");
        }
        mCurPos = pos;
        mCurTabIndex = pos;
    }

    public static int topPos = 0;

    public void switchFragment(int pos, int curpos) {
        switchTab(pos);
        mSearch.setVisibility(View.GONE);
        mRightRoot.setVisibility(View.GONE);
        mRedCircle.setVisibility(View.GONE);
        mTitle.setText("租赁");
        LeaseFragment leaseFragment = (LeaseFragment) getSupportFragmentManager().findFragmentByTag(LeaseFragment.class.getName());
        if (null != leaseFragment) {
            leaseFragment.binding.toptoolview.setCurrentSelect(curpos);
            if (leaseFragment.mCurTabIndex != curpos) {
                leaseFragment.switchTab(curpos);
                leaseFragment.mCurPos = curpos;
            }


        } else {
            topPos = curpos;
        }
        mCurPos = pos;
    }


    /**
     * 获取用户信息
     */
    public static int count;
    private void getPersonInfo() {
        new MemberModel().getPersonInfo(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
//                showLoading();
            }

            @Override
            public void successful(Object o) {
//                hideLoading();
                MemberModel m = (MemberModel) o;
                count = m.shippingTotal;
                makeRedIsGone(count);
                setMemberInfo(m);
            }


            @Override
            public void failed(ResultsModel resultsModel) {
                showToast(resultsModel.getErrorMsg().toString());
//                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }



    public void startAddAnimation(View view,int count) {
        int[] location1 = new int[2];
        view.getLocationOnScreen(location1);
        int x1 = location1[0];
        int y1 = location1[1];
        int[] location2 = new int[2];
        mRedCircle.getLocationOnScreen(location2);
        int x2 = location2[0];
        int y2 = location2[1];
        ObjectAnimator moveX = ObjectAnimator.ofFloat(mRedCircle, "translationX", -(x2 - x1), 0);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(mRedCircle, "translationY", -(y2 - y1), 0);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(moveX, moveY);
        animSet.setDuration(300);
        final int finalCount = count;
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRedCircle.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (finalCount > 99) {
                    mRedCircle.setText(99 + "+");
                } else {
                    mRedCircle.setText(finalCount + "");
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

    public void startJianAnimation(View view,int count) {
        count--;
        int[] location1 = new int[2];
        view.getLocationOnScreen(location1);
        int x1 = location1[0];
        int y1 = location1[1];
        int[] location2 = new int[2];
        mRedCircle.getLocationOnScreen(location2);
        int x2 = location2[0];
        int y2 = location2[1];
        ObjectAnimator moveX = ObjectAnimator.ofFloat(mRedCircle, "translationX", -(x2 - x1), 0);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(mRedCircle, "translationY", -(y2 - y1), 0);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(moveX, moveY);
        animSet.setDuration(300);
        final int finalCount = count;
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRedCircle.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (finalCount > 99) {
                    mRedCircle.setText(99 + "+");
                } else {
                    mRedCircle.setText(finalCount + "");
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

    private boolean isClose;

    @Override
    public void onBackPressed() {
        if (mCurTabIndex != 0) {
            switchFragment(0);
        } else {
            if (isClose) {
                finish();
            } else {
                showToast("再按一次退出");
                isClose = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            isClose = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
        Fragment f = getSupportFragmentManager().findFragmentByTag(ShopFragment.class.getName());
        /*然后在碎片中调用重写的onActivityResult方法*/
        if(f != null){
            f.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        count = 0;
        super.onDestroy();

    }
}

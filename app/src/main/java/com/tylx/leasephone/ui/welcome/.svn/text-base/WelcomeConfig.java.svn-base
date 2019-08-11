package com.tylx.leasephone.ui.welcome;



import com.tylx.leasephone.R;
import com.tylx.leasephone.ui.fragment.HomeFragment;
import com.tylx.leasephone.ui.fragment.LeaseFragment;
import com.tylx.leasephone.ui.fragment.ShopFragment;
import com.tylx.leasephone.util.activity.BaseFragment;

import java.util.ArrayList;

/**
 * There are some config info
 * Created by Administrator on 2017/1/19.
 * 也就是操作一下引导页的图片
 */

public class WelcomeConfig {
    //welcome image
    static int splashId = R.mipmap.ic_launcher_round;
    static int[] imageIds = {R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    //ni millisecond
    static int SPLASH_WAIT_TIME = 2000;

    static int[] tabNames ;
    static int[] tabIcon ;

    static ArrayList<Class<? extends BaseFragment>> fragments = new ArrayList<>();

    public static void initFragment() {//
        tabNames = new int[]{R.string.first_fragment, R.string.second_fragment,R.string.third_fragment};
        tabIcon = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        fragments.add(HomeFragment.class);
        fragments.add(LeaseFragment.class);
        fragments.add(ShopFragment.class);
    }

    static {
        initFragment();
    }
}

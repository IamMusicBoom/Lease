package com.tylx.leasephone.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;


import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityMainBinding;
import com.tylx.leasephone.ui.welcome.widget.TabHost;
import com.tylx.leasephone.util.UpdateManager;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseFragmentActivity {

    ActivityMainBinding binding;


    @Override
    protected ArrayList<Class<? extends BaseFragment>> fragmentClasses() {
        return WelcomeConfig.fragments;
    }

    @Override
    protected int containerViewId() {
        return R.id.container;
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, _containerLayout, false);
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏颜色，沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

//        StatusBarUtil.setColor(this,getResources().getColor(R.color.primary_color),0);
        UpdateManager.checkUpdate(this);
//        setRefresh(3, true);
        initTabHost();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initTabHost() {
        List<TabHost.TabInfo> tabInfos = new ArrayList<>();
        for (int i = 0; i < WelcomeConfig.tabNames.length; i++) {
            final int finalI = i;
            tabInfos.add(new TabHost.TabInfo(WelcomeConfig.tabNames[i], WelcomeConfig.tabIcon[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPage(finalI);
                }
            }));
        }
        binding.tabHost.setTabs(tabInfos);
    }

    public void selectPage(int index) {
        setFragmentSelection(index);
        binding.tabHost.activeTab(index);
//        if(index == 2){
//            binding.statusBar.setVisibility(View.GONE);
//        }else{
//            binding.statusBar.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void update(Object object) {
        super.update(object);
        System.out.println("---update:" + object);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static void into(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }



}

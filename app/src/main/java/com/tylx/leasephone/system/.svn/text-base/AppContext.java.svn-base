package com.tylx.leasephone.system;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.tencent.bugly.crashreport.CrashReport;
import com.tylx.leasephone.BuildConfig;
import com.tylx.leasephone.R;
import com.tylx.leasephone.util.PreferenceManager;
import com.tylx.leasephone.util.ToastUtil;

import java.io.File;

import cn.jpush.android.api.JPushInterface;


public class AppContext extends MultiDexApplication {

    private static final String TAG = AppContext.class.getSimpleName();
    private static AppContext mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        PreferenceManager.init(mContext);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if (BuildConfig.DEBUG) {
//            ToastUtil.showToast("是Debug");
        }else{
            CrashReport.initCrashReport(getApplicationContext(), "f9101062eb", false);
        }

    }

//    @Override
//    public String getReportUrl() {
//        return null;
//    }
//
//    @Override
//    public Bundle getCrashResources() {
//        return null;
//    }

    //dex65k限制
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AppContext getInstance() {
        return mContext;
    }

    private String basePath() {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().toString();
        } else {
            path = this.getFilesDir().getPath();
        }
        return path + File.separator + getResources().getString(R.string.app_name) + File.separator;
    }

    // 拍照文件临时存储路径
    private String imageCatchDir() {
        return basePath() + "images" + File.separator + "拍照" + File.separator;
    }

    // 图片缓存目录
    private String imageCacheDir() {
        return basePath() + "cache" + File.separator + "image" + File.separator;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}

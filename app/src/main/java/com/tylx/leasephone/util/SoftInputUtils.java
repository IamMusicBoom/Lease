package com.tylx.leasephone.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tylx.leasephone.system.AppContext;

/**
 * Created by wangm on 2017/7/17.
 * 软键盘工具类
 */

public class SoftInputUtils  {
    /**
     * 判断软键盘是否开启
     * @return
     */
    public static boolean isSoftOpen(){
        InputMethodManager imm = (InputMethodManager) AppContext.getInstance().getSystemService(AppContext.getInstance().INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity curActivity){
        ((InputMethodManager)AppContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(curActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); //(WidgetSearchActivity是当前的Activity)
    }
    /**
     * 打开软键盘
     */
    public static void showSoftInput(View view){
        InputMethodManager imm = (InputMethodManager) AppContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}

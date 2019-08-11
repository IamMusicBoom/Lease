package com.tylx.leasephone.util.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.tylx.leasephone.util.AppManager;
import com.tylx.leasephone.util.LoadingHandler;
import com.tylx.leasephone.util.NavigationBar;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.ToastUtil;

import java.util.Locale;

/**
 * 程序基类
 */
public abstract class BaseActivity extends FragmentActivity {

    protected Context mContext;
    protected String TAG = getClass().getSimpleName();

    protected LinearLayout _rootView;
    protected NavigationBar _navBar;
    protected RelativeLayout _containerLayout;
    protected View _contentView;
    protected int _contaninerViewId = 100;
    protected int _navBarViewId = 101;
    protected int page = 0;

    protected View onCreateContentView() {
        return null;
    }

    protected View createView() {
        _rootView = new LinearLayout(this);
        _rootView.setOrientation(LinearLayout.VERTICAL);
        _rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //头部
        _navBar = onCreateNavbar();
        if (_navBar != null) {
            _navBar.setLayoutParams(new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            //noinspection ResourceType
            _navBar.setId(_navBarViewId);
            _rootView.addView(_navBar);
        }
        //内容区
        _containerLayout = new RelativeLayout(this);
        _containerLayout.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        //noinspection ResourceType
        _containerLayout.setId(_contaninerViewId);
        _rootView.addView(_containerLayout);

        //嵌入内容区
        _contentView = onCreateContentView();
        if (_contentView != null) {
            _contentView.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            _containerLayout.addView(_contentView);
        }
        if (Listplaceholder() != null) {
            Listplaceholder().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placeholderOnClick();
                }
            });
        }
        return _rootView;
    }

    protected void onPostInject(Bundle savedInstanceState) {
        onPostInject();

    }
    protected void onPostInject() {

    }

    /**
     * listview的时候 会显示占位符
     *
     * @return
     */
    protected View Listplaceholder() {
        return null;
    }

    protected void placeholderOnClick() {

    }

    protected void showListplaceholder(boolean show) {
        if (Listplaceholder() == null)
            return;
        Listplaceholder().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.createView());
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.primary_color));
        mContext = this;
        showNavigationBar(false);
        _loadingHandler = new LoadingHandler(this);
        onPostInject(savedInstanceState);


        AppManager.getAppManager().addActivity(this);
        AppManager.getAppManager().addMemory(this);


    }

    public void notifyActivity(Class<?> activity, Object model) {
        AppManager.getAppManager().notifyActivity(activity, model);
    }

    public void update(Object object) {

    }

    public void showStatus_bar() {
        _navBar.showStatus();
        _navBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        AppManager.getAppManager().finalize(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    protected void setSoftInputMode() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected void hideSoftInputView() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                manager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    // region Loading dialog

    private LoadingHandler _loadingHandler;

    public void showLoading() {
        _loadingHandler.showLoading();
    }

    public void showLoading(boolean isCancelable) {
        _loadingHandler.showLoading(isCancelable);
    }

    public void showLoading(String message) {
        _loadingHandler.showLoading(message);
    }

    public void updateLoading(String message) {
        _loadingHandler.updateLoading(message);
    }

    public void hideLoading() {
        _loadingHandler.hideLoading();
    }

    public void hideLoading(String msg) {
        _loadingHandler.hideLoading();
        showToast(msg);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    public void showToast(int msg) {
        ToastUtil.showToast(msg);
    }
    // endregion

    protected ViewGroup getContainer() {
        return _containerLayout;
    }

    protected void showNavigationBar(boolean show) {

        if (_navBar == null)
            return;

        if (show) {
            _navBar.setVisibility(View.VISIBLE);
        } else {
            _navBar.setVisibility(View.GONE);
        }
    }

    public boolean w;

    protected NavigationBar onCreateNavbar() {
        return new NavigationBar(this, w);
    }

    protected void goBack() {
        finish();
    }

    protected void goNext() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    protected void setTitle(String title) {
        if (_navBar == null)
            return;
        if (!TextUtils.isEmpty(title)) {
            showNavigationBar(true);
        }
        _navBar.setTitle(title);
    }

    public void setTitle(int title) {
        setTitle(getResourceString(title));
    }

    protected void setRightImage(int resId) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerRight(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        }, resId);
    }

    protected void setRightText(String text) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerRightText(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        }, text);
    }

    protected void setRightText(int text) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerRightText(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        }, getString(text));
    }

    protected void registerBackText(String text) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerBackText(text, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    protected void registerBack() {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerBack(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    protected void registerBack(int resId, View.OnClickListener listener) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerBack(listener, resId);
    }

    protected String getResourceString(int resId) {
        String result = null;
        try {
            result = this.getResources().getString(resId);
        } catch (Exception e) {
        }
        return result;
    }

    protected View inflateContentView(int resId) {
        return getLayoutInflater().inflate(resId, _containerLayout, false);
    }

    public void switchLanguage(Locale locale) {
        Configuration config = getResources().getConfiguration();
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }

    public void startEmptyAnim(View view) {
        ProbjectUtil.startEmptyAnim(view, this);
    }
}

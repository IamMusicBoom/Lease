package com.tylx.leasephone.util.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tylx.leasephone.util.LoadingHandler;
import com.tylx.leasephone.util.NavigationBar;
import com.tylx.leasephone.util.ToastUtil;


public class BaseFragment extends android.support.v4.app.Fragment {

    private boolean injected;
    protected int page = 0;
    protected int STATE;
    protected int REFRESH = 1;
    protected int LOADMORE = 2;

    protected String TAG = getClass().getSimpleName();

    protected LinearLayout _rootView;
    protected NavigationBar _navBar;
    protected FrameLayout _containerLayout;
    protected View _contentView;
    protected LayoutInflater _layoutInflater;
    protected final int _contaninerViewId = 100;
    protected int _navBarViewId = 101;

    protected Activity mActivity;

    protected View onCreateContentView() {
        return null;
    }

    protected void onPostInject() {

    }

    protected View createView() {
        _rootView = new LinearLayout(this.getActivity());
        _rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        _rootView.setOrientation(LinearLayout.VERTICAL);

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
        _containerLayout = new FrameLayout(this.getActivity());
        _containerLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0, 1));
        _containerLayout.setId(_contaninerViewId);
        _rootView.addView(_containerLayout);

        //嵌入内容区
        _contentView = onCreateContentView();
        if (_contentView != null) {
            _containerLayout.addView(_contentView);
        }
        this.injected = true;
        return _rootView;
    }

    public void showStatus_bar() {
        _navBar.showStatus();
        _navBar.setVisibility(View.VISIBLE);
    }
    // region Life cycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _layoutInflater = inflater;
        View view = createView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _loadingHandler = new LoadingHandler(getActivity());

        showNavigationBar(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // endregion

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

    protected void showNavigationBar(boolean show) {
        if (_navBar == null)
            return;

        if (show) {
            _navBar.setVisibility(View.VISIBLE);
        } else {
            _navBar.setVisibility(View.GONE);
        }
    }

    protected ViewGroup getContainer() {
        return _containerLayout;
    }

    protected NavigationBar onCreateNavbar() {
        return new NavigationBar(this.getActivity());
    }

    protected void goBack() {
    }

    protected void goNext() {
    }

    public void setTitle(String title) {
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

    protected void setRegisterBack(int resId) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerBack(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        }, resId);
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

    protected void registerBack(int rec) {
        if (_navBar == null)
            return;
        showNavigationBar(true);
        _navBar.registerBack(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        }, rec);
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

    protected LayoutInflater getLayoutInflater() {
        return _layoutInflater;
    }

    public final boolean isInjected() {
        return this.injected;
    }
}
package com.tylx.leasephone.util;

import android.content.Context;

import com.tylx.leasephone.R;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.view.LoadingDialog;


/**
 * Created by wfpb on 15/6/25.
 *
 * Loading对话框管理
 */
public class LoadingHandler {

    private LoadingDialog _loadingDialog;
    private Context _context;

    public LoadingHandler(Context context) {
        _context = context;
    }

    public LoadingHandler(BaseFragment fragment) {
        _context = fragment.getActivity();
    }

    public void showLoading() {
        showLoading("");
    }

    public void showLoading(boolean isCancelable) {
        showLoading("",isCancelable);
    }

    public void showLoading(String message) {

        if (_loadingDialog != null)
            return;
        _loadingDialog = new LoadingDialog(_context, message);
        _loadingDialog.setMessage(message);
        _loadingDialog.show();
    }




    public void showLoading(String message,boolean isCancelable) {

        if (_loadingDialog != null)
            return;
        _loadingDialog = new LoadingDialog(_context, message,isCancelable);
        _loadingDialog.setMessage(message);
        _loadingDialog.show();
    }

    public void updateLoading(String message) {
        if (_loadingDialog != null)
            _loadingDialog.setMessage(message);
    }

    public void hideLoading() {
        if (_loadingDialog != null&&_loadingDialog.isShowing()) {
            _loadingDialog.cancel();
            _loadingDialog = null;
        }
    }
}

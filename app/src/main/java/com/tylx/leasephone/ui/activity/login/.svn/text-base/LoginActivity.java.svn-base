package com.tylx.leasephone.ui.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityLoginBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.me.MeActrivity;
import com.tylx.leasephone.util.AppManager;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;


/**
 * Created by track on 2017/4/1.
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    ActivityLoginBinding binding;

    public static void comeHereClearAll(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
    }
    public  static void into(Context activity){
        Intent intent = new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_login, _containerLayout, false);
        return binding.getRoot();
    }
    String userName,password;
    @Override
    public void onClick(View view) {
        userName = binding.loginUsername.getText().toString();
        password = binding.loginPassword.getText().toString();
        switch (view.getId()) {
            case R.id.login_login://登录
                if(TextUtils.isEmpty(userName)){
                    startEmptyAnim(binding.loginUsername);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    startEmptyAnim(binding.loginPassword);
                    return;
                }
                goLogin();
                break;
            case R.id.login_forget://忘记密码
                ForgetPasswordActivity.into(this);
                break;
            case R.id.login_register://注册
                RegisteActivity.into(LoginActivity.this);
                break;
        }
    }

    /**
     * 登录
     */
    private void goLogin() {
        new MemberModel().login(userName, ProbjectUtil.SHA1(password), new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                showToast("登录成功");
                AppManager.finishAllActivity();
                HomeActivity.into(LoginActivity.this);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }
}

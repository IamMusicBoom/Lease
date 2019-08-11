package com.tylx.leasephone.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivitySetPasswordBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * Created by wangm on 2017/7/26.
 * 充值密码第二个界面，钟德富说不要这个界面，融入ForgetPasswordActivity界面，所以这个界面没有使用
 */

public class SetPasswordActivity extends BaseActivity implements View.OnClickListener{
    ActivitySetPasswordBinding binding;
    String code,phone;
    public static int REQUEST_CODE = 9;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("设置密码");
        registerBack();
        code = getIntent().getStringExtra("code");
        phone = getIntent().getStringExtra("phone");
    }
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_set_password, _containerLayout, false);
        return binding.getRoot();
    }
    public static void into(Activity mActivity,String code,String phone){
        Intent intent = new Intent(mActivity,SetPasswordActivity.class);
        intent.putExtra("code",code);
        intent.putExtra("phone",phone);
        mActivity.startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_sure:
                goSetPassword();
                break;
        }
    }
    String pw1,pw2;
    private void goSetPassword() {

        new MemberModel().forgetPassword(code, ProbjectUtil.SHA1(pw2), phone, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }
}

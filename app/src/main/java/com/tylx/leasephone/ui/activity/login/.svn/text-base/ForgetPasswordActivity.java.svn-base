package com.tylx.leasephone.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityForgetPasswordBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * Created by wangm on 2017/7/26.
 * 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    ActivityForgetPasswordBinding binding;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("重置密码");
        registerBack();
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_forget_password, _containerLayout, false);
        return binding.getRoot();
    }
    public static void into(Activity mActivity){
        Intent intent = new Intent(mActivity,ForgetPasswordActivity.class);
        mActivity.startActivity(intent);
    }
    String username;
    @Override
    public void onClick(View v) {
        username = binding.forgetUsername.getText().toString().trim();
        switch (v.getId()){
            case R.id.forget_get_code:
                if (TextUtils.isEmpty(username)){
                    startEmptyAnim(binding.forgetUsername);
                    return;
                }
                getCode();
                break;
            case R.id.forget_next:
                goSurePassword();
                break;
        }
    }

    /**
     * 下一步
     */
    String requestCode,curCode;
    String pw1,pw2;
    private void goSurePassword() {
        curCode = binding.forgetCode.getText().toString();
        pw1 = binding.setPw1.getText().toString();
        pw2 = binding.setPw2.getText().toString();
        if (TextUtils.isEmpty(username)){
            startEmptyAnim(binding.forgetUsername);
            return;
        }
        if(TextUtils.isEmpty(pw1)){
            startEmptyAnim(binding.setPw1);
            return;
        }
        if(TextUtils.isEmpty(pw2)){
            startEmptyAnim(binding.setPw2);
            return;
        }
        if(pw1.length()>16 || pw1.length()<6){
            startEmptyAnim(binding.setPw1);
            return;
        }
        if(pw2.length()>16 || pw2.length()<6){
            startEmptyAnim(binding.setPw2);
            return;
        }
        if(!pw2.equals(pw1)){
            startEmptyAnim(binding.setPw1);
            startEmptyAnim(binding.setPw2);
            return;
        }
        if(TextUtils.isEmpty(curCode)){
            startEmptyAnim(binding.forgetCode);
            return;
        }
        if(TextUtils.isEmpty(requestCode)){
            startEmptyAnim(binding.forgetGetCode);
            return;
        }
        if(!requestCode.equals(curCode)){
            startEmptyAnim(binding.forgetCode);
            return;
        }
        goSetPassword();
    }

    private void goSetPassword() {

        new MemberModel().forgetPassword(curCode, ProbjectUtil.SHA1(pw2), username, new BaseModel.BaseModelIB() {
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
    int RemainingTime;// 剩下多少秒
    int totalTime = 120;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (RemainingTime <= 0) {
                binding.forgetGetCode.setEnabled(true);
                binding.forgetGetCode.setText("再次获取");
            } else {
                binding.forgetGetCode.setText(RemainingTime + "s");
                gotimemm();
            }

        }
    };
    /**
     * 获取验证码
     */
    private void getCode() {
        binding.forgetGetCode.setEnabled(false);
        RemainingTime = totalTime;
        binding.forgetGetCode.setText(RemainingTime + "s");
        gotimemm();
        new MemberModel().sendCode("3",username, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                if (null != o) {
                    requestCode = (String) o;
                    showToast(R.string.success);
                } else
                    showToast(R.string.success);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg());
            }
        });

    }
    /**
     * 开始计时
     */
    void gotimemm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                RemainingTime--;
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SetPasswordActivity.REQUEST_CODE && resultCode == RESULT_OK){
            finish();
        }
    }
}

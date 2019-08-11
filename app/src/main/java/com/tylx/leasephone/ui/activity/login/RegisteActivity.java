package com.tylx.leasephone.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.tylx.leasephone.MainActivity;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityRegisteBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.AppManager;
import com.tylx.leasephone.util.AppUtils;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * 注册
 */
public class RegisteActivity extends BaseActivity implements View.OnClickListener {

    ActivityRegisteBinding binding;

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_registe, _containerLayout, false);
        return binding.getRoot();
    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("新用户注册");
        registerBack();
    }
    public  static void into(Activity activity){
        Intent intent = new Intent(activity,RegisteActivity.class);
        activity.startActivity(intent);
    }
    String username,password,code;
    @Override
    public void onClick(View view) {
        username = binding.registeUsername.getText().toString().trim();
        password = binding.registePassword.getText().toString().trim();
        code = binding.registeCode.getText().toString().trim();
        switch (view.getId()) {
            case R.id.registe_get_code://获取验证码
                if (TextUtils.isEmpty(username)){
                    startEmptyAnim(binding.registeUsername);
                    return;
                }
                getCode();
                break;
            case R.id.registe_registe://注册
                if (TextUtils.isEmpty(username)){
                    startEmptyAnim(binding.registeUsername);
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    startEmptyAnim(binding.registePassword);
                    return;
                }
                if (password.length()>16 || password.length() < 6){
                    startEmptyAnim(binding.registePassword);
                    return;
                }
                if (TextUtils.isEmpty(code)){
                    startEmptyAnim(binding.registeCode);
                    return;
                }
                goRegiste();
                break;
        }
    }

    /**
     * 注册
     */
    String referee = "";
    private void goRegiste() {
        referee = binding.registeReferee.getText().toString();
        new MemberModel().register(username, code, ProbjectUtil.SHA1(password), referee, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                showToast("注册成功");
                AppManager.finishAllActivity();
                HomeActivity.into(RegisteActivity.this);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg());
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        binding.registeGetCode.setEnabled(false);
        RemainingTime = totalTime;
        binding.registeGetCode.setText(RemainingTime + "s");
        gotimemm();
        new MemberModel().sendCode("1",username, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                if (null != o) {
                    System.out.println("result:" + o + "");
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
    int RemainingTime;// 剩下多少秒
    int totalTime = 120;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (RemainingTime <= 0) {
                binding.registeGetCode.setEnabled(true);
                binding.registeGetCode.setText("再次获取");
            } else {
                binding.registeGetCode.setText(RemainingTime + "s");
                gotimemm();
            }

        }
    };
}

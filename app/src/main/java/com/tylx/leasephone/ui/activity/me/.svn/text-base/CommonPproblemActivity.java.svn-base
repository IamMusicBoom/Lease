package com.tylx.leasephone.ui.activity.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityCommoncProblemBinding;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.yongchun.library.view.ImageSelectorActivity;

/**
 * Created by wangm on 2017/6/20.
 * 常见问题
 */

public class CommonPproblemActivity extends BaseActivity implements View.OnClickListener{
    ActivityCommoncProblemBinding binding;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("常见问题");
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_commonc_problem, _containerLayout, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.problem_1:
                showToast("问题一");
                break;
            case R.id.problem_2:
                showToast("问题二");
                break;
            case R.id.problem_3:
                showToast("问题三");
                break;
        }
    }
    public  static void into(Activity activity){
        Intent intent = new Intent(activity,CommonPproblemActivity.class);
        activity.startActivity(intent);
    }
}

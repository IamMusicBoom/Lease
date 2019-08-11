package com.tylx.leasephone.ui.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DateSorter;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityAuthenticationSlectorBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.CertificatesModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.DateUtils;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * Created by wangm on 2017/6/27.
 * 实名认证，选择学生还是在职人员
 */

public class AuthenticationSelectorActivity extends BaseActivity implements View.OnClickListener {
    ActivityAuthenticationSlectorBinding binding;
    public static final int REQUEST_CODE = 65;

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_authentication_slector, _containerLayout, false);
        return binding.getRoot();
    }

    @Override
    protected void onPostInject(Bundle savedInstanceState) {
        super.onPostInject(savedInstanceState);
        registerBack();
        setTitle("实名认证");
        if (DatasStore.getCurMember().auditState <= 0) {
            binding.setStudent(studentClickable);
            binding.setWorker(workerClickable);
        } else {
            getDatas();
        }

    }

    CertificatesModel model;

    private void getDatas() {

        new MemberModel().getCertificatesByMemberId(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                model = (CertificatesModel) o;
                HomeActivity.isRefresh = true;
                setDatas();
                hideLoading();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());

            }
        });
    }

    /**
     * 设置数据
     */
    private boolean worker = true, student = true;
    private boolean workerClickable = true, studentClickable = true;
    private void setDatas() {
        if (TextUtils.isEmpty(model.studentCard)) {//如果是在职人员认证
            worker = true;
            student = false;
        } else {//如果是学生认证
            worker = false;
            student = true;
        }
        workerClickable = worker;
        studentClickable = student;
        if (worker) {
            if (model.auditState == 3) {
                binding.workerStatusTv.setText("认证成功");
            } else if (model.auditState == 2) {
                if (TextUtils.isEmpty(model.auditReason)) {
                    binding.workerStatusTv.setText("认证失败");
                } else {
                    binding.workerStatusTv.setText("认证失败" + "("+model.auditReason+")");
                }
                studentClickable = true;

            } else if (model.auditState == 1) {
                binding.workerStatusTv.setText("认证中");
            } else {
                binding.workerStatusTv.setText("");
            }

        }
        if (student) {
            if (model.auditState == 3) {
                binding.stuStatusTv.setText("认证成功");
            } else if (model.auditState == 2) {
                if (TextUtils.isEmpty(model.auditReason)) {
                    binding.stuStatusTv.setText("认证失败");
                } else {
                    binding.stuStatusTv.setText("认证失败" + "("+model.auditReason+")");
                }
                workerClickable = true;
            } else if (model.auditState == 1) {
                binding.stuStatusTv.setText("认证中");
            } else {
                binding.workerStatusTv.setText("");
            }
        }
        binding.setStudent(studentClickable);
        binding.setWorker(workerClickable);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.au_1_stu:
                if(student){
                    AuthenticationDataActivity.into(this, true, model);
                }else{
                    AuthenticationDataActivity.into(this, true, null);
                }
                break;
            case R.id.au_2_worker:
                if(worker){
                    AuthenticationDataActivity.into(this, false, model);
                }else{
                    AuthenticationDataActivity.into(this, false, null);
                }
                break;

        }
    }

    public static void into(Activity activity) {
        Intent intent = new Intent(activity, AuthenticationSelectorActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            HomeActivity.isRefresh = true;
            finish();
        }
    }
}

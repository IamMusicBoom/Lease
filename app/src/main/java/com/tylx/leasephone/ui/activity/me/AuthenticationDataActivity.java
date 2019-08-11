package com.tylx.leasephone.ui.activity.me;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityAuthenticationDataBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.CertificatesModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.util.CompressImageUtil;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.view.EditDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by wangm on 2017/7/28.
 */

public class AuthenticationDataActivity extends BaseActivity implements View.OnClickListener {
    ActivityAuthenticationDataBinding binding;

    boolean isStudent, isEdit;
    CertificatesModel model;
    ArrayList<String> imagePaths = new ArrayList<>();

    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("实名认证");
        registerBack();
        isStudent = getIntent().getBooleanExtra("isStudent", true);
        model = (CertificatesModel) getIntent().getSerializableExtra("CertificatesModel");
        if (null == model) {
            isEdit = true;
        } else {
            if (model.auditState == 2) {
                isEdit = true;
            } else {
                isEdit = false;
            }

        }
        binding.setModel(model);
        binding.setIsEdit(isEdit);
        binding.setIsStudent(isStudent);

    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_authentication_data, _containerLayout, false);
        return binding.getRoot();
    }

    public static void into(Activity mActivty, boolean isStudent, CertificatesModel model) {
        Intent intent = new Intent(mActivty, AuthenticationDataActivity.class);
        intent.putExtra("isStudent", isStudent);
        intent.putExtra("CertificatesModel", model);
        mActivty.startActivityForResult(intent, AuthenticationSelectorActivity.REQUEST_CODE);
    }

    private TimePickerView mIdTimeView, mStuTimeView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.au_real_name://真实姓名
                showEdit(R.id.au_real_name);
                break;
            case R.id.au_id_num://身份证号码
                showEdit(R.id.au_id_num);
                break;
            case R.id.au_student_num://学生证号
                showEdit(R.id.au_student_num);
                break;
            case R.id.au_student_school://毕业院校
                showEdit(R.id.au_student_school);
                break;
            case R.id.au_bank_card://银行卡号
                showEdit(R.id.au_bank_card);
                break;
            case R.id.au_worker_company_name://公司名称
                showEdit(R.id.au_worker_company_name);
                break;
            case R.id.au_worker_company_phone://公司电话
                showEdit(R.id.au_worker_company_phone);
                break;
            case R.id.au_comit://提交审核
                goAu();
                break;
            case R.id.img_id_card:
            case R.id.img_bank_card:
            case R.id.img_stu_card:
                if (isStudent) {
                    goSelectPic(3);
                } else {
                    goSelectPic(2);
                }

                break;
            case R.id.au_id_end_time:
                if (null == mIdTimeView) {
                    mIdTimeView = new TimePickerView.Builder(AuthenticationDataActivity.this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            binding.auIdEndTimeTv.setText(ProbjectUtil.dateToString(date));
                        }
                    })
                            .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                            .setCancelColor(R.color.gray_474747)
                            .setSubmitColor(R.color.gray_474747).build();
                    mIdTimeView.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                }
                mIdTimeView.show();
                break;
            case R.id.au_student_end_time:
                if (null == mStuTimeView) {
                    mStuTimeView = new TimePickerView.Builder(AuthenticationDataActivity.this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            binding.auStudentEndTimeTv.setText(ProbjectUtil.dateToString(date));
                        }
                    })
                            .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                            .setCancelColor(R.color.gray_474747)
                            .setSubmitColor(R.color.gray_474747).build();
                    mStuTimeView.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                }
                mStuTimeView.show();
                break;

        }

    }

    private void goSelectPic(int code) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE}, code);
        } else {
            goSelectImgs(code);
        }
    }

    /**
     * 提交审核
     */
    String realName, idCardNo, idCardEndTime, bankCardNo;
    String school, graduateTime, stuCardNo;
    String companyName, companyPhone, companeAddress;

    private void goAu() {
        realName = binding.auRealNameTv.getText().toString();
        idCardNo = binding.auIdNumTv.getText().toString();
        idCardEndTime = binding.auIdEndTimeTv.getText().toString();
        bankCardNo = binding.auBankCardTv.getText().toString();
        school = binding.auStudentSchoolTv.getText().toString();
        graduateTime = binding.auStudentEndTimeTv.getText().toString();
        stuCardNo = binding.auStudentNumTv.getText().toString();
        companyName = binding.auWoekerCompanyNameTv.getText().toString();
        companyPhone = binding.auWorkerCompanyPhoneTv.getText().toString();
        companeAddress = binding.auWorkerCompanyAddressEt.getText().toString();
        if (TextUtils.isEmpty(realName)) {
            startEmptyAnim(binding.auRealNameTv);
            return;
        }
        if (TextUtils.isEmpty(idCardNo) || idCardNo.length() != 18) {
            startEmptyAnim(binding.auIdNumTv);
            return;
        }
        if (TextUtils.isEmpty(idCardEndTime)) {
            startEmptyAnim(binding.auIdEndTimeTv);
            return;
        }
        if (TextUtils.isEmpty(bankCardNo)) {
            startEmptyAnim(binding.auBankCardTv);
            return;
        }
        if (isStudent) {
            if (TextUtils.isEmpty(school)) {
                startEmptyAnim(binding.auStudentSchoolTv);
                return;
            }
            if (TextUtils.isEmpty(graduateTime)) {
                startEmptyAnim(binding.auStudentEndTimeTv);
                return;
            }
            if (TextUtils.isEmpty(stuCardNo)) {
                startEmptyAnim(binding.auStudentNumTv);
                return;
            }
            if (idCardFile == null || studentFile == null || bankCardFile == null) {
                startEmptyAnim(binding.auFileLayout);
                return;
            }
        } else {
            if (TextUtils.isEmpty(companyName)) {
                startEmptyAnim(binding.auWoekerCompanyNameTv);
                return;
            }
            if (TextUtils.isEmpty(companyPhone)) {
                startEmptyAnim(binding.auWorkerCompanyPhoneTv);
                return;
            }
            if (TextUtils.isEmpty(companeAddress)) {
                startEmptyAnim(binding.auWorkerCompanyAddressEt);
                return;
            }
            if (idCardFile == null || bankCardFile == null) {
                startEmptyAnim(binding.auFileLayout);
                return;
            }
        }
        new MemberModel().uploadCertificatesInfo(DatasStore.getCurMember().id, stuCardNo, idCardNo, realName, graduateTime, idCardEndTime,
                companyName, companeAddress, companyPhone, bankCardNo, school, idCardFile, bankCardFile, studentFile, new BaseModel.BaseModelIB() {
                    @Override
                    public void StartOp() {
                        showLoading(true);
                    }

                    @Override
                    public void successful(Object o) {
                        hideLoading((String) o);
                        setResult(RESULT_OK);
                        finish();

                    }

                    @Override
                    public void failed(ResultsModel resultsModel) {
                        hideLoading(resultsModel.getErrorMsg().toString());
                    }
                });
    }


    public void showEdit(final int type) {
        final EditDialog editDialog = new EditDialog(this);
        EditText et = editDialog.getEditText();
        switch (type) {
            case R.id.au_real_name://真实姓名
                et.setHint("请填写您的真实姓名");
                break;
            case R.id.au_id_num://身份证号码
                et.setHint("请填写您的身份证号码");
                break;
            case R.id.au_student_num://学生证号
                et.setHint("请填写您的学生证号码");
                break;
            case R.id.au_student_school://毕业院校
                et.setHint("请填写您的院校及专业");
                break;
            case R.id.au_bank_card://银行卡号
                et.setHint("请填写借记卡卡号");
                break;
            case R.id.au_worker_company_name://公司名称
                et.setHint("请填写您的公司名称");
                break;
            case R.id.au_worker_company_phone://公司电话
                et.setHint("请填写您的公司电话");
                break;
        }
        editDialog.setOnConfirmClickListener(new EditDialog.OnConfirmClickListener() {
            @Override
            public void onConfirm(DialogInterface dialog, String content) {
                editDialog.dismiss();
                switch (type) {
                    case R.id.au_real_name://真实姓名
                        binding.auRealNameTv.setText(content);
                        break;
                    case R.id.au_id_num://身份证号码
                        binding.auIdNumTv.setText(content);
                        break;
                    case R.id.au_student_num://学生证号
                        binding.auStudentNumTv.setText(content);
                        break;
                    case R.id.au_student_school://毕业院校
                        binding.auStudentSchoolTv.setText(content);
                        break;
                    case R.id.au_bank_card://银行卡号
                        binding.auBankCardTv.setText(content);
                        break;
                    case R.id.au_worker_company_name://公司名称
                        binding.auWoekerCompanyNameTv.setText(content);
                        break;
                    case R.id.au_worker_company_phone://公司电话
                        binding.auWorkerCompanyPhoneTv.setText(content);
                        break;
                }
            }
        });
        editDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (3 == requestCode || 2 == requestCode) {

            if (grantResults.length > 0) {
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 授权
                    if (isStudent) {
                        goSelectImgs(3);
                    } else {
                        goSelectImgs(2);
                    }
                } else {
                    // 未授权
                    showToast("未获取到权限");
                    finish();
                }
            }
        }
    }

    private final int REQUEST_IMAGE = 332;

    private void goSelectImgs(int code) {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
// whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, code);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imagePaths);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    File idCardFile, bankCardFile, studentFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
//            final ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (null != idCardFile) {
                idCardFile = null;
            }
            if (null != bankCardFile) {
                bankCardFile = null;
            }
            if (null != studentFile) {
                studentFile = null;
            }
            imagePaths.clear();
            imagePaths.addAll(paths);
            binding.imgIdCard.setImageResource(R.mipmap.add_camera);
            binding.imgBankCard.setImageResource(R.mipmap.add_jia);
            binding.imgStuCard.setImageResource(R.mipmap.add_jia);
            showImage();
        }
    }

    /**
     * 添加图片
     */
    private void showImage() {
        for (int i = 0; i < imagePaths.size(); i++) {
            final ImageView imageView = (ImageView) ((RelativeLayout) binding.auFileLayout.getChildAt(i)).getChildAt(0);
            final ImageView delete = (ImageView) ((RelativeLayout) binding.auFileLayout.getChildAt(i)).getChildAt(1);
            delete.setTag(imagePaths.get(i));
            Glide.with(AuthenticationDataActivity.this).load(imagePaths.get(i)).into(imageView);
            final int finalI1 = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) delete.getTag();
                    imagePaths.remove(tag);
                    delete.setVisibility(View.GONE);
                    if (finalI1 == 0)
                        idCardFile = null;
                    if (finalI1 == 1)
                        bankCardFile = null;
                    if (finalI1 == 2)
                        studentFile = null;
                    if (finalI1 == 0)
                        Glide.with(AuthenticationDataActivity.this).load(R.mipmap.add_camera).into(imageView);
                    else
                        Glide.with(AuthenticationDataActivity.this).load(R.mipmap.add_jia).into(imageView);
                }
            });
            delete.setVisibility(View.VISIBLE);
            new CompressImageUtil(null).compress(imagePaths.get(i), new CompressImageUtil.CompressListener() {
                @Override
                public void onCompressSuccess(String imgPath) {
                    Glide.with(AuthenticationDataActivity.this).load(imgPath).into(imageView);
                    if (finalI1 == 0 && null == idCardFile)
                        idCardFile = new File(imgPath);
                    if (finalI1 == 1 && null == bankCardFile)
                        bankCardFile = new File(imgPath);
                    if (finalI1 == 2 && null == studentFile)
                        studentFile = new File(imgPath);
                }

                @Override
                public void onCompressFailed(String imgPath, String msg) {
                    if (finalI1 == 0)
                        Glide.with(AuthenticationDataActivity.this).load(R.mipmap.add_camera).into(imageView);
                    else
                        Glide.with(AuthenticationDataActivity.this).load(R.mipmap.add_jia).into(imageView);
                }
            });
        }
    }
}

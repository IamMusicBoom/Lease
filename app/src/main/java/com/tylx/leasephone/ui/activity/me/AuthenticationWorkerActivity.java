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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityAuthenticationBinding;
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
 * Created by wangm on 2017/6/21.
 * 实名认证在职人员
 * AuthenticationDataActivity 代替
 */

public class AuthenticationWorkerActivity extends BaseActivity implements View.OnClickListener {
    ActivityAuthenticationBinding binding;
    private final int NAME = 1;
    private final int ID_NUM = 2;
    private final int STUDENT_NUM = 3;
    private final int REQUEST_IMAGE = 332;
    ArrayList<String> imagePaths = new ArrayList<>();
    CertificatesModel model;
    private boolean isEdit;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("实名认证");
        binding.auStudentEndTime.setVisibility(View.GONE);
        binding.auAddress.setVisibility(View.VISIBLE);
        binding.img3.setVisibility(View.GONE);
        binding.auSample2.setVisibility(View.GONE);
        binding.auTip.setText("TIPS:您需要上传一张手持身份证的正面照，以及一张无遮挡无虚化真实有效的免冠正面照。");
        binding.auStuNumTip.setText("所在公司名称");
        binding.auStudentNumTv.setHint("请填写您所在的公司名称");
        model = (CertificatesModel) getIntent().getSerializableExtra("CertificatesModel");

        if(null == model){
            isEdit = true;
        }else{
            isEdit = false;
        }
        binding.setIsEdit(isEdit);
        binding.setModel(model);
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_authentication, _containerLayout, false);
        return binding.getRoot();
    }

    public static void into(Activity activity, CertificatesModel model) {
        Intent intent = new Intent(activity, AuthenticationWorkerActivity.class);
        intent.putExtra("CertificatesModel",model);
        activity.startActivityForResult(intent,AuthenticationSelectorActivity.REQUEST_CODE);
    }

    private TimePickerView mIdTimeView, mStuTimeView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_1:
            case R.id.img_2:
            case R.id.img_3:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请CAMERA权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                } else {
                    goSelectImgs();
                }
                break;
            case R.id.au_real_name:
                type = NAME;
                showEdit();
                break;
            case R.id.au_id_num:
                type = ID_NUM;
                showEdit();
                break;
            case R.id.au_student_num:
                type = STUDENT_NUM;
                showEdit();
                break;
            case R.id.au_id_end_time://身份证结束时间
                if (null == mIdTimeView) {
                    mIdTimeView = new TimePickerView.Builder(AuthenticationWorkerActivity.this, new TimePickerView.OnTimeSelectListener() {
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
            case R.id.au_student_end_time://学生证证结束时间
                if (null == mStuTimeView) {
                    mStuTimeView = new TimePickerView.Builder(AuthenticationWorkerActivity.this, new TimePickerView.OnTimeSelectListener() {
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
            case R.id.au_comit:
                goAu();
                break;


        }
    }

    /**
     * 实名认证
     */
    String studentCard, idCard, memberName, studentCardValidEnd, idCardValidEnd, corporateName, corporateAddress;
    File idCardFile, personalFile, studentFile;

    private void goAu() {
        corporateName = binding.auStudentNumTv.getText().toString();
        idCard = binding.auIdNumTv.getText().toString();
        memberName = binding.auRealNameTv.getText().toString();
//        studentCardValidEnd = binding.auStudentEndTimeTv.getText().toString();
        idCardValidEnd = binding.auIdEndTimeTv.getText().toString();
        corporateAddress  = binding.auAddressEt.getText().toString();
        if(idCardFile == null || personalFile == null){
            startEmptyAnim(binding.auFileLayout);
            return;
        }
        if (TextUtils.isEmpty(memberName)) {
            startEmptyAnim(binding.auRealNameTv);
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            startEmptyAnim(binding.auIdNumTv);
            return;
        }
        if (idCard.length()!=18) {
            startEmptyAnim(binding.auIdNumTv);
            return;
        }
        if (TextUtils.isEmpty(idCardValidEnd)) {
            startEmptyAnim(binding.auIdEndTimeTv);
            return;
        }
        if (TextUtils.isEmpty(corporateName)) {
            startEmptyAnim(binding.auStudentNumTv);
            return;
        }
        if (TextUtils.isEmpty(corporateAddress)) {
            startEmptyAnim(binding.auAddressEt);
            return;
        }

        new MemberModel().uploadCertificatesInfo(DatasStore.getCurMember().id, "", idCard, memberName, "", idCardValidEnd,
                corporateName, corporateAddress, "","","",idCardFile, personalFile, studentFile, new BaseModel.BaseModelIB() {
                    @Override
                    public void StartOp() {
                        showLoading(true);
                    }

                    @Override
                    public void successful(Object o) {
                        hideLoading((String)o);
                        setResult(RESULT_OK);
                        finish();

                    }

                    @Override
                    public void failed(ResultsModel resultsModel) {
                        hideLoading(resultsModel.getErrorMsg().toString());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (3 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 未授权
                showToast("未获取到权限");
                finish();
            } else {
                // 授权
                goSelectImgs();
            }
        }
    }

    private void goSelectImgs() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
// whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 2);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imagePaths);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
//            final ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if(null != idCardFile){
                idCardFile = null;
            }
            if(null != personalFile){
                idCardFile = null;
            }
            imagePaths.clear();
            imagePaths.addAll(paths);
            binding.img1.setImageResource(R.mipmap.add_camera);
            binding.img2.setImageResource(R.mipmap.add_jia);
//            binding.img3.setImageResource(R.mipmap.add_jia);
            showImage();
        }
    }

    /**
     * 添加图片
     */
    private void showImage() {
        for (int i = 0; i < imagePaths.size(); i++) {
            final ImageView imageView = (ImageView) ((RelativeLayout)binding.auFileLayout.getChildAt(i)).getChildAt(0);
            final ImageView delete = (ImageView) ((RelativeLayout)binding.auFileLayout.getChildAt(i)).getChildAt(1);
            delete.setTag(imagePaths.get(i));
            Glide.with(AuthenticationWorkerActivity.this).load(imagePaths.get(i)).into(imageView);
            final int finalI1 = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) delete.getTag();
                    imagePaths.remove(tag);
                    delete.setVisibility(View.GONE);
                    if(finalI1 == 0)
                        idCardFile = null;
                    if (finalI1 == 1)
                        personalFile = null;
//                    if (finalI1 == 2)
//                        studentFile = null;
                    if(finalI1 ==0)
                        Glide.with(AuthenticationWorkerActivity.this).load(R.mipmap.add_camera).into(imageView);
                    else
                        Glide.with(AuthenticationWorkerActivity.this).load(R.mipmap.add_jia).into(imageView);
                }
            });
            delete.setVisibility(View.VISIBLE);
            new CompressImageUtil(null).compress(imagePaths.get(i), new CompressImageUtil.CompressListener() {
                @Override
                public void onCompressSuccess(String imgPath) {
                    Glide.with(AuthenticationWorkerActivity.this).load(imgPath).into(imageView);
                    if(finalI1 == 0 && null == idCardFile)
                        idCardFile = new File(imgPath);
                    if (finalI1 == 1 && null == personalFile)
                        personalFile = new File(imgPath);
//                    if (finalI1 == 2 && null == studentFile)
//                        studentFile = new File(imgPath);
                }
                @Override
                public void onCompressFailed(String imgPath, String msg) {
                    if (finalI1 == 0)
                        Glide.with(AuthenticationWorkerActivity.this).load(R.mipmap.add_camera).into(imageView);
                    else
                        Glide.with(AuthenticationWorkerActivity.this).load(R.mipmap.add_jia).into(imageView);
                }
            });
        }
    }


    EditDialog editDialog;
    int type;

    public void showEdit() {
        if (editDialog == null) {
            editDialog = new EditDialog(this);
            editDialog.setOnConfirmClickListener(new EditDialog.OnConfirmClickListener() {
                @Override
                public void onConfirm(DialogInterface dialog, String content) {
                    editDialog.dismiss();
                    if (type == NAME) {
                        editDialog.getEditText().setHint("请输入您的真实姓名");
                        binding.auRealNameTv.setText(content);
                    } else if (type == ID_NUM) {
                        binding.auIdNumTv.setText(content);
                    } else if (type == STUDENT_NUM) {
                        binding.auStudentNumTv.setText(content);
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(editDialog.getContent())) {
            editDialog.clear();
        }
        if (type == NAME) {
            editDialog.getEditText().setHint("请输入您的真实姓名");
        } else if (type == ID_NUM) {
            editDialog.getEditText().setHint("请输入您的身份证号码");
        } else if (type == STUDENT_NUM) {
            editDialog.getEditText().setHint("请填写您所在的公司名称");
        }
        editDialog.show();
    }
}

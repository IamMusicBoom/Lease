package com.tylx.leasephone.ui.activity.me;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityMeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.CompressImageUtil;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.view.EditDialog;
import com.tylx.leasephone.view.RoundImageView;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;


/**
 * Created by wangm on 2017/6/20.
 * 个人信息
 */

public class MeActrivity extends BaseActivity implements View.OnClickListener{
    ActivityMeBinding binding;
    private final int REQUEST_IMAGE = 332;

    private final int NAME_INT = 89;
    private final int REFEREE_ING = 90;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("个人资料");
        getPersonInfo();
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_me, _containerLayout, false);
        return binding.getRoot();
    }
    public  static void into(Activity activity){
        Intent intent = new Intent(activity,MeActrivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_nick_name:
                showEdit(NAME_INT);
                break;
            case R.id.me_head:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请CAMERA权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ,Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                } else {
                    Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
// whether show camera
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
// default select images (support array list)
                    startActivityForResult(intent, REQUEST_IMAGE);
                }
                break;
            case R.id.me_phone:

                break;
            case R.id.me_referee:
                showEdit(REFEREE_ING);
                break;
        }
    }
    File headFile;
    String nickname;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_IMAGE&&resultCode==RESULT_OK){
//            final ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorActivity.REQUEST_OUTPUT);


            List<String> images = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (images.size()>0&&images.get(0)!=null) {
//                binding.meUserHeadImg.setImageURI(Uri.parse(images.get(0)));
                Glide.with(MeActrivity.this).load(images.get(0)).into(binding.meUserHeadImg);
                new CompressImageUtil(null).compress(images.get(0), new CompressImageUtil.CompressListener() {
                    @Override
                    public void onCompressSuccess(String imgPath) {
                        headFile = new File(imgPath);
                        modifyMember();
                    }

                    @Override
                    public void onCompressFailed(String imgPath, String msg) {
                        headFile = new File(imgPath);
                        modifyMember();
                    }
                });

//                ArrayList<File> files = new ArrayList<>();
//                files.add(file);
//                new MemberModel().uploadFile(DatasStore.getCurMember().memberId+"", 1, files, new BaseModel.BaseModelIB() {
//                    @Override
//                    public void StartOp() {
//                        showLoading();
//                    }
//
//                    @Override
//                    public void successful(Object o) {
//                        hideLoading();
//                        showToast(R.string.upload_success);
//                    }
//
//                    @Override
//                    public void failed(ResultsModel resultsModel) {
//                        hideLoading(resultsModel.getErrorMsg());
//                    }
//                });
            }
        }
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
                Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
// whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
// default select images (support array list)
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        }
    }



    /**
     * 获取用户信息
     */
    private void getPersonInfo() {
        new MemberModel().getPersonInfo(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                MemberModel m = (MemberModel) o;
                setMemberInfo(m);
            }



            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    private void setMemberInfo(MemberModel m) {
        binding.meNickNameTv.setText(TextUtils.isEmpty(m.nickName)?"":m.nickName);
        binding.mePhoneTv.setText(TextUtils.isEmpty(m.mobilePhone)?"":m.mobilePhone);
        binding.meRefereeTv.setText(TextUtils.isEmpty(m.referenceAccount)?"":m.referenceAccount);
        binding.meReferee.setClickable(TextUtils.isEmpty(m.referenceAccount));
        ProbjectUtil.loadImage(binding.meUserHeadImg,m.headImg,R.mipmap.default_face);
    }
    EditDialog editDialog;
    private String referee;
    public void showEdit(final int id) {
        referee = "";
        nickname = "";
            editDialog = new EditDialog(this);
            editDialog.setOnConfirmClickListener(new EditDialog.OnConfirmClickListener() {
                @Override
                public void onConfirm(DialogInterface dialog, String content) {
                    editDialog.dismiss();
                    if(id == NAME_INT){
                        binding.meNickNameTv.setText(content);
                        nickname = binding.meNickNameTv.getText().toString();
                    }else if(id == REFEREE_ING){
                        binding.meRefereeTv.setText(content);
                        referee = binding.meRefereeTv.getText().toString();
                    }
                    modifyMember();
                }
            });
        if(id == NAME_INT){
           editDialog.getEditText().setHint("请输入您的昵称");
        }else if(id == REFEREE_ING){
            editDialog.getEditText().setHint("请输入您的推荐人");
        }
        if(!TextUtils.isEmpty(editDialog.getContent())){
            editDialog.clear();
        }
        editDialog.show();
    }
    private void modifyMember(){
        new MemberModel().modifyMember(DatasStore.getCurMember().id, headFile, nickname,referee, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading("操作成功");
                HomeActivity.isRefresh = true;
                if(!TextUtils.isEmpty(referee)){
                    binding.meReferee.setClickable(false);
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if(!TextUtils.isEmpty(nickname)){
                    nickname = "";
                }
                if(!TextUtils.isEmpty(referee)){
                    referee = "";
                }
                if(null != headFile){
                    headFile = null;
                }
            }
        });
    }

}

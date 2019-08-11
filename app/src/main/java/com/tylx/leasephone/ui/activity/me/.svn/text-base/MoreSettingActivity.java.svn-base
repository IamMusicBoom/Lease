package com.tylx.leasephone.ui.activity.me;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityMeBinding;
import com.tylx.leasephone.databinding.ActivityMoreSettingBinding;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.ui.activity.login.LoginActivity;
import com.tylx.leasephone.ui.activity.login.QuickLoginActivity;
import com.tylx.leasephone.ui.welcome.SplashActivity;
import com.tylx.leasephone.util.AppManager;
import com.tylx.leasephone.util.AppUtils;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * Created by wangm on 2017/6/20.
 * 更多设置
 */

public class MoreSettingActivity extends BaseActivity implements View.OnClickListener {
    ActivityMoreSettingBinding binding;

    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("更多设置");
        String versionName = AppUtils.getVersionName(getApplicationContext());
        binding.settingVersion.setText("版本" + versionName);
        if (TextUtils.isEmpty(DatasStore.getCurMember().id)) {
            binding.settingExitBtn.setText("登录");
        } else {
            binding.settingExitBtn.setText("退出登录");
        }
        binding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CrashReport.testJavaCrash();


            }
        });
        binding.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap obmp = ((BitmapDrawable) (binding.img).getDrawable()).getBitmap();
                int width = obmp.getWidth();
                int height = obmp.getHeight();
                int[] data = new int[width * height];
                obmp.getPixels(data, 0, width, 0, 0, width, height);
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
                QRCodeReader reader = new QRCodeReader();
                Result re = null;
                try {
                    re = reader.decode(bitmap1);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (ChecksumException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
                if (re == null) {
                    showAlert(obmp);
                } else {
                    showSelectAlert(obmp, re.getText());
                }

                return false;
            }
        });
    }

    private void showAlert(final Bitmap bitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("保存图片").setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterfacem, int i) {
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "悦享租" + System.currentTimeMillis(), "悦享租" + System.currentTimeMillis());

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterfacem, int i) {

            }
        });
        builder.show();
    }

    private void showSelectAlert(final Bitmap bitmap, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        String str[] = {"保存图片", "扫二维码"};
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterfacem, int i) {
                switch (i) {
                    case 0: {
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "悦享租" + System.currentTimeMillis(), "悦享租" + System.currentTimeMillis());
                    }
                    break;
                    case 1: {


                    }
                    break;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterfacem, int i) {
            }
        });
        builder.show();
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_more_setting, _containerLayout, false);
        return binding.getRoot();
    }

    public static void into(Activity activity) {
        Intent intent = new Intent(activity, MoreSettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_exit_btn:
                if (TextUtils.isEmpty(DatasStore.getCurMember().id)) {
                    QuickLoginActivity.into(MoreSettingActivity.this);
                } else {
                    DatasStore.removeCurMember();
                    AppManager.finishAllActivity();
                    Intent intent = new Intent(this, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
        }
    }
}

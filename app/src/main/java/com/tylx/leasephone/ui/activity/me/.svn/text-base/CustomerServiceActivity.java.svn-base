package com.tylx.leasephone.ui.activity.me;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityCustomerServiceBinding;
import com.tylx.leasephone.ui.LoadUrlActivity;
import com.tylx.leasephone.util.ToastUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

/**
 * Created by wangm on 2017/6/21.
 * 我的客服
 */

public class CustomerServiceActivity extends BaseActivity {
    ActivityCustomerServiceBinding binding;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("我的客服");
        setRightImage(R.mipmap.custom);
//        binding.img1.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Bitmap obmp = ((BitmapDrawable) (binding.img1).getDrawable()).getBitmap();
//                int width = obmp.getWidth();
//                int height = obmp.getHeight();
//                int[] data = new int[width * height];
//                obmp.getPixels(data, 0, width, 0, 0, width, height);
//                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
//                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//                QRCodeReader reader = new QRCodeReader();
//                Result re = null;
//                try {
//                    re = reader.decode(bitmap1);
//                } catch (NotFoundException e) {
//                    e.printStackTrace();
//                } catch (ChecksumException e) {
//                    e.printStackTrace();
//                } catch (FormatException e) {
//                    e.printStackTrace();
//                }
//                if (re == null) {
//                    showAlert(obmp);
//                } else {
//                    showSelectAlert(obmp, re.getText());
//                }
//
//                return false;
//            }
//        });
//        binding.img2.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Bitmap obmp = ((BitmapDrawable) (binding.img2).getDrawable()).getBitmap();
//                int width = obmp.getWidth();
//                int height = obmp.getHeight();
//                int[] data = new int[width * height];
//                obmp.getPixels(data, 0, width, 0, 0, width, height);
//                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
//                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//                QRCodeReader reader = new QRCodeReader();
//                Result re = null;
//                try {
//                    re = reader.decode(bitmap1);
//                } catch (NotFoundException e) {
//                    e.printStackTrace();
//                } catch (ChecksumException e) {
//                    e.printStackTrace();
//                } catch (FormatException e) {
//                    e.printStackTrace();
//                }
//                if (re == null) {
//                    showAlert(obmp);
//                } else {
//                    showSelectAlert(obmp, re.getText());
//                }
//
//                return false;
//            }
//        });
    }

    @Override
    protected void goNext() {
        goCallPhone();
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
//                        showToast(url);
                        LoadUrlActivity.into(CustomerServiceActivity.this,url);
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
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_customer_service, _containerLayout, false);
        return binding.getRoot();
    }
    public static void into(Activity activity){
        Intent intent = new Intent(activity,CustomerServiceActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 致电客服
     */
    private void goCallPhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:4008070807"));
            startActivity(intent);
        }
    }

    /**
     * 权限申请回掉
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 授权
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:4008070807"));
                startActivity(intent);

            } else {
                // 未授权
                showToast("未获取到权限");
            }
        }
    }
}

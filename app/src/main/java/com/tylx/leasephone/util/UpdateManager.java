package com.tylx.leasephone.util;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;


/**
 * Created by Administrator on 2016/4/29.
 */
public class UpdateManager {

    private static Activity mContext;

    public static void checkUpdate(Activity activity) {
        mContext = activity;
//        new SystemModel().checkUpdate(new BaseModel.BaseModelIB() {
//            @Override
//            public void StartOp() {
//
//            }
//
//            @Override
//            public void successful(Object o) {
//                systemModel = (SystemModel) o;
//                String newVersionNum = systemModel.version_num;
//                if (needUpdate(newVersionNum)) {
//                    new AlertDialog.Builder(mContext)
//                            .setTitle("检测到新版本，是否更新？")
//                            .setPositiveButton("更新", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    toUpdate(systemModel.update_url);
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .show();
//                }
//            }
//
//            @Override
//            public void failed(ResultsModel resultsModel) {
//
//            }
//        });
    }

    private static boolean needUpdate(String newVersionNum) {
        if (SystemUtil.getAppVersionCode() < Integer.parseInt(newVersionNum)) {
            return true;
        }
        return false;
    }

    private static void toUpdate(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }
}
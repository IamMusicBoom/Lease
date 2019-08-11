package com.tylx.leasephone.ui.activity.lease_shop.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tylx.leasephone.R;
import com.tylx.leasephone.alipay.AuthResult;
import com.tylx.leasephone.alipay.PayDemoActivity;
import com.tylx.leasephone.alipay.PayResult;
import com.tylx.leasephone.alipay.util.OrderInfoUtil2_0;
import com.tylx.leasephone.databinding.ActivityPayBinding;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.util.Map;

/**
 * Created by wangm on 2017/7/25.
 * 支付Activity 没有界面显示，只用作跳转支付
 */

public class PayActivity extends BaseActivity {
    ActivityPayBinding binding;
    public static final String APPID = "2016080500175026";
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    public static String orderInfo = "";
    public static boolean isPaySuccess ;
    @Override
    protected void onPostInject() {
        super.onPostInject();
        payV2();

    }
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pay, _containerLayout, false);
        return binding.getRoot();
    }
    public void payV2() {
        if(TextUtils.isEmpty(orderInfo)){
            showToast("未获取到订单信息");
            finish();
            return;
        }
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        isPaySuccess = true;
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        isPaySuccess = false;
                        finish();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
}

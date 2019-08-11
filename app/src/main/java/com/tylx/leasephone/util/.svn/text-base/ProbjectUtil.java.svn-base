package com.tylx.leasephone.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Point;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tylx.leasephone.R;
import com.tylx.leasephone.zoom.ImagePagerActivity;

import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by track on 2017/1/19.
 */

public class ProbjectUtil {
    public final static int PERMISSION_RESULT_CAMERA = 1;
    /**
     * 默认上传的图片的大小
     */
    public final static int NORMAL_UPLOAD_SIZE = 150;

    /**
     * 将dp转换成px
     *
     * @param context
     * @param dpValue
     */
    public static float dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;

        return (dpValue * density + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @BindingAdapter({"loadImage"})
    public static void loadImage(ImageView view, String url) {
        if (url != null && !url.startsWith("http"))
            url = DatasStore.getFILE_PATH_PREFIX() + url;
        System.out.println("loadImage:" + url);
        if (url == null || url.equals(""))
            Glide.with(view.getContext()).load(R.mipmap.default_face).into(view);
        else
            Glide.with(view.getContext()).load(url).error(R.mipmap.default_face)
                    .centerCrop()
                    .thumbnail(0.5f).dontAnimate()
                    .into(view);
        final String finalUrl = url;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePagerActivity.startActivity(view.getContext(), finalUrl);
            }
        });
    }


    @BindingAdapter({"loadImage","defaultImg"})
    public static void loadImage(ImageView view, String url,int defaultImg) {
        if (url != null && !url.startsWith("http"))
            url = DatasStore.getFILE_PATH_PREFIX() + url;
        System.out.println("loadImage:" + url);
        if (url == null || url.equals(""))
            Glide.with(view.getContext()).load(defaultImg).into(view);
        else
            Glide.with(view.getContext()).load(url).error(defaultImg)
                    .centerCrop()
                    .thumbnail(0.5f)
                    .dontAnimate()
                    .into(view);
        final String finalUrl = url;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePagerActivity.startActivity(view.getContext(), finalUrl);
            }
        });
    }



    @BindingAdapter({"loadImage","defaultImg","isShowBig"})
    public static void loadImage(ImageView view, String url,int defaultImg,boolean isShowBig) {
        if (url != null && !url.startsWith("http"))
            url = DatasStore.getFILE_PATH_PREFIX() + url;
        System.out.println("loadImage:" + url);
        if (url == null || url.equals(""))
            Glide.with(view.getContext()).load(defaultImg).into(view);
        else
            Glide.with(view.getContext()).load(url).error(defaultImg)
                    .centerCrop()
                    .thumbnail(0.5f).dontAnimate()
                    .into(view);
        final String finalUrl = url;
        if(isShowBig){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImagePagerActivity.startActivity(view.getContext(), finalUrl);
                }
            });
        }

    }

    public static void loadImage(ImageView view, int id) {
        Glide.with(view.getContext()).load(id).placeholder(R.mipmap.default_face).error(R.mipmap.default_face).into(view);
    }

    /**
     * 如果没有就隐藏的
     *
     */
//    @BindingAdapter({"loadimagegone"})
//    public static void loadImagegone(ImageView view, String url) {
//        if (!url.startsWith("http"))
//            url = DatasStore.getFILE_PATH_PREFIX() + url;
//        System.out.println("loadimage:" + url);
//        if (url == null || url.equals(""))
//            view.setVisibility(View.GONE);
//        else {
//            view.setVisibility(View.VISIBLE);
//            Glide.with(view.getContext()).load(url).error(R.drawable.tht_ic_pic_normal).placeholder(R.drawable.tht_ic_pic_normal).into(view);
//        }
//    }

//    @BindingAdapter({"loadlocalimage"})
//    public static void loadLocalImage(ImageView view, String url) {
//        System.out.println("loadlocalimage:" + url);
//        if (url == null || url.equals(""))
//            Glide.with(view.getContext()).load(R.drawable.tht_ic_pic_normal).into(view);
//        else
//            Glide.with(view.getContext()).load(new File(url)).error(R.drawable.tht_ic_pic_normal).placeholder(R.drawable.tht_ic_pic_normal).into(view);
//    }

    //yyyy-MM-dd HH:mm:ss
    public static String infotime(String time) {
        return TimeUtils.getTime(Long.parseLong(time));
    }

    //yyyy/MM/dd
    public static String infotime1(String time) {
        if (time.equals("")) return "";
        return TimeUtils.getTime1(Long.parseLong(time) * 1000);
    }

    public static SpannableString getKetword(String content, String keyword) {
        SpannableString s = new SpannableString(content);

        Pattern p = Pattern.compile(keyword);

        Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(0xFF5BD96E), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    public static SpannableString getKetword(String content, int start, int end) {
        SpannableString s = new SpannableString(content);
        s.setSpan(new ForegroundColorSpan(0xFF5BD96E), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    private static String basePath(Context context) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().toString();
        } else {
            path = context.getFilesDir().getPath();
        }
        return path + File.separator + context.getResources().getString(R.string.app_name) + File.separator;
    }

    // 拍照文件临时存储路径
    public static String InitImageCatchDir(Context context) {
        File imagehome = new File(basePath(context) + ".images" + File.separator + "拍照" + File.separator);
        if (!imagehome.exists())
            imagehome.mkdirs();
        return imagehome.getAbsolutePath();
    }

    public static PopupWindow getPopWindow(Activity context, View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.mipmap.uncheck));
        return popupWindow;
    }

    public static PopupWindow getPopWindowWRAP(Activity context, int width, View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                width,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.mipmap.uncheck));
        return popupWindow;
    }

    public static Dialog getDialog(Activity context, int gravity) {
        Dialog dialog = new Dialog(context, R.style.my_bulider_style);
        android.view.WindowManager.LayoutParams lp = dialog.getWindow()
                .getAttributes();
        Point point = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(point);
        lp.width = point.x;
        lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;

        if (gravity == Gravity.BOTTOM) {
            dialog.getWindow().setWindowAnimations(R.style.dialoganimation);
        } else {
            lp.width = point.x - 200;
        }
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }


//    public static String getWindowetIPAddress() {
//        Context context = AppContext.getInstance();
//        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        if (info != null && info.isConnected()) {
//            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
//                try {
//                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
//                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                        NetworkInterface intf = en.nextElement();
//                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                            InetAddress inetAddress = enumIpAddr.nextElement();
//                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//                                return inetAddress.getHostAddress();
//                            }
//                        }
//                    }
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
//                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
//                return ipAddress;
//            }
//        } else {
//            //当前无网络连接,请在设置中打开网络
//        }
//        return null;
//    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static String SHA1(String content) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(content.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * EditText空的动画
     **/
    public static void startEmptyAnim(View v, Context context) {
        Animation shakAnim = AnimationUtils.loadAnimation(context, R.anim.shake);
        v.startAnimation(shakAnim);

    }

    @BindingAdapter({"formattime", "sprefix"})
    public static void formattime(TextView view, String formattime, String sprefix) {
        view.setText(sprefix + longToDateString(formattime) + "");
    }

    @BindingAdapter({"formattime", "sprefix"})
    public static void formattime(TextView view, long formattime, String sprefix) {
        view.setText(sprefix + longToDateString(formattime) + "");
    }

    /**
     * long转换String
     **/
    public static String longToDateString(long millSec) {
        if (millSec <= 0) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(millSec);
            return sdf.format(date);
        }
    }

    /**
     * long转换String
     **/
    public static String longToDateString(String millSec) {
        if (TextUtils.isEmpty(millSec)) {
            return "";
        }
        if (millSec.length() <= 0) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(millSec);
            return sdf.format(date);
        }
    }
    /**
     * long转换String
     **/
//    public static String longToDateString(long dd,int full) {
//        String millSec = String.valueOf(dd);
//        if (TextUtils.isEmpty(millSec)) {
//            return "";
//        }
//        if (millSec.length() <= 0) {
//            return "";
//        } else {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = new Date(millSec);
//            return sdf.format(date);
//        }
//    }
    public static String longToDateString(long s,int f){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * date转换long
     **/
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * date转换String
     **/
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static String dateToString(Date date,int full) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static boolean compareDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(time);
            sdf = new SimpleDateFormat("yyyyMMdd");
            long curtime = Long.parseLong(sdf.format(new Date())); //20170425
            long ptime = Long.parseLong(sdf.format(date)); //20170411
            return ptime >= curtime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean compareDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sss = sdf.parse(startTime);
            Date eee = sdf.parse(endTime);
            sdf = new SimpleDateFormat("yyyyMMdd");
            long start = Long.parseLong(sdf.format(sss)); //20170411
            long end = Long.parseLong(sdf.format(eee)); //20170425
            return start >= end;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @BindingAdapter({"setTextByInt"})
    public static void setTextByInt(TextView tv, int ss) {
        String s = String.valueOf(ss);
        tv.setText(s);
    }

    @BindingAdapter({"setTextByInt"})
    public static void setTextByInt(TextView tv, BigDecimal ss) {
        String s = String.valueOf(ss);
        tv.setText(s);
    }

    public static String getString(String... s) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length; i++) {
            buffer.append(s[i]);
            if (i != s.length - 1) {
                buffer.append("/");
            }
        }
        return buffer.toString();
    }





    public static PopupWindow getPopupWindow(Context context, View view) {
        PopupWindow pop = new PopupWindow(context);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setContentView(view);
        pop.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.uncheck));
        pop.setOutsideTouchable(false);
        pop.setFocusable(true);
        return pop;
    }
    public static String setMidHide(String content){
        StringBuffer sb = new StringBuffer();
        if(TextUtils.isEmpty(content)){
            return "*";
        }
        if(content.length()>6){
            String start = content.substring(0, 3);
            String end = content.substring(content.length() - 3, content.length());
            int rest = content.length()-6;
            sb.append(start);
            for (int i = 0; i < rest; i++) {
                sb.append("*");
            }
            sb.append(end);
            return sb.toString();
        }else {
            return content;
        }

    }
    public static Toast getToast(Context context,View view){
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        return toast;
    }
}

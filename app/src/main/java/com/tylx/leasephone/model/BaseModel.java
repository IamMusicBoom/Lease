package com.tylx.leasephone.model;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.tylx.leasephone.R;
import com.tylx.leasephone.system.AppContext;
import com.tylx.leasephone.util.SystemUtil;
import com.tylx.leasephone.util.ToastUtil;
import com.tylx.leasephone.util.verify.Verify;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 */
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 4298232973186841996L;
    public int pages;
    public int total;
    // http://api.bookcoins.tk:801/web-app-api/     192.168.0.138:8080  api.bookcoins.tk:806
//    public static String HOSTIP = "api.bookcoins.tk:801";
//    public static String API_HOST_PRE = "http://" + HOSTIP + "/web-app-api";// 主机地址
//    public static String ANOTHER_URL_PRE = "http://api.bookcoins.tk:801/web-app-api";

//    public static String HOSTIP = "39.108.111.236/web-lease";
//    public static String HOSTIP = "192.168.0.5:8080/web-lease";
public static String HOSTIP = "192.168.0.138:8090/web-lease";
//public static String HOSTIP = "l.autoops.org/web-lease";
//    public static String HOSTIP = "api.bookcoins.tk:804/web-lease";
    public static String API_HOST_PRE = "http://" + HOSTIP;// 主机地址
    public static String ANOTHER_URL_PRE = "http://api.bookcoins.tk:801/web-app-api";

    private boolean _isMock;
    public BaseModel() {
        // 网络检验
        if (AppContext.getInstance() != null
                && !SystemUtil.isConnected()) {
            ToastUtil.showToast(AppContext.getInstance().getResources().getString(R.string.check_network));
        }
    }

    // 通用接口
    public interface BaseModelIB {
        public abstract void StartOp();// 开始执行

        public abstract void successful(Object o);// 成功返回

        public abstract void failed(ResultsModel resultsModel);// 失败 及标识
    }

    // 通用接口2
    public abstract interface BaseModelIB2 extends BaseModelIB {

        public abstract void onLoading(long total, long current,
                                       boolean isUploading);
    }

    // 对外接口
    public void BIBStart(BaseModelIB bib) {
        bib.StartOp();
    }

    public void BIBSucessful(BaseModelIB bib, Object o) {
        bib.successful(o);
    }

    public void BIBFailed(BaseModelIB bib, ResultsModel resultsModel) {
        bib.failed(resultsModel);
    }

    public void BIBOnLoading(BaseModelIB2 bib, long total, Long current,
                             Boolean isUploading) {
        bib.onLoading(total, current, isUploading);
    }

    // 根据act和op构建url
    public String buildUrl(String actName, String opName) {
//		return API_HOST_PRE + "?act=" + actName + "&op=" + opName;
        return API_HOST_PRE + "/" + actName + "/" + opName;
    }

    public String buildAnotherUrl(String actName, String opName) {
        return ANOTHER_URL_PRE + "/" + actName + "/" + opName;
    }

    // 通过json反序列化为实例
    public BaseModel fromJson(String jsonStr) {
        return (new Gson()).fromJson(jsonStr, this.getClass());
    }

    // 类 序列化为 json字符串
    public String toJson() {
        return (new Gson()).toJson(this);
    }

    // 生成一个网络访问
    public HttpUtils genHttpUtils() {
        HttpUtils utils = new HttpUtils(8000);// 8秒超时
        utils.configCurrentHttpCacheExpiry(0);// 0秒缓存
        return utils;
    }

    public RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.addHeader("accept", "application/json");
        return params;
    }

    public String getVerify(Object c) {
        StringBuffer sb = new StringBuffer();
        ArrayList<VerifyClass> verifyfield = new ArrayList<>();
        Field[] field = c.getClass().getDeclaredFields();
        if (field != null && field.length > 0) {
            for (int i = 0; i < field.length; i++) {
                Verify viewInject = field[i]
                        .getAnnotation(Verify.class);
                if (viewInject != null) {
                    //排序
                    if (viewInject.index() > -1) {
                        //
                        try {
                            VerifyClass verifyClass = new VerifyClass();
                            verifyClass.name = field[i].getName();
                            verifyClass.value = field[i].get(this) + "";
                            verifyClass.index = viewInject.index();
                            verifyfield.add(verifyClass);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }


                    } else {
                    }
                }
            }
            Collections.sort(verifyfield, new indexComparator());//排序
            for (VerifyClass verifyClass : verifyfield) {
                sb.append(verifyClass.value);
            }
        }


        return sb.toString();
    }

    class indexComparator implements Comparator<VerifyClass> {

        public int compare(VerifyClass o1, VerifyClass o2) {
            return (o1.index + "").compareTo(o2.index + "");
        }
    }

    class VerifyClass {
        String name;
        String value;
        int index;
    }
}

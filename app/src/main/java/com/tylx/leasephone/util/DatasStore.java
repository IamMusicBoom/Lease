package com.tylx.leasephone.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.tylx.leasephone.model.AllCodeValueModel;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ProvincesModel;
import com.tylx.leasephone.model.ShopModel;
import com.tylx.leasephone.model.adapter.ThtGosn;
import com.tylx.leasephone.system.AppContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;


/**
 * 数据存取
 *
 * @author
 */
public class DatasStore {
    private static AppContext myApp = AppContext.getInstance();
    public static SharedPreferences infoSP = myApp.getSharedPreferences("tylx_lease_phone", Context.MODE_PRIVATE);

    private static final String KEY_FIRST_LAUNCH = "fistLaunch";
    private static final String KEY_USER_PHONE = "key_user_phone";
    private static final String KEY_USER_PASSWORD = "key_user_password";

    public static void setFirstLaunch(Boolean mm) {
        infoSP.edit().putBoolean(KEY_FIRST_LAUNCH, mm)
                .commit();
    }

    public static Boolean isFirstLaunch() {
        return infoSP.getBoolean(KEY_FIRST_LAUNCH, true);
    }

    private static String KEY_SAVE_PASSWORD = "isSavePassword";


    /**
     * 保存当前会员模型
     * @param mm
     */
    public static void saveCurMember(MemberModel mm) {
        if (mm == null) {
            ToastUtil.showToast("模型消息：setCurMember 模型为 null");
            return;
        }
        putObj2Sp(infoSP, CUR_MEMBER_KEY, mm);
    }

    public static MemberModel getCurMember() {
        MemberModel mmMemberModel = (MemberModel) getObjectFromSp(infoSP, CUR_MEMBER_KEY, MemberModel.class);

        if (mmMemberModel == null) {
            return new MemberModel();
        }

        return mmMemberModel;
    }





    /**
     * 读取登陆页面"记住密码"复选框状态
     *
     * @return
     */
    public static boolean isSavePassword() {
        return infoSP.getBoolean(KEY_SAVE_PASSWORD, false);
    }

    /**
     * 保存登陆页面"记住密码"复选框状态
     *
     * @return
     */
    public static void setSavePassword(boolean isSave) {
        infoSP.edit().putBoolean(KEY_SAVE_PASSWORD, isSave).commit();
    }

    /**
     * 保存用户电话登录使用的电话号码
     *
     * @param phone
     */
    public static void saveUserPhone(String phone) {
        infoSP.edit().putString(KEY_USER_PHONE, phone).commit();
    }

    /**
     * 读取用户电话号码
     */
    public static String getUserPhone() {
        return infoSP.getString(KEY_USER_PHONE, "");
    }

    private static final String CUR_MEMBER_KEY = "curMember";

    // 存档会员信息
    public static void setCurMember(MemberModel mm) {
        if (mm == null) {
            ToastUtil.showToast("模型消息：setCurMember 模型为 null");
            return;
        }
//        String verString = mm.isLegal();
        String verString = "access";
        if (verString.equals("access")) {
            putObj2Sp(infoSP, CUR_MEMBER_KEY, mm);
        } else {
            ToastUtil.showToast("模型消息：setCurMember" + verString);
        }
    }

    static String FILE_PATH_PREFIX;




    static final String CLIENTID = "cid";

    public static void setCid(String cid) {
        infoSP.edit().putString("CLIENTID", cid).commit();
    }

    public static String getCid() {
        return infoSP.getString("CLIENTID", null);
    }




    static final String LOCATION_CITY = "city";

    public static void setLocationCity(String city) {
        if (TextUtils.isEmpty(city)) {
            LogUtils.d("attempt to save null or empty location city ");
            return;
        }
        infoSP.edit().putString(LOCATION_CITY, city).apply();

    }

    public static String getLocationCity() {
        return infoSP.getString(LOCATION_CITY, "");
    }






    static final String SELECT_AREA_FLAG = "selectArea";

    static final String CUR_STATE_FLAG = "curState";


    //当前城市，最近访问的城市
    private static final String CUR_LOCATION_FLAG = "curLocation";
    private static final String RECENTLY_VISITED_FLAG = "recentlyVisited";

    static final String CUR_LATLNG_FLAG = "curLatLng";

    // 存档当前经纬度
    public static void setCurLatLng(LatLng ll) {
        putObj2Sp(infoSP, CUR_LATLNG_FLAG, ll);
    }

    public static class LatLng {
        double x;
        double y;

        public LatLng(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    //
    // gson一个对象并存储 如果为null 就删除原有对象
    public static void putObj2Sp(SharedPreferences sp, String key, Object o) {
        if (o != null) {
            Gson gson = new Gson();
            try {
                String ssString = gson.toJson(o, o.getClass());
                sp.edit().putString(key, ssString).commit();
            } catch (Exception e) {
                Log.e("putObj2Sp json转换出错: ", e.getMessage());
            }
        } else {
            sp.edit().remove(key).commit();
        }
    }

    // 获取一个对象 getObjectFromSp(String key,*.class) 失败返回null
    public static Object getObjectFromSp(SharedPreferences sp, String key,
                                         Type type) {
        Gson gson = new Gson();
        String ssString = sp.getString(key, "");
        return gson.fromJson(ssString, type);

    }

    private static final String VERSION_NUM = "version_num";

    public static String getVersionNum() {
        return infoSP.getString(VERSION_NUM, "1");
    }

    public static void saveVersionNum(String versionNum) {
        infoSP.edit().putString(VERSION_NUM, versionNum).apply();
    }

    public static String getLanguage() {
        String language = infoSP.getString("Language", "lang_EN");
        if (language.equals("zh_CN")) {
            return "lang_CN";
        } else if (language.equals("zh_EN")) {
            return "lang_EN";
        } else {
            return infoSP.getString("Language", "lang_ID");
        }
    }

    /***
     *    1 ID    2 EN     3 CN
     * @return
     */
    public static int iszh() {
        if (getLanguage().contains("CN"))
            return 3;
        else if (getLanguage().contains("ID"))
            return 1;
        else return 2;
    }

    public static String danwei() {
        if (getLanguage().contains("CN"))
            return "米";
        return "M";
    }

    public static void saveLanguage(String l) {
        infoSP.edit().putString("Language", l).apply();
    }


    // 保存的系统参数 文件 服务器 头地址
    public static void saveFilePrefix(String fix) {
        infoSP.edit().putString("fileprefix", fix + "").commit();
    }



//    private static void saveCitys(ArrayList<String> citys) {
//        String json = (ThtGosn.genGson()).toJson(citys);
//        infoSP.edit().putString("citymodels", json).apply();
//    }
//
//    public static ArrayList<String> getCitys() {
//        String json = infoSP.getString("citymodels", null);
//        if (json == null)
//            return null;
//        ArrayList<String> cms;
//        try {
//            cms = (ThtGosn.genGson()).fromJson(json, new TypeToken<ArrayList<String>>() {
//            }.getType());
//        } catch (Exception e) {
//            cms = null;
//        }
//        if (cms == null || cms.size() == 0)
//            return null;
//        return cms;
//    }


    static ACache mACache;//城市的
    /**通过code 获取 name**/
    public static String formatName(String code) {
        if (mACache == null)
            mACache = ACache.get(AppContext.getInstance());
        if (mACache.getAsObject(code) == null) {

            ArrayList<AllCodeValueModel> codeModels = DatasStore.getAllCodes();
            if (null != codeModels) {
                for (int i = 0; i < codeModels.size(); i++) {
                    if (codeModels.get(i).normDetailCode.equals(code)) {
                        mACache.put(code, codeModels.get(i));
                        return codeModels.get(i).normValue;
                    }
                }
            }
        } else {
            AllCodeValueModel codeModels = (AllCodeValueModel) mACache.getAsObject(code);
            if (codeModels != null)
                return codeModels.normValue;
            else {
                mACache.remove(code);
            }
        }
        return "";
    }

    public static void saveAllCodes(ArrayList<AllCodeValueModel> citys) {
        if (citys == null || citys.size() == 0)
            return;
        String json = (ThtGosn.genGson()).toJson(citys);
        infoSP.edit().putString("AllCodes", json).apply();
    }
    public static ArrayList<AllCodeValueModel> getAllCodes() {
        String json = infoSP.getString("AllCodes", null);
        if (json == null)
            return null;
        ArrayList<AllCodeValueModel> cms;
        try {
            cms = (ThtGosn.genGson()).fromJson(json, new TypeToken<ArrayList<AllCodeValueModel>>() {
            }.getType());
        } catch (Exception e) {
            cms = null;
        }
        if (cms == null || cms.size() == 0)
            return null;
        return cms;
    }
//    static ACache mACache;//城市的
//    static ACache typeACache;//车型的
//    static ACache unitACache;//单位
//    static ACache lengthACache;//车长的
//
//    public static void nullCache() {
//        if (mACache != null) {
//            mACache = null;
//        }
//        if (typeACache != null) {
//            typeACache = null;
//        }
//        if (unitACache != null) {
//            unitACache = null;
//        }
//        if (lengthACache != null) {
//            lengthACache = null;
//        }
//    }
//
//    public static String formatCity(String cityValue) {
//        if (mACache == null)
//            mACache = ACache.get(AppContext.getInstance());
//        if (mACache.getAsObject(cityValue) == null) {
//
//            ArrayList<String> cityModels = DatasStore.getAllCitys();
//            if (null != cityModels) {
//                for (int i = 0; i < cityModels.size(); i++) {
//                    if (cityModels.get(i).code.equals(cityValue)) {
//                        mACache.put(cityValue, cityModels.get(i));
//                        return cityModels.get(i).getName();
//                    }
//                }
//            }
//        } else {
//
//            CityModel cityModel = (CityModel) mACache.getAsObject(cityValue);
//            if (cityModel != null)
//                return cityModel.getName();
//            else {
//                mACache.remove(cityValue);
//            }
//        }
////        int cityValue = Integer.valueOf(str);
//
//        return "";
//    }
//
//    public static String formatTruckLength(String cityValue) {
//        if (lengthACache == null)
//            lengthACache = ACache.get(AppContext.getInstance());
//        if (lengthACache.getAsObject(cityValue) == null) {
//
//            ArrayList<CityModel> cityModels = DatasStore.getTruckLengths();
//            if (null != cityModels) {
//                for (int i = 0; i < cityModels.size(); i++) {
//                    if (cityModels.get(i).code.equals(cityValue)) {
//                        lengthACache.put(cityValue, cityModels.get(i));
//                        return cityModels.get(i).getName();
//                    }
//                }
//            }
//        } else {
//            System.out.println("缓存读取");
//            CityModel cityModel = (CityModel) lengthACache.getAsObject(cityValue);
//            if (cityModel != null)
//                return cityModel.getName();
//            else {
//                lengthACache.remove(cityValue);
//            }
//        }
////        int cityValue = Integer.valueOf(str);
//
//        return "";
//    }

//    public static String formatTruckType(String cityValue) {
//        if (typeACache == null)
//            typeACache = ACache.get(AppContext.getInstance());
//        if (typeACache.getAsObject(cityValue) == null) {
//
//            ArrayList<CityModel> cityModels = DatasStore.getTruckTypes();
//            if (null != cityModels) {
//                for (int i = 0; i < cityModels.size(); i++) {
//                    if (cityModels.get(i).code.equals(cityValue)) {
//                        typeACache.put(cityValue, cityModels.get(i));
//                        return cityModels.get(i).getName();
//                    }
//                }
//            }
//        } else {
//            System.out.println("缓存读取");
//            CityModel cityModel = (CityModel) typeACache.getAsObject(cityValue);
//            if (cityModel != null)
//                return cityModel.getName();
//            else {
//                typeACache.remove(cityValue);
//            }
//        }
////        int cityValue = Integer.valueOf(str);
//
//        return "";
//    }
//
//    public static String formatUnitType(String cityValue) {
//        if (unitACache == null)
//            unitACache = ACache.get(AppContext.getInstance());
//        if (unitACache.getAsObject(cityValue) == null) {
//
//            ArrayList<CityModel> cityModels = DatasStore.getGoodsUnit();
//            if (null != cityModels) {
//                for (int i = 0; i < cityModels.size(); i++) {
//                    if (cityModels.get(i).code.equals(cityValue)) {
//                        unitACache.put(cityValue, cityModels.get(i));
//                        return cityModels.get(i).getName();
//                    }
//                }
//            }
//        } else {
//            System.out.println("缓存读取");
//            CityModel cityModel = (CityModel) unitACache.getAsObject(cityValue);
//            if (cityModel != null)
//                return cityModel.getName();
//            else {
//                unitACache.remove(cityValue);
//            }
//        }
////        int cityValue = Integer.valueOf(str);
//
//        return "";
//    }


    public static void saveOrderId(String orderId) {
        infoSP.edit().putString("orderId", orderId).apply();
    }

    public static String getOrderId() {
        String orderId = infoSP.getString("orderId", null);
        return orderId;
    }



    public static void saveOrderStatus(int orderId) {
        infoSP.edit().putInt("OrderStatus", orderId).apply();
    }

    public static int getOrderStatus() {
        int orderId = infoSP.getInt("OrderStatus", -1);
        return orderId;
    }


    public static void saveStartEnd(String startEnd) {
        infoSP.edit().putString("StartEnd", startEnd).apply();
    }

    public static String getStartEnd() {
        String startEnd = infoSP.getString("StartEnd", "");
        return startEnd;
    }



    public static String getFILE_PATH_PREFIX() {
//        return "http://192.168.0.5:8080/lease/";
        return BaseModel.API_HOST_PRE+"/";
    }


    // 清除当前用户
    public static void removeCurMember() {
        infoSP.edit().remove(CUR_MEMBER_KEY).commit();// 移除
    }


    public static void saveMarketNum(int num) {
        infoSP.edit().putInt("MarketNum", num).commit();
    }
    public static int getMarketNum() {
        return infoSP.getInt("MarketNum", 0);
    }



    public static void saveAllCitys(ArrayList<ProvincesModel> citys) {
        if (citys == null || citys.size() == 0)
            return;
        String json = (ThtGosn.genGson()).toJson(citys);
        infoSP.edit().putString("AllCitys", json).apply();
    }
    public static ArrayList<ProvincesModel> getAllCitys() {
        String json = infoSP.getString("AllCitys", null);
        if (json == null)
            return null;
        ArrayList<ProvincesModel> cms;
        try {
            cms = (ThtGosn.genGson()).fromJson(json, new TypeToken<ArrayList<ProvincesModel>>() {
            }.getType());
        } catch (Exception e) {
            cms = null;
        }
        if (cms == null || cms.size() == 0)
            return null;
        return cms;
    }



    public static void saveShops(ArrayList<ShopModel> shops) {
        if (shops == null || shops.size() == 0)
            return;
        String json = (ThtGosn.genGson()).toJson(shops);
        infoSP.edit().putString("AllShops", json).apply();
    }


    public static ArrayList<ShopModel> getShops() {
        String json = infoSP.getString("AllShops", null);
        if (json == null)
            return null;
        ArrayList<ShopModel> cms;
        try {
            cms = (ThtGosn.genGson()).fromJson(json, new TypeToken<ArrayList<ShopModel>>() {
            }.getType());
        } catch (Exception e) {
            cms = null;
        }
        if (cms == null || cms.size() == 0)
            return null;
        return cms;
    }
}
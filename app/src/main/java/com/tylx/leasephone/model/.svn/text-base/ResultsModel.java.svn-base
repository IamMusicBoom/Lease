package com.tylx.leasephone.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Field;

/**
 *
 */

/**
 * @desc 返回的数据结构
 */
public class ResultsModel {

    public ResultsModel() {
        state = 0;
    }

    public ResultsModel(int sState, String errorMsg) {
        this.state = sState;
        this.errorMsg = errorMsg;
    }

    public Integer state;// 状态指示符
    public String jsonDatas;// 数据Json格式主体
    public String stringDatas;// 数据string格式主体
    public String errorMsg;// 错误描述

    public String getStringDatas() {
        return stringDatas;
    }

    public void setStringDatas(String stringDatas) {
        this.stringDatas = stringDatas;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getJsonDatas() {
        return jsonDatas;
    }

    public void setJsonDatas(String jsonDatas) {
        this.jsonDatas = jsonDatas;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @param requestStr 返回值：
     * @desc 获取实例
     */
    public static ResultsModel getInstanseFromStr(String requestStr) {
        System.out.println(requestStr);
        ResultsModel rm = new ResultsModel();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonEl = parser.parse(requestStr);
            JsonObject jsonObj = null;
            jsonObj = jsonEl.getAsJsonObject();// 转换成Json对象
            boolean status = jsonObj.get("status").getAsBoolean();
            String message = jsonObj.get("message").getAsString();
            int arg1 = 0;
            arg1 = status ? 1 : 0;
            if (arg1 == 1) {
                if (!jsonObj.get("extendAttr").isJsonNull()) {
                    if (jsonObj.get("extendAttr").getAsJsonObject().has("result") &&
                            !jsonObj.get("extendAttr").getAsJsonObject().get("result").isJsonNull()) {
                        if (jsonObj.get("extendAttr").getAsJsonObject().get("result").isJsonArray()) {
                            System.out.println("数组：" + jsonObj.get("extendAttr").getAsJsonObject().get("result").toString());
                            rm.setJsonDatas(jsonObj.get("extendAttr").getAsJsonObject().get("result").toString());
                        } else if (jsonObj.get("extendAttr").getAsJsonObject().get("result").isJsonObject()) {
                            System.out.println("result:" + jsonObj.get("extendAttr").getAsJsonObject().get("result").toString());
                            rm.setJsonDatas(jsonObj.get("extendAttr").getAsJsonObject().get("result").toString());
                        } else {//result:""
                            System.out.println("result:" + jsonObj.get("extendAttr").getAsJsonObject().get("result").getAsString());
                            rm.setStringDatas(jsonObj.get("extendAttr").getAsJsonObject().get("result").getAsString());
                        }

                    }
                }
            } else {
                rm.setErrorMsg(message);//转换为   本地的
            }
            rm.setState(arg1);// 设置状态指示


//            if (arg1 == 0) {
//                //如果errormsg 是过时了 就进入登陆
//                if (message.startsWith("TOKEN00")) {
//                    //过时登陆
////                    ToastUtil.showToast("重新登陆");
//                    DatasStore.removeCurMember();
//                    LoginActivity.comeHereClearAll(AppContext.getInstance());
//                }
//            }
        } catch (JsonSyntaxException e) {
            rm.errorMsg = "Json数据结构错误:" + e.getMessage();
            rm.state = 0;
            return rm;
        }

        return rm;
    }

//    static String getMessageLocal(String errorMsg) {
//        return DatasStore.findError(errorMsg);
//    }

//    static String getMessage(String msg) {
//        // int code = Integer.parseInt(message);
//        // int id = R.string.R001;
//        // switch (code) {
//        // case 0:
//        // id = R.string.R001;
//        // default:
//        // id = R.string.R001;
//        // break;
//        // }
//        int sid;
//        if (msg == null) {
//            return "bad data";
//        }
//        try {
//            Class<?> rstring = R.string.class;
//            Field field = rstring.getDeclaredField(msg);
//            sid = field.getInt(msg);
//            return AppContext.getInstance().getString(sid);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "bad data";
//
//        }
//    }

    /**
     * @author Administrator 返回 实体类
     */
    public class ResultsModelO {

        public ResultsModelO() {
            // TODO Auto-generated constructor stub
        }

        public boolean status;// 状态指示符
        public String message;
        public ExtendAttr extendAttr;// 数据主体

        public class ExtendAttr {
            public Object result;
        }

    }

    // string 转 ascii
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    @Override
    public String toString() {
        return "jsonDatas:" + jsonDatas + "  errorMsg:" + errorMsg + "    stringDatas:" + stringDatas;
    }
}

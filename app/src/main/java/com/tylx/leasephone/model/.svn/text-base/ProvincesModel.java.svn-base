package com.tylx.leasephone.model;

import android.app.Activity;
import android.content.Context;

import com.tylx.leasephone.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by wangm on 2017/7/17.
 */

public class ProvincesModel {

    public String province;
    public List<CityBean> city;

    public static class CityBean {
        /**
         * city : 合肥
         * district : [{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]
         */

        public String city;
        public List<DistrictBean> district;
    }
    public static class DistrictBean {
        /**
         * district : 合肥
         */

        public String district;

    }

    public void getAllProvince(final Activity activity, final LoadStatus loadStatus){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStreamReader inputReader = new InputStreamReader( activity.getResources().openRawResource(R.raw.all_province));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line="";
                    String Result="";
                    while((line = bufReader.readLine()) != null)
                        Result += line;
                    final String finalResult = Result;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadStatus.onSuccess(finalResult);
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadStatus.onFail(e.getMessage().toString());
                        }
                    });

                }
            }
        }).start();

    }
    public interface LoadStatus{
        void onSuccess(String r);
        void onFail(String s);
    }
}

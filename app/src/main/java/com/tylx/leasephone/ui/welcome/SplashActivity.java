package com.tylx.leasephone.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;


import com.google.gson.reflect.TypeToken;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivitySplashBinding;
import com.tylx.leasephone.model.AllCodeValueModel;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ProvincesModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.adapter.ThtGosn;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.util.ArrayList;

/**
 * 初始化界面，所需code,跳转欢迎界面，跳转引导页面（没有引导页），
 */
public class SplashActivity extends BaseActivity {


    ActivitySplashBinding binding;

    @Override
    protected void onPostInject() {
        super.onPostInject();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.splashIv.setImageResource(R.mipmap.welcome);//欢迎界面
        getAllGoodCode();
        if(DatasStore.getAllCitys() == null){
            getAllCity();
        }
//        if(DatasStore.isFirstLaunch()){//如果是第一次
//            startActivity(new Intent(SplashActivity.this,WelcomeActivity.class));//引导页
//            finish();
//        }else{
//            startWelcome();
//        }
        if(DatasStore.isFirstLaunch()){//如果是第一次
            startWelcome();
        }else{
            startWelcome();
        }
    }

    private void getAllCity() {
        new ProvincesModel().getAllProvince(this, new ProvincesModel.LoadStatus() {
            @Override
            public void onSuccess(String r) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(r);
                ArrayList<ProvincesModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<ProvincesModel>>() {}.getType());
                DatasStore.saveAllCitys(omList);
            }

            @Override
            public void onFail(String s) {
            }
        });
    }


    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_splash, _containerLayout, false);
        return binding.getRoot();
    }

    public static void into(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    private void startWelcome() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        binding.splashIv.measure(w, h);
        int width =binding.splashIv.getMeasuredWidth();

        TranslateAnimation animation = new TranslateAnimation(widthPixels+width,0,0,0);
        animation.setDuration(1000);
        binding.splashIv.startAnimation(animation);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(WelcomeConfig.SPLASH_WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();

    }

    public void getAllGoodCode() {

        new MemberModel().getNormDetail(new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                ArrayList<AllCodeValueModel> model = (ArrayList<AllCodeValueModel>) o;
                DatasStore.saveAllCodes(model);
            }

            @Override
            public void failed(ResultsModel resultsModel) {

            }
        });
    }
}

package com.tylx.leasephone.ui.activity.me;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityMapBinding;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.ProbjectUtil;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.io.File;

public class MapActivity extends BaseActivity implements GeocodeSearch.OnGeocodeSearchListener{
    ActivityMapBinding binding;
    MapView mMapView = null;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_map, null, false);
        setContentView(binding.getRoot());
        mMapView = binding.map;
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();

        }
        aMap.setOnMarkerClickListener(listener);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。


//
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//            //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。





        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        for (int i = 0; i < names.length; i++) {
            String[] split = names[i].split(",");
            getCoordinate(split[1]);
        }

    }
    String[] names = {"金牛区,蜀汉路98号欧尚超市1F","高新区,泰和一街88号欧尚超市1F","双流区,华府大道二段1号欧尚超市1F",
            "武侯区,武侯大道铁佛段171号欧尚超市1F","双流区,白衣上街177号欧尚超市1F","高新区,盛和一路99号凯德广场3F","武侯区,新光路5号附8号",
            "锦江区,大业路6号财富中心1F","金牛区,一环路北三段100号飞大壹号广场1F（中国移动）","武侯区,晋吉北路龙湖金楠天街251号",
            "成华区,建设北路三段6号龙湖三千集1F中庭","武侯区,长荣路66号泛悦国际1F","双流区,星空路二段万达广场1F 5号门（中国移动）"};

    public static void into(Activity mActivity) {
        Intent intent = new Intent(mActivity,MapActivity.class);
        mActivity.startActivity(intent);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    GeocodeSearch geocodeSearch;
    public void getCoordinate(String name){
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        GeocodeQuery query = new GeocodeQuery(name, "028");

        geocodeSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
        LatLonPoint latLonPoint = geocodeAddress.getLatLonPoint();
        double latitude = latLonPoint.getLatitude();//维度
        double longitude = latLonPoint.getLongitude();//经度
        Log.d("","维度:"+latitude+"经度:"+longitude+"-------------"+i);
        setMarkers(latitude,longitude,geocodeAddress.getFormatAddress()+"，上易时代");
    }

    private void setMarkers(double latitude,double longitude,String address) {
        LatLng latLng = new LatLng(latitude,longitude);
//        MarkerOptions markerOption = new MarkerOptions();
//        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                .decodeResource(getResources(),R.mipmap.location_shop)));
//        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
        final Marker marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.location_shop))).position(latLng).snippet(address));



    }
    AMap.OnMarkerClickListener listener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            showDialog(marker);
            return false;
        }
    };
    Dialog addressDialog;
    TextView addressTv;

    private void showDialog(final Marker marker){
       if(addressDialog == null){
           addressDialog = ProbjectUtil.getDialog(this, Gravity.CENTER);
           View view = getLayoutInflater().inflate(R.layout.dialog_address,null);
           view.findViewById(R.id.tv_go).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   goGoGo(marker);
               }
           });
           view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   addressDialog.dismiss();
               }
           });
           addressTv = (TextView) view.findViewById(R.id.tv_address);
           addressDialog.setContentView(view);
       }
//
//        String[] names = {"金牛区,蜀汉路98号欧尚超市1F","高新区,泰和一街88号欧尚超市1F","双流区,华府大道二段1号欧尚超市1F",
//                "武侯区,武侯大道铁佛段171号欧尚超市1F","双流区,白衣上街177号欧尚超市1F","高新区,盛和一路99号凯德广场3F","武侯区,新光路5号附8号",
//                "锦江区,大业路6号财富中心1F","金牛区,一环路北三段100号飞大壹号广场1F（中国移动）","武侯区,晋吉北路龙湖金楠天街251号",
//                "成华区,建设北路三段6号龙湖三千集1F中庭","武侯区,长荣路66号泛悦国际1F","双流区,星空路二段万达广场1F 5号门（中国移动）"};
        String snippet = marker.getSnippet();
        addressTv.setText(snippet);
//        if(snippet.contains("蜀汉路")){
//            addressTv.setText("蜀汉路98号欧尚超市1F");
//        }else if(snippet.contains("泰和一街")){
//            addressTv.setText("泰和一街88号欧尚超市1F");
//        }else if(snippet.contains("华府大道")){
//            addressTv.setText("华府大道二段1号欧尚超市1F");
//        }else if(snippet.contains("武侯大道")){
//            addressTv.setText("武侯大道铁佛段171号欧尚超市1F");
//        }else if(snippet.contains("白衣上街")){
//            addressTv.setText("白衣上街177号欧尚超市1F");
//        }else if(snippet.contains("盛和一路")){
//            addressTv.setText("盛和一路99号凯德广场3F");
//        }else if(snippet.contains("新光路")){
//            addressTv.setText("新光路5号附8号");
//        }else if(snippet.contains("大业路")){
//            addressTv.setText("大业路6号财富中心1F");
//        }else if(snippet.contains("一环路")){
//            addressTv.setText("一环路北三段100号飞大壹号广场1F（中国移动）");
//        }else if(snippet.contains("晋吉北路")){
//            addressTv.setText("晋吉北路龙湖金楠天街251号");
//        }else if(snippet.contains("建设北路")){
//            addressTv.setText("建设北路三段6号龙湖三千集1F中庭");
//        }else if(snippet.contains("长荣路")){
//            addressTv.setText("长荣路66号泛悦国际1F");
//        }else if(snippet.contains("星空路")){
//            addressTv.setText("星空路二段万达广场1F 5号门（中国移动）");
//        }
        addressDialog.show();
    }

    /**
     * 导航
     */
    private void goGoGo(Marker marker) {

        if(!isInstallByread("com.autonavi.minimap")){
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return;
        }

        try {
            StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                    .append("yitu8_driver").append("&lat=").append(marker.getPosition().latitude)
                    .append("&lon=").append(marker.getPosition().longitude)
                    .append("&dev=").append(1)
                    .append("&style=").append(0);
            ;
//        if (!TextUtils.isEmpty(poiname)) {
//            stringBuffer.append("&poiname=").append(poiname);
//        }
            Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(stringBuffer.toString()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
            addressDialog.dismiss();
        }catch (Exception e){
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            addressDialog.dismiss();
        }
    }
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}

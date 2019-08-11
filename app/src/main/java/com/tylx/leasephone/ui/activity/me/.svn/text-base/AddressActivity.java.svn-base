package com.tylx.leasephone.ui.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.ActivityAddressBinding;
import com.tylx.leasephone.model.AddressModel;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ProvincesModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.model.adapter.ThtGosn;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.SoftInputUtils;
import com.tylx.leasephone.util.activity.BaseActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/6/20.
 * 地址界面
 */

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    ActivityAddressBinding binding;
    boolean isEdit;
    public static int REQUEST_CODE = 225;

    @Override
    protected void onPostInject() {
        super.onPostInject();
        registerBack();
        setTitle("收货地址");
        omList = DatasStore.getAllCitys();
        if (null == omList) {
            getAllCity();
        }
        getMemberAddressByMemberId();
    }

    private void getAllCity() {
        showLoading();
        new ProvincesModel().getAllProvince(this, new ProvincesModel.LoadStatus() {
            @Override
            public void onSuccess(String r) {
                hideLoading();
                ResultsModel rsm = ResultsModel.getInstanseFromStr(r);
                omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<ProvincesModel>>() {
                }.getType());
                DatasStore.saveAllCitys(omList);
            }

            @Override
            public void onFail(String s) {
                hideLoading(s);
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_address, _containerLayout, false);
        return binding.getRoot();
    }

    public static void into(Activity activity) {
        Intent intent = new Intent(activity, AddressActivity.class);
        activity.startActivityForResult(intent,REQUEST_CODE);
    }

    ArrayList<ProvincesModel> omList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_area:

                if (SoftInputUtils.isSoftOpen()) {
                    SoftInputUtils.hideSoftInput(this);
                }
                formateDatas();
                showSelectCityDialog();
                break;
            case R.id.ok:
                if (isEdit) {
                    modifyMemberAddressById();
                } else {
                    uploadMemberAddress();
                }

                break;
        }
    }

    /**
     * 选择城市Dialog
     **/
    OptionsPickerView mOptionsPickView;

    private void showSelectCityDialog() {
        mOptionsPickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                binding.addressAreaTv.setText(allProvinces.get(options1) + " " + allCitys.get(options1).get(options2) + " " + allAreas.get(options1).get(options2).get(options3));
            }
        })
                .setCancelColor(R.color.color_e7e5e5)
                .setSubmitColor(R.color.color_e7e5e5).build();
        mOptionsPickView.setPicker(allProvinces, allCitys, allAreas);
        mOptionsPickView.show();


    }

    /**
     * 添加地址
     */
    private void uploadMemberAddress() {
        Log.d("", "添加地址-----------------------------");
        String nickName = binding.addressNameEt.getText().toString();
        String phone = binding.addressPhoneEt.getText().toString();
        String detailAddress = binding.addressDetailEt.getText().toString();
        String address = binding.addressAreaTv.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            startEmptyAnim(binding.addressNameEt);
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            startEmptyAnim(binding.addressPhoneEt);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            startEmptyAnim(binding.addressAreaTv);
            return;
        }
        if (TextUtils.isEmpty(detailAddress)) {
            startEmptyAnim(binding.addressDetailEt);
            return;
        }
        new MemberModel().uploadMemberAddress(address, detailAddress, DatasStore.getCurMember().id, "Y", nickName, phone, "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });

    }

    ArrayList<String> allProvinces = new ArrayList<>();
    ArrayList<ArrayList<String>> allCitys = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> allAreas = new ArrayList<>();

    private void formateDatas() {
        List<ProvincesModel> allProvincesEntityList = omList;
        for (int i = 0; i < allProvincesEntityList.size(); i++) {
            allProvinces.add(allProvincesEntityList.get(i).province);
            ArrayList<String> citys = new ArrayList<>();
            ArrayList<ArrayList<String>> cityAreas = new ArrayList<>();
            for (int j = 0; j < allProvincesEntityList.get(i).city.size(); j++) {
                citys.add(allProvincesEntityList.get(i).city.get(j).city);
                ArrayList<String> areas = new ArrayList<>();
                if (allProvincesEntityList.get(i).city.get(j).district != null) {
                    for (int k = 0; k < allProvincesEntityList.get(i).city.get(j).district.size(); k++) {
                        areas.add(allProvincesEntityList.get(i).city.get(j).district.get(k).district);
                    }
                } else {
                    areas.add(allProvincesEntityList.get(i).city.get(j).city);
                }
                cityAreas.add(areas);
            }
            allCitys.add(citys);
            allAreas.add(cityAreas);
        }
    }

    AddressModel addressModel;

    /**
     * 查询收货地址
     */
    public void getMemberAddressByMemberId() {
        new MemberModel().getMemberAddressByMemberId(DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                ArrayList<AddressModel> result = (ArrayList<AddressModel>) o;
                if (null == result) {
                    isEdit = false;
                } else if (result.size() <= 0) {
                    isEdit = false;
                } else {
                    addressModel = result.get(0);
                    isEdit = true;
                    binding.setModel(addressModel);
                }
                hideLoading();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    /**
     * 修改地址
     */
    private void modifyMemberAddressById() {
        Log.d("", "修改地址-----------------------------");
        String nickName = binding.addressNameEt.getText().toString();
        String phone = binding.addressPhoneEt.getText().toString();
        String detailAddress = binding.addressDetailEt.getText().toString();
        String address = binding.addressAreaTv.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            startEmptyAnim(binding.addressNameEt);
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            startEmptyAnim(binding.addressPhoneEt);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            startEmptyAnim(binding.addressAreaTv);
            return;
        }
        if (TextUtils.isEmpty(detailAddress)) {
            startEmptyAnim(binding.addressDetailEt);
            return;
        }
        new MemberModel().modifyMemberAddressById(addressModel.id, DatasStore.getCurMember().id, address, detailAddress, "Y", nickName, phone, "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }
}

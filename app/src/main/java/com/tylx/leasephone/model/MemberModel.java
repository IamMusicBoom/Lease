package com.tylx.leasephone.model;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.tylx.leasephone.R;
import com.tylx.leasephone.model.adapter.ThtGosn;
import com.tylx.leasephone.system.AppContext;
import com.tylx.leasephone.ui.activity.login.LoginActivity;
import com.tylx.leasephone.ui.activity.login.QuickLoginActivity;
import com.tylx.leasephone.util.DatasStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @desc
 */
public class MemberModel extends BaseModel {
    public static final int VISIT = 2;


    public String address;
    public String id;
    public String auditDate;
    public String auditUserId;
    public String auditReason;
    public int auditState;
    public String referenceAccount;
    public String openDate;
    public String mobilePhone;
    public String passwword;
    public String nickName;
    public String remark;
    public String reference;
    public String sex;
    public String headImg;

    public String shippingId;
    public int shippingTotal;

    public final String PAGE_SIZE = "10";

    /**
     * 发送验证码  businessType 1 注册  3 修改密码
     *
     * @param mobilePhone
     * @param bib
     */
    public void sendCode(String businessType, String mobilePhone, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("verifyInfo", "sendCode");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("businessType", businessType);
        params.addBodyParameter("mobilePhone", mobilePhone);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 注册
     *
     * @param phone          自己的电话
     * @param code           验证码
     * @param password       密码
     * @param referencePhone 推荐人电话
     * @param bib
     */
    public void register(String phone, String code, String password, String referencePhone, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "register");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("code", code);
        params.addBodyParameter("password", password);
        params.addBodyParameter("referencePhone", referencePhone);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    MemberModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), MemberModel.class);
                    DatasStore.saveCurMember(omList);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @param bib
     */
    public void login(String phone, String password, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "login");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    MemberModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), MemberModel.class);
                    DatasStore.saveCurMember(omList);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }


    /**
     * 忘记密码
     * @param code
     * @param password
     * @param mobilePhone
     * @param bib
     */
    public void forgetPassword(String code, String password, String mobilePhone, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "forgetPassword");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("code", code);
        params.addBodyParameter("password", password);
        params.addBodyParameter("mobilePhone", mobilePhone);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param memberId
     * @param bib
     */
    public void getPersonInfo(String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "getPersonInfo");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("memberId", memberId);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    MemberModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), MemberModel.class);
                    DatasStore.saveCurMember(omList);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 修改用户参数
     *
     * @param memberId
     * @param file
     * @param nickname
     * @param bib
     */
    public void modifyMember(String memberId, File file, String nickname,String referencePhone, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "modifyMember");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("memberId", memberId);
        if (null != file)
            params.addBodyParameter("file", file);//可选
        if (!TextUtils.isEmpty(nickname))
            params.addBodyParameter("nickname", nickname);//可选
        if (!TextUtils.isEmpty(referencePhone))//可选
            params.addBodyParameter("referencePhone", referencePhone);//可选
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * @param memberId
     * @param studentCard
     * @param idCard
     * @param memberName
     * @param studentCardValidEnd 学生证到期时间
     * @param idCardValidEnd      身份证到期时间
     * @param corporateName       公司名字
     * @param corporateAddress    公司地址
     * @param idCardFile
     * @param studentFile
     * @param personalFile
     * @param bib
     */
    public void uploadCertificatesInfo(String memberId, String studentCard, String idCard, String memberName,
                                       String studentCardValidEnd, String idCardValidEnd, String corporateName,
                                       String corporateAddress, String corporatePhone,String bankCard,String studentMajor,File idCardFile, File personalFile, File studentFile,
                                       final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "uploadCertificatesInfo");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        if (!TextUtils.isEmpty(memberId))
            params.addBodyParameter("memberId", memberId);
        params.addBodyParameter("studentCard", studentCard);
        params.addBodyParameter("idCard", idCard);
        params.addBodyParameter("memberName", memberName);
        params.addBodyParameter("studentCardValidEnd", studentCardValidEnd);
        params.addBodyParameter("idCardValidEnd", idCardValidEnd);
        params.addBodyParameter("corporateName", corporateName);

        params.addBodyParameter("corporatePhone", corporatePhone);
        params.addBodyParameter("bankCard", bankCard);
        params.addBodyParameter("studentMajor", studentMajor);

        params.addBodyParameter("corporateAddress", corporateAddress);
        if (null != idCardFile)
            params.addBodyParameter("idCardFile", idCardFile);
        if (null != personalFile)
            params.addBodyParameter("personalFile", personalFile);
        if (null != studentFile)
            params.addBodyParameter("studentFile", studentFile);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 获取主页数据
     *
     * @param bib
     */
    public void home(final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "home");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<GoodsModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<GoodsModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取商品详情
     *
     * @param id  商品Id
     * @param bib
     */
    public void getCommondityInfoDetail(String id, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getCommondityInfoDetail");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("id", id);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    GoodsModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), GoodsModel.class);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取所有规格
     *
     * @param bib
     */
    public void getNormDetail(final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getNormDetail");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("id", id);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<AllCodeValueModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<AllCodeValueModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取评论
     *
     * @param id  商品id
     * @param bib
     */
    public void getCommondityComments(String id, int page, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getCommondityComments");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("pageNum", page + "");

        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                CommentListModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), CommentListModel.class);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取租赁列表
     *
     * @param typeCode 1 手机   2 电脑
     * @param page
     * @param bib
     */
    public void leaseList(String typeCode, int page, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "leaseList");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("typeCode", typeCode);
        params.addQueryStringParameter("pageNum", page + "");

        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    GoodsListModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), GoodsListModel.class);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 加入购物车
     *
     * @param commodityNormDetailId
     * @param commodityNum
     * @param bib
     */
    public void addShopping(Activity mActivity, String commodityNormDetailId, String commodityNum, final BaseModel.BaseModelIB bib) {
        if (null == DatasStore.getCurMember() || TextUtils.isEmpty(DatasStore.getCurMember().id)) {
            QuickLoginActivity.into(mActivity);
            return;
        }
        String url = buildUrl("goods", "addShopping");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("memberId", DatasStore.getCurMember().id);
        params.addBodyParameter("commodityNormDetailId", commodityNormDetailId);
        params.addBodyParameter("commodityNum", commodityNum);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 查询购物车列表
     *
     * @param memberId
     * @param bib
     */

    public void getShoppingList(String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getShoppingList");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("memberId", memberId);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<ShoppingGoodsModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<ShoppingGoodsModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 移除购物车
     *
     * @param memberId
     * @param id           商品套餐id
     * @param commodityNum 数量   -1 删除整个 否则删除commodityNum数量 s
     * @param bib
     */
    public void removeShopping(String memberId, String id, String commodityNum, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "removeShopping");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("memberId", memberId);
        params.addBodyParameter("id", id);
        params.addBodyParameter("commodityNum", commodityNum);

        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 查询套餐
     *
     * @param group        "逗号分开，传属性 CORLOR_02,ME_01"
     * @param commondityId 商品id
     * @param bib
     */
    public void getCommondityNormDetail(String group, String commondityId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getCommondityNormDetail");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("group", group);
        params.addQueryStringParameter("commondityId", commondityId);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<CommondityNormDetail> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<CommondityNormDetail>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 商城列表
     *
     * @param typeCode 当选择了才有 没选择的时候不穿 或者 传空字符串
     * @param pageNum
     * @param bib
     */
    public void shoppingList(String typeCode, int pageNum, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "shoppingList");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("typeCode", typeCode);
        params.addQueryStringParameter("pageNum", pageNum + "");
        params.addQueryStringParameter("pageSize", PAGE_SIZE);

        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    GoodsListModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), GoodsListModel.class);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 清空购物车
     *
     * @param memberId
     * @param bib
     */
    public void clearShopping(String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "clearShopping");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("memberId", memberId);

        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 购物车修改套餐
     *
     * @param memberId
     * @param id                    这个购物车id
     * @param num                   数量
     * @param commodityNormDetailId 修改后的套餐
     * @param bib
     */
    public void modifyShopping(String memberId, String id, String num, String commodityNormDetailId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "modifyShopping");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("memberId", memberId);
        params.addBodyParameter("id", id);
        params.addBodyParameter("commodityNum", num);
        params.addBodyParameter("commodityNormDetailId", commodityNormDetailId);

        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取类别
     *
     * @param typeCode 查第一级 不传 第二级才传
     * @param bib
     */
    public void commondityTypes(String typeCode, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "commondityTypes");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("typeCode", typeCode);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<ShoppingGoodsModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<ShoppingGoodsModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 为app写死的二级分类
     *
     * @param bib
     */
    public void secondCommondityTypes(final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "secondCommondityTypes");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<ShopGoodsModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<ShopGoodsModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 获取用户的卡券
     *
     * @param memberId
     * @param status   status = 1 未核销  status = 2 已核销  status = 3 已过期  status = 4 已作废  status = 5 已退款
     * @param bib
     */
    public void getVoucherCashInfos(String memberId, int status, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getVoucherCashInfos");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("memberId", memberId);
        params.addQueryStringParameter("status", status + "");
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<VoucherCashModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<VoucherCashModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }


    /**
     * 查询卡券可抵用金额
     * @param cardDetail (购物车详情ID)--购物车购买时传递（多个ID用逗号隔开）（购物车购买）
     * @param memberId 会员ID
     * @param normDeatilId 商品规格ID--（商品购买）--购物车购买时不传或传-1
     * @param commondityNum 商品数量--（商品购买）--购物车购买时不传或传-1
     * @param bib
     */
    public void getVoucherModel(String cardDetail, String memberId,String normDeatilId, String commondityNum, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("orderQuery", "getVoucherModel");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("cardDetail", cardDetail);
        params.addQueryStringParameter("memberId", memberId);
        params.addQueryStringParameter("normDeatilId", normDeatilId);
        params.addQueryStringParameter("commondityNum", commondityNum);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<VoucherCashModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<VoucherCashModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }








    /**
     * 添加收货地址
     *
     * @param address
     * @param memberId
     * @param isDefault
     * @param nickName
     * @param phone
     * @param remark
     * @param bib
     */
    public void uploadMemberAddress(String address, String addressDetail, String memberId, String isDefault, String nickName, String phone, String remark, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "uploadMemberAddress");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("address", address);
        params.addBodyParameter("addressDetail", addressDetail);
        params.addBodyParameter("memberId", memberId);
        params.addBodyParameter("isDefault", isDefault);
        params.addBodyParameter("nickName", nickName);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("remark", remark);

        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取收货地址
     *
     * @param memberId
     * @param bib
     */
    public void getMemberAddressByMemberId(String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "getMemberAddressByMemberId");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("memberId", memberId);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<AddressModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<AddressModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 修改收货地址
     *
     * @param id
     * @param address
     * @param isDefault
     * @param nickName
     * @param phone
     * @param remark
     * @param bib
     */
    public void modifyMemberAddressById(String id, String memberId, String address, String addressDetail, String isDefault, String nickName, String phone, String remark, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "modifyMemberAddressById");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter("memberId", memberId);
        params.addBodyParameter("address", address);
        params.addBodyParameter("addressDetail", addressDetail);
        params.addBodyParameter("isDefault", isDefault);
        params.addBodyParameter("nickName", nickName);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("remark", remark);

        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 购买与租赁
     *
     * @param shippingId       购物车ID
     * @param commondityNormId 商品规格详情ID
     * @param commondityId     商品ID，多个ID用逗号隔开
     * @param sumAmt           订单总金额
     * @param memberId         会员ID
     * @param orderType        订单类型 1租赁，2购买
     * @param payType          支付方式    1：支付宝、2：微信(暂无)
     * @param voucherId        卡券ID
     * @param leaseTerm        租赁月数()
     * @param paidAmt          支付金额
     * @param remark           备注
     * @param commondityNum    商品数量
     * @param visit            订单来源 2,安卓，3.IOS
     * @param bib
     */
    public void buy(String shippingId, String commondityNormId, String commondityId, String sumAmt, String memberId
            , String orderType, String payType, String voucherId, String leaseTerm, String paidAmt, String remark, String commondityNum
            , String visit, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "buy");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("shippingId", shippingId);
        params.addBodyParameter("commondityNormId", commondityNormId);
        params.addBodyParameter("commondityId", commondityId);
        params.addBodyParameter("sumAmt", sumAmt);
        params.addBodyParameter("memberId", memberId);
        params.addBodyParameter("orderType", orderType);
        params.addBodyParameter("payType", payType);
        params.addBodyParameter("voucherId", voucherId);
        params.addBodyParameter("leaseTerm", leaseTerm);
        params.addBodyParameter("paidAmt", paidAmt);
        params.addBodyParameter("remark", remark);
        params.addBodyParameter("commondityNum", commondityNum);
        params.addBodyParameter("visit", visit);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 购买与租赁
     *
     * @param buyModel
     * @param bib
     */
    public void buy(BuyModel buyModel, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "buy");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("shippingId", buyModel.shippingId);
        params.addBodyParameter("commondityNormId", buyModel.commondityNormId);
        params.addBodyParameter("commondityId", buyModel.commondityId);
        params.addBodyParameter("sumAmt", buyModel.sumAmt);
        params.addBodyParameter("memberId", buyModel.memberId);
        params.addBodyParameter("orderType", buyModel.orderType + "");
        params.addBodyParameter("payType", buyModel.payType + "");
        params.addBodyParameter("voucherId", buyModel.voucherId);
        params.addBodyParameter("leaseTerm", buyModel.leaseTerm);
        params.addBodyParameter("paidAmt", buyModel.paidAmt);
        params.addBodyParameter("remark", buyModel.remark);
        params.addBodyParameter("voucherNum", buyModel.voucherNum);
        params.addBodyParameter("commondityNum", buyModel.commondityNum + "");
        params.addBodyParameter("visit", buyModel.visit + "");
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }


    /**
     * 获取芝麻信用
     * @param mobile
     * @param memberId
     * @param bib
     */
    public void authZhima(String mobile, String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "authZhima");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("memberId", memberId);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }






    /**
     * 获取用户的已使用的
     *
     * @param memberId
     * @param bib
     */
    public void getVoucherCashInfoHistory(String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "getVoucherCashInfoHistory");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("memberId", memberId);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    ArrayList<VoucherCashModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<VoucherCashModel>>() {
                    }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });
    }

    /**
     * 获取订单列表
     * @param pageNo 当前页
     * @param tradeStatus 交易状态//0：预约成功（等待支付） 2：已付款（等待发货）  3：运送中（确认签收）   4：申请退款（同意或拒绝）5，交易撤销 ,6.租赁中  9:  交易完成
     * @param orderType  1：租赁、2：购买
     * @param bib
     */
    public void getOrderTradePage(int pageNo,int tradeStatus,int orderType, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("orderQuery", "getOrderTradePage");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("pageNo", pageNo+"");
        params.addQueryStringParameter("pageSize", PAGE_SIZE + "");
        params.addQueryStringParameter("memberId", DatasStore.getCurMember().id);
        params.addQueryStringParameter("tradeStatus", tradeStatus+"");
        params.addQueryStringParameter("orderType", orderType+"");


        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    OrderListModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), OrderListModel.class);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 获取认证信息
     * @param memberId
     * @param bib
     */
    public void getCertificatesByMemberId(String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "getCertificatesByMemberId");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("memberId", memberId);
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    CertificatesModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), CertificatesModel.class);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 确认收货
     * @param orderId
     * @param memberId
     * @param bib
     */
    public void confirmReceipt(String orderId,String memberId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "confirmReceipt");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("memberId",memberId);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     *  申请退款
     * @param orderId
     * @param memberId
     * @param remark
     * @param bib
     */
    public void cancelOrder(String orderId,String memberId,String remark, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "cancelOrder");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("memberId",memberId);
        params.addQueryStringParameter("remark",remark);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 评论
     * @param orderGoodsId
     * @param content
     * @param grade
     * @param memberId
     * @param payOrderId
     * @param bib
     */
    public void comment(String orderGoodsId,String content,String grade,String memberId,String payOrderId, final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "comment");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("orderGoodsId",orderGoodsId);
        params.addQueryStringParameter("content",content);
        params.addQueryStringParameter("grade",grade);
        params.addQueryStringParameter("memberId",memberId);
        params.addQueryStringParameter("payOrderId",payOrderId);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     *  首页广告
     * @param bib
     */
    public void homeAdv( final BaseModel.BaseModelIB bib) {
        String url = buildUrl("goods", "homeAdv");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        (genHttpUtils()).send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                   ArrayList<HomeAdModel> omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), new TypeToken<ArrayList<HomeAdModel>>() {
                }.getType());
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 支付订单
     * @param OrderId
     * @param amt
     * @param body
     * @param memberId
     * @param payType
     * @param bib
     */
    public void payOrder(String OrderId,String amt,String body,String memberId,String payType,final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "payOrder");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("OrderId",OrderId);
        params.addQueryStringParameter("amt",amt);
        params.addQueryStringParameter("body",body);
        params.addQueryStringParameter("memberId",memberId);
        params.addQueryStringParameter("payType",payType);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 快捷登录
     * @param phone
     * @param code
     * @param bib
     */
    public void quickLogin(String phone,String code,final BaseModel.BaseModelIB bib) {
        String url = buildUrl("member", "quickLogin");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("phone",phone);
        params.addQueryStringParameter("code",code);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    MemberModel omList = (ThtGosn.genGson()).fromJson(rsm.getJsonDatas(), MemberModel.class);
                    DatasStore.saveCurMember(omList);
                    BIBSucessful(bib, omList);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }

    /**
     * 删除订单
     * @param orderId 订单ID
     * @param memberId 用户ID
     * @param bib
     */
    public void delOrder(String orderId,String memberId,final BaseModel.BaseModelIB bib) {
        String url = buildUrl("order", "delOrder");// 构建API地址
        if (bib == null)
            LogUtils.e("member_info 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib
        RequestParams params = getParams();
        params.addQueryStringParameter("orderId",orderId);
        params.addQueryStringParameter("memberId",memberId);
        (genHttpUtils()).send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(responseInfo.result);
                if (rsm.getState() == 1) {// 成功
                    BIBSucessful(bib, rsm.getStringDatas());// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                BIBFailed(bib, new ResultsModel(-999, "连接超时"));// 访问接口失败
            }
        });

    }
}
package com.tylx.leasephone.model;

import java.math.BigDecimal;

/**
 * Created by track on 2017/7/10.
 */
public class BuyModel extends BaseModel {
    public static final int LEASE = 1;//租赁
    public static final int BUY = 2;//购买


    public static final int ALIPAY = 1;//支付宝
    public static final int WECHAT = 2;//微信
    public String shippingId;//购物车ID，多个ID用逗号隔开
    public String commondityNormId;// 商品规格详情ID
    public String commondityId;//商品ID
    public String sumAmt;// 订单总金额
    public String memberId;// 会员ID
    public int orderType;// 订单类型 1租赁，2购买
    public int payType;// 支付方式    1：支付宝、2：微信(暂无)
    public String voucherId;// 卡券ID
    public String leaseTerm;// 租赁月数()
    public String paidAmt;// 支付金额
    public int visit;// 订单来源 2,安卓，3.IOS
    public String remark;// 备注
    public int commondityNum;//商品数量
    public String voucherNum;





}

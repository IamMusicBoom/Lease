package com.tylx.leasephone.model;

import java.util.List;

/**
 * Created by wangm on 2017/7/21.
 */

public class OrderModel extends BaseModel {

    /**
     * headImg : temp/preImg/0720161101645f45d9b76.jpg
     * memberPhone : 88888888888
     * memberName : 我是谁123
     * goods : [{"id":"e1de2d2c54874d7abf88082b11dbe9ff","commondityNormId":"6xpk40xrxtrec980ku27ia13","commondityNum":1,"commondityPrice":90,"orderTradeId":"e1de2d2c54874d7abf88082b11dbe9ff","commondityNorm":"CORLOR_04,ME_01,SCREEN_01","commondityImg":"temp/preImg/070515222335150860bfd.jpg","goodsName":"华为P9"}]
     * id : e1de2d2c54874d7abf88082b11dbe9ff
     * startTime : 1500541570000
     * endTime : null
     * memberId : 3
     * remark :
     * sumAmt : 90
     * voucherId :
     * orderType : 2
     * leaseTerm : null
     * isLocked : N
     * isUndo : N
     * payableAmt : 90
     * dealTime : null
     * paidAmt : 0
     * undoRemark : null
     * tradeStatus : 0
     * payNoticeVoucherId : null
     * overdueTime : 1500543370000
     * overdueStatus : 0
     * lockedRemark : null
     * updateTime : 1500541570000
     * payStatus : 1
     * payType : 0
     * leasePrice : null
     * summary : null
     * undoTime : null
     * visit : 2
     * accDate : null
     * lockedTime : null
     * img : null
     */

    public String headImg;
    public String memberPhone;
    public String memberName;
    public String id;
    public long startTime;
    public long endTime;
    public String memberId;
    public String remark;
    public String sumAmt;
    public String voucherId;
    public int orderType;
    public String leaseTerm;
    public String isLocked;
    public String isUndo;
    public String payableAmt;
    public String dealTime;
    public String paidAmt;
    public String undoRemark;
    public int tradeStatus;
    public String payNoticeVoucherId;
    public long overdueTime;
    public int overdueStatus;
    public String lockedRemark;
    public long updateTime;
    public int payStatus;
    public int payType;
    public String leasePrice;
    public String summary;
    public String undoTime;
    public int visit;
    public String accDate;
    public String lockedTime;
    public String img;
    public List<GoodsBean> goods;
    public String nickName;
    public String phone;
    public String address;
    public String expressNo;
    public String expressCompany;


    public static class GoodsBean extends BaseModel{
        /**
         * id : e1de2d2c54874d7abf88082b11dbe9ff
         * commondityNormId : 6xpk40xrxtrec980ku27ia13
         * commondityNum : 1
         * commondityPrice : 90
         * orderTradeId : e1de2d2c54874d7abf88082b11dbe9ff
         * commondityNorm : CORLOR_04,ME_01,SCREEN_01
         * commondityImg : temp/preImg/070515222335150860bfd.jpg
         * goodsName : 华为P9
         */

        public String id;
        public String commondityNormId;
        public int commondityNum;
        public String commondityPrice;
        public String orderTradeId;
        public String commondityNorm;
        public String commondityImg;
        public String goodsName;
        public String original;
        public String isComment;
        public String commodityId;

    }
}

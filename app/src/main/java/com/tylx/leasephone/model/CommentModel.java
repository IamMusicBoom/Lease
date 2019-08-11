package com.tylx.leasephone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangm on 2017/7/3.
 */

public class CommentModel extends BaseModel {
    public String id;
    public String content;
    public int memberId;
    public String remark;
    public String isOpenl;
    public long createTime;
    public float grade;
    public String commodityId;
    public String reply;
    public String payOrderId;
    public String updateTime;
    public MemberAccInfo memberAccInfo;

    public class MemberAccInfo {
        public int id;
        public String headImg;
        public String mobilePhone;
        public String nickName;
    }
}

package com.tylx.leasephone.model;

import java.util.List;

/**
 * Created by wangm on 2017/7/12.
 */

public class ShopGoodsModel extends BaseModel {
    public String typeName;
    public String typeCode;
    public String remark;
    public List<ShopGoodsModel> commondityTypes;
    public String parentCode;
    public String typeDesc;
}

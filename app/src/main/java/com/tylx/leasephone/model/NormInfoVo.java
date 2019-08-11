package com.tylx.leasephone.model;


import java.util.List;

public class NormInfoVo extends BaseModel {
    public String normCode;

    public String normMean;

    public String normValue;

    public Integer sort;

    public String isOpen;

    public List<NormDetail> normDetails;

}
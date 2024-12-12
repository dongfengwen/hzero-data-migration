package com.mig.data.controller.dto;

/**
 * @description:
 * @author: dongbx@dianzhong.com
 * @createDate: 2024-12-10 10:10
 */
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Response implements Serializable {
    private int rc;
    private int rt;
    private long svr;
    private int lt;
    private int full;
    private String dlmkts;
    private DataRes data;

    // Getters and Setters
}

@Data
class DataRes implements Serializable{
    private int total;
    private List<Diff> diff;

    // Getters and Setters
}
@Data
class Diff implements Serializable{
    private int f1;
    private double f2;
    private double f3;
    private String f12;
    private int f13;
    private String f14;
    private int f26;
    private int f152;
    private double f227;
    private double f228;
    private double f229;
    private double f230;
    private int f231;
    private String f232;
    private int f233;
    private String f234;
    private double f235;
    private double f236;
    private double f237;
    private double f238;
    private double f239;
    private double f240;
    private double f241;
    private int f242;
    private int f243;

    // Getters and Setters
}


package com.mig.data.controller.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 股票实时价格详情
 *
 * @author: fengwen.dong@going-link.com
 * @createDate: 2022-05-11 18:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorChgDTO implements Serializable {
    //查询 沪深A股涨速
    //http://77.push2.eastmoney.com/api/qt/clist/get

    //最新
    private String f2;

    //涨幅 -1.63%
    private String f3;

    //涨跌 -0.08
    private String f4;

    //总手
    private String f5;

    //成交额
    private String f6;

    //昨收
    private String f7;
    //换手：1.97%
    private String f8;
    //PE(动):
    private String f9;
    //量比：
    private String f10;

    private String f11;
    //股票编码
    private String f12;
    //股票前缀 类型 0上证 1深证
    private String f13;
    //股票名称
    private String f14;
    //最高：4.94
    private String f15;
    // 最低：4.70
    private String f16;
    //今开：4.88
    private String f17;
    //昨收：4.90
    private String f18;

    //总值：4367954618
    private String f20;

    //流值：4341820737
    private String f21;

    //涨速
    private String f22;

    //市净率
    private String f23;

    //60日涨跌幅
    private String f24;

    //年初至今涨跌幅
    private String f25;

    private String f62;

    private String f115;

}

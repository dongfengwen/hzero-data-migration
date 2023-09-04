package com.mig.data.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-25 15:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayPriceDTO implements Serializable {

    //代码
    private String f57;
    //名称
    private String f58;
    //开盘
    private String f46;
    //最高
    private String f44;
    //最低
    private String f45;
    //最新价格
    private String f43;
    //成交总手 99088
    private String f47;
    //成交金额 837303861.16
    private String f48;
    //涨停价
    private String f51;
    //跌停价
    private String f52;
    //昨天收盘价
    private String f60;
    //涨跌幅
    private String f170;

    //卖1价
    private String f39;
    //卖1量
    private String f40;
    //卖2价
    private String f37;
    //卖2量
    private String f38;
    //卖3价
    private String f35;
    //卖3量
    private String f36;
    //卖4价
    private String f33;
    //卖4量
    private String f34;
    //卖4价
    private String f31;
    //卖4量
    private String f32;

    //买1价
    private String f19;
    //买1量
    private String f20;
    //买2价
    private String f17;
    //买2量
    private String f18;
    //买3价
    private String f15;
    //买3量
    private String f16;
    //买4价
    private String f13;
    //买4量
    private String f14;
    //买5价
    private String f11;
    //买5量
    private String f12;

    //板块
    private String f127;
    //行业
    private String f128;
    //概念
    private String f129;
    //主力流入差额
    private String f137;



    //{
    //    "rc": 0,
    //    "rt": 4,
    //    "svr": 182995333,
    //    "lt": 1,
    //    "full": 1,
    //    "dlmkts": "",
    //    "data": {
    //        "f43": 78.7, //最新价格
    //        "f44": 93, // 最高
    //        "f45": 76, // 最低
    //        "f46": 78, // 今开盘价
    //        "f47": 99088, //成交量 手
    //        "f48": 837303861.16, //成交额
    //        "f49": 47927,
    //        "f50": 1.02,//量比
    //        "f51": 101.68, // 涨停价
    //        "f52": 67.78, //跌停价
    //        "f55": -0.046910911,//每股收益
    //        "f57": "301372",//代码
    //        "f58": "科净源",//名称
    //        "f60": 84.73,//昨收
    //        "f62": 1,
    //        "f71": 84.5,
    //        "f78": 0,
    //        "f80": "[{\"b\":202308250930,\"e\":202308251130},{\"b\":202308251300,\"e\":202308251500}]",
    //        "f84": 68571430,//总股本
    //        "f85": 16257930,//流通股本
    //        "f86": 1692948873,
    //        "f92": 13.0925725,
    //        "f104": 415754181.29,
    //        "f105": -3216748.22, //净利润
    //        "f107": 0,
    //        "f110": 0,
    //        "f111": 80,
    //        "f116": 5396571541,//总市值
    //        "f117": 1279499091,//流通市值
    //        "f127": "环保行业",
    //        "f128": "北京板块",
    //        "f135": 233226797,主力流入
    //        "f136": 251320347,主力流出
    //        "f137": -18093550,主力流入差额
    //        "f138": 41381754,//超大单买
    //        "f139": 26961041,//超大单卖
    //        "f140": 14420713,//超大单差额
    //        "f141": 191845043,
    //        "f142": 224359306,
    //        "f143": -32514263,//大单差额
    //        "f144": 342970224,
    //        "f145": 353751680,
    //        "f146": -10781456,//中单差额
    //        "f147": 240871843,
    //        "f148": 211996829,
    //        "f149": 28875014,//小单差额
    //        "f161": 51161,
    //        "f162": -419.41, //PE(动)
    //        "f163": 62.53,
    //        "f164": 58.16,
    //        "f167": 6.01,
    //        "f168": 60.95,
    //        "f169": -6.03, //涨跌
    //        "f170": -7.12, //涨幅
    //        "f173": -1.19,
    //        "f177": 72,
    //        "f183": 40811149.46,
    //        "f184": -36.5742734951,
    //        "f185": 66.849416049732,
    //        "f186": 37.937544531,
    //        "f187": -7.9227649865,
    //        "f188": 66.65203442399999,
    //        "f189": 20230811,
    //        "f190": 2.122432736637,
    //        "f191": 52.48, //委比
    //        "f192": 180, //委差
    //        "f193": -2.16,
    //        "f194": 1.72,
    //        "f195": -3.88,
    //        "f196": -1.29,
    //        "f197": 3.45,
    //        "f199": 90,
    //        "f250": "-",
    //        "f251": "-",
    //        "f252": "-",
    //        "f253": "-",
    //        "f254": "-",
    //        "f255": 0,
    //        "f256": "-",
    //        "f257": 0,
    //        "f258": "-",
    //        "f262": "-",
    //        "f263": 0,
    //        "f264": "-",
    //        "f266": "-",
    //        "f267": "-",
    //        "f268": "-",
    //        "f269": "-",
    //        "f270": 0,
    //        "f271": "-",
    //        "f273": "-",
    //        "f274": "-",
    //        "f275": "-",
    //        "f280": "-",
    //        "f281": "-",
    //        "f282": "-",
    //        "f284": 0,
    //        "f285": "-",
    //        "f286": 0,
    //        "f287": "-",
    //        "f292": 5,
    //        "f21": "-",
    //        "f22": "-",
    //        "f23": "-",
    //        "f24": "-",
    //        "f25": "-",
    //        "f26": "-",
    //        "f27": "-",
    //        "f28": "-",
    //        "f29": "-",
    //        "f30": "-",
    //        "f31": 78.93,
    //        "f32": 1,
    //        "f33": 78.91,
    //        "f34": 4,
    //        "f35": 78.9,
    //        "f36": 5,
    //        "f37": 78.8,
    //        "f38": 51,
    //        "f39": 78.74,
    //        "f40": 20,

    //        "f19": 78.7,
    //        "f20": 247,
    //        "f17": 78.69,
    //        "f18": 1,
    //        "f15": 78.67,
    //        "f16": 1,
    //        "f13": 78.65,
    //        "f14": 11,
    //        "f11": 78.64,
    //        "f12": 1,
    //        "f9": "-",
    //        "f10": "-",
    //        "f7": "-",
    //        "f8": "-",
    //        "f5": "-",
    //        "f6": "-",
    //        "f3": "-",
    //        "f4": "-",
    //        "f1": "-",
    //        "f2": "-"
    //    }
    //}



}

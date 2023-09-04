package com.mig.data.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 监控
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-20 15:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorChgRequest implements Serializable {

    //类型
    private String fs;

    //股票前缀 类型 0上证 1深证
    private String f13;

    //代码
    private String code;

    //成本
    private BigDecimal cost;

    //数量
    private Integer number;


}

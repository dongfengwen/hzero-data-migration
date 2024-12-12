package com.mig.data.controller.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: dongbx@dianzhong.com
 * @createDate: 2024-12-10 10:07
 */
@Data
public class ConvertibleBond implements Serializable {

    // 类别或状态标识
    private Integer category;

    // 当前价格或价值
    private BigDecimal currentPrice;

    // 比例（可能是收益率等）
    private BigDecimal ratio;

    // 编号或识别码
    private String bondCode;

    // 转债名称
    private String bondName;

    // 价格相关数值（如面值或发行价格）
    private BigDecimal faceValue;

    // 债务相关数据（如利息或利率）
    private BigDecimal interestRate;

    // 其他价格相关数值
    private BigDecimal additionalPrice1;
    private BigDecimal additionalPrice2;
    private BigDecimal additionalPrice3;

    // 股票代码（关联公司）
    private String stockCode;

    // 到期时间
    private String maturityDate;

    // 其他相关日期（如发行日期）
    private String issueDate;

}

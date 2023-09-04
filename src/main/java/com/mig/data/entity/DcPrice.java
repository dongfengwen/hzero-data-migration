package com.mig.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 行情价格
 * </p>
 *
 * @author DongFengWen
 * @since 2023-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dc_price")
public class DcPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "price_id", type = IdType.AUTO)
    private Long priceId;

    /**
     * 股票前缀 类型 0上证 1深证
     */
    private String prefix;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 最新价格
     */
    private String nowPrice;

    /**
     * 涨幅%
     */
    private String increaseTage;

    /**
     * 涨幅价格
     */
    private String increasePrice;

    /**
     * 总手
     */
    private String totalHand;

    /**
     * 成交额
     */
    private String turnover;

    /**
     * PE(动)
     */
    private String peMove;

    /**
     * 最高
     */
    private String highest;

    /**
     * 最低
     */
    private String minimum;

    /**
     * 今开
     */
    private String openPrice;

    /**
     * 昨收
     */
    private String received;

    /**
     * 涨速%
     */
    private String speed;

    /**
     * 创建日期
     */
    private Date createTime;


}

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
 * 
 * </p>
 *
 * @author DongFengWen
 * @since 2023-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("omp_a_order")
public class OmpAOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 商户外部订单号
     */
    private String originOrderNo;

    /**
     * 订单创建时间
     */
    private Date orderTime;

    /**
     * 客户账号
     */
    private String account;

    /**
     * 通道id
     */
    private Long channelId;

    /**
     * 订单金额
     */
    private String amount;

    /**
     * 回调地址
     */
    private String notifyUrl;

    private Date createdAt;

    private Date updatedAt;

    private Long version;

    /**
     * 失效原因
     */
    private String failReason;


}

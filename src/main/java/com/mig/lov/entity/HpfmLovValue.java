package com.mig.lov.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * LOV独立值集表
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hpfm_lov_value")
public class HpfmLovValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lov_value_id", type = IdType.AUTO)
    private Long lovValueId;

    /**
     * lov表Id
     */
    private Long lovId;

    /**
     * 值集代码
     */
    private String lovCode;

    private String value;

    private String meaning;

    private String lovValue;

    private String lovMeaning;

    private String enabledFlagName;
    /**
     * 描述
     */
    private String description;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 标记
     */
    private String tag;

    /**
     * 排序号
     */
    private Integer orderSeq;

    private String parentValue;

    /**
     * 有效期起
     */
    private Date startDateActive;

    /**
     * 有效期止
     */
    private Date endDateActive;

    /**
     * 生效标识：1:生效，0:失效
     */
    private Integer enabledFlag;

    /**
     * 行版本号，用来处理锁
     */
    private Long objectVersionNumber;

    private Date creationDate;

    private Long createdBy;

    private Date lastUpdateDate;

    private Long lastUpdatedBy;


}

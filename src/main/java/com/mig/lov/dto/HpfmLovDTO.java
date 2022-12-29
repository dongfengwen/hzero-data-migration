package com.mig.lov.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 存储解密字段
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HpfmLovDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long lovId;

    /**
     * LOV代码
     */
    private String lovCode;

    /**
     * LOV数据类型: URL/SQL/FIXED
     */
    private String lovTypeCode;

    /**
     * 目标路由
     */
    private String routeName;

    /**
     * 值集名称
     */
    private String lovName;

    /**
     * 描述
     */
    private String description;

    /**
     * 租户ID
     */
    private Long tenantId;

    private String parentLovCode;

    /**
     * 父级值集租户ID
     */
    private Long parentTenantId;

    /**
     * 自定义sql
     */
    private String customSql;

    /**
     * 查询URL
     */
    private String customUrl;

    /**
     * 值字段
     */
    private String valueField;

    /**
     * 显示字段
     */
    private String displayField;

    /**
     * 是否必须分页
     */
    private Integer mustPageFlag;

    /**
     * 是否启用
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

    /**
     * 翻译sql
     */
    private String translationSql;

    /**
     * 是否公开权限，0:不公开 1:公开
     */
    private Integer publicFlag;

    /**
     * 加密字段
     */
    private String encryptField;

    /**
     * 存储解密字段
     */
    private String decryptField;
    

}

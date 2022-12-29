package com.mig.lov.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@TableName("hpfm_lov")
public class HpfmLov implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lov_id", type = IdType.AUTO)
    private Long lovId;

    /**
     * LOV代码
     */
    private String lovCode;

    /**
     * LOV数据类型: URL/SQL/FIXED
     */
    private String lovTypeCode;

    private String lovTypeName;

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

    private String tenantName;
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
    private String createdByName;

    private Date lastUpdateDate;

    private String lastUpdatedBy;

    private String lastUpdatedByName;

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

    //新增/修改
    private String addModify;

    //新增/修改
    private String addModifyColor;
    //对比
    private String difference;

    private Long devLovId;
    private Long prodLovId;

    private String devLovCode;
    private String prodLovCode;
    private String devLovName;
    private String prodLovName;
    private String devLovTypeName;
    private String prodLovTypeName;
    private String devLovValueField;
    private String prodLovValueField;
    private String devDisplayField;
    private String prodDisplayField;
    private String devRouteName;
    private String prodRouteName;
    private String devEncryptField;
    private String prodEncryptField;
    private String devCustomSql;
    private String prodCustomSql;
    private String devCustomUrl;
    private String prodCustomUrl;
    private String devTranslationSql;
    private String prodTranslationSql;
    private Integer devEnabledFlag;
    private Integer prodEnabledFlag;


    /**
     * 值
     */
    private List<HpfmLovValue> devLovValueList = new ArrayList<>();
    /**
     * 值
     */
    private List<HpfmLovValue> prodLovValueList = new ArrayList<>();


}

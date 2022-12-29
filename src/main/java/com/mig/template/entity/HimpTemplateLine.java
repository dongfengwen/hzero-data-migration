package com.mig.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("himp_template_line")
public class HimpTemplateLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板目标ID,himp_template_target.id
     */
    private Long targetId;

    /**
     * 列序号
     */
    private Integer columnIndex;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列代码
     */
    private String columnCode;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 列长度
     */
    private Long length;

    /**
     * 是否有效
     */
    private Integer enabledFlag;

    /**
     * 必输验证标识
     */
    private Integer nullableFlag;

    /**
     * 验证标识
     */
    private Integer validateFlag;

    /**
     * 是否开启数据转换
     */
    private Integer changeDataFlag;

    /**
     * 数据中最大值
     */
    private String maxValue;

    /**
     * 数据中最小值
     */
    private String minValue;

    private String validateSet;

    private String regularExpression;

    /**
     * 租户ID,hpfm_tenant.tenant_id
     */
    private Long tenantId;

    /**
     * 行版本号，用来处理锁
     */
    private Long objectVersionNumber;

    private Date creationDate;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date lastUpdateDate;

    /**
     * 列值格式掩码
     */
    private String formatMask;

    /**
     * 示例数据
     */
    private String sampleData;

    /**
     * 描述
     */
    private String description;


}

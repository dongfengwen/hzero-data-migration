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
 * 来源编码
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hpfm_lov_view_line")
public class HpfmLovViewLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "view_line_id", type = IdType.AUTO)
    private Long viewLineId;

    /**
     * 值集查询视图头表ID,hpfm_fnd_lov_view_header.view_header_id
     */
    private Long viewHeaderId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 值集ID,hpfm_fnd_lov.lov_id
     */
    private Long lovId;

    /**
     * 显示名称
     */
    private String display;

    /**
     * 排序号
     */
    private Integer orderSeq;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 是否查询字段
     */
    private Integer queryFieldFlag;

    /**
     * 是否表格列
     */
    private Integer tableFieldFlag;

    /**
     * 表格列宽度
     */
    private Integer tableFieldWidth;

    /**
     * 是否启用
     */
    private Integer enabledFlag;

    /**
     * 行版本号，用来处理锁
     */
    private Long objectVersionNumber;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人
     */
    private Long lastUpdatedBy;

    /**
     * 表单控件类型，值集:HPFM.VIEW.DATA_TYPE
     */
    private String dataType;

    /**
     * 来源编码
     */
    private String sourceCode;


}

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
 * 个性化单元编码
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("himp_template_target")
public class HimpTemplateTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板头ID,himp_template_header.id
     */
    private Long headerId;

    /**
     * sheet页序号
     */
    private Integer sheetIndex;

    /**
     * Sheet页名称
     */
    private String sheetName;

    /**
     * 正式数据表名
     */
    private String tableName;

    /**
     * 脚本编码,hpfm_rule_script.script_code
     */
    private String ruleScriptCode;

    /**
     * 启用标识
     */
    private Integer enabledFlag;

    /**
     * 行版本号，用来处理锁
     */
    private Long objectVersionNumber;

    private Date creationDate;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date lastUpdateDate;

    /**
     * 数据源编码，hpfm_datasource.datasource_code
     */
    private String datasourceCode;

    /**
     * 导入起始行
     */
    private Integer startLine;

    /**
     * 租户ID,hpfm_tenant.tenant_id
     */
    private Long tenantId;

    /**
     * 个性化单元编码
     */
    private String unitCode;


}

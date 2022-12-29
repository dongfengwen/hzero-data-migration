package com.mig.template.entity;

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
 * 分片上传标识
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("himp_template_header")
public class HimpTemplateHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long devId;
    private Long prodId;

    private Long targetId;

    private Long devTargetId;
    private Long prodTargetId;


    /**
     * 模板代码
     */
    private String templateCode;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 启用标识
     */
    private Integer enabledFlag;

    /**
     * 模板类型
     */
    private String templateType;

    /**
     * 客户端路径前缀
     */
    private String prefixPatch;

    /**
     * 描述
     */
    private String description;

    /**
     * 租户ID,hpfm_tenant.tenant_id
     */
    private Long tenantId;
    private String tenantName;

    /**
     * 行版本号，用来处理锁
     */
    private Long objectVersionNumber;

    private Date creationDate;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date lastUpdateDate;

    /**
     * 自定义模板地址
     */
    private String templateUrl;

    /**
     * 分片上传标识
     */
    private Integer fragmentFlag;


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
     * 数据源编码，hpfm_datasource.datasource_code
     */
    private String datasourceCode;

    /**
     * 导入起始行
     */
    private Integer startLine;


    /**
     * 个性化单元编码
     */
    private String unitCode;

    //新增/修改
    private String addModify;

    //新增/修改
    private String addModifyColor;
    //对比
    private String difference;

    private List<HimpTemplateLine> devTemplateLineList = new ArrayList<>();

    private List<HimpTemplateLine> prodTemplateLineList = new ArrayList<>();


}

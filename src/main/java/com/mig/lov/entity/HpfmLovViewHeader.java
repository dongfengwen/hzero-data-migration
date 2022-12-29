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
 * 值集查询视图头表
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hpfm_lov_view_header")
public class HpfmLovViewHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "view_header_id", type = IdType.AUTO)
    private Long viewHeaderId;

    private String viewCode;

    /**
     * 视图名称
     */
    private String viewName;

    /**
     * 值集ID,hpfm_fnd_lov.lov_id
     */
    private Long lovId;

    /**
     * 租户ID
     */
    private Long tenantId;

    private Long viewTenantId;

    private String tenantName;

    /**
     * 值字段
     */
    private String valueField;

    /**
     * 显示字段
     */
    private String displayField;

    /**
     * 标题
     */
    private String title;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 是否延迟加载
     */
    private Integer delayLoadFlag;

    /**
     * 树形查询子字段名
     */
    private String childrenFieldName;

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

    private Long devViewHeaderId;

    private Long prodViewHeaderId;
    //新增/修改
    private String addModify;

    //新增/修改
    private String addModifyColor;
    //对比
    private String difference;
    private String lovCode;
    private String lovName;

    private List<HpfmLovViewLine> devLovViewList = new ArrayList<>();

    private List<HpfmLovViewLine> prodLovViewList = new ArrayList<>();


}

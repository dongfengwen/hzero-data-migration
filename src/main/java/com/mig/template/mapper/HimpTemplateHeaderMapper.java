package com.mig.template.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mig.template.entity.HimpTemplateHeader;

import java.util.List;

/**
 * <p>
 * 分片上传标识 Mapper 接口
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
public interface HimpTemplateHeaderMapper extends BaseMapper<HimpTemplateHeader> {

    List<HimpTemplateHeader> pageByTemplateHeaderDev(HimpTemplateHeader templateHeader);

    @DS("slave_1")
    List<HimpTemplateHeader> pageByTemplateHeaderProd(HimpTemplateHeader templateHeader);

}

package com.mig.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.model.PageRequest;
import com.mig.template.entity.HimpTemplateHeader;

import java.util.List;

/**
 * <p>
 * 分片上传标识 服务类
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
public interface HimpTemplateHeaderService extends IService<HimpTemplateHeader> {

    List<HimpTemplateHeader> pageByTemplateHeader(HimpTemplateHeader templateHeader, PageRequest pageRequest);
}

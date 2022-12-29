package com.mig.template.controller;


import com.common.model.PageRequest;
import com.common.util.JsonData;
import com.mig.template.entity.HimpTemplateHeader;
import com.mig.template.service.HimpTemplateHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 分片上传标识 前端控制器
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
@RestController
@RequestMapping("/v1/template")
@Slf4j
public class HimpTemplateHeaderController {

    @Autowired
    private HimpTemplateHeaderService templateHeaderService;

    @GetMapping("value-page")
    public JsonData pageByTemplateHeader(HimpTemplateHeader templateHeader, @ApiIgnore PageRequest pageRequest) {
        List<HimpTemplateHeader> pageRes = templateHeaderService.pageByTemplateHeader(templateHeader, pageRequest);
        return JsonData.buildSuccess(pageRes);
    }

}


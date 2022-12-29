package com.mig.lov.controller;


import com.common.model.PageRequest;
import com.common.util.JsonData;
import com.mig.lov.entity.HpfmLov;
import com.mig.lov.entity.HpfmLovValue;
import com.mig.lov.service.HpfmLovService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 存储解密字段 前端控制器
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@RestController("HpfmLovController.v1")
@RequestMapping("/v1/lov")
@Slf4j
public class HpfmLovController {
    @Autowired
    private HpfmLovService hpfmLovService;

    @GetMapping("value-page")
    public JsonData pageByLovValue(HpfmLov hpfmLov, @ApiIgnore PageRequest pageRequest) {
        List<HpfmLov> pageRes = hpfmLovService.pageByLovValue(hpfmLov, pageRequest);
        return JsonData.buildSuccess(pageRes);
    }

    @PostMapping("data-approval")
    public JsonData dataApproval(@RequestBody List<HpfmLov> lovValues) {
        hpfmLovService.dataApproval(lovValues);
        return JsonData.buildSuccess();
    }

    @GetMapping("data-differences-dev")
    public JsonData dataDifferencesDev(HpfmLovValue lovValue) {
        List<HpfmLovValue> pageRes = hpfmLovService.dataDifferencesDev(lovValue);
        return JsonData.buildSuccess(pageRes);
    }

    @GetMapping("data-differences-prod")
    public JsonData dataDifferencesProd(HpfmLovValue lovValue) {
        List<HpfmLovValue> pageRes = hpfmLovService.dataDifferencesProd(lovValue);
        return JsonData.buildSuccess(pageRes);
    }
}


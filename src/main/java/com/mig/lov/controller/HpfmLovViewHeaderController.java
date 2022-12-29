package com.mig.lov.controller;


import com.common.model.PageRequest;
import com.common.util.JsonData;
import com.mig.lov.entity.HpfmLovViewHeader;
import com.mig.lov.entity.HpfmLovViewLine;
import com.mig.lov.service.HpfmLovViewHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 值集查询视图头表 前端控制器
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/v1/lovView")
public class HpfmLovViewHeaderController {

    @Autowired
    private HpfmLovViewHeaderService hpfmLovViewHeaderService;

    @GetMapping("value-page")
    public JsonData pageByLovValue(HpfmLovViewHeader lovValue, @ApiIgnore PageRequest pageRequest) {
        List<HpfmLovViewHeader> pageRes = hpfmLovViewHeaderService.pageByLovValue(lovValue, pageRequest);
        return JsonData.buildSuccess(pageRes);
    }

    @PostMapping("data-approval")
    public JsonData dataApproval(@RequestBody List<HpfmLovViewHeader> lovValues) {
        hpfmLovViewHeaderService.dataApproval(lovValues);
        return JsonData.buildSuccess();
    }

    @GetMapping("data-differences-dev")
    public JsonData dataDifferencesDev(HpfmLovViewLine lovValue) {
        List<HpfmLovViewLine> pageRes = hpfmLovViewHeaderService.dataDifferencesDev(lovValue);
        return JsonData.buildSuccess(pageRes);
    }

    @GetMapping("data-differences-prod")
    public JsonData dataDifferencesProd(HpfmLovViewLine lovValue) {
        List<HpfmLovViewLine> pageRes = hpfmLovViewHeaderService.dataDifferencesProd(lovValue);
        return JsonData.buildSuccess(pageRes);
    }

}


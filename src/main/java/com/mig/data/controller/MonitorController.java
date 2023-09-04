package com.mig.data.controller;

import com.common.util.JsonData;
import com.mig.data.controller.dto.MonitorChgDTO;
import com.mig.data.controller.dto.MonitorChgRequest;
import com.mig.data.service.MonitorService;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-01-03 23:46
 */
@RestController("DataController.v1")
@RequestMapping("/v1/monitor/data")
@Slf4j
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @ApiModelProperty("查询A股 涨跌幅")
    @PostMapping("chg")
    @Scheduled(fixedRate = 1500)
    public JsonData chg() {
        List<MonitorChgDTO> chg = monitorService.chg(null);
        return JsonData.buildSuccess(chg);
    }

    @ApiModelProperty("监控异常（持仓）")
    @PostMapping("sub-monitor-chg")
    public JsonData monitorChg(@RequestBody List<MonitorChgRequest> request) {
        monitorService.monitorChg(request);
        return JsonData.buildSuccess();
    }

    @ApiModelProperty("监控异常（持仓）")
    @PostMapping("sub-monitor")
    @Scheduled(fixedRate = 1500)
    public JsonData monitor() {
        List<MonitorChgDTO> chg = monitorService.monitor(null);
        return JsonData.buildSuccess(chg);
    }

    @ApiModelProperty("监控异常（板块）")
    @PostMapping("sub-monitor-template")
    public JsonData monitorTemplate() {
        List<MonitorChgDTO> chg = monitorService.monitorTemplate();
        return JsonData.buildSuccess(chg);
    }

}

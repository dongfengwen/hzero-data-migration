package com.mig.data.service;

import com.mig.data.controller.dto.MonitorChgDTO;
import com.mig.data.controller.dto.MonitorChgRequest;

import java.util.List;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-20 19:45
 */
public interface MonitorService {


    List<MonitorChgDTO> chg(MonitorChgRequest fs);

    List<MonitorChgDTO> monitor(MonitorChgRequest request);

    List<MonitorChgDTO> monitorTemplate();

    void monitorChg(List<MonitorChgRequest> request);

}

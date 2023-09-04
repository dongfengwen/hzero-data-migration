package com.mig.data.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-20 19:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorChgResponse implements Serializable {

    private MonitorChgData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class MonitorChgData implements Serializable {
        private Long total;

        private List<MonitorChgDTO> diff;
    }
}

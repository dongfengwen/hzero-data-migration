package com.mig.data.controller;


import com.common.util.JsonData;
import com.mig.data.entity.OmpAOrder;
import com.mig.data.service.OmpAOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author DongFengWen
 * @since 2023-01-11
 */
@RestController
@RequestMapping("/ompAOrder")
public class OmpAOrderController {

    @Autowired
    private OmpAOrderService ompAOrderService;

    @PostMapping("data-stream")
    public JsonData algorithm() {

        List<OmpAOrder> ompAOrderList = ompAOrderService.algorithm();
        return JsonData.buildSuccess(ompAOrderList);
    }

}


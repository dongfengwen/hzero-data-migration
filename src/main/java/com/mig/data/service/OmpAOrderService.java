package com.mig.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mig.data.entity.OmpAOrder;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author DongFengWen
 * @since 2023-01-11
 */
public interface OmpAOrderService extends IService<OmpAOrder> {

    List<OmpAOrder> algorithm();
}

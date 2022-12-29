package com.mig.lov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.model.PageRequest;
import com.mig.lov.entity.HpfmLov;
import com.mig.lov.entity.HpfmLovValue;

import java.util.List;

/**
 * <p>
 * 存储解密字段 服务类
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
public interface HpfmLovService extends IService<HpfmLov> {

    List<HpfmLov> pageByLovValue(HpfmLov hpfmLov, PageRequest pageRequest);

    void dataApproval(List<HpfmLov> lovValues);

    List<HpfmLovValue> dataDifferencesDev(HpfmLovValue lovValue);

    List<HpfmLovValue> dataDifferencesProd(HpfmLovValue lovValue);
}

package com.mig.lov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.model.PageRequest;
import com.mig.lov.entity.HpfmLovViewHeader;
import com.mig.lov.entity.HpfmLovViewLine;

import java.util.List;

/**
 * <p>
 * 值集查询视图头表 服务类
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
public interface HpfmLovViewHeaderService extends IService<HpfmLovViewHeader> {

    List<HpfmLovViewHeader> pageByLovValue(HpfmLovViewHeader lovValue, PageRequest pageRequest);

    void dataApproval(List<HpfmLovViewHeader> lovValues);


    List<HpfmLovViewLine> dataDifferencesDev(HpfmLovViewLine lovValue);


    List<HpfmLovViewLine> dataDifferencesProd(HpfmLovViewLine lovValue);
    

}

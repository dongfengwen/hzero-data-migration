package com.mig.lov.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mig.lov.entity.HpfmLovViewHeader;
import com.mig.lov.entity.HpfmLovViewLine;

import java.util.List;

/**
 * <p>
 * 值集查询视图头表 Mapper 接口
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
public interface HpfmLovViewHeaderMapper extends BaseMapper<HpfmLovViewHeader> {

    List<HpfmLovViewHeader> devPageByLovValue(HpfmLovViewHeader lovValue);

    @DS("slave_1")
    List<HpfmLovViewHeader> prodPageByLovValue(HpfmLovViewHeader lovValue);


    List<HpfmLovViewLine> devDataDifferences(HpfmLovViewLine lovValue);

    @DS("slave_1")
    List<HpfmLovViewLine> prodDataDifferences(HpfmLovViewLine lovValue);

}

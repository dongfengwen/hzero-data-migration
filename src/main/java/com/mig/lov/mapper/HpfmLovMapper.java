package com.mig.lov.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mig.lov.entity.HpfmLov;
import com.mig.lov.entity.HpfmLovValue;

import java.util.List;

/**
 * <p>
 * 存储解密字段 Mapper 接口
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
public interface HpfmLovMapper extends BaseMapper<HpfmLov> {

    List<HpfmLov> selectDevPageVo(HpfmLov hpfmLov);


    @DS("slave_1")
    List<HpfmLov> selectProdPageVo(HpfmLov hpfmLov);

    @DS("slave_1")
    List<HpfmLovValue> dataDifferencesProd(HpfmLovValue lovValue);

    List<HpfmLovValue> dataDifferencesDev(HpfmLovValue lovValue);

}

package com.mig.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mig.data.entity.OmpAOrder;
import org.apache.ibatis.cursor.Cursor;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author DongFengWen
 * @since 2023-01-11
 */
public interface OmpAOrderMapper extends BaseMapper<OmpAOrder> {

    @DS("slave_2")
    Cursor<OmpAOrder> selectAlgorithm();


}

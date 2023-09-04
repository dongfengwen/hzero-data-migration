package com.mig.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mig.data.entity.OmpAOrder;
import com.mig.data.mapper.OmpAOrderMapper;
import com.mig.data.service.OmpAOrderService;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author DongFengWen
 * @since 2023-01-11
 */
@Service
public class OmpAOrderServiceImpl extends ServiceImpl<OmpAOrderMapper, OmpAOrder> implements OmpAOrderService {

    @Autowired
    private OmpAOrderMapper ompAOrderMapper;

    @Override
    public List<OmpAOrder> algorithm() {
        Cursor<OmpAOrder> ompAOrderCursor = ompAOrderMapper.selectAlgorithm();
        ompAOrderCursor.isOpen();
        ompAOrderCursor.forEach(ompAOrder -> {
            System.out.println(ompAOrder.getOriginOrderNo());
        });
        return new ArrayList<>();
    }
}

package com.mig.lov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.model.PageRequest;
import com.mig.lov.entity.HpfmLovViewHeader;
import com.mig.lov.entity.HpfmLovViewLine;
import com.mig.lov.mapper.HpfmLovViewHeaderMapper;
import com.mig.lov.service.HpfmLovViewHeaderService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 值集查询视图头表 服务实现类
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@Service
public class HpfmLovViewHeaderServiceImpl extends ServiceImpl<HpfmLovViewHeaderMapper, HpfmLovViewHeader> implements HpfmLovViewHeaderService {
    @Autowired
    private HpfmLovViewHeaderMapper hpfmLovViewHeaderMapper;

    @Override
    public List<HpfmLovViewHeader> pageByLovValue(HpfmLovViewHeader lovViewHeader, PageRequest pageRequest) {
        List<HpfmLovViewHeader> devViewHeaderList = hpfmLovViewHeaderMapper.devPageByLovValue(lovViewHeader);
        List<HpfmLovViewHeader> prodViewHeaderList = hpfmLovViewHeaderMapper.prodPageByLovValue(lovViewHeader);

        LinkedHashMap<String, List<HpfmLovViewHeader>> devViewHeaderMap = devViewHeaderList.stream()
                .collect(Collectors.groupingBy(lovValue -> lovValue.getViewTenantId() + "" + lovValue.getViewCode(), LinkedHashMap::new, Collectors.toList()));

        LinkedHashMap<String, List<HpfmLovViewHeader>> prodViewHeaderMap = prodViewHeaderList.stream()
                .collect(Collectors.groupingBy(lovValue -> lovValue.getViewTenantId() + "" + lovValue.getViewCode(), LinkedHashMap::new, Collectors.toList()));

        List<HpfmLovViewHeader> resultLovList = new ArrayList<>();
        devViewHeaderMap.forEach((key, value) -> {
            HpfmLovViewHeader devViewHeader = value.get(0);
            devViewHeader.setDevViewHeaderId(devViewHeader.getViewHeaderId());
            if (prodViewHeaderMap.get(key) != null) {
                devViewHeader.setAddModify("不一致");
                devViewHeader.setDifference("对比");
                devViewHeader.setAddModifyColor("volcano");
                HpfmLovViewHeader prodLovViewHeader = prodViewHeaderMap.get(key).get(0);
                devViewHeader.setProdViewHeaderId(prodLovViewHeader.getViewHeaderId());
                //对比值集视图
                Boolean flag = contrastValue(devViewHeader, prodLovViewHeader);
                if (!flag) {
                    devViewHeader.setProdLovViewList(prodLovViewHeader.getDevLovViewList());
                    resultLovList.add(devViewHeader);
                }
            } else {
                devViewHeader.setAddModify("缺失");
                devViewHeader.setAddModifyColor("blue");
                resultLovList.add(devViewHeader);
            }
        });
        return resultLovList;
    }

    private Boolean contrastValue(HpfmLovViewHeader devViewHeader,
                                  HpfmLovViewHeader prodViewHeader) {
        AtomicReference<Boolean> flag = new AtomicReference<>(Boolean.TRUE);
        if (!StringUtils.equals(devViewHeader.getDisplayField(), prodViewHeader.getDisplayField())
                || !StringUtils.equals(devViewHeader.getValueField(), prodViewHeader.getValueField())
                || ObjectUtils.notEqual(devViewHeader.getEnabledFlag(), prodViewHeader.getEnabledFlag())
                || !StringUtils.equals(devViewHeader.getLovCode(), prodViewHeader.getLovCode())
                || ObjectUtils.notEqual(devViewHeader.getViewTenantId(), prodViewHeader.getViewTenantId())
        ) {
            return false;
        }

        return flag.get();
    }

    @Override
    public void dataApproval(List<HpfmLovViewHeader> lovValues) {

    }

    @Override
    public List<HpfmLovViewLine> dataDifferencesDev(HpfmLovViewLine lovValue) {
        List<HpfmLovViewLine> devDataDifferencesDev = hpfmLovViewHeaderMapper.devDataDifferences(lovValue);
        return devDataDifferencesDev;
    }

    @Override
    public List<HpfmLovViewLine> dataDifferencesProd(HpfmLovViewLine lovValue) {
        List<HpfmLovViewLine> prodDataDifferences = hpfmLovViewHeaderMapper.prodDataDifferences(lovValue);
        return prodDataDifferences;
    }
}

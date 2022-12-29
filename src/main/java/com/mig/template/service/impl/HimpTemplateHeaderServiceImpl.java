package com.mig.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.model.PageRequest;
import com.mig.template.entity.HimpTemplateHeader;
import com.mig.template.entity.HimpTemplateLine;
import com.mig.template.mapper.HimpTemplateHeaderMapper;
import com.mig.template.service.HimpTemplateHeaderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 分片上传标识 服务实现类
 * </p>
 *
 * @author DongFengWen
 * @since 2022-12-05
 */
@Service
public class HimpTemplateHeaderServiceImpl extends ServiceImpl<HimpTemplateHeaderMapper, HimpTemplateHeader> implements HimpTemplateHeaderService {

    @Autowired
    private HimpTemplateHeaderMapper himpTemplateHeaderMapper;

    @Override
    public List<HimpTemplateHeader> pageByTemplateHeader(HimpTemplateHeader templateHeader, PageRequest pageRequest) {

        List<HimpTemplateHeader> pageByTemplateHeaderDev = himpTemplateHeaderMapper.pageByTemplateHeaderDev(templateHeader);
        List<HimpTemplateHeader> pageByTemplateHeaderProd = himpTemplateHeaderMapper.pageByTemplateHeaderProd(templateHeader);

        LinkedHashMap<String, List<HimpTemplateHeader>> templateHeaderDevMap = pageByTemplateHeaderDev.stream()
                .collect(Collectors.groupingBy(header -> header.getTenantId() + "" + header.getTemplateCode(), LinkedHashMap::new, Collectors.toList()));

        LinkedHashMap<String, List<HimpTemplateHeader>> templateHeaderProdMap = pageByTemplateHeaderProd.stream()
                .collect(Collectors.groupingBy(header -> header.getTenantId() + "" + header.getTemplateCode(), LinkedHashMap::new, Collectors.toList()));

        List<HimpTemplateHeader> result = new ArrayList<>();
        templateHeaderDevMap.forEach((key, value) -> {
            HimpTemplateHeader devTemplateHeader = value.get(0);
            devTemplateHeader.setDevId(devTemplateHeader.getId());
            if (templateHeaderProdMap.get(key) != null) {
                devTemplateHeader.setAddModify("不一致");
                devTemplateHeader.setDifference("对比");
                devTemplateHeader.setAddModifyColor("volcano");
                HimpTemplateHeader prodTemplateHeader = templateHeaderProdMap.get(key).get(0);
                devTemplateHeader.setProdId(prodTemplateHeader.getId());
                //对比数据
                Boolean flag = contrastValue(devTemplateHeader, prodTemplateHeader);
                if (!flag) {
                    result.add(devTemplateHeader);
                }
            } else {
                devTemplateHeader.setAddModify("缺失");
                devTemplateHeader.setAddModifyColor("blue");
                result.add(devTemplateHeader);
            }
        });
        return result;
    }

    private Boolean contrastValue(HimpTemplateHeader devTemplateHeader, HimpTemplateHeader prodTemplateHeader) {
        AtomicReference<Boolean> flag = new AtomicReference<>(Boolean.TRUE);
        List<HimpTemplateLine> devTemplateLineList = devTemplateHeader.getDevTemplateLineList();
        List<HimpTemplateLine> prodTemplateLineList = prodTemplateHeader.getDevTemplateLineList();

        if (CollectionUtils.isEmpty(prodTemplateLineList) || CollectionUtils.isEmpty(devTemplateLineList)) {
            return false;
        }
        LinkedHashMap<String, List<HimpTemplateLine>> devTemplateLineMap = devTemplateLineList.stream()
                .collect(Collectors.groupingBy(templateLine -> templateLine.getColumnCode() + templateLine.getTenantId(), LinkedHashMap::new, Collectors.toList()));

        LinkedHashMap<String, List<HimpTemplateLine>> prodTemplateLineMap = prodTemplateLineList.stream()
                .collect(Collectors.groupingBy(templateLine -> templateLine.getColumnCode() + templateLine.getTenantId(), LinkedHashMap::new, Collectors.toList()));

        devTemplateLineMap.forEach((key, value) -> {
            if (prodTemplateLineMap.get(key) == null) {
                flag.set(Boolean.FALSE);
            }
        });
        return flag.get();
    }
}

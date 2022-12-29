package com.mig.lov.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.model.PageRequest;
import com.mig.lov.entity.HpfmLov;
import com.mig.lov.entity.HpfmLovValue;
import com.mig.lov.mapper.HpfmLovMapper;
import com.mig.lov.service.HpfmLovService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 存储解密字段 服务实现类
 * </p>
 *
 * @author DongFengWen
 * @since 2022-11-21
 */
@Service
@Slf4j
public class HpfmLovServiceImpl extends ServiceImpl<HpfmLovMapper, HpfmLov> implements HpfmLovService {

    @Autowired
    private HpfmLovMapper hpfmLovMapper;

    @Override
    public List<HpfmLov> pageByLovValue(HpfmLov hpfmLov, PageRequest pageRequest) {
        //查询DEV全部数据
        List<HpfmLov> devLovList = hpfmLovMapper.selectDevPageVo(hpfmLov);
        //查询PROD全部数据
        List<HpfmLov> prodLovList = hpfmLovMapper.selectProdPageVo(hpfmLov);

        LinkedHashMap<String, List<HpfmLov>> devLovByIdMap = devLovList.stream()
                .collect(Collectors.groupingBy(lovValue -> lovValue.getTenantId() + "" + lovValue.getLovCode(), LinkedHashMap::new, Collectors.toList()));

        LinkedHashMap<String, List<HpfmLov>> prodLovByIdMap = prodLovList.stream()
                .collect(Collectors.groupingBy(lovValue -> lovValue.getTenantId() + "" + lovValue.getLovCode(), LinkedHashMap::new, Collectors.toList()));

        List<HpfmLov> resultLovList = new ArrayList<>();
        devLovByIdMap.forEach((key, value) -> {
            HpfmLov devLovByMap = value.get(0);
            devLovByMap.setDevLovId(devLovByMap.getLovId());
            if (prodLovByIdMap.get(key) != null) {
                devLovByMap.setAddModify("不一致");
                devLovByMap.setDifference("对比");
                devLovByMap.setAddModifyColor("volcano");
                HpfmLov prodLovByMap = prodLovByIdMap.get(key).get(0);
                devLovByMap.setProdLovId(prodLovByMap.getLovId());
                //对比值集
                Boolean flag = contrastValue(devLovByMap, prodLovByMap);
                if (!flag) {
                    devLovByMap.setProdLovValueList(prodLovByMap.getDevLovValueList());
                    resultLovList.add(devLovByMap);
                }
            } else {
                devLovByMap.setAddModify("缺失");
                devLovByMap.setAddModifyColor("blue");
                resultLovList.add(devLovByMap);
            }
        });
        //iIPage.setRecords(resultLovList);
        // Page<HpfmLov> resultPage = PageUtil.doPage(resultLovList, pageRequest);
        return resultLovList;
    }

    @Override
    @Transactional
    public void dataApproval(List<HpfmLov> lovValues) {

        log.info(JSON.toJSONString(lovValues));
    }

    @Override
    public List<HpfmLovValue> dataDifferencesDev(HpfmLovValue lovValue) {
        List<HpfmLovValue> devLovValue = hpfmLovMapper.dataDifferencesDev(lovValue);
        return devLovValue;
    }

    @Override
    public List<HpfmLovValue> dataDifferencesProd(HpfmLovValue lovValue) {
        List<HpfmLovValue> prodLovValue = hpfmLovMapper.dataDifferencesProd(lovValue);
        return prodLovValue;
    }

    private Boolean contrastValue(HpfmLov devLovByMap, HpfmLov prodLovByMap) {
        AtomicReference<Boolean> flag = new AtomicReference<>(Boolean.TRUE);
        if (devLovByMap.getLovTypeCode().equals("IDP")) {
            List<HpfmLovValue> devLovValueList = devLovByMap.getDevLovValueList();
            List<HpfmLovValue> prodLovValueList = prodLovByMap.getDevLovValueList();
            LinkedHashMap<String, List<HpfmLovValue>> devLovByIdMap = devLovValueList.stream()
                    .collect(Collectors.groupingBy(lovValue -> lovValue.getValue() + lovValue.getLovCode(), LinkedHashMap::new, Collectors.toList()));
            if (CollectionUtils.isEmpty(prodLovValueList)) {
                return false;
            }
            LinkedHashMap<String, List<HpfmLovValue>> prodLovByIdMap = prodLovValueList.stream()
                    .collect(Collectors.groupingBy(lovValue -> lovValue.getValue() + lovValue.getLovCode(), LinkedHashMap::new, Collectors.toList()));

            devLovByIdMap.forEach((key, value) -> {
                if (prodLovByIdMap.get(key) == null) {
                    flag.set(Boolean.FALSE);
                }
            });
        } else if (devLovByMap.getLovTypeCode().equals("URL")) {

            // //URL
            if (!StringUtils.equals(devLovByMap.getCustomUrl(), prodLovByMap.getCustomUrl())
                    || ObjectUtils.notEqual(devLovByMap.getEnabledFlag(), prodLovByMap.getEnabledFlag())
                    || !comparisonStrBoolean(devLovByMap.getTranslationSql(), prodLovByMap.getTranslationSql())
                    || !StringUtils.equals(devLovByMap.getValueField(), prodLovByMap.getValueField())
                    || !StringUtils.equals(devLovByMap.getDisplayField(), prodLovByMap.getDisplayField())
            ) {
                changeContrast(devLovByMap, prodLovByMap);
                //对比几个值
                return false;
            }
        } else if (devLovByMap.getLovTypeCode().equals("SQL")) {
            //自定义SQL
            if (!comparisonStrBoolean(devLovByMap.getCustomSql(), prodLovByMap.getCustomSql())
                    || !comparisonStrBoolean(devLovByMap.getTranslationSql(), prodLovByMap.getTranslationSql())
                    || ObjectUtils.notEqual(devLovByMap.getEnabledFlag(), prodLovByMap.getEnabledFlag())
                    || !StringUtils.equals(devLovByMap.getValueField(), prodLovByMap.getValueField())
                    || !StringUtils.equals(devLovByMap.getDisplayField(), prodLovByMap.getDisplayField())
            ) {
                changeContrast(devLovByMap, prodLovByMap);
                //对比几个值
                return false;
            }
        }
        return flag.get();
    }

    private void changeContrast(HpfmLov devLovByMap, HpfmLov prodLovByMap) {
        devLovByMap.setDevLovCode(devLovByMap.getLovCode());
        devLovByMap.setProdLovCode(prodLovByMap.getLovCode());
        devLovByMap.setDevLovName(devLovByMap.getLovName());
        devLovByMap.setProdLovName(prodLovByMap.getLovName());
        devLovByMap.setDevLovTypeName(devLovByMap.getLovTypeName());
        devLovByMap.setProdLovTypeName(prodLovByMap.getLovTypeName());
        devLovByMap.setDevLovValueField(devLovByMap.getValueField());
        devLovByMap.setProdLovValueField(prodLovByMap.getValueField());
        devLovByMap.setDevDisplayField(devLovByMap.getDisplayField());
        devLovByMap.setProdDisplayField(prodLovByMap.getDisplayField());
        devLovByMap.setDevRouteName(devLovByMap.getRouteName());
        devLovByMap.setProdRouteName(prodLovByMap.getRouteName());
        devLovByMap.setDevEncryptField(devLovByMap.getEncryptField());
        devLovByMap.setProdEncryptField(prodLovByMap.getEncryptField());
        devLovByMap.setDevCustomUrl(devLovByMap.getCustomUrl());
        devLovByMap.setProdCustomUrl(prodLovByMap.getCustomUrl());
        devLovByMap.setDevCustomSql(devLovByMap.getCustomSql());
        devLovByMap.setProdCustomSql(prodLovByMap.getCustomSql());
        devLovByMap.setDevTranslationSql(devLovByMap.getTranslationSql());
        devLovByMap.setProdTranslationSql(prodLovByMap.getTranslationSql());
        devLovByMap.setDevEnabledFlag(devLovByMap.getEnabledFlag());
        devLovByMap.setProdEnabledFlag(prodLovByMap.getEnabledFlag());

    }


    private Boolean comparisonStrBoolean(String comparisonA, String comparisonB) {
        if (ObjectUtils.isNotEmpty(comparisonA) && ObjectUtils.isNotEmpty(comparisonB)) {
            boolean equals = comparisonA.replaceAll(" ", "")
                    .replaceAll("\n", "")
                    .replaceAll("\t", "")
                    .equals(comparisonB.replaceAll(" ", "")
                            .replaceAll("\n", "")
                            .replaceAll("\t", ""));
            return equals;
        } else {
            return StringUtils.equals(comparisonA, comparisonB);
        }
    }
}

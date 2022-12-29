package com.common.utils;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.model.PageRequest;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;


public class PageUtil {
    /**
     * 只分页
     *
     * @param list        待分页数据
     * @param pageRequest 分页参数
     * @param <T>         数据类型
     * @return 分页数据
     */
    public static <T> Page<T> doPage(final List<T> list, final PageRequest pageRequest) {
        if (CollectionUtils.isEmpty(list) || list.size() == 0) {
            return new Page<>(0, list.size());
        }
        if (pageRequest.getPage() >= 0) {
            Page<T> objectPage = new Page<>(pageRequest.getPage(), pageRequest.getSize());
            objectPage.setTotal(list.size());
            objectPage.setRecords(getPageContent(pageRequest, list));
            return objectPage;
        } else {
            Page<T> objectPage = new Page<>(pageRequest.getPage(), pageRequest.getSize());
            objectPage.setTotal(list.size());
            return objectPage;
        }
    }

    /**
     * 获取页面体
     *
     * @param pageRequest 分页参数
     * @param list        数据
     * @param <T>         数据类型
     * @return List<T>
     */
    private static <T> List<T> getPageContent(final PageRequest pageRequest, final List<T> list) {
        final int page = pageRequest.getPage();
        final int pageSize = pageRequest.getSize();
        final int start = page * pageSize;
        int end = start + pageSize;
        if (start >= list.size()) {
            return Collections.emptyList();
        }
        if (end >= list.size()) {
            end = list.size();
        }
        return list.subList(start, end);
    }

}

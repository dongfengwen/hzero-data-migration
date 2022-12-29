package com.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2022-11-21 21:00
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest implements Serializable {

    private Integer size;

    private Integer page;

    public Integer getSize() {
        return ObjectUtils.isEmpty(size) ? 10 : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return ObjectUtils.isEmpty(page) ? 0 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

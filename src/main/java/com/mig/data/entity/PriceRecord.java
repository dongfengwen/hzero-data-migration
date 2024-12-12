package com.mig.data.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: dongbx@dianzhong.com
 * @createDate: 2024-12-10 20:47
 */
@Data
public class PriceRecord implements Serializable {
    double price;
    long timestamp;

    public PriceRecord(double price, long timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }
}
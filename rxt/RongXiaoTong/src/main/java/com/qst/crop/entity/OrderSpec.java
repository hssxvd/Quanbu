package com.qst.crop.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSpec {

    private Integer specId;

    private Integer orderId;

    private String specName;

    private BigDecimal specPrice;

    private Integer specStock;

    private Integer specSales;

    private Integer specSort;

    private Date createTime;

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public BigDecimal getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(BigDecimal specPrice) {
        this.specPrice = specPrice;
    }

    public Integer getSpecStock() {
        return specStock;
    }

    public void setSpecStock(Integer specStock) {
        this.specStock = specStock;
    }

    public Integer getSpecSales() {
        return specSales;
    }

    public void setSpecSales(Integer specSales) {
        this.specSales = specSales;
    }

    public Integer getSpecSort() {
        return specSort;
    }

    public void setSpecSort(Integer specSort) {
        this.specSort = specSort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
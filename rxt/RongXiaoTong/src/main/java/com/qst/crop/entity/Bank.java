package com.qst.crop.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Bank {
    private Integer bankId;
    private String bankName;
    private String introduce;
    private String bankPhone;
    private BigDecimal money;
    private BigDecimal rate;
    private Integer repayment;
}
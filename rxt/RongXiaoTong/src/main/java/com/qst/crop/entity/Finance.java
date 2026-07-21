package com.qst.crop.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Finance {
    private Integer financeId;
    private Integer bankId;
    private String ownName;
    private String realName;
    private String phone;
    private String idNum;
    private Integer status;
    @NotNull(message = "金额不能为空")
    private BigDecimal money;
    @NotNull(message = "利率不能为空")
    private BigDecimal rate;
    @NotNull(message = "还款周期不能为空")
    private Integer repayment;
    private Date createTime;
    private Date updateTime;
    private String combinationName1;
    private String combinationPhone1;
    private String combinationIdnum1;
    private String combinationName2;
    private String combinationPhone2;
    private String combinationIdnum2;
}
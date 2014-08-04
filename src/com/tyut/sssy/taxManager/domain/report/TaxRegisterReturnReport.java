package com.tyut.sssy.taxManager.domain.report;

import com.tyut.sssy.taxManager.domain.identity.TaxPayerRegisterStatus;

import java.math.BigDecimal;

/**
 * 项目名称：sssyrisk
 * 类名称：税收征管税务登记查询结果类
 * 类描述：税收征管 税务登记查询 结果返回表
 * 创建人：刘翔
 * 创建时间：2014-4-20
 */
public class TaxRegisterReturnReport {

    private String registerType; // 类别
    private int a1;// 基期户次
    private int a2; //分析期户次
    private int a3 ;// 增减户次
    private BigDecimal a4 ; //增减幅度

    private TaxPayerRegisterStatus registerStatus;

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    public int getA3() {
        return a3;
    }

    public void setA3(int a3) {
        this.a3 = a3;
    }

    public BigDecimal getA4() {
        return a4;
    }

    public void setA4(BigDecimal a4) {
        this.a4 = a4;
    }

    public TaxPayerRegisterStatus getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(TaxPayerRegisterStatus registerStatus) {
        this.registerStatus = registerStatus;
    }
}

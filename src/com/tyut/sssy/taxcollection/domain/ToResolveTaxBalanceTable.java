package com.tyut.sssy.taxcollection.domain;

import java.math.BigDecimal;

/**
 * 项目名称：sssy20120506
 * 类名称：TaxCollectionFinishTable
 * 类描述：待解税金余额表
 * 创建人：梁斌
 * 创建时间：2012-5-9 下午03:08:46
 * 修改人：梁斌
 * 修改时间：2012-5-9 下午03:08:46
 * 修改备注：
 */
public class ToResolveTaxBalanceTable {

    private String xm;    // 项目列

    private String xsxm;    // 显示项目

    private BigDecimal a1;

    private BigDecimal a2;

    private BigDecimal a3;

    private BigDecimal a4;

    private BigDecimal a5;

    private BigDecimal a6;

    private BigDecimal a7;

    private BigDecimal a8;

    private BigDecimal a9;

    private BigDecimal a10;

    private BigDecimal a11;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public BigDecimal getA1() {
        if (a1 == null)
            return new BigDecimal(0);
        else
            return a1;
    }

    public void setA1(BigDecimal a1) {
        this.a1 = a1;
    }

    public BigDecimal getA2() {
        if (a2 == null)
            return new BigDecimal(0);
        else
            return a2;
    }

    public void setA2(BigDecimal a2) {
        this.a2 = a2;
    }

    public BigDecimal getA3() {
        if (a3 == null)
            return new BigDecimal(0);
        else
            return a3;
    }

    public void setA3(BigDecimal a3) {
        this.a3 = a3;
    }

    public BigDecimal getA4() {
        if (a4 == null)
            return new BigDecimal(0);
        else
            return a4;
    }

    public void setA4(BigDecimal a4) {
        this.a4 = a4;
    }

    public BigDecimal getA5() {
        if (a5 == null)
            return new BigDecimal(0);
        else
            return a5;
    }

    public void setA5(BigDecimal a5) {
        this.a5 = a5;
    }

    public BigDecimal getA6() {
        if (a6 == null)
            return new BigDecimal(0);
        else
            return a6;
    }

    public void setA6(BigDecimal a6) {
        this.a6 = a6;
    }

    public BigDecimal getA7() {
        if (a7 == null)
            return new BigDecimal(0);
        else
            return a7;
    }

    public void setA7(BigDecimal a7) {
        this.a7 = a7;
    }

    public BigDecimal getA8() {
        if (a8 == null)
            return new BigDecimal(0);
        else
            return a8;
    }

    public void setA8(BigDecimal a8) {
        this.a8 = a8;
    }

    public BigDecimal getA9() {
        if (a9 == null)
            return new BigDecimal(0);
        else
            return a9;
    }

    public void setA9(BigDecimal a9) {
        this.a9 = a9;
    }

    public BigDecimal getA10() {
        if (a10 == null)
            return new BigDecimal(0);
        else
            return a10;
    }

    public void setA10(BigDecimal a10) {
        this.a10 = a10;
    }

    public BigDecimal getA11() {
        if (a11 == null)
            return new BigDecimal(0);
        else
            return a11;
    }

    public void setA11(BigDecimal a11) {
        this.a11 = a11;
    }

    public String getXsxm() {
        return xsxm;
    }

    public void setXsxm(String xsxm) {
        this.xsxm = xsxm;
    }
}

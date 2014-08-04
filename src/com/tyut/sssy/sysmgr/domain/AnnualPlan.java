package com.tyut.sssy.sysmgr.domain;

import com.tyut.sssy.base.domain.TaxUnit;

import java.math.BigDecimal;

public class AnnualPlan {

    private int id ; // 主键

    private String nd ; //年度

    private String fl ; //分类

    private String flMx; // 分类明细

    private BigDecimal zjh ; //总计划

    private BigDecimal ndjh ; //年度计划

    private String swjgDm ; //税务机关代码

    private TaxUnit taxUnit ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFlMx() {
        return flMx;
    }

    public void setFlMx(String flMx) {
        this.flMx = flMx;
    }

    public BigDecimal getNdjh() {
        return ndjh;
    }

    public void setNdjh(BigDecimal ndjh) {
        this.ndjh = ndjh;
    }

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }

    public BigDecimal getZjh() {
        return zjh;
    }

    public void setZjh(BigDecimal zjh) {
        this.zjh = zjh;
    }

    public TaxUnit getTaxUnit() {
        return taxUnit;
    }

    public void setTaxUnit(TaxUnit taxUnit) {
        this.taxUnit = taxUnit;
    }
}

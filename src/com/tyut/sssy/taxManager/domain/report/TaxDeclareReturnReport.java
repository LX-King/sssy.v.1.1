package com.tyut.sssy.taxManager.domain.report;

import java.math.BigDecimal;

/**
 * 项目名称：SSSY
 * 类名称：${CLASS}
 * 创建人：刘翔
 * 创建时间：2014-07-02
 *
 * @version 1.1
 */
public class TaxDeclareReturnReport {

    private int ysbhs;//应申报户次
    private BigDecimal sbl;//当月申报率
    private int zqsbhs;//准期申报户次
    private BigDecimal zqsbl;//准期申报率
    private int yqsbhs;//逾期申报户数
    private BigDecimal yqsbl;//逾期申报率
    private int lsbhs;//零申报户次
    private BigDecimal lsbl;//零申报率
    private int lxsbhs;//连续三个月申报户次
    private BigDecimal lxsbl;
    private int wsbhs;
    private BigDecimal wsbl;

    public int getYsbhs() {
        return ysbhs;
    }

    public void setYsbhs(int ysbhs) {
        this.ysbhs = ysbhs;
    }

    public BigDecimal getSbl() {
        return sbl;
    }

    public void setSbl(BigDecimal sbl) {
        this.sbl = sbl;
    }

    public int getZqsbhs() {
        return zqsbhs;
    }

    public void setZqsbhs(int zqsbhs) {
        this.zqsbhs = zqsbhs;
    }

    public BigDecimal getZqsbl() {
        return zqsbl;
    }

    public void setZqsbl(BigDecimal zqsbl) {
        this.zqsbl = zqsbl;
    }

    public int getYqsbhs() {
        return yqsbhs;
    }

    public void setYqsbhs(int yqsbhs) {
        this.yqsbhs = yqsbhs;
    }

    public BigDecimal getYqsbl() {
        return yqsbl;
    }

    public void setYqsbl(BigDecimal yqsbl) {
        this.yqsbl = yqsbl;
    }

    public int getLsbhs() {
        return lsbhs;
    }

    public void setLsbhs(int lsbhs) {
        this.lsbhs = lsbhs;
    }

    public BigDecimal getLsbl() {
        return lsbl;
    }

    public void setLsbl(BigDecimal lsbl) {
        this.lsbl = lsbl;
    }

    public int getLxsbhs() {
        return lxsbhs;
    }

    public void setLxsbhs(int lxsbhs) {
        this.lxsbhs = lxsbhs;
    }

    public BigDecimal getLxsbl() {
        return lxsbl;
    }

    public void setLxsbl(BigDecimal lxsbl) {
        this.lxsbl = lxsbl;
    }

    public int getWsbhs() {
        return wsbhs;
    }

    public void setWsbhs(int wsbhs) {
        this.wsbhs = wsbhs;
    }

    public BigDecimal getWsbl() {
        return wsbl;
    }

    public void setWsbl(BigDecimal wsbl) {
        this.wsbl = wsbl;
    }
}

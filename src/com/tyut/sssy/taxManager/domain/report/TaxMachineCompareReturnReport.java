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
public class TaxMachineCompareReturnReport {


    private int yyshs;//营业税户数
    private int lph;//领票户
    private BigDecimal lpzb;//领票占比
    private int jdp;//机打票
    private BigDecimal jdpzb;//机打票占比
    private int wgp;//未购票户数
    private BigDecimal wgpzb;//未购票占比

    public int getYyshs() {
        return yyshs;
    }

    public void setYyshs(int yyshs) {
        this.yyshs = yyshs;
    }

    public int getLph() {
        return lph;
    }

    public void setLph(int lph) {
        this.lph = lph;
    }

    public BigDecimal getLpzb() {
        return lpzb;
    }

    public void setLpzb(BigDecimal lpzb) {
        this.lpzb = lpzb;
    }

    public int getJdp() {
        return jdp;
    }

    public void setJdp(int jdp) {
        this.jdp = jdp;
    }

    public BigDecimal getJdpzb() {
        return jdpzb;
    }

    public void setJdpzb(BigDecimal jdpzb) {
        this.jdpzb = jdpzb;
    }

    public int getWgp() {
        return wgp;
    }

    public void setWgp(int wgp) {
        this.wgp = wgp;
    }

    public BigDecimal getWgpzb() {
        return wgpzb;
    }

    public void setWgpzb(BigDecimal wgpzb) {
        this.wgpzb = wgpzb;
    }
}

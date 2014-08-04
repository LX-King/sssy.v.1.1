package com.tyut.sssy.taxManager.domain.report;

import java.math.BigDecimal;

/**
 * 项目名称：SSSY
 * 类名称：${CLASS}
 * 创建人：刘翔
 * 创建时间：2014-06-08
 *
 * @version 1.1
 */
public class TipsReturnReport {

    private BigDecimal ynsje;//应纳税金额
    private BigDecimal rkse;
    private BigDecimal rkl;
    private int zhs;
    private int kths;
    private BigDecimal ktl;

    public BigDecimal getYnsje() {
        return ynsje;
    }

    public void setYnsje(BigDecimal ynsje) {
        this.ynsje = ynsje;
    }

    public BigDecimal getRkse() {
        return rkse;
    }

    public void setRkse(BigDecimal rkse) {
        this.rkse = rkse;
    }

    public BigDecimal getRkl() {
        return rkl;
    }

    public void setRkl(BigDecimal rkl) {
        this.rkl = rkl;
    }

    public int getZhs() {
        return zhs;
    }

    public void setZhs(int zhs) {
        this.zhs = zhs;
    }

    public int getKths() {
        return kths;
    }

    public void setKths(int kths) {
        this.kths = kths;
    }

    public BigDecimal getKtl() {
        return ktl;
    }

    public void setKtl(BigDecimal ktl) {
        this.ktl = ktl;
    }
}

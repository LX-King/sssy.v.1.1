package com.tyut.sssy.taxManager.domain.report;

import java.math.BigDecimal;

/**
 * 项目名称：SSSY
 * 类名称：网报查询返回报表
 * 创建人：刘翔
 * 创建时间：2014-07-03
 *
 * @version 1.1
 */
public class TaxNetDeclareReturnReport {

    private int zgh;//总管户
    private int ybh;//应报户
    private int dqwbh;//当期网报户
    private BigDecimal ktl;//开通率
    private BigDecimal syl;//使用率
    private BigDecimal sbse;//申报税额
    private BigDecimal yjse;//已缴税额

    public int getZgh() {
        return zgh;
    }

    public void setZgh(int zgh) {
        this.zgh = zgh;
    }

    public int getYbh() {
        return ybh;
    }

    public void setYbh(int ybh) {
        this.ybh = ybh;
    }

    public int getDqwbh() {
        return dqwbh;
    }

    public void setDqwbh(int dqwbh) {
        this.dqwbh = dqwbh;
    }

    public BigDecimal getKtl() {
        return ktl;
    }

    public void setKtl(BigDecimal ktl) {
        this.ktl = ktl;
    }

    public BigDecimal getSyl() {
        return syl;
    }

    public void setSyl(BigDecimal syl) {
        this.syl = syl;
    }

    public BigDecimal getSbse() {
        return sbse;
    }

    public void setSbse(BigDecimal sbse) {
        this.sbse = sbse;
    }

    public BigDecimal getYjse() {
        return yjse;
    }

    public void setYjse(BigDecimal yjse) {
        this.yjse = yjse;
    }
}

package com.tyut.sssy.taxManager.domain.identity;

/**
 * 项目名称：SSSY
 * 类名称：发票种类
 * 创建人：刘翔
 * 创建时间：2014-06-26
 *
 * @version 1.1
 */
public class TaxCategory {

    private String fpzlDm;
    private String mc;
    private String yxbz;

    public String getFpzlDm() {
        return fpzlDm;
    }

    public void setFpzlDm(String fpzlDm) {
        this.fpzlDm = fpzlDm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }
}

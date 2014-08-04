package com.tyut.sssy.taxManager.domain.identity;

/**
 * 项目名称：SSSY
 * 类名称：发票发售人员
 * 创建人：刘翔
 * 创建时间：2014-05-27
 *
 * @version 1.1
 */
public class InvoiceSaler {

    private String fpfsryDm ;//发票发售人员代码

    private String mc;

    private String yxbz;//?

    public String getFpfsryDm() {
        return fpfsryDm;
    }

    public void setFpfsryDm(String fpfsryDm) {
        this.fpfsryDm = fpfsryDm;
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

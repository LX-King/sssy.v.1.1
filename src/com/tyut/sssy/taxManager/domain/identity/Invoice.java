package com.tyut.sssy.taxManager.domain.identity;

/**
 * 项目名称：SSSY
 * 类名称：发票实体
 * 创建人：刘翔
 * 创建时间：2014-05-27
 *
 * @version 1.1
 */
public class Invoice {

    private  String fpDm;//发票代码

    private  String mc;//名称

    private int je;//金额

    public String getFpDm() {
        return fpDm;
    }

    public void setFpDm(String fpDm) {
        this.fpDm = fpDm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public int getJe() {
        return je;
    }

    public void setJe(int je) {
        this.je = je;
    }
}

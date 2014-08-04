package com.tyut.sssy.taxManager.domain.parameters;

/**
 * 项目名称：sssyrisk
 * 类名称：税收征管税务登记查询参数类
 * 类描述：税收征管 税务登记查询 查询参数表
 * 创建人：刘翔
 * 创建时间：2014.4.20
 */
public class TaxRegisterQueryParameters {

    private String fxq; //分析期

    private String czq; //参照期

    private String zclx;// 注册类型

    private String hylb;// 行业类别

    private String hsjg; // 核算机关

    private String gljg; // 管理机关

    private String zgy; // 征管员 ||管理员

    private String hsxs;//核算形式

    private String zdsy; //重点税源标志

    private String qyzclx ;//企业注册类型

    public String getFxq() {
        return fxq;
    }

    public void setFxq(String fxq) {
        this.fxq = fxq;
    }

    public String getCzq() {
        return czq;
    }

    public void setCzq(String czq) {
        this.czq = czq;
    }

    public String getZclx() {
        return zclx;
    }

    public void setZclx(String zclx) {
        this.zclx = zclx;
    }

    public String getHylb() {
        return hylb;
    }

    public void setHylb(String hylb) {
        this.hylb = hylb;
    }

    public String getHsjg() {
        return hsjg;
    }

    public void setHsjg(String hsjg) {
        this.hsjg = hsjg;
    }

    public String getGljg() {
        return gljg;
    }

    public void setGljg(String gljg) {
        this.gljg = gljg;
    }

    public String getZgy() {
        return zgy;
    }

    public void setZgy(String zgy) {
        this.zgy = zgy;
    }

    public String getHsxs() {
        return hsxs;
    }

    public void setHsxs(String hsxs) {
        this.hsxs = hsxs;
    }

    public String getZdsy() {
        return zdsy;
    }

    public void setZdsy(String zdsy) {
        this.zdsy = zdsy;
    }

    public String getQyzclx() {
        return qyzclx;
    }

    public void setQyzclx(String qyzclx) {
        this.qyzclx = qyzclx;
    }
}

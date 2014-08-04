package com.tyut.sssy.taxManager.domain.parameters;

/**
 * 项目名称：SSSY
 * 类名称：领购机打发票纳税人申报比对数据查询参数
 * 创建人：刘翔
 * 创建时间：2014-07-02
 *
 * @version 1.1
 */
public class TaxMachineCompareQueryParameters {

    private String sbrqq;//申报日期起
    private String sbrqz;//申报日期止
    private String cbrqq;//抄报日期起
    private String cbrqz;//抄报日期止
    private String zclx;//注册类型
    private String hydl;//行业大类
    private String hsjg;//核算机关
    private String swjg;//管理机关
    private String zgy;//征管员
    private String nsrsbh;//纳税人识别号


    public String getSbrqq() {
        return sbrqq;
    }

    public void setSbrqq(String sbrqq) {
        this.sbrqq = sbrqq;
    }

    public String getSbrqz() {
        return sbrqz;
    }

    public void setSbrqz(String sbrqz) {
        this.sbrqz = sbrqz;
    }

    public String getCbrqq() {
        return cbrqq;
    }

    public void setCbrqq(String cbrqq) {
        this.cbrqq = cbrqq;
    }

    public String getCbrqz() {
        return cbrqz;
    }

    public void setCbrqz(String cbrqz) {
        this.cbrqz = cbrqz;
    }

    public String getZclx() {
        return zclx;
    }

    public void setZclx(String zclx) {
        this.zclx = zclx;
    }

    public String getHydl() {
        return hydl;
    }

    public void setHydl(String hydl) {
        this.hydl = hydl;
    }

    public String getHsjg() {
        return hsjg;
    }

    public void setHsjg(String hsjg) {
        this.hsjg = hsjg;
    }

    public String getSwjg() {
        return swjg;
    }

    public void setSwjg(String swjg) {
        this.swjg = swjg;
    }

    public String getZgy() {
        return zgy;
    }

    public void setZgy(String zgy) {
        this.zgy = zgy;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }
}

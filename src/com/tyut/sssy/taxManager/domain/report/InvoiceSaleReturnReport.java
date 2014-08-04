package com.tyut.sssy.taxManager.domain.report;

import java.math.BigDecimal;

/**
 * 项目名称：SSSY
 * 类名称：${CLASS}
 * 创建人：刘翔
 * 创建时间：2014-06-04
 *
 * @version 1.1
 */

public class InvoiceSaleReturnReport {
    private String nsrsbh ;
    private String nsrmc;
    private String zclx;
    private String hydl;
    private String fpzl;
    private int yde;//月定额
    private BigDecimal ynyys;//应纳营业税
    private BigDecimal ynsds;//应纳所得税
    private BigDecimal gpje;//购票金额
    private String fsry;
    private String fsrq;//发售日期
    private String fphm;//
    private BigDecimal dgce;//定购差额
    private int qzd;//起征点
    private String xwqy;//小微企业
    private BigDecimal yysjsyj; //营业税计税依据
    private BigDecimal yjyys;//已缴营业税
    private BigDecimal yjsds;//已缴所得税
    private BigDecimal yysce;//营业税差额
    private BigDecimal sdsce;//所得税差额
    private String sbrq;//申报日期
    private String sbbzl;//申报表种类

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
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

    public String getFpzl() {
        return fpzl;
    }

    public void setFpzl(String fpzl) {
        this.fpzl = fpzl;
    }

    public int getYde() {
        return yde;
    }

    public void setYde(int yde) {
        this.yde = yde;
    }

    public BigDecimal getYnyys() {
        return ynyys;
    }

    public void setYnyys(BigDecimal ynyys) {
        this.ynyys = ynyys;
    }

    public BigDecimal getYnsds() {
        return ynsds;
    }

    public void setYnsds(BigDecimal ynsds) {
        this.ynsds = ynsds;
    }

    public BigDecimal getGpje() {
        return gpje;
    }

    public void setGpje(BigDecimal gpje) {
        this.gpje = gpje;
    }

    public String getFsry() {
        return fsry;
    }

    public void setFsry(String fsry) {
        this.fsry = fsry;
    }

    public int getQzd() {
        return qzd;
    }

    public void setQzd(int qzd) {
        this.qzd = qzd;
    }

    public String getXwqy() {
        return xwqy;
    }

    public void setXwqy(String xwqy) {
        this.xwqy = xwqy;
    }

    public BigDecimal getYysjsyj() {
        return yysjsyj;
    }

    public void setYysjsyj(BigDecimal yysjsyj) {
        this.yysjsyj = yysjsyj;
    }

    public BigDecimal getYjyys() {
        return yjyys;
    }

    public void setYjyys(BigDecimal yjyys) {
        this.yjyys = yjyys;
    }

    public BigDecimal getYjsds() {
        return yjsds;
    }

    public void setYjsds(BigDecimal yjsds) {
        this.yjsds = yjsds;
    }

    public BigDecimal getSdsce() {
        return sdsce;
    }

    public void setSdsce(BigDecimal sdsce) {
        this.sdsce = sdsce;
    }

    public String getSbrq() {
        return sbrq;
    }

    public void setSbrq(String sbrq) {
        this.sbrq = sbrq;
    }

    public String getSbbzl() {
        return sbbzl;
    }

    public void setSbbzl(String sbbzl) {
        this.sbbzl = sbbzl;
    }

    public String getFsrq() {
        return fsrq;
    }

    public void setFsrq(String fsrq) {
        this.fsrq = fsrq;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public BigDecimal getDgce() {
        return dgce;
    }

    public void setDgce(BigDecimal dgce) {
        this.dgce = dgce;
    }

    public BigDecimal getYysce() {
        return yysce;
    }

    public void setYysce(BigDecimal yysce) {
        this.yysce = yysce;
    }
}

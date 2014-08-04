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
public class TaxInvoiceConsignReturnReport {

    private String nsrmc;
    private String nsrsbh;
    private String hymc;
    private String jyxm;//经营项目
    private String kprq;
    private String fpmc;
    private String fphm;
    private String wspzhm;//完税凭证号码
    private BigDecimal dkfpje;//代开发票金额
    private String zspm;//征收项目
    private BigDecimal se;//税额
    private String zfbz;
    private String zfrq;
    private String zfyy;//作废原因
    private String spc;//审批人
    private int cs;//代开发票次数

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getHymc() {
        return hymc;
    }

    public void setHymc(String hymc) {
        this.hymc = hymc;
    }

    public String getJyxm() {
        return jyxm;
    }

    public void setJyxm(String jyxm) {
        this.jyxm = jyxm;
    }

    public String getKprq() {
        return kprq;
    }

    public void setKprq(String kprq) {
        this.kprq = kprq;
    }

    public String getFpmc() {
        return fpmc;
    }

    public void setFpmc(String fpmc) {
        this.fpmc = fpmc;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public String getWspzhm() {
        return wspzhm;
    }

    public void setWspzhm(String wspzhm) {
        this.wspzhm = wspzhm;
    }

    public BigDecimal getDkfpje() {
        return dkfpje;
    }

    public void setDkfpje(BigDecimal dkfpje) {
        this.dkfpje = dkfpje;
    }

    public String getZspm() {
        return zspm;
    }

    public void setZspm(String zspm) {
        this.zspm = zspm;
    }

    public BigDecimal getSe() {
        return se;
    }

    public void setSe(BigDecimal se) {
        this.se = se;
    }

    public String getZfbz() {
        return zfbz;
    }

    public void setZfbz(String zfbz) {
        this.zfbz = zfbz;
    }

    public String getZfrq() {
        return zfrq;
    }

    public void setZfrq(String zfrq) {
        this.zfrq = zfrq;
    }

    public String getZfyy() {
        return zfyy;
    }

    public void setZfyy(String zfyy) {
        this.zfyy = zfyy;
    }

    public String getSpc() {
        return spc;
    }

    public void setSpc(String spc) {
        this.spc = spc;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }
}

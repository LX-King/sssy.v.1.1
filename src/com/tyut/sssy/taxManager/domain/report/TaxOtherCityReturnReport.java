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
public class TaxOtherCityReturnReport {

    private String nsrmc;
    private String nsrsbh;
    private String jydz;//经营所在地
    private String jyxm;//经营项目
    private String zspm;//征收品目
    private String sbrq;//申报日期 datetime
    private BigDecimal htje;//合同金额
    private BigDecimal yhs;//印花税
    private BigDecimal yys;//营业税
    private BigDecimal sds;//所得税
    private BigDecimal cye;//差异额
    private String zcdgljg;//注册地管理机关
    private String wjzhm;// 外经证号码
    private String wjckjjg;// 外经证开具机关
    private String wjzkjr;//   外经证开具经办人
    private String   wjzkjrq ;//  外经证开具日期 datetime
    private String wjzyxq;// 外经证有效期止
    private String zfb;// 是否总分包
    private String bydjrq;//bydjrq datetime
    private String sxbz;//  外经证失效提示
    private String  gcxmbh;// 建筑业工程项目编号
    private String  gcxmmc;//  建筑业工程项目名称
    private BigDecimal jnsfhj;// 缴纳税费合计


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

    public String getJydz() {
        return jydz;
    }

    public void setJydz(String jydz) {
        this.jydz = jydz;
    }

    public String getJyxm() {
        return jyxm;
    }

    public void setJyxm(String jyxm) {
        this.jyxm = jyxm;
    }

    public String getZspm() {
        return zspm;
    }

    public void setZspm(String zspm) {
        this.zspm = zspm;
    }

    public String getSbrq() {
        return sbrq;
    }

    public void setSbrq(String sbrq) {
        this.sbrq = sbrq;
    }

    public BigDecimal getHtje() {
        return htje;
    }

    public void setHtje(BigDecimal htje) {
        this.htje = htje;
    }

    public BigDecimal getYhs() {
        return yhs;
    }

    public void setYhs(BigDecimal yhs) {
        this.yhs = yhs;
    }

    public BigDecimal getYys() {
        return yys;
    }

    public void setYys(BigDecimal yys) {
        this.yys = yys;
    }

    public BigDecimal getSds() {
        return sds;
    }

    public void setSds(BigDecimal sds) {
        this.sds = sds;
    }

    public BigDecimal getCye() {
        return cye;
    }

    public void setCye(BigDecimal cye) {
        this.cye = cye;
    }

    public String getZcdgljg() {
        return zcdgljg;
    }

    public void setZcdgljg(String zcdgljg) {
        this.zcdgljg = zcdgljg;
    }

    public String getWjzhm() {
        return wjzhm;
    }

    public void setWjzhm(String wjzhm) {
        this.wjzhm = wjzhm;
    }

    public String getWjckjjg() {
        return wjckjjg;
    }

    public void setWjckjjg(String wjckjjg) {
        this.wjckjjg = wjckjjg;
    }

    public String getWjzkjr() {
        return wjzkjr;
    }

    public void setWjzkjr(String wjzkjr) {
        this.wjzkjr = wjzkjr;
    }

    public String getWjzkjrq() {
        return wjzkjrq;
    }

    public void setWjzkjrq(String wjzkjrq) {
        this.wjzkjrq = wjzkjrq;
    }

    public String getWjzyxq() {
        return wjzyxq;
    }

    public void setWjzyxq(String wjzyxq) {
        this.wjzyxq = wjzyxq;
    }

    public String getZfb() {
        return zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    public String getBydjrq() {
        return bydjrq;
    }

    public void setBydjrq(String bydjrq) {
        this.bydjrq = bydjrq;
    }

    public String getSxbz() {
        return sxbz;
    }

    public void setSxbz(String sxbz) {
        this.sxbz = sxbz;
    }

    public String getGcxmbh() {
        return gcxmbh;
    }

    public void setGcxmbh(String gcxmbh) {
        this.gcxmbh = gcxmbh;
    }

    public String getGcxmmc() {
        return gcxmmc;
    }

    public void setGcxmmc(String gcxmmc) {
        this.gcxmmc = gcxmmc;
    }

    public BigDecimal getJnsfhj() {
        return jnsfhj;
    }

    public void setJnsfhj(BigDecimal jnsfhj) {
        this.jnsfhj = jnsfhj;
    }
}

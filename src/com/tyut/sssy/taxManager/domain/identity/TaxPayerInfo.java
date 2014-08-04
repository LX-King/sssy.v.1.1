package com.tyut.sssy.taxManager.domain.identity;

import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.Industry;
import com.tyut.sssy.base.domain.TaxUnit;

/**
 * 项目名称：sssyrisk
 * 类名称：${CLASS}
 * 类描述：微观任务类
 * 创建人：刘翔
 * 创建时间：
 * 修改人：刘翔
 * 修改时间：14-5-26
 * 修改备注：
 */
public class TaxPayerInfo {

    private String nsrsbh ; //纳税人识别号

    private String nsrmc ; //纳税人名称

    private String swjgDm ;//税务机关代码

    private String swjgMc ; //税务机关名称

    private String zgyDm ; //征管员代码

    private String djztDm ; //登记状态代码

    private String swdjztDm ;//税务登记类别代码

    private String dkdjDm ; //贷款登记代码

    private String wtdzDm ;//

    private String qyzclxDm ;//企业注册类型代码

    private String qykglxDm ;//

    private String cyDm ;//产业

    private String hydlDm;     //行业



    private TaxUnit swjg;

    private TaxManager zgy;

    private Wtdz wtdz ;

    private Dkdj dkdj;

    private FirmRegType qyzclx;

    private Industry cy;

    private BigIndustry hydl;


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

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }

    public String getSwjgMc() {
        return swjgMc;
    }

    public void setSwjgMc(String swjgMc) {
        this.swjgMc = swjgMc;
    }

    public String getZgyDm() {
        return zgyDm;
    }

    public void setZgyDm(String zgyDm) {
        this.zgyDm = zgyDm;
    }

    public String getDjztDm() {
        return djztDm;
    }

    public void setDjztDm(String djztDm) {
        this.djztDm = djztDm;
    }

    public String getSwdjztDm() {
        return swdjztDm;
    }

    public void setSwdjztDm(String swdjztDm) {
        this.swdjztDm = swdjztDm;
    }

    public String getDkdjDm() {
        return dkdjDm;
    }

    public void setDkdjDm(String dkdjDm) {
        this.dkdjDm = dkdjDm;
    }

    public String getWtdzDm() {
        return wtdzDm;
    }

    public void setWtdzDm(String wtdzDm) {
        this.wtdzDm = wtdzDm;
    }

    public String getQyzclxDm() {
        return qyzclxDm;
    }

    public void setQyzclxDm(String qyzclxDm) {
        this.qyzclxDm = qyzclxDm;
    }

    public String getQykglxDm() {
        return qykglxDm;
    }

    public void setQykglxDm(String qykglxDm) {
        this.qykglxDm = qykglxDm;
    }

    public String getCyDm() {
        return cyDm;
    }

    public void setCyDm(String cyDm) {
        this.cyDm = cyDm;
    }

    public String getHydlDm() {
        return hydlDm;
    }

    public void setHydlDm(String hydlDm) {
        this.hydlDm = hydlDm;
    }

    public TaxUnit getSwjg() {
        return swjg;
    }

    public void setSwjg(TaxUnit swjg) {
        this.swjg = swjg;
    }

    public TaxManager getZgy() {
        return zgy;
    }

    public void setZgy(TaxManager zgy) {
        this.zgy = zgy;
    }

    public Wtdz getWtdz() {
        return wtdz;
    }

    public void setWtdz(Wtdz wtdz) {
        this.wtdz = wtdz;
    }

    public Dkdj getDkdj() {
        return dkdj;
    }

    public void setDkdj(Dkdj dkdj) {
        this.dkdj = dkdj;
    }

    public FirmRegType getQyzclx() {
        return qyzclx;
    }

    public void setQyzclx(FirmRegType qyzclx) {
        this.qyzclx = qyzclx;
    }

    public Industry getCy() {
        return cy;
    }

    public void setCy(Industry cy) {
        this.cy = cy;
    }

    public BigIndustry getHydl() {
        return hydl;
    }

    public void setHydl(BigIndustry hydl) {
        this.hydl = hydl;
    }
}

package com.tyut.sssy.task.domain;

import com.tyut.sssy.base.domain.*;
import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.sysmgr.domain.User;

import java.math.BigDecimal;

/**
 * ClassName:RiskMicroTask
 * Function:
 * Author: LiuXiang
 * Date: 13-7-8
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚
 */
public class RiskMicroTask {

    private int id;                // 任务id

    private String nsrsbh;        // 纳税人识别号

    private String nsrmc;      //纳税人名称

    private TaxPayer taxPayer;    // 纳税人对象

    private String zgSwjgDm; //征管税务机关代码

    private String zgSwjgMc; //征管税务机关名称

    private TaxUnit taxUnit;    // 税务机关对象

    private String fxqNd;        // 分析期年度

    private String fxqSjq;        // 分析期时间起

    private String fxqSjz;        // 分析期时间止

    private String cyDm;        // 产业代码

    private Industry industry;    // 产业对象

    private String hydlDm;        // 行业大类代码

    private String hydlMc ;     //行业大类名称

    private BigIndustry bigIndustry;    // 行业大类对象

    private String qyzclxDm;	// 产业注册类型代码

    private FirmRegType firmRegType;	// 产业注册类型对象

    private BigDecimal se;

    private String gygm;    // 企业规模

    private CompanyScale companyScale;    //企业规模

    private BigDecimal fxz;        // 分析值

    private String zsjgDm ;

    private String zsjgMc  ;

    private BigDecimal jsz1;  //

    private BigDecimal fz1; //

    private BigDecimal jsz2;  //

    private BigDecimal fz2; //

    private BigDecimal jsz3;  //

    private BigDecimal fz3; //

    private BigDecimal jsz4;  //

    private BigDecimal fz4; //

    private BigDecimal jsz5;  //

    private BigDecimal fz5; //
    private BigDecimal jsz6;  //

    private BigDecimal fz6; //
    private BigDecimal jsz7;  //

    private BigDecimal fz7; //
    private BigDecimal jsz8;  //

    private BigDecimal fz8;

    private BigDecimal fz9; //
    private BigDecimal jsz9;  //

    private BigDecimal fz10; //
    private BigDecimal jsz10;  //

    private BigDecimal fz11; //
    private BigDecimal jsz11;  //

    private BigDecimal fz12; //
    private BigDecimal jsz12;  //

    private BigDecimal fz13; //
    private BigDecimal jsz13;  //

    private BigDecimal fz14; //
    private BigDecimal jsz14;  //

    private BigDecimal jsz15; //private BigDecimal jsz1 ;  //

    private BigDecimal fz15; //

    private BigDecimal jsz16;  //
    private BigDecimal fz16; //

    private BigDecimal jsz17;  //
    private BigDecimal fz17; //

    private BigDecimal jsz18;  //
    private BigDecimal fz18; //

    private BigDecimal jsz19;  //
    private BigDecimal fz19; //

    private BigDecimal jsz20;  //
    private BigDecimal fz20; //

    private BigDecimal jsz21;  //
    private BigDecimal fz21; //

    private BigDecimal jsz22;  //
    private BigDecimal fz22; //

    private BigDecimal jsz23;  //
    private BigDecimal fz23; //

    private BigDecimal jsz24;  //
    private BigDecimal fz24; //

    private String jbjb ; //颜色

    private String sffb ; //

    private String clsx ; //

    private BigDecimal bqYysrLrl ; //

    private BigDecimal tqYysrLrl ;

    private BigDecimal bqYycbLrl ;

    private BigDecimal tqYycbLrl ;

    private BigDecimal bqQjfyLrl ;

    private BigDecimal tqQjfyLrl ;

    private String qyfl;

    private String rwztDm;        // 任务状态代码

    private TaskState taskState;    // 任务状态对象

    private String rwtqryDm;    // 任务提取人员代码

    private User rwtqry;        // 任务提取人员

    private String rwtqRq;        // 任务提取日期

    private String rwfbryDm;    // 任务发布人员代码

    private User rwfbry;        // 任务发布人员

    private String rwfbRq;        // 任务发布日期

    private String rwzxryDm;    // 任务执行人员代码

    private User rwzxry;        // 任务执行人员

    private String bzjsRq;        // 标准接收日期

    private String sjjsRq;        // 实际接收日期

    private String bzfkRq;        // 标准反馈日期

    private String sjfkRq;        // 实际反馈日期

    private String rwbgDm;        // 任务报告代码

    private RiskMicroTaskSurveyReport riskMicroTaskSurveyReport;    // 任务调查报告

    private String glpj;        // 管理评价

    private String zfpj;        // 执法评价

    private String zhpj;        // 综合评价

    private String pjryDm;        // 评价人员代码

    private User pjry;            // 评价人员对象

    private String rwpjRq;        // 任务评价日期

    private int rwjsDf;            // 任务接收得分

    private int rwfkDf;            // 任务反馈得分

    private int rwzxDf;            // 任务执行得分

    private String rwshryMc ; //


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public TaxPayer getTaxPayer() {
        return taxPayer;
    }

    public void setTaxPayer(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    public String getZgSwjgDm() {
        return zgSwjgDm;
    }

    public void setZgSwjgDm(String zgSwjgDm) {
        this.zgSwjgDm = zgSwjgDm;
    }

    public String getZgSwjgMc() {
        return zgSwjgMc;
    }

    public void setZgSwjgMc(String zgSwjgMc) {
        this.zgSwjgMc = zgSwjgMc;
    }

    public TaxUnit getTaxUnit() {
        return taxUnit;
    }

    public void setTaxUnit(TaxUnit taxUnit) {
        this.taxUnit = taxUnit;
    }

    public String getFxqNd() {
        return fxqNd;
    }

    public void setFxqNd(String fxqNd) {
        this.fxqNd = fxqNd;
    }

    public String getFxqSjq() {
        return fxqSjq;
    }

    public void setFxqSjq(String fxqSjq) {
        this.fxqSjq = fxqSjq;
    }

    public String getFxqSjz() {
        return fxqSjz;
    }

    public void setFxqSjz(String fxqSjz) {
        this.fxqSjz = fxqSjz;
    }

    public String getCyDm() {
        return cyDm;
    }

    public void setCyDm(String cyDm) {
        this.cyDm = cyDm;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public String getHydlDm() {
        return hydlDm;
    }

    public void setHydlDm(String hydlDm) {
        this.hydlDm = hydlDm;
    }

    public BigIndustry getBigIndustry() {
        return bigIndustry;
    }

    public void setBigIndustry(BigIndustry bigIndustry) {
        this.bigIndustry = bigIndustry;
    }

    public String getQyzclxDm() {
        return qyzclxDm;
    }

    public void setQyzclxDm(String qyzclxDm) {
        this.qyzclxDm = qyzclxDm;
    }

    public FirmRegType getFirmRegType() {
        return firmRegType;
    }

    public void setFirmRegType(FirmRegType firmRegType) {
        this.firmRegType = firmRegType;
    }

    public String getGygm() {
        return gygm;
    }

    public void setGygm(String gygm) {
        this.gygm = gygm;
    }

    public CompanyScale getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(CompanyScale companyScale) {
        this.companyScale = companyScale;
    }

    public BigDecimal getFxz() {
        return fxz;
    }

    public void setFxz(BigDecimal fxz) {
        this.fxz = fxz;
    }

    public BigDecimal getJsz1() {
        return jsz1;
    }

    public void setJsz1(BigDecimal jsz1) {
        this.jsz1 = jsz1;
    }

    public BigDecimal getFz1() {
        return fz1;
    }

    public void setFz1(BigDecimal fz1) {
        this.fz1 = fz1;
    }

    public BigDecimal getJsz2() {
        return jsz2;
    }

    public void setJsz2(BigDecimal jsz2) {
        this.jsz2 = jsz2;
    }

    public BigDecimal getFz2() {
        return fz2;
    }

    public void setFz2(BigDecimal fz2) {
        this.fz2 = fz2;
    }

    public BigDecimal getJsz3() {
        return jsz3;
    }

    public void setJsz3(BigDecimal jsz3) {
        this.jsz3 = jsz3;
    }

    public BigDecimal getFz3() {
        return fz3;
    }

    public void setFz3(BigDecimal fz3) {
        this.fz3 = fz3;
    }

    public BigDecimal getJsz4() {
        return jsz4;
    }

    public void setJsz4(BigDecimal jsz4) {
        this.jsz4 = jsz4;
    }

    public BigDecimal getFz4() {
        return fz4;
    }

    public void setFz4(BigDecimal fz4) {
        this.fz4 = fz4;
    }

    public BigDecimal getJsz5() {
        return jsz5;
    }

    public void setJsz5(BigDecimal jsz5) {
        this.jsz5 = jsz5;
    }

    public BigDecimal getFz5() {
        return fz5;
    }

    public void setFz5(BigDecimal fz5) {
        this.fz5 = fz5;
    }

    public BigDecimal getJsz6() {
        return jsz6;
    }

    public void setJsz6(BigDecimal jsz6) {
        this.jsz6 = jsz6;
    }

    public BigDecimal getFz6() {
        return fz6;
    }

    public void setFz6(BigDecimal fz6) {
        this.fz6 = fz6;
    }

    public BigDecimal getJsz7() {
        return jsz7;
    }

    public void setJsz7(BigDecimal jsz7) {
        this.jsz7 = jsz7;
    }

    public BigDecimal getFz7() {
        return fz7;
    }

    public void setFz7(BigDecimal fz7) {
        this.fz7 = fz7;
    }

    public BigDecimal getJsz8() {
        return jsz8;
    }

    public void setJsz8(BigDecimal jsz8) {
        this.jsz8 = jsz8;
    }

    public BigDecimal getFz8() {
        return fz8;
    }

    public void setFz8(BigDecimal fz8) {
        this.fz8 = fz8;
    }

    public BigDecimal getFz9() {
        return fz9;
    }

    public void setFz9(BigDecimal fz9) {
        this.fz9 = fz9;
    }

    public BigDecimal getJsz9() {
        return jsz9;
    }

    public void setJsz9(BigDecimal jsz9) {
        this.jsz9 = jsz9;
    }

    public BigDecimal getFz10() {
        return fz10;
    }

    public void setFz10(BigDecimal fz10) {
        this.fz10 = fz10;
    }

    public BigDecimal getJsz10() {
        return jsz10;
    }

    public void setJsz10(BigDecimal jsz10) {
        this.jsz10 = jsz10;
    }

    public BigDecimal getFz11() {
        return fz11;
    }

    public void setFz11(BigDecimal fz11) {
        this.fz11 = fz11;
    }

    public BigDecimal getJsz11() {
        return jsz11;
    }

    public void setJsz11(BigDecimal jsz11) {
        this.jsz11 = jsz11;
    }

    public BigDecimal getFz12() {
        return fz12;
    }

    public void setFz12(BigDecimal fz12) {
        this.fz12 = fz12;
    }

    public BigDecimal getJsz12() {
        return jsz12;
    }

    public void setJsz12(BigDecimal jsz12) {
        this.jsz12 = jsz12;
    }

    public BigDecimal getFz13() {
        return fz13;
    }

    public void setFz13(BigDecimal fz13) {
        this.fz13 = fz13;
    }

    public BigDecimal getJsz13() {
        return jsz13;
    }

    public void setJsz13(BigDecimal jsz13) {
        this.jsz13 = jsz13;
    }

    public BigDecimal getFz14() {
        return fz14;
    }

    public void setFz14(BigDecimal fz14) {
        this.fz14 = fz14;
    }

    public BigDecimal getJsz14() {
        return jsz14;
    }

    public void setJsz14(BigDecimal jsz14) {
        this.jsz14 = jsz14;
    }

    public BigDecimal getJsz15() {
        return jsz15;
    }

    public void setJsz15(BigDecimal jsz15) {
        this.jsz15 = jsz15;
    }

    public BigDecimal getFz15() {
        return fz15;
    }

    public void setFz15(BigDecimal fz15) {
        this.fz15 = fz15;
    }

    public BigDecimal getJsz16() {
        return jsz16;
    }

    public void setJsz16(BigDecimal jsz16) {
        this.jsz16 = jsz16;
    }

    public BigDecimal getFz16() {
        return fz16;
    }

    public void setFz16(BigDecimal fz16) {
        this.fz16 = fz16;
    }

    public BigDecimal getJsz17() {
        return jsz17;
    }

    public void setJsz17(BigDecimal jsz17) {
        this.jsz17 = jsz17;
    }

    public BigDecimal getFz17() {
        return fz17;
    }

    public void setFz17(BigDecimal fz17) {
        this.fz17 = fz17;
    }

    public BigDecimal getJsz18() {
        return jsz18;
    }

    public void setJsz18(BigDecimal jsz18) {
        this.jsz18 = jsz18;
    }

    public BigDecimal getFz18() {
        return fz18;
    }

    public void setFz18(BigDecimal fz18) {
        this.fz18 = fz18;
    }

    public BigDecimal getJsz19() {
        return jsz19;
    }

    public void setJsz19(BigDecimal jsz19) {
        this.jsz19 = jsz19;
    }

    public BigDecimal getFz19() {
        return fz19;
    }

    public void setFz19(BigDecimal fz19) {
        this.fz19 = fz19;
    }

    public BigDecimal getJsz20() {
        return jsz20;
    }

    public void setJsz20(BigDecimal jsz20) {
        this.jsz20 = jsz20;
    }

    public BigDecimal getFz20() {
        return fz20;
    }

    public void setFz20(BigDecimal fz20) {
        this.fz20 = fz20;
    }

    public BigDecimal getJsz21() {
        return jsz21;
    }

    public void setJsz21(BigDecimal jsz21) {
        this.jsz21 = jsz21;
    }

    public BigDecimal getFz21() {
        return fz21;
    }

    public void setFz21(BigDecimal fz21) {
        this.fz21 = fz21;
    }

    public BigDecimal getJsz22() {
        return jsz22;
    }

    public void setJsz22(BigDecimal jsz22) {
        this.jsz22 = jsz22;
    }

    public BigDecimal getFz22() {
        return fz22;
    }

    public void setFz22(BigDecimal fz22) {
        this.fz22 = fz22;
    }

    public BigDecimal getJsz23() {
        return jsz23;
    }

    public void setJsz23(BigDecimal jsz23) {
        this.jsz23 = jsz23;
    }

    public BigDecimal getFz23() {
        return fz23;
    }

    public void setFz23(BigDecimal fz23) {
        this.fz23 = fz23;
    }

    public BigDecimal getJsz24() {
        return jsz24;
    }

    public void setJsz24(BigDecimal jsz24) {
        this.jsz24 = jsz24;
    }

    public BigDecimal getFz24() {
        return fz24;
    }

    public void setFz24(BigDecimal fz24) {
        this.fz24 = fz24;
    }

    public String getJbjb() {
        return jbjb;
    }

    public void setJbjb(String jbjb) {
        this.jbjb = jbjb;
    }

    public String getSffb() {
        return sffb;
    }

    public void setSffb(String sffb) {
        this.sffb = sffb;
    }

    public String getClsx() {
        return clsx;
    }

    public void setClsx(String clsx) {
        this.clsx = clsx;
    }

    public BigDecimal getBqYysrLrl() {
        return bqYysrLrl;
    }

    public void setBqYysrLrl(BigDecimal bqYysrLrl) {
        this.bqYysrLrl = bqYysrLrl;
    }

    public BigDecimal getTqYysrLrl() {
        return tqYysrLrl;
    }

    public void setTqYysrLrl(BigDecimal tqYysrLrl) {
        this.tqYysrLrl = tqYysrLrl;
    }

    public BigDecimal getBqYycbLrl() {
        return bqYycbLrl;
    }

    public void setBqYycbLrl(BigDecimal bqYycbLrl) {
        this.bqYycbLrl = bqYycbLrl;
    }

    public BigDecimal getBqQjfyLrl() {
        return bqQjfyLrl;
    }

    public void setBqQjfyLrl(BigDecimal bqQjfyLrl) {
        this.bqQjfyLrl = bqQjfyLrl;
    }

    public BigDecimal getTqQjfyLrl() {
        return tqQjfyLrl;
    }

    public void setTqQjfyLrl(BigDecimal tqQjfyLrl) {
        this.tqQjfyLrl = tqQjfyLrl;
    }

    public String getQyfl() {
        return qyfl;
    }

    public void setQyfl(String qyfl) {
        this.qyfl = qyfl;
    }

    public String getRwztDm() {
        return rwztDm;
    }

    public void setRwztDm(String rwztDm) {
        this.rwztDm = rwztDm;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public String getRwtqryDm() {
        return rwtqryDm;
    }

    public void setRwtqryDm(String rwtqryDm) {
        this.rwtqryDm = rwtqryDm;
    }

    public User getRwtqry() {
        return rwtqry;
    }

    public void setRwtqry(User rwtqry) {
        this.rwtqry = rwtqry;
    }

    public String getRwtqRq() {
        return rwtqRq;
    }

    public void setRwtqRq(String rwtqRq) {
        this.rwtqRq = rwtqRq;
    }

    public String getRwfbryDm() {
        return rwfbryDm;
    }

    public void setRwfbryDm(String rwfbryDm) {
        this.rwfbryDm = rwfbryDm;
    }

    public User getRwfbry() {
        return rwfbry;
    }

    public void setRwfbry(User rwfbry) {
        this.rwfbry = rwfbry;
    }

    public String getRwfbRq() {
        return rwfbRq;
    }

    public void setRwfbRq(String rwfbRq) {
        this.rwfbRq = rwfbRq;
    }

    public String getRwzxryDm() {
        return rwzxryDm;
    }

    public void setRwzxryDm(String rwzxryDm) {
        this.rwzxryDm = rwzxryDm;
    }

    public User getRwzxry() {
        return rwzxry;
    }

    public void setRwzxry(User rwzxry) {
        this.rwzxry = rwzxry;
    }

    public String getBzjsRq() {
        return bzjsRq;
    }

    public void setBzjsRq(String bzjsRq) {
        this.bzjsRq = bzjsRq;
    }

    public String getSjjsRq() {
        return sjjsRq;
    }

    public void setSjjsRq(String sjjsRq) {
        this.sjjsRq = sjjsRq;
    }

    public String getBzfkRq() {
        return bzfkRq;
    }

    public void setBzfkRq(String bzfkRq) {
        this.bzfkRq = bzfkRq;
    }

    public String getSjfkRq() {
        return sjfkRq;
    }

    public void setSjfkRq(String sjfkRq) {
        this.sjfkRq = sjfkRq;
    }

    public String getRwbgDm() {
        return rwbgDm;
    }

    public void setRwbgDm(String rwbgDm) {
        this.rwbgDm = rwbgDm;
    }

    public RiskMicroTaskSurveyReport getRiskMicroTaskSurveyReport() {
        return riskMicroTaskSurveyReport;
    }

    public void setRiskMicroTaskSurveyReport(RiskMicroTaskSurveyReport riskMicroTaskSurveyReport) {
        this.riskMicroTaskSurveyReport = riskMicroTaskSurveyReport;
    }

    public String getGlpj() {
        return glpj;
    }

    public void setGlpj(String glpj) {
        this.glpj = glpj;
    }

    public String getZfpj() {
        return zfpj;
    }

    public void setZfpj(String zfpj) {
        this.zfpj = zfpj;
    }

    public String getZhpj() {
        return zhpj;
    }

    public void setZhpj(String zhpj) {
        this.zhpj = zhpj;
    }

    public String getPjryDm() {
        return pjryDm;
    }

    public void setPjryDm(String pjryDm) {
        this.pjryDm = pjryDm;
    }

    public User getPjry() {
        return pjry;
    }

    public void setPjry(User pjry) {
        this.pjry = pjry;
    }

    public String getRwpjRq() {
        return rwpjRq;
    }

    public void setRwpjRq(String rwpjRq) {
        this.rwpjRq = rwpjRq;
    }

    public int getRwjsDf() {
        return rwjsDf;
    }

    public void setRwjsDf(int rwjsDf) {
        this.rwjsDf = rwjsDf;
    }

    public int getRwfkDf() {
        return rwfkDf;
    }

    public void setRwfkDf(int rwfkDf) {
        this.rwfkDf = rwfkDf;
    }

    public int getRwzxDf() {
        return rwzxDf;
    }

    public void setRwzxDf(int rwzxDf) {
        this.rwzxDf = rwzxDf;
    }

    public BigDecimal getSe() {
        return se;
    }

    public void setSe(BigDecimal se) {
        this.se = se;
    }

    public BigDecimal getTqYycbLrl() {
        return tqYycbLrl;
    }

    public void setTqYycbLrl(BigDecimal tqYycbLrl) {
        this.tqYycbLrl = tqYycbLrl;
    }


    public String getZsjgDm() {
        return zsjgDm;
    }

    public void setZsjgDm(String zsjgDm) {
        this.zsjgDm = zsjgDm;
    }

    public String getZsjgMc() {
        return zsjgMc;
    }

    public void setZsjgMc(String zsjgMc) {
        this.zsjgMc = zsjgMc;
    }

    public String getHydlMc() {
        return hydlMc;
    }

    public void setHydlMc(String hydlMc) {
        this.hydlMc = hydlMc;
    }

    public String getRwshryMc() {
        return rwshryMc;
    }

    public void setRwshryMc(String rwshryMc) {
        this.rwshryMc = rwshryMc;
    }
}

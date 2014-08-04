package com.tyut.sssy.infosearch.actions.graphic.resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.FirmSizeService;
import com.tyut.sssy.sysmgr.service.CompanyScaleService;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorFirmDetailTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorTableParameter;
import com.tyut.sssy.utils.SessionAttributeKey;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ClassName:TaxFinishGraphicAction
 * Function:重点税源检测户数
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 10:53:53
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FirmCounterMonitorGraphicAction extends ActionSupport implements Preparable {

    //所属机构代码
    public String ssjgDm;

    //企业规模
    public String qygm;

    //行业大类代码
    public String hydlDm;

    //企业注册类型
    public String qyzclx;

    //报表期
    public String reportPeriod;

    //年度
    public String nd;

    //
    private String monthPeriod;

    private String xmbz;    // 项目标志


    /**
     * @throws Exception
     */
    public void prepare() throws Exception {
        // 经济类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        // 行业大类
        BigIndustryService bigIndustryService = new BigIndustryService();
        // 企业规模
        CompanyScaleService companyScaleService = new CompanyScaleService();


        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        TaxResourceMonitorTableParameter taxResourceMonitorTableParameter = (TaxResourceMonitorTableParameter) session.getAttribute(SessionAttributeKey.ZDSY_JCHS_TJ);

        //注册类型名称
        this.qyzclx = request.getParameter("firmRegType");
        this.qyzclx = firmRegTypeService.getFirmRegTypeById(this.qyzclx).getMc();

        this.ssjgDm = taxResourceMonitorTableParameter.getSsjgDm();
        //企业注册类型
        this.hydlDm = bigIndustryService.getBigIndustryById(taxResourceMonitorTableParameter.getHydlDm()).getMc();

        //企业规模名称
        this.qygm = taxResourceMonitorTableParameter.getQygm();
        /*this.qygm = companyScaleService.getById(this.qygm).getCsName();*/

        this.nd = taxResourceMonitorTableParameter.getNd();
        String sssqQ = taxResourceMonitorTableParameter.getSssqQ();
        String sssqZ = taxResourceMonitorTableParameter.getSssqZ();
        this.monthPeriod = sssqQ + "-" + sssqZ;
        this.xmbz = taxResourceMonitorTableParameter.getXmbz();

    }

    @Override
    public String execute() {
        return SUCCESS;
    }


    public String getSsjgDm() {
        return ssjgDm;
    }

    public void setSsjgDm(String ssjgDm) {
        this.ssjgDm = ssjgDm;
    }

    public String getQygm() {
        return qygm;
    }

    public void setQygm(String qygm) {
        this.qygm = qygm;
    }

    public String getHydlDm() {
        return hydlDm;
    }

    public void setHydlDm(String hydlDm) {
        this.hydlDm = hydlDm;
    }

    public String getQyzclx() {
        return qyzclx;
    }

    public void setQyzclx(String qyzclx) {
        this.qyzclx = qyzclx;
    }

    public String getReportPeriod() {
        return reportPeriod;
    }

    public void setReportPeriod(String reportPeriod) {
        this.reportPeriod = reportPeriod;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getMonthPeriod() {
        return monthPeriod;
    }

    public void setMonthPeriod(String monthPeriod) {
        this.monthPeriod = monthPeriod;
    }

    public String getXmbz() {
        return xmbz;
    }

    public void setXmbz(String xmbz) {
        this.xmbz = xmbz;
    }
}
package com.tyut.sssy.infosearch.actions.graphic.resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorFirmDetailTableParameter;
import com.tyut.sssy.utils.SessionAttributeKey;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ClassName:FirmDetailMonitorGraphicAction
 * Function:重点税源监控企业明细
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 10:53:53
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FirmDetailMonitorGraphicAction extends ActionSupport implements Preparable {

    //纳税人编号
    public String nsrsbh;
    //年度
    public String nd;
    //报表期
    public String monthPeriod;


    /**
     * 预处理
     *
     * @throws Exception
     */
    public void prepare() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        TaxResourceMonitorFirmDetailTableParameter taxResourceMonitorFirmDetailTableParameter = (TaxResourceMonitorFirmDetailTableParameter) session.getAttribute(SessionAttributeKey.ZDSY_JK_QYMX);
        this.nsrsbh = request.getParameter("taxPayerCode");
        this.monthPeriod = taxResourceMonitorFirmDetailTableParameter.getSssqQ() + "-" + taxResourceMonitorFirmDetailTableParameter.getSssqZ();
        this.nd = taxResourceMonitorFirmDetailTableParameter.getNd();
    }

    @Override
    public String execute() {
        return SUCCESS;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
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
}
package com.tyut.sssy.infosearch.actions.graphic.resource;

import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTableParameter;
import com.tyut.sssy.taxresource.domain.FirmRunInfoTableParameter;
import com.tyut.sssy.utils.SessionAttributeKey;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ClassName:FirmRunInfoGraphicAction
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-9
 * Time: 下午8:51
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FirmRunInfoGraphicAction implements Preparable {

    //纳税人编号
    public String nsrsbh;
    //报表期
    public String monthPeriod;
    // 年度
    public String nd;

    /**
     * 预处理
     *
     * @throws Exception
     */
    public void prepare() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        FirmRunInfoTableParameter firmRunInfoTableParameter = (FirmRunInfoTableParameter) session.getAttribute(SessionAttributeKey.QYJYXX);
        this.nsrsbh = firmRunInfoTableParameter.getNsrsbh();
        this.nd = firmRunInfoTableParameter.getNd();
        this.monthPeriod = "01-" + firmRunInfoTableParameter.getSssqZ();
    }

    /**
     * 查询利润表
     *
     * @return String
     */
    public String queryInterest() {
        return "QUERY_INTERST";
    }

    /**
     * 查询负载
     *
     * @return String
     */
    public String queryAsset() {
        return "QUERY_ASSET";
    }


    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getMonthPeriod() {
        return monthPeriod;
    }

    public void setMonthPeriod(String monthPeriod) {
        this.monthPeriod = monthPeriod;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }
}

package com.tyut.sssy.infosearch.actions.graphic.resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTableParameter;
import com.tyut.sssy.utils.SessionAttributeKey;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ClassName:FirmBaseInfoGraphicAction
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-9
 * Time: 下午7:53
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FirmBaseInfoGraphicAction extends ActionSupport implements Preparable {

    //纳税人编号
        public String nsrsbh;
        //所属机构代码
        public String ssjgDm;
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
        FirmInfoSearchTableParameter firmInfoSearchTableParameter = (FirmInfoSearchTableParameter) session.getAttribute(SessionAttributeKey.QYJCXX);
        this.nsrsbh = firmInfoSearchTableParameter.getNsrsbh();
        this.nd = firmInfoSearchTableParameter.getNd();
        this.monthPeriod = "01-" + firmInfoSearchTableParameter.getSssqZ();
        this.ssjgDm = request.getParameter("institutionCode");

    }


    @Override
    public String execute() throws Exception {
        return SUCCESS;
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

    public String getSsjgDm() {
        return ssjgDm;
    }

    public void setSsjgDm(String ssjgDm) {
        this.ssjgDm = ssjgDm;
    }
}

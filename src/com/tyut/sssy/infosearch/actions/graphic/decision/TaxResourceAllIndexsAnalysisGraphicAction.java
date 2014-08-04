package com.tyut.sssy.infosearch.actions.graphic.decision;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTableParameter;
import com.tyut.sssy.sysmgr.service.IndexService;
import com.tyut.sssy.utils.*;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:TaxGrowAndDevelopIndexGraphicAction
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-10
 * Time: 上午12:49
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class TaxResourceAllIndexsAnalysisGraphicAction extends ActionSupport implements Preparable {


    /*税务机关*/
    public String swjg;
    /*纳税人编号*/
    public String nsrsbh;
    /*企业注册类型*/
    public String qyzclx;
    /*产业*/
    public String cy;
    /*行业*/
    public String hy;
    /*分析指标代码*/
    public String fxzb;
    /*年度*/
    public String nd;
    /*月份*/
    public String monthPeriod;

    /**
     * 预处理
     *
     * @throws Exception
     */
    public void prepare() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        String method = request.getParameter("method");
        if (method.equals("detail")) {
            HttpSession session = request.getSession();
            TaxResourceAllIndexTableParameter taxCollectionAllIndexTableParameter = (TaxResourceAllIndexTableParameter) session.getAttribute(SessionAttributeKey.SYLSYZB);
            this.swjg = taxCollectionAllIndexTableParameter.getSwjg();
            this.nsrsbh = taxCollectionAllIndexTableParameter.getNsrsbh();
            this.qyzclx = taxCollectionAllIndexTableParameter.getQyzclx();
            this.cy = taxCollectionAllIndexTableParameter.getCy();
            this.hy = taxCollectionAllIndexTableParameter.getHy();
            this.fxzb = request.getParameter("indexCode");
            IndexService indexService = new IndexService();
            this.fxzb = indexService.getByName(this.fxzb).getIndexCode();
            this.nd = taxCollectionAllIndexTableParameter.getNd();
            this.monthPeriod = taxCollectionAllIndexTableParameter.getSqQ() + "-" + taxCollectionAllIndexTableParameter.getSqZ();
        }
    }

    @Override
    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String method = request.getParameter("method");
        if (method != null && !method.equals("")) {
            if (method.equals("detail")) {
                return SUCCESS;
            } else {
                String caption = "测试数据";
                //税务机关
                String swjgDm = request.getParameter("swjgDm");
                //数据类型
                String dataType = "";
                //金额单位
                String moneyUnit = "yuan";
                //报表期
                String reportPeriod = request.getParameter("reportPeriod");
                //显示项目
                String displayItem = request.getParameter("displayItem");
                //图表类型
                String graphicType = request.getParameter("grahpicType");

                generateFushionChartDataSource(caption);
                printJson(null);

                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * 生成FushionChartDataSource
     *
     * @param caption
     * @return FushionChartDataSource
     */
    private FushionChartDataSource generateFushionChartDataSource(String caption) {
        FushionChartDesc chart = new FushionChartDesc();
        chart.setCaption(caption);
        chart.setXAxisName("Week");
        chart.setYAxisName("Sales");
        chart.setNumberPerfix("$");

        FushionChartCategories categories = new FushionChartCategories();
        FushionChartCategory f = new FushionChartCategory();
        f.setLabel("一月");
        categories.add(f);
        f = new FushionChartCategory();
        f.setLabel("二月");
        categories.add(f);
        List<FushionChartCategories> categoriesList = new ArrayList<FushionChartCategories>();
        categoriesList.add(categories);

        List<FushionChartDataSet> dataSet = new ArrayList<FushionChartDataSet>();
        FushionChartDataSet dataSet1 = new FushionChartDataSet("一所");
        FushionChartData data1 = new FushionChartData();
        data1.setValue("14400");
        dataSet1.add(data1);
        data1 = new FushionChartData();
        data1.setValue("15500");
        dataSet1.add(data1);
        dataSet.add(dataSet1);
        FushionChartDataSource dataSource = new FushionChartDataSource(chart, categoriesList, dataSet);

        return dataSource;
    }

    /**
     * 输出结果 封装
     *
     * @param json
     * @return void
     */
    private void printJson(String json) {
        if (json == null || json.equals(""))
            json = "{\"chart\":{\"caption\":\"Business Results 2005 v 2006\",\"xaxisname\":\"Month\",\"yaxisname\":\"Revenue\",\"showvalues\":\"0\",\"numberprefix\":\"$\"},\"categories\":[{\"category\":[{\"label\":\"Jan\"},{\"label\":\"Feb\"},{\"label\":\"Mar\"},{\"label\":\"Apr\"},{\"label\":\"May\"},{\"label\":\"Jun\"},{\"vline\":\"true\",\"color\":\"FF5904\",\"thickness\":\"2\"},{\"label\":\"Jul\"},{\"label\":\"Aug\"},{\"label\":\"Sep\"},{\"label\":\"Oct\"},{\"label\":\"Nov\"},{\"label\":\"Dec\"}]}],\"dataset\":[{\"seriesname\":\"2006\",\"data\":[{\"value\":\"27400\"},{\"value\":\"29800\"},{\"value\":\"25800\"},{\"value\":\"26800\"},{\"value\":\"29600\"},{\"value\":\"32600\"},{\"value\":\"31800\"},{\"value\":\"36700\"},{\"value\":\"29700\"},{\"value\":\"31900\"},{\"value\":\"34800\"},{\"value\":\"24800\"}]},{\"seriesname\":\"2005\",\"data\":[{\"value\":\"10000\"},{\"value\":\"11500\"},{\"value\":\"12500\"},{\"value\":\"15000\"},{\"value\":\"11000\"},{\"value\":\"9800\"},{\"value\":\"11800\"},{\"value\":\"19700\"},{\"value\":\"21700\"},{\"value\":\"21900\"},{\"value\":\"22900\"},{\"value\":\"20800\"}]}],\"trendlines\":{\"line\":[{\"startvalue\":\"26000\",\"color\":\"91C728\",\"displayvalue\":\"Target\",\"showontop\":\"1\"}]},\"styles\": {\"definition\": [{\"name\": \"CanvasAnim\",\"type\": \"animation\",\"param\": \"_xScale\",\"start\": \"0\",\"duration\": \"1\"}],\"application\": [{\"toobject\": \"Canvas\",\"styles\": \"CanvasAnim\"}]}}";
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert out != null;
        out.flush();
        out.close();
    }


    public String getSwjg() {
        return swjg;
    }

    public void setSwjg(String swjg) {
        this.swjg = swjg;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getQyzclx() {
        return qyzclx;
    }

    public void setQyzclx(String qyzclx) {
        this.qyzclx = qyzclx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getHy() {
        return hy;
    }

    public void setHy(String hy) {
        this.hy = hy;
    }

    public String getFxzb() {
        return fxzb;
    }

    public void setFxzb(String fxzb) {
        this.fxzb = fxzb;
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

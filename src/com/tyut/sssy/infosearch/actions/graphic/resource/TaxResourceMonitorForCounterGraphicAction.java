package com.tyut.sssy.infosearch.actions.graphic.resource;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tyut.sssy.infosearch.service.TaxCollectionSearchService;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTableParameter;
import com.tyut.sssy.utils.*;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.base.service.DisplayItemService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

/**
 * ClassName:TaxFinishGraphicAction
 * Function:税收完成情况图表功能
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 10:53:53
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class TaxResourceMonitorForCounterGraphicAction extends ActionSupport{

    @Override
    public String execute() {
        /*TaxCollectionFinishTableParameter taxCollectionFinishTableParameter = new TaxCollectionFinishTableParameter();
        TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
        List<TaxCollectionFinishTable> tableList = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter);*/

        TaxUnitService taxUnitService = new TaxUnitService();
        DisplayItemService displayItemService = new DisplayItemService();
        HttpServletRequest request = ServletActionContext.getRequest();
        TaxCollectionFinishTableParameter taxCollectionFinishTableParameter = new TaxCollectionFinishTableParameter();
        String swjgDm = request.getParameter("swjgDm");
        String dataType = request.getParameter("dataType");
        String moneyUnit = "yuan";
        String reportPeriod = request.getParameter("reportPeriod");
        String displayItem = request.getParameter("displayItem");

        String sjOrSs = request.getParameter("show1");
        String tbOrWc = request.getParameter("show2");

        String unitName = taxUnitService.getTaxUnitById(swjgDm).getMc();
        String dataTypeMc = "";
        if(dataType.equals("rkss"))
            dataTypeMc ="入库税收";
        else
            dataTypeMc = "实缴税收";
        String displayIteMc = displayItemService.getById(displayItem).getFlmc();
        String caption = unitName + reportPeriod+ dataTypeMc+"税收收入完成情况图表";

        FushionChartDesc chart = new FushionChartDesc();
        chart.setCaption(caption);
        chart.setXAxisName("Week");
        chart.setYAxisName("Sales");
        chart.setNumberPerfix("$");

        FushionChartCategories categories = new FushionChartCategories();
        FushionChartCategory f = new FushionChartCategory();
        f.setLabel("一月");
        categories.add(f);
        f  = new FushionChartCategory();
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
        FushionChartDataSource dataSource = new FushionChartDataSource(chart, categoriesList,dataSet);

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();

            out.print("{\"chart\":{\"caption\":\"Business Results 2005 v 2006\",\"xaxisname\":\"Month\",\"yaxisname\":\"Revenue\",\"showvalues\":\"0\",\"numberprefix\":\"$\"},\"categories\":[{\"category\":[{\"label\":\"Jan\"},{\"label\":\"Feb\"},{\"label\":\"Mar\"},{\"label\":\"Apr\"},{\"label\":\"May\"},{\"label\":\"Jun\"},{\"vline\":\"true\",\"color\":\"FF5904\",\"thickness\":\"2\"},{\"label\":\"Jul\"},{\"label\":\"Aug\"},{\"label\":\"Sep\"},{\"label\":\"Oct\"},{\"label\":\"Nov\"},{\"label\":\"Dec\"}]}],\"dataset\":[{\"seriesname\":\"2006\",\"data\":[{\"value\":\"27400\"},{\"value\":\"29800\"},{\"value\":\"25800\"},{\"value\":\"26800\"},{\"value\":\"29600\"},{\"value\":\"32600\"},{\"value\":\"31800\"},{\"value\":\"36700\"},{\"value\":\"29700\"},{\"value\":\"31900\"},{\"value\":\"34800\"},{\"value\":\"24800\"}]},{\"seriesname\":\"2005\",\"data\":[{\"value\":\"10000\"},{\"value\":\"11500\"},{\"value\":\"12500\"},{\"value\":\"15000\"},{\"value\":\"11000\"},{\"value\":\"9800\"},{\"value\":\"11800\"},{\"value\":\"19700\"},{\"value\":\"21700\"},{\"value\":\"21900\"},{\"value\":\"22900\"},{\"value\":\"20800\"}]}],\"trendlines\":{\"line\":[{\"startvalue\":\"26000\",\"color\":\"91C728\",\"displayvalue\":\"Target\",\"showontop\":\"1\"}]},\"styles\": {\"definition\": [{\"name\": \"CanvasAnim\",\"type\": \"animation\",\"param\": \"_xScale\",\"start\": \"0\",\"duration\": \"1\"}],\"application\": [{\"toobject\": \"Canvas\",\"styles\": \"CanvasAnim\"}]}}");
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.flush();
        out.close();

        return null;

    }
}
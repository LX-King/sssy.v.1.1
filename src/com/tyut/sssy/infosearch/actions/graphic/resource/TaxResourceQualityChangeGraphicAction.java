package com.tyut.sssy.infosearch.actions.graphic.resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.infosearch.service.TaxResourceSearchService;
import com.tyut.sssy.taxresource.domain.TaxResourceQualityChangeTable;
import com.tyut.sssy.taxresource.domain.TaxResourceQualityChangeTableParameter;
import com.tyut.sssy.utils.*;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName:TaxResourceSumChangeGraphicAction
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-10
 * Time: 上午12:28
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class TaxResourceQualityChangeGraphicAction extends ActionSupport implements Preparable {

    private TaxResourceSearchService service = null;

    private TaxResourceQualityChangeTableParameter parameter = null;

    private Map<String, List<BigDecimal>> dataset = null;

    private List<String> categories = null;

    /**
     * 预处理
     *
     * @throws Exception
     */
    public void prepare() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        this.parameter = (TaxResourceQualityChangeTableParameter) session.getAttribute(SessionAttributeKey.SYZLBD);
        this.service = new TaxResourceSearchService();
        this.dataset = new LinkedHashMap<String, List<BigDecimal>>();
        this.categories = new ArrayList<String>();

        for (int i = 1; i <= Integer.parseInt(this.parameter.getSssqZ()) / 3; i++) {
            this.categories.add("第" + i + "季度");
        }

    }

    /**
     * 默认执行方法
     *
     * @return string
     */
    @Override
    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String method = request.getParameter("method");
        if (method.equals("line")) {
            drawLineGraphic();
            return null;
        } else if (method.equals("column")) {
            drawColumnGraphic();
            return null;
        } else {
            return null;
        }
    }

    /*画折线图*/
    private void drawLineGraphic() {
        String caption = generateCaption();

        //生成FushionChartDataSource
        String xAxisName = Constants.TAX_RESOUCE_XMBZ[Integer.parseInt(parameter.getXmbz()) - 1] + " 同比增减";
        String yAxisName = "";
        FushionChartLineJsonData dataSource = new FushionChartLineJsonData(caption, xAxisName, yAxisName, this.categories, this.dataset);
        //        FushionChartDataSource dataSource = generateLineDataSource(caption, xAxisName, yAxisName, displayItemService.getById(displayItem).getFlmc());
        printJson(dataSource.toJson());
    }

    /*画柱图*/
    private void drawColumnGraphic() {

        String caption = generateCaption();

        //生成FushionChartDataSource
        String xAxisName = Constants.TAX_RESOUCE_XMBZ[Integer.parseInt(parameter.getXmbz()) - 1] + " 同比增减";
        String yAxisName = "";
        FushionChartColumnJsonData dataSource = new FushionChartColumnJsonData(caption, xAxisName, yAxisName, this.categories, this.dataset);
        printJson(dataSource.toJson());
    }

    /**
     * 生成题头
     *
     * @return String
     */
    private String generateCaption() {
        String monthStr = this.parameter.getSssqZ();
        int month = Integer.parseInt(monthStr);
        if (this.dataset.size() == 0)
            extractData(month);
        TaxUnitService taxUnitService = new TaxUnitService();

        //税务机关代码
        String swjgDm = parameter.getSwjgDm();
        //数据类型
        /*String dataType = parameter.getSjlx();*/
        //报表期
        String reportPeriod = parameter.getSssqZ().trim().equals("03") ? parameter.getNd() + "年" + "第 1 季度" : parameter.getNd() + "年" + "第 1 - " + ((Integer.parseInt(parameter.getSssqZ())) / 3) + " 季度";
        //税务机关单位名称
        String unitName = taxUnitService.getTaxUnitById(swjgDm).getMc();
        // 数据类型名称

        //图表的标题
        return unitName + reportPeriod + "税源总量增减变动情况图表";
    }

    /**
     * 根据显示项目 封装数据
     *
     * @param month
     */
    private void extractData(int month) {

        List<String> list = Arrays.asList(Constants.TAX_RESOURCE_QU_CHANGE);
        for (int j = 0; j < list.size(); j++) {
            String curr = list.get(j);
            List<BigDecimal> tempList = new ArrayList<BigDecimal>();
            this.dataset.put(curr, tempList);
        }

        for (int i = 1; i <= month / 3; i++) {
            String temp = String.valueOf(i * 3);
            if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                temp = "0" + temp;
            }
            parameter.setSssqZ(temp);
            List<TaxResourceQualityChangeTable> finishTable = service.getTaxResourceQualityChangeTable(parameter);
            BigDecimal yyTotal = new BigDecimal(0);
            BigDecimal lrTotal = new BigDecimal(0);
            BigDecimal zlTotal = new BigDecimal(0);
            BigDecimal xlTotal = new BigDecimal(0);
            BigDecimal zgTotal = new BigDecimal(0);
            for (TaxResourceQualityChangeTable t : finishTable) {
                yyTotal = yyTotal.add(new BigDecimal(t.getB1()));
                lrTotal = lrTotal.add(new BigDecimal(t.getC1()));
                zlTotal = zlTotal.add(new BigDecimal(t.getD1()));
                xlTotal =xlTotal.add(new BigDecimal(t.getE1()));
                zgTotal = zgTotal.add(new BigDecimal(t.getF1()));
            }
            for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
                if (entry.getKey().trim().equals("资产负债率")) {
                    entry.getValue().add(yyTotal);
                } else if (entry.getKey().trim().equals("净资产收益率")) {
                    entry.getValue().add(lrTotal);
                } else if (entry.getKey().trim().equals("主营营业收入利润率")) {
                    entry.getValue().add(zlTotal);
                } else if (entry.getKey().trim().equals("收入成本弹性")) {
                    entry.getValue().add(xlTotal);
                } else {
                    entry.getValue().add(zgTotal);
                }
            }
        }
    }

    /**
     * 生成Line数据源
     *
     * @param caption
     * @return FushionChartDataSource
     */

    private FushionChartDataSource generateLineDataSource(String caption, String xAxisName, String yAxisName, String displayItem) {
        FushionChartDesc chart = new FushionChartDesc();
        chart.setCaption(caption);
        chart.setXAxisName(xAxisName);
        chart.setYAxisName(yAxisName);
        chart.setNumberPerfix("￥");

        FushionChartCategories categories = new FushionChartCategories();

        List<FushionChartCategories> categoriesList = new ArrayList<FushionChartCategories>();
        categoriesList.add(categories);
        List<FushionChartDataSet> dataSet = new ArrayList<FushionChartDataSet>();

        for (Map.Entry<String, List<BigDecimal>> entrySet : this.dataset.entrySet()) {
            FushionChartDataSet dataSet1 = new FushionChartDataSet(entrySet.getKey());
            for (BigDecimal d : entrySet.getValue()) {
                FushionChartData data1 = new FushionChartData();
                data1.setValue(d.toString());
                dataSet1.add(data1);
            }
            dataSet.add(dataSet1);
        }

        FushionChartDataSource dataSource = new FushionChartDataSource(chart, categoriesList, dataSet);
        return dataSource;
    }

    /**
     * 生成饼图 数据源
     *
     * @param caption
     * @param xAxisName
     * @param yAxisName
     * @param displayItem
     * @return FushionChartDataSource
     */
    private FushionChartDataSource generatePieDataSource(String caption, String xAxisName, String yAxisName, String displayItem) {
        FushionChartDesc chart = new FushionChartDesc();
        chart.setCaption(caption);
        chart.setXAxisName(xAxisName);
        chart.setYAxisName(yAxisName);
        chart.setNumberPerfix("￥");

        FushionChartCategories categories = new FushionChartCategories();
        for (int i = 1; i <= dataset.size(); i++) {
            FushionChartCategory f = new FushionChartCategory();
            f.setLabel(i + "月");
            categories.add(f);
        }
        List<FushionChartCategories> categoriesList = new ArrayList<FushionChartCategories>();
        categoriesList.add(categories);
        List<FushionChartDataSet> dataSet = new ArrayList<FushionChartDataSet>();
        FushionChartDataSet dataSet1 = new FushionChartDataSet(displayItem);
        for (int i = 1; i <= this.dataset.size(); i++) {
            FushionChartData data1 = new FushionChartData();
            data1.setValue(this.dataset.get(i - 1).toString());
            dataSet1.add(data1);
        }
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
            json = "{\"chart\":{\"caption\":\"Business Results 2005 v 2006\",\"xaxisname\":\"Month\",\"yaxisname\":\"Revenue\",\"showvalues\":\"0\",\"numberprefix\":\"$\"},\"categories\":[{\"category\":[{\"label\":\"Jan\"},{\"label\":\"Feb\"},{\"label\":\"Mar\"},{\"label\":\"Apr\"},{\"label\":\"May\"},{\"label\":\"Jun\"},{\"vline\":\"true\",\"color\":\"FF5904\",\"thickness\":\"2\"},{\"label\":\"Jul\"},{\"label\":\"Aug\"},{\"label\":\"Sep\"},{\"label\":\"Oct\"},{\"label\":\"Nov\"},{\"label\":\"Dec\"}]}],\"dataset\":[{\"seriesname\":\"2006\",\"dataset\":[{\"value\":\"27400\"},{\"value\":\"29800\"},{\"value\":\"25800\"},{\"value\":\"26800\"},{\"value\":\"29600\"},{\"value\":\"32600\"},{\"value\":\"31800\"},{\"value\":\"36700\"},{\"value\":\"29700\"},{\"value\":\"31900\"},{\"value\":\"34800\"},{\"value\":\"24800\"}]},{\"seriesname\":\"2005\",\"dataset\":[{\"value\":\"10000\"},{\"value\":\"11500\"},{\"value\":\"12500\"},{\"value\":\"15000\"},{\"value\":\"11000\"},{\"value\":\"9800\"},{\"value\":\"11800\"},{\"value\":\"19700\"},{\"value\":\"21700\"},{\"value\":\"21900\"},{\"value\":\"22900\"},{\"value\":\"20800\"}]}],\"trendlines\":{\"line\":[{\"startvalue\":\"26000\",\"color\":\"91C728\",\"displayvalue\":\"Target\",\"showontop\":\"1\"}]},\"styles\": {\"definition\": [{\"name\": \"CanvasAnim\",\"type\": \"animation\",\"param\": \"_xScale\",\"start\": \"0\",\"duration\": \"1\"}],\"application\": [{\"toobject\": \"Canvas\",\"styles\": \"CanvasAnim\"}]}}";
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

}

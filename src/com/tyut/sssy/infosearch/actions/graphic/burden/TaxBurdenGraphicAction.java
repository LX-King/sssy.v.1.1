package com.tyut.sssy.infosearch.actions.graphic.burden;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.infosearch.service.TaxBurdenSearchService;
import com.tyut.sssy.sysmgr.dao.MajorTaxPayerDao;
import com.tyut.sssy.sysmgr.domain.MajorTaxPayerGraphic;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTable;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTableParameter;
import com.tyut.sssy.utils.*;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName:TaxBurdenGraphicAction
 * Function: 税负发展 图表链接
 * Author: LiuXiang
 * Date: 12-7-10
 * Time: 上午12:46
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class TaxBurdenGraphicAction extends ActionSupport implements Preparable {

    private TaxBurdenSearchService service = null;

    private TaxBurdenAnalysisSearchTableParameter parameter = null;

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
        this.parameter = (TaxBurdenAnalysisSearchTableParameter) session.getAttribute(SessionAttributeKey.SFZHFX);
        this.service = new TaxBurdenSearchService();
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
        String xAxisName = Constants.TAX_BURDEN_TYPE[Integer.parseInt(parameter.getSflx())] + " 当期变化";
        String yAxisName = "";
        MajorTaxPayerDao taxPayerDao = new MajorTaxPayerDao();
        Map<String, List<BigDecimal>> dataSet = new LinkedHashMap<String, List<BigDecimal>>();
        dataSet.put("合计",this.dataset.get("合计"));
        for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
            if (!entry.getKey().trim().equals("合计")) {
                MajorTaxPayerGraphic major = taxPayerDao.queryById(entry.getKey().trim());
                dataSet.put(major.getNsrmc(), entry.getValue());
            }
        }
        FushionChartLineJsonData dataSource = new FushionChartLineJsonData(caption, xAxisName, yAxisName, this.categories, dataSet);
        //        FushionChartDataSource dataSource = generateLineDataSource(caption, xAxisName, yAxisName, displayItemService.getById(displayItem).getFlmc());
        printJson(dataSource.toJson());
    }

    /*画柱图*/
    private void drawColumnGraphic() {

        String caption = generateCaption();
        MajorTaxPayerDao taxPayerDao = new MajorTaxPayerDao();
        //生成FushionChartDataSource
        String xAxisName = Constants.TAX_BURDEN_TYPE[Integer.parseInt(parameter.getSflx())] + " 当期变化";
        String yAxisName = "";
        Map<String, List<BigDecimal>> dataSet = new LinkedHashMap<String, List<BigDecimal>>();
        dataSet.put("合计",this.dataset.get("合计"));
        for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
            if (!entry.getKey().trim().equals("合计")) {
                MajorTaxPayerGraphic major = taxPayerDao.queryById(entry.getKey().trim());
                dataSet.put(major.getNsrmc(), entry.getValue());
            }
        }
        FushionChartColumnJsonData dataSource = new FushionChartColumnJsonData(caption, xAxisName, yAxisName, this.categories, dataSet);
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
        return unitName + reportPeriod + "税负类重点企业当期变动情况图表";
    }

    /**
     * 根据显示项目 封装数据
     *
     * @param month
     */
    private void extractData(int month) {

        MajorTaxPayerDao dao = new MajorTaxPayerDao();
        List<MajorTaxPayerGraphic> list = dao.queryAll();
        this.dataset.put("合计", new ArrayList<BigDecimal>());
        for (int j = 0; j < list.size(); j++) {
            String curr = list.get(j).getNsrsbh();
            List<BigDecimal> tempList = new ArrayList<BigDecimal>();
            this.dataset.put(curr, tempList);
        }


        for (int i = 1; i <= month / 3; i++) {
            String temp = String.valueOf(i * 3);
            if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                temp = "0" + temp;
            }
            parameter.setSssqZ(temp);
            List<TaxBurdenAnalysisSearchTable> finishTable = service.getTaxBurdenAnalysisSearchTable(parameter);

            for (TaxBurdenAnalysisSearchTable t : finishTable) {
                if (t.getMc().trim().equals("合计")) {
                    this.dataset.get("合计").add(t.getDqsf());
                } else {
                    for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
                        if (entry.getKey().trim().equals(t.getDm().trim())) {
                            entry.getValue().add(t.getDqsf());
                            break;
                        }
                    }
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

package com.tyut.sssy.infosearch.actions.graphic.tax;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.infosearch.service.TaxCollectionSearchService;
import com.tyut.sssy.sysmgr.dao.TaxCategoryGraphicDao;
import com.tyut.sssy.sysmgr.domain.TaxCategoryGraphic;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTable;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTableParameter;
import com.tyut.sssy.utils.*;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:TaxFinishGraphicAction
 * Function:待解税金变动
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 10:53:53
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class TaxResolveForChangeGraphicAction extends ActionSupport implements Preparable {

    private ToResolveTaxChangeTableParameter parameter = null;

    private TaxCollectionSearchService service = null;

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
        this.parameter = (ToResolveTaxChangeTableParameter) session.getAttribute(SessionAttributeKey.DJSJBD);

        this.service = new TaxCollectionSearchService();
        this.dataset = new LinkedHashMap<String, List<BigDecimal>>();
        this.categories = new ArrayList<String>();
        for (int i = 1; i <= Integer.parseInt(this.parameter.getSqZ()); i++) {
            this.categories.add(i + "月");
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
            drawPieGraphic();
            return null;
        }
    }

    /*画折线图*/
    private void drawLineGraphic() {
        //图表的标题
        String caption = generateCaption();

        //生成FushionChartDataSource
        String xAxisName = "待解税金";
        String yAxisName = "期末余额";
        FushionChartLineJsonData dataSource = new FushionChartLineJsonData(caption, xAxisName, yAxisName, this.categories, this.dataset);
        //        FushionChartDataSource dataSource = generateLineDataSource(caption, xAxisName, yAxisName, displayItemService.getById(displayItem).getFlmc());
        printJson(dataSource.toJson());
    }


    /*画饼图*/
    private void drawPieGraphic() {

        //图表的标题
        String caption = generateCaption();

        //生成FushionChartDataSource
        String xAxisName = "待解税金";
        String yAxisName = "期末余额";
        Map<String, BigDecimal> dataSet = new LinkedHashMap<String, BigDecimal>();
        BigDecimal other = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
            if (entry.getKey().trim().equals("合计")) {
                for (BigDecimal b : entry.getValue()) {
                    total = total.add(b);
                }
            } else {
                BigDecimal tempOther = new BigDecimal(0);
                for (BigDecimal b : entry.getValue()) {
                    tempOther = tempOther.add(b);
                }
                if (other.equals(new BigDecimal(0))) {
                    other = total.subtract(tempOther);
                } else {
                    other = other.subtract(tempOther);
                }
                dataSet.put(entry.getKey(), tempOther.divide(total, 5, BigDecimal.ROUND_HALF_EVEN));
            }
        }

        dataSet.put("其它", other.divide(total, 5, BigDecimal.ROUND_HALF_EVEN));
        FushionChartPieJsonData dataSource = new FushionChartPieJsonData(caption, xAxisName, yAxisName, dataSet);
        //        FushionChartDataSource dataSource = generateLineDataSource(caption, xAxisName, yAxisName, displayItemService.getById(displayItem).getFlmc());
        printJson(dataSource.toJson());

    }


    /*画柱图*/
    private void drawColumnGraphic() {
        //图表的标题
        String caption = generateCaption();

        //生成FushionChartDataSource
        String xAxisName = "待解税金";
        String yAxisName = "期末余额";
        FushionChartColumnJsonData dataSource = new FushionChartColumnJsonData(caption, xAxisName, yAxisName, this.categories, this.dataset);
        printJson(dataSource.toJson());
    }

    /**
     * 生成题头
     *
     * @return String
     */
    private String generateCaption() {
        String monthStr = this.parameter.getSqZ();
        int month = Integer.parseInt(monthStr);
        if (this.dataset.size() == 0)
            extractData(month);
        TaxUnitService taxUnitService = new TaxUnitService();

        //税务机关代码
        String swjgDm = parameter.getSwjg();
        //数据类型
        /*String dataType = parameter.getSjlx();*/
        //报表期
        String reportPeriod = parameter.getSqZ().trim().equals("1") ? parameter.getNd() + "年" + " 1 月" : parameter.getNd() + "年" + " 1 - " + (Integer.parseInt(parameter.getSqZ())) + " 月";
        //税务机关单位名称
        String unitName = taxUnitService.getTaxUnitById(swjgDm).getMc();
        // 数据类型名称

        //图表的标题
        return unitName + reportPeriod + "待解税金变动情况图表";
    }


    /**
     * 根据显示项目 封装数据
     *
     * @param month
     */
    private void extractData(int month) {

        TaxCategoryGraphicDao categoryGaphicDao = new TaxCategoryGraphicDao();
        List<TaxCategoryGraphic> list = categoryGaphicDao.queryAll();
        List<BigDecimal> totalList = new ArrayList<BigDecimal>();
        this.dataset.put("合计", totalList);
        for (int j = 0; j < list.size(); j++) {
            TaxCategoryGraphic currTaxCategory = list.get(j);
            List<BigDecimal> tempList = new ArrayList<BigDecimal>();
            this.dataset.put(currTaxCategory.getMc(), tempList);
        }

        for (int i = 1; i <= month; i++) {
            String temp = String.valueOf(i);
            if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                temp = "0" + temp;
            }
            parameter.setSqZ(temp);
            List<ToResolveTaxChangeTable> table = service.getToResolveChangeTable(parameter);

            for (ToResolveTaxChangeTable t : table) {
                if (t.getXm().trim().equals("合计")) {
                    totalList.add(t.getA10());
                } else {
                    if (this.dataset.containsKey(t.getXm().trim())) {
                        this.dataset.get(t.getXm().trim()).add( t.getA10());
                    }
                }
            }
            for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
                if (!entry.getKey().trim().equals("合计")) {
                    while (entry.getValue().size() < totalList.size()) {
                        entry.getValue().add(new BigDecimal(0));
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
     * 求和
     *
     * @param recorder
     * @return bigdecimal
     */
    private BigDecimal add(ToResolveTaxChangeTable recorder) {
        BigDecimal total = new BigDecimal(0);
        Field[] fields = ToResolveTaxChangeTable.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().contains("a")) {
                try {
                    Method m = ToResolveTaxChangeTable.class.getDeclaredMethod("getA" + f.getName().substring(1));
                    try {
                        total = total.add((BigDecimal) m.invoke(recorder, null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
        }
        return total;
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
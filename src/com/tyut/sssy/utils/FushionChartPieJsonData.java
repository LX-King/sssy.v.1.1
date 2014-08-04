package com.tyut.sssy.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ClassName:FushionChartLineJsonData
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-25
 * Time: 下午2:12
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class FushionChartPieJsonData {

    private StringBuffer json = null;

    public FushionChartPieJsonData(String caption, String xAxisName, String yAxisName, Map<String, BigDecimal> dataset) {

        NumberFormat numberFormat = XMLNumUnitFactory.getInstance().getNumberFormat();
        this.json = new StringBuffer();
        this.json.append("{");
        this.json.append("\"chart\":{");
        this.json.append("\"caption\":\"" + caption + "\",");
        this.json.append("\"baseFontSize\":\"" + 12 + "\",");
        this.json.append("\"xaxisName\":\"" + xAxisName + "\",");
        this.json.append("\"yaxisName\":\"" + yAxisName + "\",");

        this.json.append("\"pieyscale\": \"30\",\n" +
                "\"plotfillalpha\": \"80\",\n" +
                "\"pieinnerfacealpha\": \"60\",\n" +
                "\"slicingdistance\": \"35\",\n" +
                "\"startingangle\": \"190\",\n" +
                "\"numbersuffix\": \"%\",\n" +
                "\"numberscalevalue\":\"" + numberFormat.getValue().trim() + "\",\n" +
                "\"numberscaleunit\":\"" + numberFormat.getUnit().trim() + "\"," +
                "\"showborder\": \"1\",\n" +
                "\"showvalues\": \"1\",\n" +
                "\"showlabels\": \"1\",\n" +
                "\"decimalPrecision\":\"3\"," +
                "\"showlegend\": \"1\"");
        this.json.append("},");
        /*categories*/
        this.json.append("\"data\":[");
        for (Map.Entry<String, BigDecimal> entry : dataset.entrySet()) {
            this.json.append("{ \"label\":\"" + entry.getKey() + "\",");
            this.json.append("\"value\":\"" + entry.getValue().multiply(new BigDecimal(100)) + "\"},");
        }
        this.json.deleteCharAt(this.json.lastIndexOf(","));
        this.json.append("]}");

    }


    /**
     * 返回  FushionChart 格式 LineJson
     *
     * @return String
     */
    public String toJson() {
        return this.json.toString();
    }
}

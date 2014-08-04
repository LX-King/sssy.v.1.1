package com.tyut.sssy.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ClassName:FushionChartLineJsonData
 * Function:FushionChart图表画线图工具
 * Author: LiuXiang
 * Date: 12-7-25
 * Time: 下午2:12
 * Mail:LXiang.tyut@gmail.com
 */
public class FushionChartLineJsonData {

    private StringBuffer json = null;

    public FushionChartLineJsonData(String caption, String xAxisName, String yAxisName, List<String> categorys, Map<String, List<BigDecimal>> dataset) {
        this.json = new StringBuffer();
        this.json.append("{");
        this.json.append("\"chart\":{");
        this.json.append("\"caption\":\"" + caption + "\",");
        this.json.append("\"xaxisName\":\"" + xAxisName + "\",");
        this.json.append("\"yaxisName\":\"" + yAxisName + "\",");

        this.json.append("\"baseFontSize\":\"" + 12 + "\",");
        NumberFormat numberFormat = XMLNumUnitFactory.getInstance().getNumberFormat();
        this.json.append(" \"linethickness\": \"2\",\n" +
                "\"showvalues\": \"0\",\n" +
                "\"numDivLines \": \"26\",\n" +
                "\"formatnumberscale\": \"1\",\n" +
                "\"numberscalevalue\":\"" + numberFormat.getValue().trim() + "\",\n" +
                "\"numberscaleunit\":\"" + numberFormat.getUnit().trim() + "\"," +
                "\"showvalues\": \"0\"," +
                "\"slantlabels\": \"1\",\n" +
                "\"anchorradius\": \"2\",\n" +
                "\"anchorbgalpha\": \"50\",\n" +
                "\"showalternatevgridcolor\": \"1\",\n" +
                "\"anchoralpha\": \"100\",\n" +
                "\"decimalPrecision\":\"3\"," +
                "\"animation\": \"1\",\n" +
                "\"limitsdecimalprecision\": \"3\",\n" +
                "\"divlinedecimalprecision\": \"3\"");
        this.json.append("},");
        /*categories*/
        this.json.append("\"categories\":[{ \"category\":[");
        for (String category : categorys) {
            this.json.append("{ \"label\":\"" + category + "\"},");
        }
        this.json.deleteCharAt(this.json.lastIndexOf(","));

        this.json.append("]}],");
        this.json.append("\"dataset\":[");
        for (Map.Entry<String, List<BigDecimal>> entry : dataset.entrySet()) {
            this.json.append("{");
            this.json.append("\"seriesname\":\"" + entry.getKey() + "\",");
            this.json.append("\"data\":[");
            for (BigDecimal data : entry.getValue()) {
                this.json.append("{");
                this.json.append("\"value\":\"" + data.toString() + "\"},");
            }
            this.json.deleteCharAt(this.json.lastIndexOf(","));
            this.json.append("]},");
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

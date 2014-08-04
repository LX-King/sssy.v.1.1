package com.tyut.sssy.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * ClassName:FushionChartDataSet
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 17:06:22
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FushionChartDataSet {

    private String seriesname;

    private List<FushionChartData> data;

    public FushionChartDataSet(String seriesName) {
        this.seriesname = seriesName;
        this.data = new ArrayList<FushionChartData>();
    }

    public void add(FushionChartData f) {
        this.data.add(f);
    }

    public String getSeriesname() {
        return seriesname;
    }

    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname;
    }

    public List<FushionChartData> getData() {
        return data;
    }

    public void setData(List<FushionChartData> data) {
        this.data = data;
    }
}

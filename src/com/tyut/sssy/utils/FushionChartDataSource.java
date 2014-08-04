package com.tyut.sssy.utils;

import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * ClassName:FushionChartJsonDataSource
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-27
 * Time: 12:20:14
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FushionChartDataSource {

    private List<FushionChartDataSet> dataSet = null;

    private FushionChartDesc chart = null;

    private List<FushionChartCategories> categorys = null;


    public FushionChartDataSource(FushionChartDesc chart , List<FushionChartCategories> fushionChartCategories,List<FushionChartDataSet> dataSet){
        this.dataSet = dataSet;
        this.categorys = fushionChartCategories;
        this.chart = chart;
    }

    public String getDataSource(){
        Map<String,Object> map  =  new HashMap<String,Object>();
        map.put("chart" , this.chart);
        map.put("categories",this.categorys);
        map.put("dataset",this.dataSet);
        String dataSource = JSONArray.fromObject(map).toString();
        return dataSource.substring(1,dataSource.lastIndexOf("]"));

    }




    
}

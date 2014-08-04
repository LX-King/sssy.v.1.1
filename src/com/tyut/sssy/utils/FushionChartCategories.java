package com.tyut.sssy.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * ClassName:FushionChartCategories
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 17:04:34
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class FushionChartCategories {

    private List<FushionChartCategory> category ;

    public FushionChartCategories(){
        this.category = new ArrayList<FushionChartCategory>();
    }

    public void add(FushionChartCategory f){
        this.category.add(f);
    }

    public List<FushionChartCategory> getCategory() {
        return category;
    }

    public void setCategory(List<FushionChartCategory> category) {
        this.category = category;
    }
}

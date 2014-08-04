package com.tyut.sssy.sysmgr.domain;

import java.math.BigDecimal;

/**
 * ClassName:DIFilterCon
 * Function:发展指数过滤条件维护
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 19:02:22
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DIFilterCon {

    private int id; //主键

    private BigDecimal maxValue; //最大值

    private BigDecimal minValue;//最小值

    private BigDecimal maxValueDef; //最大值趋势

    private BigDecimal minValueDef;//最小值趋势

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValueDef() {
        return maxValueDef;
    }

    public void setMaxValueDef(BigDecimal maxValueDef) {
        this.maxValueDef = maxValueDef;
    }

    public BigDecimal getMinValueDef() {
        return minValueDef;
    }

    public void setMinValueDef(BigDecimal minValueDef) {
        this.minValueDef = minValueDef;
    }
}

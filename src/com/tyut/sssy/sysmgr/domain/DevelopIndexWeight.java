package com.tyut.sssy.sysmgr.domain;

import java.math.BigDecimal;

/**
 * ClassName:DevelopIndexWeight
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-15
 * Time: 14:23:49
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DevelopIndexWeight {

    private int id ; //主键

    private String indexCode; //指标代码

    private String indexName ; //指标名称

    private BigDecimal indexWeight; // 指标权重


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public BigDecimal getIndexWeight() {
        return indexWeight;
    }

    public void setIndexWeight(BigDecimal indexWeight) {
        this.indexWeight = indexWeight;
    }
}

package com.tyut.sssy.sysmgr.domain;

import java.math.BigDecimal;

/**
 * ClassName:IndexWeightAndValue
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 22:39:16
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexWeightAndValue {

    private String indexCode ; //指标编码

    private Index index ; //指标

    private BigDecimal unusualRange ; //异常区间

    private BigDecimal usualRange ; //正常区间

    private BigDecimal unusualRange2;//异常区间2

    private BigDecimal excellentRange ;//优势区间

    private BigDecimal inferiorRange;//劣势区间

    private int weight;//权值

    private String indexName ;

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public BigDecimal getUnusualRange() {
        return unusualRange;
    }

    public void setUnusualRange(BigDecimal unusualRange) {
        this.unusualRange = unusualRange;
    }

    public BigDecimal getUsualRange() {
        return usualRange;
    }

    public void setUsualRange(BigDecimal usualRange) {
        this.usualRange = usualRange;
    }

    public BigDecimal getUnusualRange2() {
        return unusualRange2;
    }

    public void setUnusualRange2(BigDecimal unusualRange2) {
        this.unusualRange2 = unusualRange2;
    }

    public BigDecimal getExcellentRange() {
        return excellentRange;
    }

    public void setExcellentRange(BigDecimal excellentRange) {
        this.excellentRange = excellentRange;
    }

    public BigDecimal getInferiorRange() {
        return inferiorRange;
    }

    public void setInferiorRange(BigDecimal inferiorRange) {
        this.inferiorRange = inferiorRange;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIndexName() {
        return this.index.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
        this.index.setIndexName(indexName);
    }
}

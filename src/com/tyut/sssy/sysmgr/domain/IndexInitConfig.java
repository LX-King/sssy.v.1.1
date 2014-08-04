package com.tyut.sssy.sysmgr.domain;

import java.math.BigDecimal;

/**
 * ClassName:IndexInitConfig
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 15:46:42
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexInitConfig {

    private String indexCode; //代码

    private String indexName; //名称

    private BigDecimal unusualRange; //异常区间

    private BigDecimal usualRange; //正常区间

    private BigDecimal unusualRange2;//异常区间2

    private BigDecimal excellentRange;//优势区间

    private BigDecimal inferiorRange;//劣势区间

    private BigDecimal unusualRangeDef; //异常区间缺省

    private BigDecimal usualRangeDef; //正常区间缺省

    private BigDecimal unusualRange2Def;//异常区间2缺省

    private BigDecimal excellentRangeDef;//优势区间缺省

    private BigDecimal inferiorRangeDef;//劣势区间缺省

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

    public BigDecimal getUnusualRangeDef() {
        return unusualRangeDef;
    }

    public void setUnusualRangeDef(BigDecimal unusualRangeDef) {
        this.unusualRangeDef = unusualRangeDef;
    }

    public BigDecimal getUsualRangeDef() {
        return usualRangeDef;
    }

    public void setUsualRangeDef(BigDecimal usualRangeDef) {
        this.usualRangeDef = usualRangeDef;
    }

    public BigDecimal getUnusualRange2Def() {
        return unusualRange2Def;
    }

    public void setUnusualRange2Def(BigDecimal unusualRange2Def) {
        this.unusualRange2Def = unusualRange2Def;
    }

    public BigDecimal getExcellentRangeDef() {
        return excellentRangeDef;
    }

    public void setExcellentRangeDef(BigDecimal excellentRangeDef) {
        this.excellentRangeDef = excellentRangeDef;
    }

    public BigDecimal getInferiorRangeDef() {
        return inferiorRangeDef;
    }

    public void setInferiorRangeDef(BigDecimal inferiorRangeDef) {
        this.inferiorRangeDef = inferiorRangeDef;
    }
}

package com.tyut.sssy.sysmgr.domain;

import java.math.BigDecimal;

/**
 * ClassName:CompanyScale
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-11
 * Time: 9:44:54
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class CompanyScale {

    /*对应的数据库表*/

    private String csCode ;       //企业规模代码

    private String csName ;       //名称

    private BigDecimal minLimit ; //下限

    private BigDecimal maxLimit ; //上限

    public String getCsCode() {
        return csCode;
    }

    public void setCsCode(String csCode) {
        this.csCode = csCode;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public BigDecimal getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(BigDecimal minLimit) {
        this.minLimit = minLimit;
    }

    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(BigDecimal maxLimit) {
        this.maxLimit = maxLimit;
    }
}

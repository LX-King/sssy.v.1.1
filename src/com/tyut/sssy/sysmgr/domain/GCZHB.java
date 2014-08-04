package com.tyut.sssy.sysmgr.domain;

import java.math.BigDecimal;

/**
 * ClassName:ImportantCompanyBaseInfo
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-31
 * Time: 16:07:04
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class GCZHB {


    private int id ;//主键

    private String nd;  //年度

    private String sssq_q;  //所属时期起

    private String sssq_z;  //所属时期止

    private String qygm ;   //企业规模

    private BigDecimal snsjse;//上年实缴税额



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getSssq_q() {
        return sssq_q;
    }

    public void setSssq_q(String sssq_q) {
        this.sssq_q = sssq_q;
    }

    public String getSssq_z() {
        return sssq_z;
    }

    public void setSssq_z(String sssq_z) {
        this.sssq_z = sssq_z;
    }

    public String getQygm() {
        return qygm;
    }

    public void setQygm(String qygm) {
        this.qygm = qygm;
    }

    public BigDecimal getSnsjse() {
        return snsjse;
    }

    public void setSnsjse(BigDecimal snsjse) {
        this.snsjse = snsjse;
    }
}

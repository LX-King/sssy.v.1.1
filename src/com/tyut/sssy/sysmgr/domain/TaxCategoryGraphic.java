package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:TaxCategory
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-25
 * Time: 上午10:23
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class TaxCategoryGraphic {

    private int id;

    private String szDm;    // 税种代码

    private String mc;        // 名称

    private String yxbz;    // 有效标志


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSzDm() {
        return szDm;
    }

    public void setSzDm(String szDm) {
        this.szDm = szDm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }
}

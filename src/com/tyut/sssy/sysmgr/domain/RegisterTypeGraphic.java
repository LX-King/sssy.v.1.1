package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:RegisterTypeGraphic
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-26
 * Time: 上午9:04
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class RegisterTypeGraphic {

    private int id;

    private String qyzclxDm;    // 企业注册类型代码

    private String mc;            // 企业注册类型名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQyzclxDm() {
        return qyzclxDm;
    }

    public void setQyzclxDm(String qyzclxDm) {
        this.qyzclxDm = qyzclxDm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}

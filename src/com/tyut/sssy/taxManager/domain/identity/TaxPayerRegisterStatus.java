package com.tyut.sssy.taxManager.domain.identity;

/**
 * 项目名称：sssyrisk
 * 类名称：纳税人状态实体
 * 创建人：刘翔
 * 创建时间：${year}-${month}-${day}
 *
 * @version 1.1
 */
public class TaxPayerRegisterStatus {

    private  String djztDm;

    private  String mc;

    public String getDjztDm() {
        return djztDm;
    }

    public void setDjztDm(String djztDm) {
        this.djztDm = djztDm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}

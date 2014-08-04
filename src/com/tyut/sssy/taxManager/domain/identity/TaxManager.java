package com.tyut.sssy.taxManager.domain.identity;

/**
 * 项目名称：sssyrisk
 * 类名称：z征管员
 * 类描述：微观任务类
 * 创建人：刘翔
 * 修改人：刘翔
 * 修改时间：14-5-25
 * 修改备注：
 */
public class TaxManager {

    private String zgyDm ; //征管员代码

    private String mc ; //名称

    private String swjgDm ; //税务机关代码

    public String getZgyDm() {
        return zgyDm;
    }

    public void setZgyDm(String zgyDm) {
        this.zgyDm = zgyDm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }
}

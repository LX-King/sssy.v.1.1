package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:MajorIndustry
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 14:25:05
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class MajorIndustry {

    private String majorIndustryCode ; //主要行业代码

    private String majorIndustryName ; //名称

    private String jc ;

    private String gmlxDm;

    private String industryCode ;//产业代码

    private String industryName ;

    private String flag ; //有效表之

    public String getMajorIndustryCode() {
        return majorIndustryCode;
    }

    public void setMajorIndustryCode(String majorIndustryCode) {
        this.majorIndustryCode = majorIndustryCode;
    }

    public String getMajorIndustryName() {
        return majorIndustryName;
    }

    public void setMajorIndustryName(String majorIndustryName) {
        this.majorIndustryName = majorIndustryName;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getGmlxDm() {
        return gmlxDm;
    }

    public void setGmlxDm(String gmlxDm) {
        this.gmlxDm = gmlxDm;
    }
}

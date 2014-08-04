package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:UnitEmpBusiness
 * Function:单位重点行业
 * Author: LiuXiang
 * Date: 2012-5-14
 * Time: 14:04:29
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class UnitEmpBusiness {

   /* 对应于数据库的 dm_hydl */ 

    private String ebCode ; //行业大类代码

    private String ebName ; //名称

    private String industryCode ; //产业代码

    private String flag ; //标志位

    public String getEbCode() {
        return ebCode;
    }

    public void setEbCode(String ebCode) {
        this.ebCode = ebCode;
    }

    public String getEbName() {
        return ebName;
    }

    public void setEbName(String ebName) {
        this.ebName = ebName;
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
}

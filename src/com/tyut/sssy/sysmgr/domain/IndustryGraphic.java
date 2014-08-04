package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:IndustryGraphic
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 12-7-25
 * Time: 上午9:44
 * Mail:LXiang.tyut@gmail.com
 * Company:
 */
public class IndustryGraphic {

    private int id ;

    private String industryCode;//产业代码

    private String industryName;//产业名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}

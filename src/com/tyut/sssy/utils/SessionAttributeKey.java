package com.tyut.sssy.utils;

/**
 * ClassName:SessionAttributeKey
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-6-20
 * Time: 23:35:11
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class SessionAttributeKey {

    /*登录用户保存名称*/
    public static final String LOGIN_USER = "loginUser";

    /*角色修改--*/
    public static final String MODIFIED_ROLE = "modifyRole";

    /**
     * 税源类查询参数类
     */

    /*重点税源监测户统计*/
    public static final String ZDSY_JCHS_TJ = "taxResourceMonitorTableParameter";

    /*重点税源监控企业明细表*/
    public static final String ZDSY_JK_QYMX = "taxResourceMonitorFirmDetailTableParameter";

    /*企业基础信息*/
    public static final String QYJCXX = "firmInfoSearchTableParameter";

    /*企业经营信息*/
    public static final String QYJYXX = "firmRunInfoTableParameter";

    /*税收收入完成情况*/
    public static final String SSSRWC = "taxCollectionFinishTableParameter";

    /*税收收入结构变化*/
    public static final String SSSRJGBH = "taxCollectionStructureChangeTableParameter";


    /*税收类所有指标*/
    public static final String SSLSYZB = "taxCollectionAllIndexTableParameter";

    /*税源类所有指标*/
    public static final String SYLSYZB = "taxResourceAllIndexTableParameter";

    /*税负类所有指标*/
    public static final String SFLSYZB = "taxBurdenAllIndexTableParameter";

    /*待解税金余额*/
    public static final String DJSJYE = "toResolveBalanceTableParameter";

    //待解税金变动
    public static final String DJSJBD = "toResolveTaxChangeTableParameter";

    /*欠缴税金明细*/
    public static final String QJSJMX = "ownPayTaxDetailTableParameter";

    /*税源总量增减*/
    public static final String SYZLZJ = "taxResourceSumChangeTableParameter";

    /*税源质量变动*/
    public static final String SYZLBD = "taxResourceQualityChangeTableParameter";

    /*税负分析*/
    public static final String SFZHFX = "taxBurdenAnalysisSearchTableParameter";

    /*决策类税源发展指数*/
    public static final String JCSYSS = "taxGrowTaxResourceDevelopIndexTableParameter";
}

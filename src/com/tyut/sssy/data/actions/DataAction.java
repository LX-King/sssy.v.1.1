package com.tyut.sssy.data.actions;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.data.domain.DataImportQt;
import com.tyut.sssy.data.domain.DataImportZdsy;
import com.tyut.sssy.data.service.DataService;
import com.tyut.sssy.task.service.MacroTaskService;
import com.tyut.sssy.task.service.MicroTaskService;
import com.tyut.sssy.task.service.RiskMicroTaskService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

// 数据导入与分析action
public class DataAction {

    /**
     * 综合征管软件数据导入
     *
     * @return
     */
    public String zrDataImport() {

        HttpServletRequest request = ServletActionContext.getRequest();

        DataService dataService = new DataService();
        DataImportQt dataImportQt = new DataImportQt();

        // 导入数据类型
        String importDataType[] = request.getParameterValues("importDataType");
        for (int i = 0; i < importDataType.length; i++) {
            if (importDataType[i].equals("ruku"))
                dataImportQt.setRkbz("Y");
            else if (importDataType[i].equals("shijiao"))
                dataImportQt.setSjbz("Y");
            else if (importDataType[i].equals("daijie"))
                dataImportQt.setDjbz("Y");
            else if (importDataType[i].equals("qianjiao"))
                dataImportQt.setQjbz("Y");
        }

        // 频度
        String intervalType = request.getParameter("intervalType");

        // 数据导入频度包括 日，旬，月
        if (intervalType.equals("day")) {
            // 日
            String daySpanDay = request.getParameter("daySpanDay");

            String fxqNd = daySpanDay.substring(0, 4);
            String fxqYf = daySpanDay.substring(5, 7);
            String fxqSssqZ = daySpanDay.substring(8);
            String pd = "日";

            dataImportQt.setFxqNd(fxqNd);
            dataImportQt.setFxqYf(fxqYf);
            dataImportQt.setFxqSssqQ("01");
            dataImportQt.setFxqSssqZ(fxqSssqZ);
            dataImportQt.setPd(pd);

        } else if (intervalType.equals("xun")) {
            // 旬
            String fxqNd = request.getParameter("xunSpanYear");
            String fxqYf = request.getParameter("xunSpanMonth");
            if (!fxqYf.equals("10") && !fxqYf.equals("11") && !fxqYf.equals("12")) {
                fxqYf = "0" + fxqYf;
            }

            String xun = request.getParameter("xunSpanXun");
            String pd = xun;

            dataImportQt.setFxqNd(fxqNd);
            dataImportQt.setFxqYf(fxqYf);
            dataImportQt.setFxqSssqQ("");
            dataImportQt.setFxqSssqZ("");
            dataImportQt.setPd(pd);
        } else if (intervalType.equals("month")) {
            // 月
            String fxqNd = request.getParameter("monthSpanYear");
            String fxqYf = request.getParameter("monthSpanMonth");
            if (!fxqYf.equals("10") && !fxqYf.equals("11") && !fxqYf.equals("12")) {
                fxqYf = "0" + fxqYf;
            }

            String pd = "月";

            dataImportQt.setFxqNd(fxqNd);
            dataImportQt.setFxqYf(fxqYf);
            dataImportQt.setFxqSssqQ("");
            dataImportQt.setFxqSssqZ("");
            dataImportQt.setPd(pd);
        }

        // 数据导入
        dataService.zrDataImport(dataImportQt);

        request.setAttribute("msg", "综合征管软件 数据导入完成！");
        return "zr_data_import";
    }

    /**
     * 数据分析
     *
     * @return
     */
    public String dataAnalyze() {

        HttpServletRequest request = ServletActionContext.getRequest();

        DataService dataService = new DataService();

        DataCalcParameter dataCalcParameter = new DataCalcParameter();        // 数据分析参数

        // 季
        String fxqNd = request.getParameter("year");
        String monthPeriod = request.getParameter("monthPeriod");

        String fxqSssqQ = monthPeriod.substring(0, 2);
        String fxqSssqZ = monthPeriod.substring(3);

        dataCalcParameter.setFxqNd(fxqNd);
        dataCalcParameter.setFxqSssqQ(fxqSssqQ);
        dataCalcParameter.setFxqSssqZ(fxqSssqZ);

        // 数据分析
        dataService.dataAnalyze(dataCalcParameter);
        dataService.dataAnalyze2(dataCalcParameter);

        // 向微观任务表插入>=阈值的记录
//		dataService.insertDataIntoMicroTaskTable(dataCalcParameter);

        request.setAttribute("msg", "数据分析完成！");
        return "data_analyze_succ";
    }

    /**
     * 重点税源数据导入
     *
     * @return
     */
    public String zdsyDataImport() {
        HttpServletRequest request = ServletActionContext.getRequest();
        DataImportZdsy dataImportZdsy = new DataImportZdsy();

        String fxqNd = request.getParameter("zdsyTimeSpanYear");
        String zdsyTimeSpanMonthPeriod = request.getParameter("zdsyTimeSpanMonthPeriod");
        String fxqSssqQ = zdsyTimeSpanMonthPeriod.substring(0, 2);
        String fxqSssqZ = zdsyTimeSpanMonthPeriod.substring(3, 5);
        dataImportZdsy.setFxqNd(fxqNd);
        dataImportZdsy.setFxqSssqQ(fxqSssqQ);
        dataImportZdsy.setFxqSssqZ(fxqSssqZ);

        DataService dataService = new DataService();
        dataService.zdsyDataImport(dataImportZdsy);

        request.setAttribute("msg", "重点税源  数据导入完成！");
        return "zdsy_data_import";
    }

    /**
     * 代码刷新
     *
     * @return
     */
    public String refreshCode() {
        HttpServletRequest request = ServletActionContext.getRequest();
        DataService dataService = new DataService();
        dataService.refreshCode();
        request.setAttribute("msg", "代码刷新成功！");
        return "refresh_code";
    }

    /**
     * 生成任务数据
     *
     * @return
     *
     * 备注：修改了其中的微观任务导入
     * 修改人：刘翔
     * 日期：2013-07-08
     */
    public String dataGenerate() {

        HttpServletRequest request = ServletActionContext.getRequest();

        DataService dataService = new DataService();

        DataCalcParameter dataCalcParameter = new DataCalcParameter();        // 数据分析参数

        String isOverrided = request.getParameter("isOverrided");

        // 季
        String fxqNd = request.getParameter("dataGenerateForm_year");
        String monthPeriod = request.getParameter("dataGenerateForm_monthPeriod");

        String fxqSssqQ = monthPeriod.substring(0, 2);
        String fxqSssqZ = monthPeriod.substring(3);

        dataCalcParameter.setFxqNd(fxqNd);
        dataCalcParameter.setFxqSssqQ(fxqSssqQ);
        dataCalcParameter.setFxqSssqZ(fxqSssqZ);

        // 向微观任务表插入>=阈值的记录
        // 1. 删除相同分析期的任务记录
        if (isOverrided.equals("y")) {
            // 1.1  删除微观任务
           // RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();
            //riskMicroTaskService.deleteByFxq(dataCalcParameter);

            // 1.2 删除总/分量任务
            MacroTaskService macroTaskService = new MacroTaskService();
            macroTaskService.deleteByFxq(dataCalcParameter);
        }

        if (isOverrided.equals("n")) {
            // 1.1  删除微观任务
           // RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();
           // riskMicroTaskService.deleteByFxqAndZt(dataCalcParameter);

            // 1.2 删除总/分量任务
            MacroTaskService macroTaskService = new MacroTaskService();
            macroTaskService.deleteByFxqAndZt(dataCalcParameter);
        }

        // 2. 插入新的任务记录
        // 2.1  插入微观任务
       // dataService.insertDataIntoMicroTaskTable(dataCalcParameter , isOverrided);

        //导入风险指数微观任务
        dataService.importRiskMicroTask(dataCalcParameter,isOverrided);

        // 2.2 插入总/分量任务
        dataService.insertDataIntoMacroTaskTable(dataCalcParameter , isOverrided);



        request.setAttribute("msg", "任务数据生成完成！");
        return "data_generate_succ";
    }
}

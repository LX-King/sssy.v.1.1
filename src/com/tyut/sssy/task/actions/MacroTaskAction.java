package com.tyut.sssy.task.actions;

import com.opensymphony.xwork2.ActionContext;
import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.AnalysisIndexService;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.IndexFeatureDBService;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.domain.AnalysisMacroIndex;
import com.tyut.sssy.task.domain.MacroTask;
import com.tyut.sssy.task.domain.MacroTaskExtractParameter;
import com.tyut.sssy.task.domain.MacroTaskSurveyReport;
import com.tyut.sssy.task.service.AnalysisMacroIndexService;
import com.tyut.sssy.task.service.MacroTaskService;
import com.tyut.sssy.task.service.MacroTaskSurveyReportService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目名称：sssy20120713
 * 类名称：MacroTaskAction
 * 类描述：总/分量任务action
 * 创建人：梁斌
 * 创建时间：2012-7-18 下午01:07:43
 * 修改人：梁斌
 * 修改时间：2012-7-18 下午01:07:43
 * 修改备注：
 */
public class MacroTaskAction {

    /**
     * 显示总/分量任务提取条件选择
     *
     * @return
     */
    public String showMacroTaskExtractConditionUI() {

        //  管理机关列表
        TaxUnitService taxUnitService = new TaxUnitService();
//		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
        List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitListByType("Y");

        // 行业大类列表
        BigIndustryService bigIndustryService = new BigIndustryService();
        List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

        // 企业注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();

        // 指标名称
        AnalysisIndexService analysisIndexService = new AnalysisIndexService();
        List<AnalysisIndex> analysisIndexList = analysisIndexService.getAnalysisIndexList();

        HttpServletRequest request = ServletActionContext.getRequest();

        request.setAttribute("taxUnitList", taxUnitList);
        request.setAttribute("bigIndustryList", bigIndustryList);
        request.setAttribute("firmRegTypeList", firmRegTypeList);
        request.setAttribute("analysisIndexList", analysisIndexList);

        return "show_macro_task_extract_condition_ui";
    }

    /**
     * 显示总/分量任务提取列表
     *
     * @return
     */
    public String showMacroTaskExtractTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String type = request.getParameter("type");
        MacroTaskExtractParameter macroTaskExtractParameter = new MacroTaskExtractParameter();

        String fxq = "";

        // 非排序
        if (null == type) {
            if (null == type) {
                type = "none";
            }
            String order = request.getParameter("order");
            if (null == order) {
                order = "normal";
            }

            // 分析期
            String fxqNd = request.getParameter("fxqNd");
            String monthPeriod = request.getParameter("monthPeriod");
            String fxqSjq = "01";
            String fxqSjz = monthPeriod.substring(3);
            fxq = fxqNd + "-" + fxqSjq + " -- " + fxqNd + "-" + fxqSjz;

            String zflLx = request.getParameter("zflLx");
            String swjgDm = "";
            String hydlDm = "";
            String qyzclxDm = "";
            if (zflLx.equals("管理机关")) {
                // 总分量类型为 管理机关
                swjgDm = request.getParameter("swjgDm");
                if ("0".equals(swjgDm)) {
                    swjgDm = "%";
                }
            } else if (zflLx.equals("行业")) {
                // 总分量类型为 行业
                hydlDm = request.getParameter("hydlDm");
                if ("".equals(hydlDm)) {
                    hydlDm = "%";
                }
            } else if (zflLx.equals("注册类型")) {
                // 总分量类型为 注册类型
                qyzclxDm = request.getParameter("qyzclxDm");
                if ("".equals(qyzclxDm)) {
                    qyzclxDm = "%";
                }
            }

            // 指标名称
            String fxzbDm = request.getParameter("fxzbDm");

            if ("".equals(fxzbDm)) {
                fxzbDm = "%";
            }

            // 任务状态
            String rwztDm = request.getParameter("rwztDm");
            if ("".equals(rwztDm)) {
                rwztDm = "%";
            }

            macroTaskExtractParameter.setFxqNd(fxqNd);
            macroTaskExtractParameter.setFxqSjq(fxqSjq);
            macroTaskExtractParameter.setFxqSjz(fxqSjz);
            macroTaskExtractParameter.setZflLx(zflLx);
            macroTaskExtractParameter.setSwjgDm(swjgDm);
            macroTaskExtractParameter.setHydlDm(hydlDm);
            macroTaskExtractParameter.setQyzclxDm(qyzclxDm);
            macroTaskExtractParameter.setFxzbDm(fxzbDm);
            macroTaskExtractParameter.setRwztDm(rwztDm);
            macroTaskExtractParameter.setType(type);
            macroTaskExtractParameter.setOrder(order);
        } else {
            // 排序
            String fxqNd = request.getParameter("fxqNd");
            String fxqSjq = request.getParameter("fxqSjq");
            String fxqSjz = request.getParameter("fxqSjz");
            String zflLx = request.getParameter("zflLx");
            try {
                zflLx = URLDecoder.decode(zflLx, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String swjgDm = request.getParameter("swjgDm");
            String hydlDm = request.getParameter("hydlDm");
            String qyzclxDm = request.getParameter("qyzclxDm");
            String fxzbDm = request.getParameter("fxzbDm");
            String rwztDm = request.getParameter("rwztDm");
            type = request.getParameter("type");
            String order = request.getParameter("order");

            // 指标名称
            if ("".equals(fxzbDm)) {
                fxzbDm = "%";
            }

            macroTaskExtractParameter.setFxqNd(fxqNd);
            macroTaskExtractParameter.setFxqSjq(fxqSjq);
            macroTaskExtractParameter.setFxqSjz(fxqSjz);
            macroTaskExtractParameter.setZflLx(zflLx);
            macroTaskExtractParameter.setSwjgDm(swjgDm);
            macroTaskExtractParameter.setHydlDm(hydlDm);
            macroTaskExtractParameter.setQyzclxDm(qyzclxDm);
            macroTaskExtractParameter.setFxzbDm(fxzbDm);
            macroTaskExtractParameter.setRwztDm(rwztDm);
            macroTaskExtractParameter.setType(type);
            macroTaskExtractParameter.setOrder(order);
        }

        MacroTaskService macroTaskService = new MacroTaskService();
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListInExtractSection(macroTaskExtractParameter);

        request.setAttribute("fxq", fxq);

        if (macroTaskExtractParameter.getSwjgDm().equals("%"))
            macroTaskExtractParameter.setSwjgDm("0");
        if (macroTaskExtractParameter.getHydlDm().equals("%"))
            macroTaskExtractParameter.setHydlDm("");
        if (macroTaskExtractParameter.getQyzclxDm().equals("%"))
            macroTaskExtractParameter.setQyzclxDm("");
        if (macroTaskExtractParameter.getFxzbDm().equals("%"))
            macroTaskExtractParameter.setFxzbDm("");

        request.setAttribute("macroTaskExtractParameter", macroTaskExtractParameter);
        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_extract_table";
    }

    /**
     * 指标详情
     *
     * @return
     */
    public String showIndexDetail() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String fxzbDm = request.getParameter("fxzbDm");
        String tzqj = request.getParameter("tzqj");
        int id = Integer.valueOf(request.getParameter("id"));

        IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
        IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(fxzbDm, tzqj);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);
        BigDecimal bdl = macroTask.getBdl();

        float bdlFloat = 0.0f;
        if (bdl != null)
            bdlFloat = bdl.floatValue();
        request.setAttribute("indexFeatureDB", indexFeatureDB);
        request.setAttribute("bdlFloat", bdlFloat);

        String fxq = macroTask.getFxqNd() + "-" + macroTask.getFxqSjq() + " -- " + macroTask.getFxqNd() + "-" + macroTask.getFxqSjz();

        // 显示变动率计算过程

        String tzqj1 = macroTask.getTzqj();
        String fxqNd = macroTask.getFxqNd();
        String fxqSjq = macroTask.getFxqSjq();
        String fxqSjz = macroTask.getFxqSjz();
        String fxzblxMx = macroTask.getFxzblxMx();
        String fxzblxMxDm = macroTask.getFxzblxMxDm();

        String zgswjgdm = macroTask.getSwjgDm();
        fxzbDm = macroTask.getFxzbDm();

        AnalysisMacroIndexService analysisMacroIndexService = new AnalysisMacroIndexService();
        AnalysisMacroIndex analysisMacroIndex = analysisMacroIndexService.getForProcess(fxqNd, fxqSjq, fxqSjz, fxzbDm, tzqj1, fxzblxMx, fxzblxMxDm, zgswjgdm);    // 根据参数获得分析微观指标
        if(analysisMacroIndex.getBdl() != null)
        if (analysisMacroIndex.getBdl().toString().trim().equals("0E-8")) {
            analysisMacroIndex.setBdl(new BigDecimal(0));
        }
        AnalysisIndexService analysisIndexService = new AnalysisIndexService();
        String zbMc = analysisIndexService.getAnalysisIndexById(fxzbDm).getMc();

        request.setAttribute("analysisMicroIndex", analysisMacroIndex);
        request.setAttribute("macroTask", macroTask);
        request.setAttribute("zbMc", zbMc);
        request.setAttribute("fxq", fxq);

        return "show_index_detail";
    }

    /**
     * 提取总/分量任务
     *
     * @return
     */
    public String extractMacroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStrArr[] = request.getParameterValues("macroTaskId");

        User user = (User) ActionContext.getContext().getSession().get("loginUser");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String rwtqRq = sdf.format(date);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = null;
        for (int i = 0; i < idStrArr.length; i++) {
            int id = Integer.valueOf(idStrArr[i]);
            macroTask = new MacroTask();
            macroTask = macroTaskService.getMacroTaskById(id);

            macroTask.setRwtqryDm(user.getCode());
            macroTask.setRwtqRq(rwtqRq);
            macroTask.setRwztDm("01");

            macroTaskService.update(macroTask);
        }

        request.setAttribute("msg", "提取成功！");

        return "extract_macro_task";
    }

    /**
     * 显示总/分量任务发布条件选择
     *
     * @return
     */
    public String showMacroTaskPublishConditionUI() {

        //  管理机关列表
        TaxUnitService taxUnitService = new TaxUnitService();
//		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
        List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitListByType("Y");

        // 行业大类列表
        BigIndustryService bigIndustryService = new BigIndustryService();
        List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();

        // 企业注册类型
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();

        // 指标名称
        AnalysisIndexService analysisIndexService = new AnalysisIndexService();
        List<AnalysisIndex> analysisIndexList = analysisIndexService.getAnalysisIndexList();

        HttpServletRequest request = ServletActionContext.getRequest();

        request.setAttribute("taxUnitList", taxUnitList);
        request.setAttribute("bigIndustryList", bigIndustryList);
        request.setAttribute("firmRegTypeList", firmRegTypeList);
        request.setAttribute("analysisIndexList", analysisIndexList);

        return "show_macro_task_publish_condition_ui";
    }

    /**
     * 总/分量任务发布
     *
     * @return
     */
    public String showMacroTaskPublishTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String type = request.getParameter("type");
        MacroTaskExtractParameter macroTaskExtractParameter = new MacroTaskExtractParameter();

        String fxq = "";

        // 非排序
        if (null == type) {
            if (null == type) {
                type = "none";
            }
            String order = request.getParameter("order");
            if (null == order) {
                order = "normal";
            }

            // 分析期
            String fxqNd = request.getParameter("fxqNd");
            String monthPeriod = request.getParameter("monthPeriod");
            String fxqSjq = "01";
            String fxqSjz = monthPeriod.substring(3);
            fxq = fxqNd + "-" + fxqSjq + " -- " + fxqNd + "-" + fxqSjz;

            String zflLx = request.getParameter("zflLx");
            String swjgDm = "";
            String hydlDm = "";
            String qyzclxDm = "";
            if (zflLx.equals("管理机关")) {
                // 总分量类型为 管理机关
                swjgDm = request.getParameter("swjgDm");
                if ("0".equals(swjgDm)) {
                    swjgDm = "%";
                }
            } else if (zflLx.equals("行业")) {
                // 总分量类型为 行业
                hydlDm = request.getParameter("hydlDm");
                if ("".equals(hydlDm)) {
                    hydlDm = "%";
                }
            } else if (zflLx.equals("注册类型")) {
                // 总分量类型为 注册类型
                qyzclxDm = request.getParameter("qyzclxDm");
                if ("".equals(qyzclxDm)) {
                    qyzclxDm = "%";
                }
            }

            // 指标名称
            String fxzbDm = request.getParameter("fxzbDm");

            if ("".equals(fxzbDm)) {
                fxzbDm = "%";
            }

            // 任务状态
            String rwztDm = request.getParameter("rwztDm");
            if ("".equals(rwztDm)) {
                rwztDm = "%";
            }

            macroTaskExtractParameter.setFxqNd(fxqNd);
            macroTaskExtractParameter.setFxqSjq(fxqSjq);
            macroTaskExtractParameter.setFxqSjz(fxqSjz);
            macroTaskExtractParameter.setZflLx(zflLx);
            macroTaskExtractParameter.setSwjgDm(swjgDm);
            macroTaskExtractParameter.setHydlDm(hydlDm);
            macroTaskExtractParameter.setQyzclxDm(qyzclxDm);
            macroTaskExtractParameter.setFxzbDm(fxzbDm);
            macroTaskExtractParameter.setRwztDm(rwztDm);
            macroTaskExtractParameter.setType(type);
            macroTaskExtractParameter.setOrder(order);
        } else {
            // 排序
            String fxqNd = request.getParameter("fxqNd");
            String fxqSjq = request.getParameter("fxqSjq");
            String fxqSjz = request.getParameter("fxqSjz");
            String zflLx = request.getParameter("zflLx");
            try {
                zflLx = URLDecoder.decode(zflLx, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String swjgDm = request.getParameter("swjgDm");
            String hydlDm = request.getParameter("hydlDm");
            String qyzclxDm = request.getParameter("qyzclxDm");
            String fxzbDm = request.getParameter("fxzbDm");
            String rwztDm = request.getParameter("rwztDm");
            type = request.getParameter("type");
            String order = request.getParameter("order");

            // 指标名称
            if ("".equals(fxzbDm)) {
                fxzbDm = "%";
            }

            macroTaskExtractParameter.setFxqNd(fxqNd);
            macroTaskExtractParameter.setFxqSjq(fxqSjq);
            macroTaskExtractParameter.setFxqSjz(fxqSjz);
            macroTaskExtractParameter.setZflLx(zflLx);
            macroTaskExtractParameter.setSwjgDm(swjgDm);
            macroTaskExtractParameter.setHydlDm(hydlDm);
            macroTaskExtractParameter.setQyzclxDm(qyzclxDm);
            macroTaskExtractParameter.setFxzbDm(fxzbDm);
            macroTaskExtractParameter.setRwztDm(rwztDm);
            macroTaskExtractParameter.setType(type);
            macroTaskExtractParameter.setOrder(order);
        }

        MacroTaskService macroTaskService = new MacroTaskService();
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListInExtractSection(macroTaskExtractParameter);

        request.setAttribute("fxq", fxq);

        if (macroTaskExtractParameter.getSwjgDm().equals("%"))
            macroTaskExtractParameter.setSwjgDm("0");
        if (macroTaskExtractParameter.getHydlDm().equals("%"))
            macroTaskExtractParameter.setHydlDm("");
        if (macroTaskExtractParameter.getQyzclxDm().equals("%"))
            macroTaskExtractParameter.setQyzclxDm("");
        if (macroTaskExtractParameter.getFxzbDm().equals("%"))
            macroTaskExtractParameter.setFxzbDm("");

        // 接受任务人员
        UserService userService = new UserService();
        List<User> tmpUserList = userService.getList();
        List<User> userList = new ArrayList<User>();

        // 显示税务所
        for (User user : tmpUserList) {
            if (user.getRoleCode().equals("05")) {
                userList.add(user);
            }
        }

        request.setAttribute("macroTaskExtractParameter", macroTaskExtractParameter);
        request.setAttribute("macroTaskList", macroTaskList);
        request.setAttribute("userList", userList);

        return "show_macro_task_publish_table";
    }

    /**
     * 发布总/分量任务
     *
     * @return
     */
    public String publishMacroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStrArr[] = request.getParameterValues("macroTaskId");
        String rwzxryDm = request.getParameter("rwzxryDm");
        String bzjsRq = request.getParameter("bzjsRq");
        String bzfkRq = request.getParameter("bzfkRq");

        User user = (User) ActionContext.getContext().getSession().get("loginUser");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String rwfbRq = sdf.format(date);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = null;
        for (int i = 0; i < idStrArr.length; i++) {
            int id = Integer.valueOf(idStrArr[i]);
            macroTask = new MacroTask();
            macroTask = macroTaskService.getMacroTaskById(id);

            macroTask.setRwfbryDm(user.getCode());
            macroTask.setRwfbRq(rwfbRq);
            macroTask.setRwzxryDm(rwzxryDm);
            macroTask.setBzjsRq(bzjsRq);
            macroTask.setBzfkRq(bzfkRq);
            macroTask.setRwztDm("02");

            macroTaskService.update(macroTask);
        }

        request.setAttribute("msg", "发布成功！");
        request.setAttribute("rwfbryDm", user.getCode());
        request.setAttribute("rwfbRq", rwfbRq);
        request.setAttribute("count", idStrArr.length);
        request.setAttribute("rwzxryDm", rwzxryDm);

        return "publish_macro_task";
    }

    /**
     * 任务发布成功报告
     *
     * @return
     */
    public String macroTaskPublishReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String rwfbryDm = request.getParameter("rwfbryDm");
        String rwfbRq = request.getParameter("rwfbRq");
        String count = request.getParameter("count");
        String rwzxryDm = request.getParameter("rwzxryDm");

        UserService userService = new UserService();

        request.setAttribute("rwfbryMc", userService.getByCode(rwfbryDm).getName());
        request.setAttribute("rwfbRq", rwfbRq);
        request.setAttribute("count", count);
        request.setAttribute("rwzxryMc", userService.getByCode(rwzxryDm).getName());

        return "macro_task_publish_report";
    }

    /**
     * 显示总/分量任务接收界面
     *
     * @return
     */
    public String showMacroTaskReceiveUI() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        MacroTaskService macroTaskService = new MacroTaskService();
        String rwzrDm = "";

        // 已发布的任务列表
        rwzrDm = "02";
        List<MacroTask> publishMacroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int publishMacroTaskListSize = publishMacroTaskList.size();

        // 已接收的任务列表
        rwzrDm = "03";
        List<MacroTask> receiveMacroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int receiveMacroTaskListSize = receiveMacroTaskList.size();

        // 已执行(已反馈)的任务列表
        rwzrDm = "04";
        List<MacroTask> feedbackMacroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int feedbackMacroTaskListSize = feedbackMacroTaskList.size();

        // 已评价的任务列表
        rwzrDm = "05";
        List<MacroTask> examMacroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int examMacroTaskListSize = examMacroTaskList.size();

        // msg
        String msg = request.getParameter("msg");
        if ("feedback_succ".equals(msg)) {
            msg = "上报成功!";
        } else if ("receive_succ".equals(msg)) {
            msg = "接收成功!";
        } else {
            msg = "";
        }

        request.setAttribute("publishMacroTaskListSize", publishMacroTaskListSize);
        request.setAttribute("receiveMacroTaskListSize", receiveMacroTaskListSize);
        request.setAttribute("feedbackMacroTaskListSize", feedbackMacroTaskListSize);
        request.setAttribute("examMacroTaskListSize", examMacroTaskListSize);
        request.setAttribute("msg", msg);

        return "show_macro_task_receive_ui";
    }

    /**
     * 上级发布向你发布的任务列表
     *
     * @return
     */
    public String showMacroTaskToReceiveTable() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        MacroTaskService macroTaskService = new MacroTaskService();
        String rwzrDm = "";

        // 已发布的任务列表
        rwzrDm = "02";
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);

        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_to_receive_table";
    }

    /**
     * 显示任务发布通知书
     *
     * @return
     */
    public String showMacroTaskReceiveReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String macroTaskIdStr = request.getParameter("macroTaskId");
        int id = Integer.valueOf(macroTaskIdStr);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        // 登录人员
        User user = (User) ActionContext.getContext().getSession().get("loginUser");

        // 分析期
        String fxqStr = "";
        fxqStr = macroTask.getFxqNd() + "-" + macroTask.getFxqSjq() + "--" + macroTask.getFxqNd() + "-" + macroTask.getFxqSjz();

        // 特征详情
        IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
        IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj());

        // 变动率
        float bdlFloat = 0.0f;
        if (macroTask.getBdl() != null)
            bdlFloat = macroTask.getBdl().floatValue();

        request.setAttribute("macroTask", macroTask);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("user", user);
        request.setAttribute("fxqStr", fxqStr);
        request.setAttribute("bdlFloat", bdlFloat);
        request.setAttribute("indexFeatureDB", indexFeatureDB);

        return "show_macro_task_receive_report";
    }

    /**
     * 接收任务
     *
     * @return
     */
    public String receiveMacroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStrArr[] = request.getParameterValues("macroTaskId");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sjjsRq = sdf.format(date);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = null;
        for (int i = 0; i < idStrArr.length; i++) {
            int id = Integer.valueOf(idStrArr[i]);
            macroTask = new MacroTask();
            macroTask = macroTaskService.getMacroTaskById(id);

            macroTask.setSjjsRq(sjjsRq);
            macroTask.setRwztDm("03");

            macroTaskService.update(macroTask);
        }

        request.setAttribute("msg", "接收成功！");

        return "receive_macro_task";
    }

    /**
     * 显示已接收任务列表，即预反馈任务列表
     *
     * @return
     */
    public String showMacroTaskToFeedbackTable() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        MacroTaskService macroTaskService = new MacroTaskService();
        String rwzrDm = "";

        // 已接收的任务列表
        rwzrDm = "03";
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);

        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_to_feedback_table";
    }

    /**
     * 显示调查报告
     *
     * @return
     */
    public String showMacroTaskEditSurveyReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String macroTaskIdStr = request.getParameter("macroTaskId");
        int id = Integer.valueOf(macroTaskIdStr);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        // 分析期（有待修改，应该为人工填入）
        String fxqStr = "";
        fxqStr = macroTask.getFxqNd() + "-" + macroTask.getFxqSjq() + "--" + macroTask.getFxqNd() + "-" + macroTask.getFxqSjz();

        // 上报日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sjfkRq = sdf.format(date);

        request.setAttribute("macroTask", macroTask);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("fxqStr", fxqStr);
        request.setAttribute("sjfkRq", sjfkRq);

        return "show_macro_task_edit_survey_report";
    }

    /**
     * 反馈任务
     *
     * @return
     */
    public String feedbackMacroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String rwbgDm = getRwbgDm();
        String fxqNd = request.getParameter("fxqNd");
        String fxqSjq = request.getParameter("fxqSjq");
        String fxqSjz = request.getParameter("fxqSjz");
        String fxzblxMx = request.getParameter("fxzblxMx");
        String flMc = request.getParameter("flMc");
        String fxzbDm = request.getParameter("fxzbDm");
        String tzqj = request.getParameter("tzqj");
        String jbqk = request.getParameter("jbqk");
        String dcqk = request.getParameter("dcqk");
        String czwt = request.getParameter("czwt");
        String lscs = request.getParameter("lscs");
        String rwfbryDm = request.getParameter("rwfbryDm");
        String rwzxryDm = request.getParameter("rwzxryDm");
        String dcrqQ = request.getParameter("dcrqQ");
        String dcrqZ = request.getParameter("dcrqZ");
        String sjfkRq = request.getParameter("sjfkRq");

        MacroTaskSurveyReport macroTaskSurveyReport = new MacroTaskSurveyReport();
        macroTaskSurveyReport.setRwbgDm(rwbgDm);
        macroTaskSurveyReport.setFxqNd(fxqNd);
        macroTaskSurveyReport.setFxqSjq(fxqSjq);
        macroTaskSurveyReport.setFxqSjz(fxqSjz);
        macroTaskSurveyReport.setFxzblxMx(fxzblxMx);
        macroTaskSurveyReport.setFlMc(flMc);
        macroTaskSurveyReport.setFxzbDm(fxzbDm);
        macroTaskSurveyReport.setTzqj(tzqj);
        macroTaskSurveyReport.setJbqk(jbqk);
        macroTaskSurveyReport.setDcqk(dcqk);
        macroTaskSurveyReport.setCzwt(czwt);
        macroTaskSurveyReport.setLscs(lscs);
        macroTaskSurveyReport.setRwfbryDm(rwfbryDm);
        macroTaskSurveyReport.setRwzxryDm(rwzxryDm);
        macroTaskSurveyReport.setDcrqQ(dcrqQ);
        macroTaskSurveyReport.setDcrqZ(dcrqZ);
        macroTaskSurveyReport.setSjfkRq(sjfkRq);

        // 插入新的任务调查报告
        MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();
        macroTaskSurveyReportService.add(macroTaskSurveyReport);

        // 更新微观任务的对应记录
        MacroTaskService macroTaskService = new MacroTaskService();
        int id = Integer.valueOf(request.getParameter("id"));
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);
        macroTask.setSjfkRq(sjfkRq);
        macroTask.setRwbgDm(rwbgDm);
        macroTask.setRwztDm("04");
        macroTaskService.update(macroTask);

        return "feedback_macro_task";
    }

    /**
     * 获取任务报告代码（20120621001, 20120621002, ...）
     *
     * @return
     */
    private String getRwbgDm() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(date);

        MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();
        String maxWgrwDm = macroTaskSurveyReportService.getMaxWgrwDm(dateStr);
        String wgrwDm = "";

        if (null == maxWgrwDm) {
            // 当日无记录，插入第一条记录
            wgrwDm = dateStr + "001";
        } else {
            // 当日有记录，在其基础上加1
            int maxWgrwDmInt = Integer.valueOf("1" + maxWgrwDm.substring(8));
            int wgrwDmInt = maxWgrwDmInt + 1;
            wgrwDm = dateStr + String.valueOf(wgrwDmInt).substring(1);
        }

        return wgrwDm;
    }

    /**
     * 显示已反馈的任务列表
     *
     * @return
     */
    public String showMacroTaskToExamTable() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        MacroTaskService macroTaskService = new MacroTaskService();
        String rwztDm = "";

        // 已反馈的任务列表
        rwztDm = "04";
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);

        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_to_exam_table";
    }

    /**
     * 显示任务调查报告
     *
     * @return
     */
    public String showMacroTaskSurveyReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String macroTaskIdStr = request.getParameter("macroTaskId");
        int id = Integer.valueOf(macroTaskIdStr);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();
        MacroTaskSurveyReport macroTaskSurveyReport = macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm());

        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        request.setAttribute("macroTaskSurveyReport", macroTaskSurveyReport);
        request.setAttribute("macroTask", macroTask);
        request.setAttribute("taxUnit", taxUnit);

        return "show_macro_task_survey_report";
    }

    /**
     * 显示总/分量任务评价和考核界面
     *
     * @return
     */
    public String showMacroTaskExamUI() {

        HttpServletRequest request = ServletActionContext.getRequest();

        MacroTaskService macroTaskService = new MacroTaskService();
        String rwztDm = "";

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        // 已发布的任务列表
        rwztDm = "02";
        List<MacroTask> publishMacroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
        int publishMacroTaskListSize = publishMacroTaskList.size();

        // 已反馈的任务列表
        rwztDm = "04";
        List<MacroTask> feedbackMacroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
        int feedbackMacroTaskListSize = feedbackMacroTaskList.size();

        // 已考核的任务列表
        rwztDm = "05";
        List<MacroTask> examMacroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
        int examMacroTaskListSize = examMacroTaskList.size();

        // msg
        String msg = request.getParameter("msg");
        if ("exam_succ".equals(msg)) {
            msg = "下发评价成功!";
        } else {
            msg = "";
        }

        request.setAttribute("publishMacroTaskListSize", publishMacroTaskListSize);
        request.setAttribute("feedbackMacroTaskListSize", feedbackMacroTaskListSize);
        request.setAttribute("examMacroTaskListSize", examMacroTaskListSize);
        request.setAttribute("msg", msg);

        return "show_macro_task_exam_ui";
    }

    /**
     * 在任务考核模块中显示任务发布列表
     *
     * @return
     */
    public String showMacroTaskPublishTableInExamSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        // 任务状态
        String rwztDm = "02";

        MacroTaskService macroTaskService = new MacroTaskService();
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);


        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_publish_table_in_exam_section";
    }

    /**
     * 在任务评价模块中显示需要评价的任务列表
     *
     * @return
     */
    public String showMacroTaskToExamTableInExamSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        // 任务状态
        String rwztDm = "04";

        MacroTaskService macroTaskService = new MacroTaskService();
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);

        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_to_exam_table_in_exam_section";
    }


    /**
     * 打开提交任务评价报告窗口
     *
     * @return
     */
    public String showMacroTaskSubmitExamReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String macroTaskIdStr = request.getParameter("macroTaskId");
        int id = Integer.valueOf(macroTaskIdStr);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        // 评价日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rwpjRq = sdf.format(date);

        request.setAttribute("macroTask", macroTask);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("rwpjRq", rwpjRq);

        return "show_macro_task_submit_exam_report";
    }

    /**
     * 评价任务
     *
     * @return
     */
    public String examMacroTask() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        String macroTaskIdStr = request.getParameter("macroTaskId");
        int id = Integer.valueOf(macroTaskIdStr);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        String glpj = request.getParameter("glpj");
        String zfpj = request.getParameter("zfpj");
        String zhpj = request.getParameter("zhpj");
        String pjryDm = request.getParameter("pjryDm");
        String rwpjRq = request.getParameter("rwpjRq");
        String rwztDm = "05";

        macroTask.setGlpj(glpj);
        macroTask.setZfpj(zfpj);
        macroTask.setZhpj(zhpj);
        macroTask.setPjryDm(pjryDm);
        macroTask.setRwpjRq(rwpjRq);
        macroTask.setRwztDm(rwztDm);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 计算任务得分
        // 1. 计算接收得分
        int rwjsDf = 0;
        String bzjsRqStr = macroTask.getBzjsRq();    // 标准接收日期
        Date bzjsRqDate = sdf.parse(bzjsRqStr);

        String sjjsRqStr = macroTask.getSjjsRq().substring(0, 10);    // 实际接收日期
        Date sjjsRqDate = sdf.parse(sjjsRqStr);

        long diffLong = (sjjsRqDate.getTime() - bzjsRqDate.getTime()) / ((24 * 60 * 60 * 1000));
        int diffInt = (int) diffLong;

        if (diffInt < 0) {
            rwjsDf = 30;
        } else if (diffInt > 30) {
            rwjsDf = 0;
        } else {
            rwjsDf = 30 - diffInt;
        }

        // 2. 计算任务反馈得分
        int rwfkDf = 0;
        String bzfkRqStr = macroTask.getBzfkRq();    // 标准反馈日期
        Date bzfkRqDate = sdf.parse(bzfkRqStr);

        String sjfkRqStr = macroTask.getSjfkRq();    // 实际反馈日期
        Date sjfkRqDate = sdf.parse(sjfkRqStr);

        diffLong = (sjfkRqDate.getTime() - bzfkRqDate.getTime()) / ((24 * 60 * 60 * 1000));

        diffInt = (int) diffLong;

        if (diffInt < 0) {
            rwfkDf = 30;
        } else if (diffInt > 30) {
            rwfkDf = 0;
        } else {
            rwfkDf = 30 - diffInt;
        }

        // 3. 计算任务执行得分
        int rwzxDf = 0;
        zhpj = macroTask.getZhpj();
        if (zhpj.equals("优秀")) {
            rwzxDf = 40;
        } else if (zhpj.equals("良好")) {
            rwzxDf = 32;
        } else if (zhpj.equals("差")) {
            rwzxDf = 24;
        }

        macroTask.setRwjsDf(rwjsDf);
        macroTask.setRwfkDf(rwfkDf);
        macroTask.setRwzxDf(rwzxDf);

        macroTaskService.update(macroTask);

        request.setAttribute("msg", "下发评价成功！");

        return "exam_macro_task";
    }

    /**
     * 任务评价模块中显示任务评价列表
     *
     * @return
     */
    public String showFinishExamTableInExamSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        // 任务状态
        String rwztDm = "05";

        MacroTaskService macroTaskService = new MacroTaskService();
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);


        request.setAttribute("macroTaskList", macroTaskList);

        return "show_finish_exam_table_in_exam_section";
    }

    /**
     * 显示任务评价报告
     *
     * @return
     */
    public String showMacroTaskExamReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String macroTaskIdStr = request.getParameter("macroTaskId");
        int id = Integer.valueOf(macroTaskIdStr);

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        request.setAttribute("macroTask", macroTask);
        request.setAttribute("taxUnit", taxUnit);

        return "show_macro_task_exam_report";
    }

    /**
     * 显示任务评价列表
     *
     * @return
     */
    public String showMacroTaskFinishExamTableInReceiveSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        // 任务状态
        String rwztDm = "05";

        MacroTaskService macroTaskService = new MacroTaskService();
//		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByRwzt(rwztDm);
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();
        List<MacroTask> macroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);

        request.setAttribute("macroTaskList", macroTaskList);

        return "show_macro_task_finish_exam_table_in_receive_section";
    }

    /**
     * 显示总分值计算过程
     *
     * @return
     */
    public String showZfzDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        int id = Integer.valueOf(request.getParameter("macroTaskId"));

        MacroTaskService macroTaskService = new MacroTaskService();
        MacroTask macroTask = macroTaskService.getMacroTaskById(id);

        String fxzblxMx = macroTask.getFxzblxMx().trim();

        DataCalcParameter dataCalcParameter = new DataCalcParameter();
        dataCalcParameter.setFxqNd(macroTask.getFxqNd());
        dataCalcParameter.setFxqSssqQ(macroTask.getFxqSjq());
        dataCalcParameter.setFxqSssqZ(macroTask.getFxqSjz());

        AnalysisMacroIndexService analysisMacroIndexService = new AnalysisMacroIndexService();

        StringBuffer calcProcess = new StringBuffer("总分值计算过程如下：<br/>");
        StringBuffer calcTmp = new StringBuffer("");

        DecimalFormat floatFormat = new DecimalFormat("0.00");     // 两位小数

        if (fxzblxMx.equals("管理机关")) {
            String swjgDm = macroTask.getSwjgDm();
            List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexService.getListByFxqAndFxzblxMxAndSwjgDm(dataCalcParameter, fxzblxMx, swjgDm);

            for (int i = 0; i < analysisMacroIndexList.size(); i++) {
                calcProcess.append("(").append(i + 1).append(")&nbsp;&nbsp;")        // 显示：(1)
                        .append(analysisMacroIndexList.get(i).getFxzblx().trim()).append("：")    // 显示：(1)欠缴税金 ：
                ;

                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMacroIndexList.get(i).getTzqj().equals("ycqj1")) {
                    if (analysisMacroIndexList.get(i).getBdl() != null) {
//						calcProcess.append("3/120 * ")
//						   .append(analysisMacroIndexList.get(i).getBdl().floatValue())
//						   .append("*").append(analysisMacroIndexList.get(i).getZbqz())
//						   .append("=").append(floatFormat.format(3.0 /120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()))
//						   .append("<br/>");

                        calcProcess.append("3/120 * ");

                        // 变动率>=1000的标记红色
                        if (analysisMacroIndexList.get(i).getBdl().floatValue() >= 1000) {
                            calcProcess.append("<font color=red>")
                                    .append(analysisMacroIndexList.get(i).getBdl().floatValue())
                                    .append("</font>");
                        } else {
                            calcProcess.append(analysisMacroIndexList.get(i).getBdl().floatValue());
                        }

                        calcProcess.append("*").append(analysisMacroIndexList.get(i).getZbqz())
                                .append("=").append(floatFormat.format(3.0 / 120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()))
                                .append("<br/>");

                        if (i != 0)
                            calcTmp.append("+");
                        calcTmp.append(floatFormat.format(3.0 / 120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()));
                    }

                } else {
                    calcProcess.append(analysisMacroIndexList.get(i).getTzfz()).append("*")
                            .append(analysisMacroIndexList.get(i).getZbqz())
                            .append("=").append(analysisMacroIndexList.get(i).getTzfz() * analysisMacroIndexList.get(i).getZbqz())
                            .append("<br/>");

                    if (i != 0)
                        calcTmp.append("+");
                    calcTmp.append(analysisMacroIndexList.get(i).getTzfz() * analysisMacroIndexList.get(i).getZbqz());
                }
            }
        } else if (fxzblxMx.equals("行业")) {
            String hydlDm = macroTask.getFxzblxMxDm();
            List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexService.getListByFxqAndFxzblxMxAndHydlDm(dataCalcParameter, fxzblxMx, hydlDm);

            for (int i = 0; i < analysisMacroIndexList.size(); i++) {
                calcProcess.append("(").append(i + 1).append(")&nbsp;&nbsp;")        // 显示：(1)
                        .append(analysisMacroIndexList.get(i).getFxzblx().trim()).append("：")    // 显示：(1)欠缴税金 ：
                ;

                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMacroIndexList.get(i).getTzqj().equals("ycqj1")) {
                    if (analysisMacroIndexList.get(i).getBdl() != null) {
//						calcProcess.append("3/120 * ")
//						   .append(analysisMacroIndexList.get(i).getBdl().floatValue())
//						   .append("*").append(analysisMacroIndexList.get(i).getZbqz())
//						   .append("=").append(floatFormat.format(3.0 /120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()))
//						   .append("<br/>");

                        calcProcess.append("3/120 * ");

                        // 变动率>=1000的标记红色
                        if (analysisMacroIndexList.get(i).getBdl().floatValue() >= 1000) {
                            calcProcess.append("<font color=red>")
                                    .append(analysisMacroIndexList.get(i).getBdl().floatValue())
                                    .append("</font>");
                        } else {
                            calcProcess.append(analysisMacroIndexList.get(i).getBdl().floatValue());
                        }

                        calcProcess.append("*").append(analysisMacroIndexList.get(i).getZbqz())
                                .append("=").append(floatFormat.format(3.0 / 120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()))
                                .append("<br/>");

                        if (i != 0)
                            calcTmp.append("+");
                        calcTmp.append(floatFormat.format(3.0 / 120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()));
                    }

                } else {
                    calcProcess.append(analysisMacroIndexList.get(i).getTzfz()).append("*")
                            .append(analysisMacroIndexList.get(i).getZbqz())
                            .append("=").append(analysisMacroIndexList.get(i).getTzfz() * analysisMacroIndexList.get(i).getZbqz())
                            .append("<br/>");

                    if (i != 0)
                        calcTmp.append("+");
                    calcTmp.append(analysisMacroIndexList.get(i).getTzfz() * analysisMacroIndexList.get(i).getZbqz());
                }
            }
        } else if (fxzblxMx.equals("注册类型")) {
            String qyzclxDm = macroTask.getFxzblxMxDm();
            List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexService.getListByFxqAndFxzblxMxAndQyzclxDm(dataCalcParameter, fxzblxMx, qyzclxDm);

            for (int i = 0; i < analysisMacroIndexList.size(); i++) {
                calcProcess.append("(").append(i + 1).append(")&nbsp;&nbsp;")        // 显示：(1)
                        .append(analysisMacroIndexList.get(i).getFxzblx().trim()).append("：")    // 显示：(1)欠缴税金 ：
                ;

                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMacroIndexList.get(i).getTzqj().equals("ycqj1")) {
                    if (analysisMacroIndexList.get(i).getBdl() != null) {
//						calcProcess.append("3/120 * ")
//						   .append(analysisMacroIndexList.get(i).getBdl().floatValue())
//						   .append("*").append(analysisMacroIndexList.get(i).getZbqz())
//						   .append("=").append(floatFormat.format(3.0 /120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()))
//						   .append("<br/>");

                        calcProcess.append("3/120 * ");

                        // 变动率>=1000的标记红色
                        if (analysisMacroIndexList.get(i).getBdl().floatValue() >= 1000) {
                            calcProcess.append("<font color=red>")
                                    .append(analysisMacroIndexList.get(i).getBdl().floatValue())
                                    .append("</font>");
                        } else {
                            calcProcess.append(analysisMacroIndexList.get(i).getBdl().floatValue());
                        }

                        calcProcess.append("*").append(analysisMacroIndexList.get(i).getZbqz())
                                .append("=").append(floatFormat.format(3.0 / 120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()))
                                .append("<br/>");

                        if (i != 0)
                            calcTmp.append("+");
                        calcTmp.append(floatFormat.format(3.0 / 120 * analysisMacroIndexList.get(i).getBdl().floatValue() * analysisMacroIndexList.get(i).getZbqz()));
                    }

                } else {
                    calcProcess.append(analysisMacroIndexList.get(i).getTzfz()).append("*")
                            .append(analysisMacroIndexList.get(i).getZbqz())
                            .append("=").append(analysisMacroIndexList.get(i).getTzfz() * analysisMacroIndexList.get(i).getZbqz())
                            .append("<br/>");

                    if (i != 0)
                        calcTmp.append("+");
                    calcTmp.append(analysisMacroIndexList.get(i).getTzfz() * analysisMacroIndexList.get(i).getZbqz());
                }
            }
        }

        calcProcess.append(macroTask.getZfz()).append("=").append(calcTmp);

        request.setAttribute("calcProcess", calcProcess);
        return "show_zfz_detail";
    }
}

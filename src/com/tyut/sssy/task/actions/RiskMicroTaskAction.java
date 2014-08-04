package com.tyut.sssy.task.actions;

import com.opensymphony.xwork2.ActionContext;
import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.*;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.RiskIndexFeatherDao;
import com.tyut.sssy.task.domain.*;
import com.tyut.sssy.task.service.*;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName:RiskMicroTaskAction
 * Function:风险微观任务Action
 * Author: LiuXiang
 * Date: 13-7-9
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚
 */

public class RiskMicroTaskAction {

    /**
     * 显示微观任务提取选择条件界面
     *
     * @return
     */
    public String showRiskMicroTaskExtractConditionUI() {

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

        /*// 指标名称
          AnalysisIndexService analysisIndexService = new AnalysisIndexService();
          List<AnalysisIndex> analysisIndexList = analysisIndexService.getAnalysisIndexList();*/

        HttpServletRequest request = ServletActionContext.getRequest();

        request.setAttribute("taxUnitList", taxUnitList);
        request.setAttribute("bigIndustryList", bigIndustryList);
        request.setAttribute("firmRegTypeList", firmRegTypeList);
        /*request.setAttribute("analysisIndexList", analysisIndexList);*/

        return "show_risk_micro_task_extract_condition_ui";
    }

    /**
     * 显示风险微观任务提取列表
     *
     * @return
     */
    public String showRiskMicroTaskExtractTable() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String type = request.getParameter("type");
        RiskMicroTaskExtractParameter riskMicroTaskExtractParameter = new RiskMicroTaskExtractParameter();


        // 非排序
        if (null == type)
            type = "none";

        String order = "";
        if (type.equals("none"))
            order = "";
        else {
            order = request.getParameter("order");
        }


        // 分析期
        String fxqNd = request.getParameter("fxqNd");
        String monthPeriod = request.getParameter("monthPeriod");
        String fxqSjq = "01";
        String fxqSjz = monthPeriod.substring(3);
        String fxq = fxqNd + "-" + fxqSjq + " -- " + fxqNd + "-" + fxqSjz;

        // 税务机关
        String swjgDm = request.getParameter("swjgDm");
        String swjgMc = "";
        if ("0".equals(swjgDm)) {
            swjgDm = "%";
            swjgMc = "全部";
        } else {
            TaxUnitService taxUnitService = new TaxUnitService();
            swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
        }

        // 纳税人识别号
        String nsrsbh = request.getParameter("nsrsbh");
        String nsrmc = "";
        if ("".equals(nsrsbh)) {
            nsrsbh = "%";
            nsrmc = "全部";
        } else {
            TaxPayerService taxPayerService = new TaxPayerService();
            nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
        }

        // 行业大类
        String hydlDm = request.getParameter("hydlDm");
        String hydlMc = "";
        if ("".equals(hydlDm)) {
            hydlDm = "%";
            hydlMc = "全部";
        } else {
            BigIndustryService bigIndustryService = new BigIndustryService();
            hydlMc = bigIndustryService.getBigIndustryById(hydlDm).getMc();
        }

        // 注册类型
        String qyzclxDm = request.getParameter("qyzclxDm");
        String qyzclxMc = "";
        if ("".equals(qyzclxDm)) {
            qyzclxDm = "%";
            qyzclxMc = "全部";
        } else {
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclxDm).getMc();
        }


        // 任务状态
        String rwztDm = request.getParameter("rwztDm");
        if ("".equals(rwztDm)) {
            rwztDm = "%";
        }

        String cyDm = request.getParameter("cyDm");
        if (cyDm == null || cyDm.equals(""))
            cyDm = "%";

        riskMicroTaskExtractParameter.setFxqNd(fxqNd);
        riskMicroTaskExtractParameter.setFxqSjq(fxqSjq);
        riskMicroTaskExtractParameter.setFxqSjz(fxqSjz);
        riskMicroTaskExtractParameter.setSwjgDm(swjgDm);
        riskMicroTaskExtractParameter.setNsrsbh(nsrsbh);
        riskMicroTaskExtractParameter.setHydlDm(hydlDm);
        riskMicroTaskExtractParameter.setQyzclxDm(qyzclxDm);
        riskMicroTaskExtractParameter.setRwztDm(rwztDm);
        riskMicroTaskExtractParameter.setType(type);
        riskMicroTaskExtractParameter.setOrder(order);
        riskMicroTaskExtractParameter.setCyDm(cyDm);

        RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();
        List<RiskMicroTask> riskMicroTaskList = riskMicroTaskService.getExtractRiskMicroTaskList(riskMicroTaskExtractParameter);

        request.setAttribute("swjgMc", swjgMc);
        request.setAttribute("nsrmc", nsrmc);
        request.setAttribute("hydlMc", hydlMc);
        request.setAttribute("qyzclxMc", qyzclxMc);
        request.setAttribute("fxq", fxq);

        if (riskMicroTaskExtractParameter.getSwjgDm().equals("%"))
            riskMicroTaskExtractParameter.setSwjgDm("0");
        if (riskMicroTaskExtractParameter.getNsrsbh().equals("%"))
            riskMicroTaskExtractParameter.setNsrsbh("");
        if (riskMicroTaskExtractParameter.getHydlDm().equals("%"))
            riskMicroTaskExtractParameter.setHydlDm("");
        if (riskMicroTaskExtractParameter.getQyzclxDm().equals("%"))
            riskMicroTaskExtractParameter.setQyzclxDm("");

        request.setAttribute("riskMicroTaskExtractParameter", riskMicroTaskExtractParameter);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "show_risk_micro_task_extract_table";
    }


    /**
     * 提取风险微观任务
     *
     * @return
     */
    public String extractRiskMicroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStrArr[] = request.getParameterValues("microTaskId");

        User user = (User) ActionContext.getContext().getSession().get("loginUser");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String rwtqRq = sdf.format(date);

        RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();
        RiskMicroTask microTask = null;
        for (int i = 0; i < idStrArr.length; i++) {
            int id = Integer.valueOf(idStrArr[i]);
            microTask = riskMicroTaskService.getRiskMicroTaskById(id);

            microTask.setRwtqryDm(user.getCode());
            microTask.setRwtqRq(rwtqRq);
            microTask.setRwztDm("01");

            riskMicroTaskService.update(microTask);
        }

        request.setAttribute("msg", "提取成功！");
        return "extract_risk_micro_task";
    }

    /**
     * 显示风险微观指标详情
     *
     * @return
     */
    public String showRiskDetail() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String idStr = request.getParameter("id") == null ? "0" : request.getParameter("id");
        int id = Integer.valueOf(idStr).intValue();

        RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();

        RiskMicroTask riskMicroTask = riskMicroTaskService.getRiskMicroTaskById(id);

        String zsjgDm = riskMicroTask.getZsjgDm();//征收机构代码
        String cyDm = riskMicroTask.getCyDm(); //产业代码
        String hydlDm = riskMicroTask.getHydlDm(); //行业大类代码
        String nsrmc = riskMicroTask.getNsrmc();
        String fxq = riskMicroTask.getFxqNd() + "." + riskMicroTask.getFxqSjq() + " -- " + riskMicroTask.getFxqNd() + "." + riskMicroTask.getFxqSjz();
        int weight = 0;
        String detail = "";
        //0:国税征缴 1:地税征缴 2.混合征缴 3.不征收
        List<RiskDetail> riskDetailList = computeRiskDetail(riskMicroTask, zsjgDm, cyDm, hydlDm);

        request.setAttribute("list", riskDetailList);
        request.setAttribute("nsrmc", nsrmc);
        request.setAttribute("fxq", fxq);
        request.setAttribute("detail", detail);

        return "show_risk_detail_succ";
    }

    private List<RiskDetail> computeRiskDetail(RiskMicroTask riskMicroTask, String zsjgDm, String cyDm, String hydlDm) {
        int weight;
        List<RiskDetail> riskDetailList;
        if (zsjgDm != null && cyDm != null) {
            zsjgDm = zsjgDm.trim();
            cyDm = cyDm.trim();
            hydlDm = hydlDm.trim();
            //地税征缴且工业企业 属于权重1
            if (zsjgDm.equals("1") && cyDm.equals("02")) {
                weight = 1;
            } else if (zsjgDm.equals("1") && !cyDm.equals("02") && //权重2 地税征缴 且非工业企业 并且非房地产和金融 66.67，68，69，70
                    !hydlDm.equals("66") && !hydlDm.equals("67") && !hydlDm.equals("68") && !hydlDm.equals("69") && !hydlDm.equals("70")) {
                weight = 2;

            } else if (!zsjgDm.equals("1") &&   //权重3 非地税征缴 并且非房地产和金融 66.67，68，69，70
                    !hydlDm.equals("66") && !hydlDm.equals("67") && !hydlDm.equals("68") && !hydlDm.equals("69") && !hydlDm.equals("70")) {
                weight = 3;
            } else if (zsjgDm.equals("1") && hydlDm.equals("70")) {//权重4 地税征缴 且是房地产行业
                weight = 4;
            } else if (!zsjgDm.equals("!") && hydlDm.equals("70")) { //权重5 非地税征缴 且是房地产行业
                weight = 5;
            } else if (zsjgDm.equals("1") && (hydlDm.equals("67") || hydlDm.equals("68") || hydlDm.equals("69") || hydlDm.equals("66"))) {//权重6 地税征缴 且金融保险业
                weight = 6;
            } else {//权重7
                weight = 7;
            }
            try {
                riskDetailList = getRiskDetailList(weight, riskMicroTask);
                return riskDetailList;

            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
                return null;
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
                return null;
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return null;
            }
        } else

            return null;

    }

    /**
     * 显示风险微观任务发布选择条件界面
     *
     * @return
     */
    public String showRiskMicroTaskPublishConditionUI() {

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

        return "show_risk_micro_task_publish_condition_ui";
    }

    /**
     * 显示微观任务发布列表
     *
     * @return
     */
    public String showRiskMicroTaskPublishTable() {

        HttpServletRequest request = ServletActionContext.getRequest();


        // 分析期
        String fxqNd = request.getParameter("fxqNd");
        String monthPeriod = request.getParameter("monthPeriod");
        String fxqSjq = "01";
        String fxqSjz = monthPeriod.substring(3);
        String fxq = fxqNd + "-" + fxqSjq + " -- " + fxqNd + "-" + fxqSjz;

        // 税务机关
        String swjgDm = request.getParameter("swjgDm");
        String swjgMc = "";
        if ("0".equals(swjgDm)) {
            swjgDm = "%";
            swjgMc = "全部";
        } else {
            TaxUnitService taxUnitService = new TaxUnitService();
            swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
        }

        // 纳税人识别号
        String nsrsbh = request.getParameter("nsrsbh");
        String nsrmc = "";
        if ("".equals(nsrsbh)) {
            nsrsbh = "%";
            nsrmc = "全部";
        } else {
            TaxPayerService taxPayerService = new TaxPayerService();
            nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
        }

        // 行业大类
        String hydlDm = request.getParameter("hydlDm");
        String hydlMc = "";
        if ("".equals(hydlDm)) {
            hydlDm = "%";
            hydlMc = "全部";
        } else {
            BigIndustryService bigIndustryService = new BigIndustryService();
            hydlMc = bigIndustryService.getBigIndustryById(hydlDm).getMc();
        }

        // 注册类型
        String qyzclxDm = request.getParameter("qyzclxDm");
        String qyzclxMc = "";
        if ("".equals(qyzclxDm)) {
            qyzclxDm = "%";
            qyzclxMc = "全部";
        } else {
            FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
            qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclxDm).getMc();
        }


        // 任务状态
        String rwztDm = request.getParameter("rwztDm");
        if ("".equals(rwztDm)) {
            rwztDm = "%";
        }

        String cyDm = request.getParameter("cyDm");
        if (cyDm == null || cyDm.equals(""))
            cyDm = "%";


        // 接受任务人员
        UserService userService = new UserService();
        List<User> tmpUserList = userService.getList();
        List<User> userList = new ArrayList<User>();

        // 显示税收管理员
        for (User user : tmpUserList) {
            if (user.getRoleCode().equals("03")) {
                userList.add(user);
            }
        }

        RiskMicroTaskExtractParameter microTaskExtractParameter = new RiskMicroTaskExtractParameter();
        microTaskExtractParameter.setFxqNd(fxqNd);
        microTaskExtractParameter.setFxqSjq(fxqSjq);
        microTaskExtractParameter.setFxqSjz(fxqSjz);
        microTaskExtractParameter.setSwjgDm(swjgDm);
        microTaskExtractParameter.setNsrsbh(nsrsbh);
        microTaskExtractParameter.setHydlDm(hydlDm);
        microTaskExtractParameter.setQyzclxDm(qyzclxDm);
        microTaskExtractParameter.setCyDm(cyDm);
        microTaskExtractParameter.setRwztDm(rwztDm);
        microTaskExtractParameter.setType("none");
        microTaskExtractParameter.setOrder("normal");

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getExtractRiskMicroTaskList(microTaskExtractParameter);

        request.setAttribute("swjgMc", swjgMc);
        request.setAttribute("nsrmc", nsrmc);
        request.setAttribute("hydlMc", hydlMc);
        request.setAttribute("qyzclxMc", qyzclxMc);

        request.setAttribute("fxq", fxq);
        request.setAttribute("userList", userList);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "show_risk_micro_task_publish_table";
    }

    /**
     * 发布微观任务
     *
     * @return
     */
    public String publishRiskMicroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStrArr[] = request.getParameterValues("microTaskId");
        String rwzxryDm = request.getParameter("rwzxryDm");
        String bzjsRq = request.getParameter("bzjsRq");
        String bzfkRq = request.getParameter("bzfkRq");

        User user = (User) ActionContext.getContext().getSession().get("loginUser");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String rwfbRq = sdf.format(date);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask microTask = null;
        for (int i = 0; i < idStrArr.length; i++) {
            int id = Integer.valueOf(idStrArr[i]);
            microTask = microTaskService.getRiskMicroTaskById(id);

            microTask.setRwfbryDm(user.getCode());
            microTask.setRwfbRq(rwfbRq);
            microTask.setRwzxryDm(rwzxryDm);
            microTask.setBzjsRq(bzjsRq);
            microTask.setBzfkRq(bzfkRq);
            microTask.setRwztDm("02");

            microTaskService.update(microTask);
        }

        request.setAttribute("msg", "发布成功！");
        request.setAttribute("rwfbryDm", user.getCode());
        request.setAttribute("rwfbRq", rwfbRq);
        request.setAttribute("count", idStrArr.length);
        request.setAttribute("rwzxryDm", rwzxryDm);

        return "publish_risk_micro_task";
    }

    /**
     * 发布成功报告
     *
     * @return
     */
    public String riskMicroTaskPublishReport() {

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

        return "risk_micro_task_publish_report";
    }

    /**
     * 显示微观任务接收界面
     *
     * @return
     */
    public String showRiskMicroTaskReceiveUI() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        String rwzrDm = "";

        // 已发布的任务列表
        rwzrDm = "02";
        List<RiskMicroTask> publishMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int publishRiskMicroTaskListSize = publishMicroTaskList.size();

        // 已接收的任务列表
        rwzrDm = "03";
        List<RiskMicroTask> receiveMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int receiveRiskMicroTaskListSize = receiveMicroTaskList.size();

        // 已执行(已反馈)的任务列表
        rwzrDm = "04";
        List<RiskMicroTask> feedbackMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int feedbackRiskMicroTaskListSize = feedbackMicroTaskList.size();

        // 已评价的任务列表
        rwzrDm = "05";
        List<RiskMicroTask> examMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
        int examRiskMicroTaskListSize = examMicroTaskList.size();

        // msg
        String msg = request.getParameter("msg");
        if ("feedback_succ".equals(msg)) {
            msg = "上报成功!";
        } else if ("receive_succ".equals(msg)) {
            msg = "接收成功!";
        } else {
            msg = "";
        }

        request.setAttribute("publishRiskMicroTaskListSize", publishRiskMicroTaskListSize);
        request.setAttribute("receiveRiskMicroTaskListSize", receiveRiskMicroTaskListSize);
        request.setAttribute("feedbackRiskMicroTaskListSize", feedbackRiskMicroTaskListSize);
        request.setAttribute("examRiskMicroTaskListSize", examRiskMicroTaskListSize);
        request.setAttribute("msg", msg);

        return "show_risk_micro_task_receive_ui";
    }

    /**
     * 显示风险微观接收任务列表
     *
     * @return
     */
    public String showRiskMicroTaskReceiveTable() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        String rwzrDm = "";

        // 已发布的任务列表
        rwzrDm = "02";
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);
        return "show_risk_micro_task_receive_table";
    }

    /**
     * 显示任务发布通知书
     *
     * @return
     */
    public String showRiskMicroTaskReceiveReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStr = request.getParameter("id");
        int id = Integer.valueOf(idStr);

        RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();
        RiskMicroTask riskMicroTask = riskMicroTaskService.getRiskMicroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        // 登录人员
        User user = (User) ActionContext.getContext().getSession().get("loginUser");

        // 分析期
        String fxqStr = "";
        fxqStr = riskMicroTask.getFxqNd() + "-" + riskMicroTask.getFxqSjq() + "--" + riskMicroTask.getFxqNd() + "-" + riskMicroTask.getFxqSjz();

        request.setAttribute("riskMicroTask", riskMicroTask);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("user", user);
        request.setAttribute("fxqStr", fxqStr);

        return "show_risk_micro_task_receive_report";
    }

    /**
     * 接收微观任务
     *
     * @return
     */
    public String receiveRiskMicroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStrArr[] = request.getParameterValues("microTaskId");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sjjsRq = sdf.format(date);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask microTask = null;
        for (int i = 0; i < idStrArr.length; i++) {
            int id = Integer.valueOf(idStrArr[i]);

            microTask = microTaskService.getRiskMicroTaskById(id);

            microTask.setSjjsRq(sjjsRq);
            microTask.setRwztDm("03");

            microTaskService.update(microTask);
        }

        request.setAttribute("msg", "接收成功！");

        return "receive_risk_micro_task";
    }

    /**
     * 显示已接收任务列表，即预反馈任务列表
     *
     * @return
     */
    public String showRiskMicroTaskToFeedbackTable() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        String rwzrDm = "";

        // 已接收的任务列表
        rwzrDm = "03";
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);
        return "show_risk_micro_task_to_feedback_table";
    }

    /**
     * 显示调查报告
     *
     * @return
     */
    public String showRiskMicroTaskEditSurveyReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String idStr = request.getParameter("id");
        int id = Integer.valueOf(idStr);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask riskMicroTask = microTaskService.getRiskMicroTaskById(id);

        if(riskMicroTask.getRwbgDm()!= null){
            RiskMicroTaskSurveyReportService service  = new RiskMicroTaskSurveyReportService();
            RiskMicroTaskSurveyReport report = service.getSurveyReportById(riskMicroTask.getRwbgDm());
            request.setAttribute("report",report);
        }
        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        // 分析期
        // 分析期
        String fxqStr = "";
        fxqStr = riskMicroTask.getFxqNd() + "-" + riskMicroTask.getFxqSjq() + "--" + riskMicroTask.getFxqNd() + "-" + riskMicroTask.getFxqSjz();

        // 上报日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sjfkRq = sdf.format(date);

        List<RiskDetail> riskDetailList1 = computeRiskDetail(riskMicroTask, riskMicroTask.getZsjgDm(), riskMicroTask.getCyDm(), riskMicroTask.getHydlDm());
        List<RiskDetail> riskDetailList = new ArrayList<RiskDetail>();
        for (RiskDetail riskDetail : riskDetailList1) {
            if (Float.valueOf(riskDetail.getZbz()) >= 3.0)
                riskDetailList.add(riskDetail);
        }

        request.setAttribute("riskMicroTask", riskMicroTask);
        request.setAttribute("riskDetailList", riskDetailList);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("fxqStr", fxqStr);
        request.setAttribute("sjfkRq", sjfkRq);

        return "show_risk_micro_task_edit_survey_report";
    }

    /**
     * 任务反馈
     *
     * @return
     */
    public String feedbackRiskMicroTask() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String rwbgDm = getRwbgDm();
        String fxqNd = request.getParameter("fxqNd");
        String fxqSjq = request.getParameter("fxqSjq");
        String fxqSjz = request.getParameter("fxqSjz");
        String nsrsbh = request.getParameter("nsrsbh");
        String jbjb = request.getParameter("jbjb");
        String fxzStr = request.getParameter("fxz");
        BigDecimal fxz = new BigDecimal(fxzStr);
        String ytfx = request.getParameter("ytfx");
        String jbqk = request.getParameter("jbqk");
        String dcqk = request.getParameter("dcqk");
        String xgfx = request.getParameter("xgfx");
        String lscs = request.getParameter("lscs");
        String rwfbryDm = request.getParameter("rwfbryDm") == null ? "" : request.getParameter("rwfbryDm");
        ;
        String rwzxryDm = request.getParameter("rwzxryDm") == null ? "" : request.getParameter("rwzxryDm");
        String rwshryMc = request.getParameter("rwshryMc") == null ? "" : request.getParameter("rwshryMc");
        String dcrqQ = request.getParameter("dcrqQ");
        String dcrqZ = request.getParameter("dcrqZ");

        /*实际反馈日期*/
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sjfkRq = sdf.format(date);


        RiskMicroTaskSurveyReport riskMicroTaskSurveyReport = new RiskMicroTaskSurveyReport();
        riskMicroTaskSurveyReport.setRwbgDm(rwbgDm);
        riskMicroTaskSurveyReport.setFxqNd(fxqNd);
        riskMicroTaskSurveyReport.setFxqSjq(fxqSjq);
        riskMicroTaskSurveyReport.setFxqSjz(fxqSjz);
        riskMicroTaskSurveyReport.setNsrsbh(nsrsbh);
        riskMicroTaskSurveyReport.setFxz(fxz);
        riskMicroTaskSurveyReport.setJbjb(jbjb);

        riskMicroTaskSurveyReport.setXgfx(xgfx);
        riskMicroTaskSurveyReport.setJbqk(jbqk);
        riskMicroTaskSurveyReport.setDcqk(dcqk);
        riskMicroTaskSurveyReport.setYtfx(ytfx);
        riskMicroTaskSurveyReport.setLscs(lscs);

        /*riskMicroTaskSurveyReport.setCzwt(czwt);*/
        riskMicroTaskSurveyReport.setRwfbryDm(rwfbryDm);
        riskMicroTaskSurveyReport.setRwzxryDm(rwzxryDm);
        riskMicroTaskSurveyReport.setRwshryMc(rwshryMc);
        riskMicroTaskSurveyReport.setDcrqQ(dcrqQ);
        riskMicroTaskSurveyReport.setDcrqZ(dcrqZ);
        riskMicroTaskSurveyReport.setSjfkRq(sjfkRq);

        // 插入新的任务调查报告
        RiskMicroTaskSurveyReportService riskMicroTaskSurveyReportService = new RiskMicroTaskSurveyReportService();
        riskMicroTaskSurveyReportService.add(riskMicroTaskSurveyReport);

        // 更新微观任务的对应记录
        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        int id = Integer.valueOf(request.getParameter("riskMicroTaskId"));
        RiskMicroTask microTask = microTaskService.getRiskMicroTaskById(id);
        microTask.setSjfkRq(sjfkRq);
        microTask.setRwbgDm(rwbgDm);
        microTask.setRwztDm("04");
        microTaskService.update(microTask);

        return "feedback_micro_task";
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

        MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
        String maxWgrwDm = microTaskSurveyReportService.getMaxWgrwDm(dateStr);
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
    public String showRiskMicroTaskToExamTable() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        String rwztDm = "";

        // 已反馈的任务列表
        rwztDm = "04";
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "show_risk_micro_task_to_exam_table";
    }

    /**
     * 显示任务调查报告
     *
     * @return
     */
    public String showRiskMicroTaskSurveyReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String microTaskIdStr = request.getParameter("microTaskId");
        int id = Integer.valueOf(microTaskIdStr);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask microTask = microTaskService.getRiskMicroTaskById(id);

        RiskMicroTaskSurveyReportService microTaskSurveyReportService = new RiskMicroTaskSurveyReportService();
        RiskMicroTaskSurveyReport riskMicroTaskSurveyReport = microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm());

        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        String fxqStr = "";
        fxqStr = riskMicroTaskSurveyReport.getFxqNd() + "-" + riskMicroTaskSurveyReport.getFxqSjq() + "--" + riskMicroTaskSurveyReport.getFxqNd() + "-" + riskMicroTaskSurveyReport.getFxqSjz();

        request.setAttribute("riskMicroTaskSurveyReport", riskMicroTaskSurveyReport);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("fxqStr", fxqStr);
        return "show_risk_micro_task_survey_report";
    }

    /**
     * 显示微观任务评价和考核界面
     *
     * @return
     */
    public String showRiskMicroTaskExamUI() {

        HttpServletRequest request = ServletActionContext.getRequest();

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        String rwztDm = "";

        // 已发布的任务列表
        rwztDm = "02";
        List<RiskMicroTask> publishMicroTaskList = microTaskService.getRiskMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
        int publishMicroTaskListSize = publishMicroTaskList.size();

        // 已反馈的任务列表
        rwztDm = "04";
        List<RiskMicroTask> feedbackMicroTaskList = microTaskService.getRiskMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
        int feedbackMicroTaskListSize = feedbackMicroTaskList.size();

        // 已评价的任务列表
        rwztDm = "05";
        List<RiskMicroTask> examMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwfbryDm, rwztDm);
        int examMicroTaskListSize = examMicroTaskList.size();

        // msg
        String msg = request.getParameter("msg");
        if ("exam_succ".equals(msg)) {
            msg = "下发评价成功!";
        } else {
            msg = "";
        }

        request.setAttribute("publishMicroTaskListSize", publishMicroTaskListSize);
        request.setAttribute("feedbackMicroTaskListSize", feedbackMicroTaskListSize);
        request.setAttribute("examMicroTaskListSize", examMicroTaskListSize);
        request.setAttribute("msg", msg);

        return "show_risk_micro_task_exam_ui";
    }

    /**
     * 在任务考核模块中显示任务发布列表
     *
     * @return
     */
    public String showRiskMicroTaskPublishTableInExamSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        // 任务状态
        String rwztDm = "02";

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);


        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "show_risk_micro_task_publish_table_in_exam_section";
    }

    /**
     * 在任务评价模块中显示需要评价的任务列表
     *
     * @return
     */
    public String showRiskMicroTaskToExamTableInExamSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();

        // 任务状态
        String rwztDm = "04";

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);


        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "show_risk_micro_task_to_exam_table_in_exam_section";
    }

    /**
     * 打开提交任务评价报告窗口
     *
     * @return
     */
    public String showRiskMicroTaskSubmitExamReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String microTaskIdStr = request.getParameter("id");
        int id = Integer.valueOf(microTaskIdStr);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask riskMicroTask = microTaskService.getRiskMicroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        // 评价日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rwpjRq = sdf.format(date);

        request.setAttribute("riskMicroTask", riskMicroTask);
        request.setAttribute("taxUnit", taxUnit);
        request.setAttribute("rwpjRq", rwpjRq);

        return "show_risk_micro_task_submit_exam_report";
    }

    /**
     * 评价任务
     *
     * @return
     */
    public String examRiskMicroTask() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        String microTaskIdStr = request.getParameter("id");
        int id = Integer.valueOf(microTaskIdStr);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask microTask = microTaskService.getRiskMicroTaskById(id);

        String glpj = request.getParameter("glpj");
        String zfpj = request.getParameter("zfpj");
        String zhpj = request.getParameter("zhpj");
        String pjryDm = request.getParameter("pjryDm");
        String rwpjRq = request.getParameter("rwpjRq");
        String rwztDm = "05";

        microTask.setGlpj(glpj);
        microTask.setZfpj(zfpj);
        microTask.setZhpj(zhpj);
        microTask.setPjryDm(pjryDm);
        microTask.setRwpjRq(rwpjRq);
        microTask.setRwztDm(rwztDm);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 计算任务得分
        // 1. 计算接收得分
        int rwjsDf = 0;
        String bzjsRqStr = microTask.getBzjsRq();    // 标准接收日期
        Date bzjsRqDate = sdf.parse(bzjsRqStr);

        String sjjsRqStr = microTask.getSjjsRq().substring(0, 10);    // 实际接收日期
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
        String bzfkRqStr = microTask.getBzfkRq();    // 标准反馈日期
        Date bzfkRqDate = sdf.parse(bzfkRqStr);

        String sjfkRqStr = microTask.getSjfkRq();    // 实际反馈日期
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
        zhpj = microTask.getZhpj();
        if (zhpj.equals("优秀")) {
            rwzxDf = 40;
        } else if (zhpj.equals("良好")) {
            rwzxDf = 32;
        } else if (zhpj.equals("差")) {
            rwzxDf = 24;
        }

        microTask.setRwjsDf(rwjsDf);
        microTask.setRwfkDf(rwfkDf);
        microTask.setRwzxDf(rwzxDf);

        microTaskService.update(microTask);

        request.setAttribute("msg", "下发评价成功！");
        return "exam_micro_task";
    }

    /**
     * 任务评价模块中显示任务评价列表
     *
     * @return
     */
    public String finishExamTableInExamSection() {

        HttpServletRequest request = ServletActionContext.getRequest();

        // 任务状态
        String rwztDm = "05";

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
//		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByRwzt(rwztDm);
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwfbryDm = user.getCode();
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwfbryDm, rwztDm);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "finish_exam_table_in_exam_section";
    }

    /**
     * 显示任务评价报告
     *
     * @return
     */
    public String showRiskMicroTaskExamReport() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String microTaskIdStr = request.getParameter("id");
        int id = Integer.valueOf(microTaskIdStr);

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        RiskMicroTask riskMicroTask = microTaskService.getRiskMicroTaskById(id);

        // 核算机关
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);

        request.setAttribute("riskMicroTask", riskMicroTask);
        request.setAttribute("taxUnit", taxUnit);

        return "show_risk_micro_task_exam_report";
    }

    /**
     * 在接收界面显示已评价任务列表
     *
     * @return
     */
    public String showMicroTaskFinishExamTableInReceiveSection() {

        HttpServletRequest request = ServletActionContext.getRequest();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String rwzxryDm = user.getCode();

        RiskMicroTaskService microTaskService = new RiskMicroTaskService();
        String rwztDm = "";

        // 已评价的任务列表
        rwztDm = "05";
        List<RiskMicroTask> riskMicroTaskList = microTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);

        request.setAttribute("riskMicroTaskList", riskMicroTaskList);

        return "show_risk_micro_task_finish_exam_table_in_receive_section";
    }

    /**
     * 显示任务阈值设定界面
     *
     * @return
     */
    public String showTaskThresholdSetting() {

        HttpServletRequest request = ServletActionContext.getRequest();
        TaskThresholdService taskThresholdService = new TaskThresholdService();

        // 微观任务阈值
        String rwLx = "wgrw";
        int wgrwYz = taskThresholdService.getRwyzByRwlx(rwLx);

        // 总/分量任务阈值
        rwLx = "hgrw";
        int hgrwYz = taskThresholdService.getRwyzByRwlx(rwLx);

        request.setAttribute("wgrwYz", wgrwYz);
        request.setAttribute("hgrwYz", hgrwYz);
        return "show_task_threshold_setting";
    }

    /**
     * 设定任务阈值
     *
     * @return
     */
    public String setThreshold() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String wgrwYzStr = request.getParameter("wgrwYz");
        String hgrwYzStr = request.getParameter("hgrwYz");

        int wgrwYz = Integer.valueOf(wgrwYzStr);
        int hgrwYz = Integer.valueOf(hgrwYzStr);

        TaskThresholdService taskThresholdService = new TaskThresholdService();

        // 微观任务阈值设定
        String rwLx = "wgrw";
        int rwyz = wgrwYz;
        taskThresholdService.updateRwyzByRwlx(rwLx, rwyz);

        // 总/分量任务阈值
        rwLx = "hgrw";
        rwyz = hgrwYz;
        taskThresholdService.updateRwyzByRwlx(rwLx, rwyz);

        String msg = "设置成功！";

        request.setAttribute("msg", msg);
        return "set_threshold";
    }

    /**
     * 显示总分值计算过程
     *
     * @return
     */
    public String showZfzDetail() {

        HttpServletRequest request = ServletActionContext.getRequest();
        int id = Integer.valueOf(request.getParameter("microTaskId"));

        MicroTaskService microTaskService = new MicroTaskService();
        MicroTask microTask = microTaskService.getMicroTaskById(id);

        String nsrsbh = microTask.getNsrsbh().trim();
        DataCalcParameter dataCalcParameter = new DataCalcParameter();
        dataCalcParameter.setFxqNd(microTask.getFxqNd());
        dataCalcParameter.setFxqSssqQ(microTask.getFxqSjq());
        dataCalcParameter.setFxqSssqZ(microTask.getFxqSjz());

        AnalysisMicroIndexService analysisMicroIndexService = new AnalysisMicroIndexService();
        List<AnalysisMicroIndex> analysisMicroIndexList = analysisMicroIndexService.getListByTaxPayerCodeAndFxq(nsrsbh, dataCalcParameter);

        StringBuffer calcProcess = new StringBuffer("总分值计算过程如下：<br/>");
        StringBuffer calcTmp = new StringBuffer("");

        DecimalFormat floatFormat = new DecimalFormat("0.00");     // 两位小数

        for (int i = 0; i < analysisMicroIndexList.size(); i++) {
            calcProcess.append("(").append(i + 1).append(")&nbsp;&nbsp;")        // 显示：(1)
                    .append(analysisMicroIndexList.get(i).getFxzblx().trim()).append("：")    // 显示：(1)欠缴税金 ：
            ;

            // 当处于异常区间1时，3/120 * 变动率 * 权重
            if (analysisMicroIndexList.get(i).getTzqj().equals("ycqj1")) {
                if (analysisMicroIndexList.get(i).getBdl() != null) {
//					calcProcess.append("3/120 * ")
//					   .append(analysisMicroIndexList.get(i).getBdl().floatValue())
//					   .append("*").append(analysisMicroIndexList.get(i).getZbqz())
//					   .append("=").append(floatFormat.format(3.0 /120 * analysisMicroIndexList.get(i).getBdl().floatValue() * analysisMicroIndexList.get(i).getZbqz()))
//					   .append("<br/>");
                    calcProcess.append("3/120 * ");

                    // 变动率>=1000的标记红色
                    if (analysisMicroIndexList.get(i).getBdl().floatValue() >= 1000) {
                        calcProcess.append("<font color=red>")
                                .append(analysisMicroIndexList.get(i).getBdl().floatValue())
                                .append("</font>");
                    } else {
                        calcProcess.append(analysisMicroIndexList.get(i).getBdl().floatValue());
                    }

                    calcProcess.append("*").append(analysisMicroIndexList.get(i).getZbqz())
                            .append("=").append(floatFormat.format(3.0 / 120 * analysisMicroIndexList.get(i).getBdl().floatValue() * analysisMicroIndexList.get(i).getZbqz()))
                            .append("<br/>");

                    if (i != 0)
                        calcTmp.append("+");
                    calcTmp.append(floatFormat.format(3.0 / 120 * analysisMicroIndexList.get(i).getBdl().floatValue() * analysisMicroIndexList.get(i).getZbqz()));
                }

            } else {
                calcProcess.append(analysisMicroIndexList.get(i).getTzfz()).append("*")
                        .append(analysisMicroIndexList.get(i).getZbqz())
                        .append("=").append(analysisMicroIndexList.get(i).getTzfz() * analysisMicroIndexList.get(i).getZbqz())
                        .append("<br/>");

                if (i != 0)
                    calcTmp.append("+");
                calcTmp.append(analysisMicroIndexList.get(i).getTzfz() * analysisMicroIndexList.get(i).getZbqz());
            }
        }

        calcProcess.append(microTask.getZfz()).append("=").append(calcTmp);

        request.setAttribute("calcProcess", calcProcess);
        return "show_zfz_detail";
    }

    private List<RiskDetail> getRiskDetailList(int weight, RiskMicroTask riskMicroTask) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InvocationTargetException {
        List<RiskDetail> list = new ArrayList<RiskDetail>();
        Class clazz = riskMicroTask.getClass();
        int t = 0;
        if (weight == 1 || weight == 2) {
            t = 24;
        } else if (weight == 3) {
            t = 10;
        } else if (weight == 4 || weight == 6)
            t = 6;
        else if (weight == 5 || weight == 7)
            t = 4;

        for (int i = 1; i <= t; i++) {
            RiskDetail riskDetail = new RiskDetail();
            String getFzMethodStr = "getFz" + String.valueOf(i);
            String getJszMethodStr = "getJsz" + String.valueOf(i);
            Method getFzMethod = clazz.getDeclaredMethod(getFzMethodStr);
            Method getJszMethod = clazz.getDeclaredMethod(getJszMethodStr);
            String zb = "R" + String.valueOf(i);
            String fz = getFzMethod.invoke(riskMicroTask).toString();
            String jsz = getJszMethod.invoke(riskMicroTask).toString();
            String tz = getRiskFeather(zb, new BigDecimal(jsz));
            String fxjb = getRiskLevel(new BigDecimal(fz));
            riskDetail.setZb(zb);
            riskDetail.setZbz(fz);
            riskDetail.setFxjb(fxjb);
            riskDetail.setJsz(jsz);
            riskDetail.setTz(tz);
            list.add(riskDetail);
        }
        return list;
    }

    private String getRiskDetailStr(int weight, RiskMicroTask riskMicroTask) {
        StringBuffer detail = new StringBuffer();
        detail.append("{");
        detail.append("\"chart\":[");
        //权重1
        if (weight == 1 || weight == 2) {
            detail.append("{");
            detail.append("\"R1:\"" + riskMicroTask.getFz1().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz1()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz1().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R1", riskMicroTask.getJsz1()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R2:\"" + riskMicroTask.getFz2().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz2()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz2().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R2", riskMicroTask.getJsz2()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R3:\"" + riskMicroTask.getFz3().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz3()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz3().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R3", riskMicroTask.getJsz3()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R4:\"" + riskMicroTask.getFz4().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz4()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz4().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R4", riskMicroTask.getJsz4()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R5:\"" + riskMicroTask.getFz5().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz5()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz5().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R5", riskMicroTask.getJsz5()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R6:\"" + riskMicroTask.getFz6().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz6()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz6().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R6", riskMicroTask.getJsz6()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R7:\"" + riskMicroTask.getFz7().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz7()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz7().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R7", riskMicroTask.getJsz7()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R8:\"" + riskMicroTask.getFz8().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz8()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz8().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R8", riskMicroTask.getJsz8()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R9:\"" + riskMicroTask.getFz9().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz9()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz9().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R9", riskMicroTask.getJsz9()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R10:\"" + riskMicroTask.getFz10().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz10()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz10().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R10", riskMicroTask.getJsz10()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R11:\"" + riskMicroTask.getFz11().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz11()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz11().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R11", riskMicroTask.getJsz11()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R12:\"" + riskMicroTask.getFz12().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz12()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz12().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R12", riskMicroTask.getJsz12()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R13:\"" + riskMicroTask.getFz13().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz13()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz13().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R13", riskMicroTask.getJsz13()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R14:\"" + riskMicroTask.getFz14().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz14()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz14().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R14", riskMicroTask.getJsz14()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R15:\"" + riskMicroTask.getFz15().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz15()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz15().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R15", riskMicroTask.getJsz15()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R16:\"" + riskMicroTask.getFz16().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz16()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz16().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R16", riskMicroTask.getJsz16()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R17:\"" + riskMicroTask.getFz17().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz17()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz17().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R17", riskMicroTask.getJsz17()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R18:\"" + riskMicroTask.getFz15().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz18()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz18().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R18", riskMicroTask.getJsz18()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R19:\"" + riskMicroTask.getFz19().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz19()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz19().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R19", riskMicroTask.getJsz19()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R20:\"" + riskMicroTask.getFz20().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz20()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz20().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R20", riskMicroTask.getJsz20()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R21:\"" + riskMicroTask.getFz21().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz21()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz21().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R21", riskMicroTask.getJsz21()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R22:\"" + riskMicroTask.getFz22().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz22()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz22().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R22", riskMicroTask.getJsz22()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R23:\"" + riskMicroTask.getFz23().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz23()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz23().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R23", riskMicroTask.getJsz23()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R24:\"" + riskMicroTask.getFz24().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz24()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz24().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R24", riskMicroTask.getJsz24()) + "\"");
            detail.append("}");

        } else if (weight == 3) {//权重2
            detail.append("{");
            detail.append("\"R1:\"" + riskMicroTask.getFz1().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz1()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz1().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R1", riskMicroTask.getJsz1()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R2:\"" + riskMicroTask.getFz2().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz2()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz2().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R2", riskMicroTask.getJsz2()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R3:\"" + riskMicroTask.getFz3().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz3()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz3().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R3", riskMicroTask.getJsz3()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R4:\"" + riskMicroTask.getFz4().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz4()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz4().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R4", riskMicroTask.getJsz4()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R5:\"" + riskMicroTask.getFz5().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz5()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz5().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R5", riskMicroTask.getJsz5()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R6:\"" + riskMicroTask.getFz6().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz6()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz6().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R6", riskMicroTask.getJsz6()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R7:\"" + riskMicroTask.getFz7().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz7()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz7().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R7", riskMicroTask.getJsz7()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R8:\"" + riskMicroTask.getFz8().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz8()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz8().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R8", riskMicroTask.getJsz8()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R9:\"" + riskMicroTask.getFz9().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz9()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz9().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R9", riskMicroTask.getJsz9()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R10:\"" + riskMicroTask.getFz10().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz10()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz10().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R10", riskMicroTask.getJsz10()) + "\"");
            detail.append("}");
        } else if (weight == 4 || weight == 6) {
            detail.append("{");
            detail.append("\"R1:\"" + riskMicroTask.getFz1().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz1()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz1().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R1", riskMicroTask.getJsz1()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R2:\"" + riskMicroTask.getFz2().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz2()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz2().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R2", riskMicroTask.getJsz2()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R3:\"" + riskMicroTask.getFz3().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz3()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz3().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R3", riskMicroTask.getJsz3()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R4:\"" + riskMicroTask.getFz4().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz4()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz4().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R4", riskMicroTask.getJsz4()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R5:\"" + riskMicroTask.getFz5().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz5()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz5().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R5", riskMicroTask.getJsz5()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R6:\"" + riskMicroTask.getFz6().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz6()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz6().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R6", riskMicroTask.getJsz6()) + "\"");
            detail.append("}");
        } else if (weight == 5 || weight == 7) {
            detail.append("{");
            detail.append("\"R1:\"" + riskMicroTask.getFz1().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz1()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz1().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R1", riskMicroTask.getJsz1()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R2:\"" + riskMicroTask.getFz2().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz2()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz2().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R2", riskMicroTask.getJsz2()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R3:\"" + riskMicroTask.getFz3().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz3()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz3().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R3", riskMicroTask.getJsz3()) + "\"");
            detail.append("},");
            detail.append("{");
            detail.append("\"R4:\"" + riskMicroTask.getFz4().toString() + "\",");
            detail.append("\"风险级别:\"" + getRiskLevel(riskMicroTask.getFz4()) + "\",");
            detail.append("\"计算值:\"" + riskMicroTask.getJsz4().toString() + "\",");
            detail.append("\"特征:\"" + getRiskFeather("R4", riskMicroTask.getJsz4()) + "\"");
            detail.append("}");
        }
        detail.append("]}");
        return detail.toString();
    }

    private String getRiskLevel(BigDecimal fxz) {
        float fxzInt = fxz.floatValue();
        if (fxzInt >= 5.00)
            return "红色";
        else if (fxzInt < 5.00 && fxzInt >= 4.00)
            return "橙色";
        else if (fxzInt < 4.00 && fxzInt >= 3.00)
            return "蓝色";
        else
            return "绿色";

    }

    private String getRiskFeather(String index, BigDecimal jsz) {
        RiskIndexFeatherDao riskIndexFeatherDao = new RiskIndexFeatherDao();
        float jszFLoat = jsz.floatValue();
        if (index != null && !index.equals("")) {
            RiskIndexFeather riskIndexFeather = riskIndexFeatherDao.getRiskIndexFeatherById(index);
            if (jszFLoat >= 5.00)
                return riskIndexFeather.getTz1();
            else if (jszFLoat > 1.00 && jszFLoat < 5.00)
                return riskIndexFeather.getTz2();
            else if (jszFLoat == 1.00)
                return riskIndexFeather.getTz3();
            else if (jszFLoat > 0.20 && jszFLoat < 1.00)
                return riskIndexFeather.getTz4();
            else if (jszFLoat <= 0.20 && jszFLoat > 0.00)
                return riskIndexFeather.getTz5();
            else
                return riskIndexFeather.getTz6();
        } else
            return null;
    }

    private void printJson(String res) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    private void printHtml(String msg) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert out != null;
        out.flush();
        out.close();
    }

}

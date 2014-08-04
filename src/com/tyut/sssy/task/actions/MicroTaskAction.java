package com.tyut.sssy.task.actions;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.AnalysisIndexService;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.TaxPayerService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.IndexFeatureDBService;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.domain.AnalysisMicroIndex;
import com.tyut.sssy.task.domain.MicroTask;
import com.tyut.sssy.task.domain.MicroTaskExtractParameter;
import com.tyut.sssy.task.domain.MicroTaskSurveyReport;
import com.tyut.sssy.task.service.AnalysisMicroIndexService;
import com.tyut.sssy.task.service.MicroTaskService;
import com.tyut.sssy.task.service.MicroTaskSurveyReportService;
import com.tyut.sssy.task.service.TaskThresholdService;

/**
 * 
 * 项目名称：sssy20120618
 * 类名称：MicroTaskAction  
 * 类描述：微观任务action  
 * 创建人：梁斌  
 * 创建时间：2012-6-18 下午03:47:46  
 * 修改人：梁斌  
 * 修改时间：2012-6-18 下午03:47:46  
 * 修改备注：  
 * @version 
 *
 */
public class MicroTaskAction {

	/**
	 * 显示微观任务提取选择条件界面
	 * @return
	 */
	public String showMicroTaskExtractConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
//		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitListByType("Y");
		
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
		
		return "show_micro_task_extract_condition_ui";
	}
	
	/**
	 * 显示微观任务提取列表
	 * @return
	 */
	public String showMicroTaskExtractTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		MicroTaskExtractParameter microTaskExtractParameter = new MicroTaskExtractParameter();
		
		String swjgMc = "";
		String nsrmc = "";
		String hydlMc = "";
		String qyzclxMc = "";
		String fxzbMc = "";
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
			
	 		// 税务机关
			String swjgDm = request.getParameter("swjgDm");
			
			if ("0".equals(swjgDm)) {
				swjgDm = "%";
				swjgMc = "全部";
			} else {
				TaxUnitService taxUnitService = new TaxUnitService();
				swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
			}
			
			// 纳税人识别号
			String nsrsbh = request.getParameter("nsrsbh");
			
			if ("".equals(nsrsbh)) {
				nsrsbh = "%";
				nsrmc = "全部";
			} else {
				TaxPayerService taxPayerService = new TaxPayerService();
				nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
			}
			
			// 行业大类
			String hydlDm = request.getParameter("hydlDm");
			
			if ("".equals(hydlDm)) {
				hydlDm = "%";
				hydlMc = "全部";
			} else {
				BigIndustryService bigIndustryService = new BigIndustryService();
				hydlMc = bigIndustryService.getBigIndustryById(hydlDm).getMc();
			}
			
			// 注册类型
			String qyzclxDm = request.getParameter("qyzclxDm");
			
			if ("".equals(qyzclxDm)) {
				qyzclxDm = "%";
				qyzclxMc = "全部";
			} else {
				FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
				qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclxDm).getMc();
			}
			
			// 指标名称
			String fxzbDm = request.getParameter("fxzbDm");
			
			if ("".equals(fxzbDm)) {
				fxzbDm = "%";
				fxzbMc = "全部";
			} else {
				AnalysisIndexService analysisIndexService = new AnalysisIndexService();
				fxzbMc = analysisIndexService.getAnalysisIndexById(fxzbDm).getMc();
			}
			
			// 任务状态
			String rwztDm = request.getParameter("rwztDm");
			if ("".equals(rwztDm)) {
				rwztDm = "%";
			}
			
			microTaskExtractParameter.setFxqNd(fxqNd);
			microTaskExtractParameter.setFxqSjq(fxqSjq);
			microTaskExtractParameter.setFxqSjz(fxqSjz);
			microTaskExtractParameter.setSwjgDm(swjgDm);
			microTaskExtractParameter.setNsrsbh(nsrsbh);
			microTaskExtractParameter.setHydlDm(hydlDm);
			microTaskExtractParameter.setQyzclxDm(qyzclxDm);
			microTaskExtractParameter.setFxzbDm(fxzbDm);
			microTaskExtractParameter.setRwztDm(rwztDm);
			microTaskExtractParameter.setType(type);
			microTaskExtractParameter.setOrder(order);
		} else {
			// 排序
			String fxqNd = request.getParameter("fxqNd");
			String fxqSjq = request.getParameter("fxqSjq");
			String fxqSjz = request.getParameter("fxqSjz");
			String swjgDm = request.getParameter("swjgDm");
			String nsrsbh = request.getParameter("nsrsbh");
			String hydlDm = request.getParameter("hydlDm");
			String qyzclxDm = request.getParameter("qyzclxDm");
			String fxzbDm = request.getParameter("fxzbDm");
			String rwztDm = request.getParameter("rwztDm");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			
			// 税务机关
			if ("0".equals(swjgDm)) {
				swjgDm = "%";
				swjgMc = "全部";
			} else {
				TaxUnitService taxUnitService = new TaxUnitService();
				swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
			}
			
			// 纳税人识别号
			if ("".equals(nsrsbh)) {
				nsrsbh = "%";
				nsrmc = "全部";
			} else {
				TaxPayerService taxPayerService = new TaxPayerService();
				nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
			}
			
			// 行业大类
			if ("".equals(hydlDm)) {
				hydlDm = "%";
				hydlMc = "全部";
			} else {
				BigIndustryService bigIndustryService = new BigIndustryService();
				hydlMc = bigIndustryService.getBigIndustryById(hydlDm).getMc();
			}
			
			// 注册类型
			if ("".equals(qyzclxDm)) {
				qyzclxDm = "%";
				qyzclxMc = "全部";
			} else {
				FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
				qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclxDm).getMc();
			}
			
			// 指标名称
			if ("".equals(fxzbDm)) {
				fxzbDm = "%";
				fxzbMc = "全部";
			} else {
				AnalysisIndexService analysisIndexService = new AnalysisIndexService();
				fxzbMc = analysisIndexService.getAnalysisIndexById(fxzbDm).getMc();
			}
			
			microTaskExtractParameter.setFxqNd(fxqNd);
			microTaskExtractParameter.setFxqSjq(fxqSjq);
			microTaskExtractParameter.setFxqSjz(fxqSjz);
			microTaskExtractParameter.setSwjgDm(swjgDm);
			microTaskExtractParameter.setNsrsbh(nsrsbh);
			microTaskExtractParameter.setHydlDm(hydlDm);
			microTaskExtractParameter.setQyzclxDm(qyzclxDm);
			microTaskExtractParameter.setFxzbDm(fxzbDm);
			microTaskExtractParameter.setRwztDm(rwztDm);
			microTaskExtractParameter.setType(type);
			microTaskExtractParameter.setOrder(order);
		}
		
		MicroTaskService microTaskService = new MicroTaskService();
		List<MicroTask> microTaskList = microTaskService.getExtractMicroTaskList(microTaskExtractParameter);
		
		request.setAttribute("swjgMc", swjgMc);
		request.setAttribute("nsrmc", nsrmc);
		request.setAttribute("hydlMc", hydlMc);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("fxzbMc", fxzbMc);
		request.setAttribute("fxq", fxq);
		
		if (microTaskExtractParameter.getSwjgDm().equals("%"))
			microTaskExtractParameter.setSwjgDm("0");
		if (microTaskExtractParameter.getNsrsbh().equals("%"))
			microTaskExtractParameter.setNsrsbh("");
		if (microTaskExtractParameter.getHydlDm().equals("%"))
			microTaskExtractParameter.setHydlDm("");
		if (microTaskExtractParameter.getQyzclxDm().equals("%"))
			microTaskExtractParameter.setQyzclxDm("");
		if (microTaskExtractParameter.getFxzbDm().equals("%"))
			microTaskExtractParameter.setFxzbDm("");
		request.setAttribute("microTaskExtractParameter", microTaskExtractParameter);
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "show_micro_task_extract_table";
	}
	
	/**
	 * 提取微观任务
	 * @return
	 */
	public String extractMicroTask() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String idStrArr[] = request.getParameterValues("microTaskId");
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String rwtqRq = sdf.format(date);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = null;
		for (int i = 0; i < idStrArr.length; i++) {
			int id = Integer.valueOf(idStrArr[i]);
			microTask = new MicroTask();
			microTask = microTaskService.getMicroTaskById(id);
			
			microTask.setRwtqryDm(user.getCode());
			microTask.setRwtqRq(rwtqRq);
			microTask.setRwztDm("01");
			
			microTaskService.update(microTask);
		}
		
		request.setAttribute("msg", "提取成功！");
		return "extract_micro_task";
	}
	
	/**
	 * 显示指标详情
	 * @return
	 */
	public String showIndexDetail() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String fxzbDm = request.getParameter("fxzbDm");
		String tzqj = request.getParameter("tzqj");
		int id = Integer.valueOf(request.getParameter("id"));
		
		IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
		IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(fxzbDm, tzqj);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		BigDecimal bdl = microTask.getBdl();
		
		float bdlFloat = 0.0f;
		if (bdl != null)
			bdlFloat = bdl.floatValue();
		request.setAttribute("indexFeatureDB", indexFeatureDB);
		request.setAttribute("bdlFloat", bdlFloat);
		
		String fxq = microTask.getFxqNd() + "-" + microTask.getFxqSjq() + " -- " + microTask.getFxqNd() + "-" + microTask.getFxqSjz(); 
		
		// 显示变动率计算过程
		String nsrsbh = microTask.getNsrsbh();
		String fxqNd = microTask.getFxqNd();
		String fxqSjq = microTask.getFxqSjq();
		String fxqSjz = microTask.getFxqSjz();
		fxzbDm = microTask.getFxzbDm();
		
		AnalysisMicroIndexService analysisMicroIndexService = new AnalysisMicroIndexService();
		AnalysisMicroIndex analysisMicroIndex = analysisMicroIndexService.getForProcess(nsrsbh, fxqNd, fxqSjq, fxqSjz, fxzbDm);	// 根据参数获得分析微观指标
		
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		String zbMc = analysisIndexService.getAnalysisIndexById(fxzbDm).getMc();
		
		request.setAttribute("analysisMicroIndex", analysisMicroIndex);
		request.setAttribute("microTask", microTask);
		request.setAttribute("zbMc", zbMc);
		request.setAttribute("fxq", fxq);
		return "show_index_detail";
	}
	
	/**
	 * 显示微观任务发布选择条件界面
	 * @return
	 */
	public String showMicroTaskPublishConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
//		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitListByType("Y");
		
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
		
		return "show_micro_task_publish_condition_ui";
	}
	
	/**
	 * 显示微观任务发布列表
	 * @return
	 */
	public String showMicroTaskPublishTable() {

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
		
		// 指标名称
		String fxzbDm = request.getParameter("fxzbDm");
		String fxzbMc = "";
		if ("".equals(fxzbDm)) {
			fxzbDm = "%";
			fxzbMc = "全部";
		} else {
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			fxzbMc = analysisIndexService.getAnalysisIndexById(fxzbDm).getMc();
		}
		
		// 任务状态
		String rwztDm = request.getParameter("rwztDm");
		
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
		
		MicroTaskExtractParameter microTaskExtractParameter = new MicroTaskExtractParameter(); 
		microTaskExtractParameter.setFxqNd(fxqNd);
		microTaskExtractParameter.setFxqSjq(fxqSjq);
		microTaskExtractParameter.setFxqSjz(fxqSjz);
		microTaskExtractParameter.setSwjgDm(swjgDm);
		microTaskExtractParameter.setNsrsbh(nsrsbh);
		microTaskExtractParameter.setHydlDm(hydlDm);
		microTaskExtractParameter.setQyzclxDm(qyzclxDm);
		microTaskExtractParameter.setFxzbDm(fxzbDm);
		microTaskExtractParameter.setRwztDm(rwztDm);
		microTaskExtractParameter.setType("none");
		microTaskExtractParameter.setOrder("normal");
		
		MicroTaskService microTaskService = new MicroTaskService();
		List<MicroTask> microTaskList = microTaskService.getExtractMicroTaskList(microTaskExtractParameter);
		
		request.setAttribute("swjgMc", swjgMc);
		request.setAttribute("nsrmc", nsrmc);
		request.setAttribute("hydlMc", hydlMc);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("fxzbMc", fxzbMc);
		request.setAttribute("fxq", fxq);
		request.setAttribute("userList", userList);
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "show_micro_task_publish_table";
	}
	
	/**
	 * 发布微观任务
	 * @return
	 */
	public String publishMicroTask() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String idStrArr[] = request.getParameterValues("microTaskId");
		String rwzxryDm = request.getParameter("rwzxryDm");
		String bzjsRq = request.getParameter("bzjsRq");
		String bzfkRq = request.getParameter("bzfkRq");
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String rwfbRq = sdf.format(date);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = null;
		for (int i = 0; i < idStrArr.length; i++) {
			int id = Integer.valueOf(idStrArr[i]);
			microTask = new MicroTask();
			microTask = microTaskService.getMicroTaskById(id);
			
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
		
		return "publish_micro_task";
	}
	
	/**
	 * 发布成功报告
	 * @return
	 */
	public String microTaskPublishReport() {
		
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
		
		return "micro_task_publish_report";
	}
	
	/**
	 * 显示微观任务接收界面
	 * @return
	 */
	public String showMicroTaskReceiveUI() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwzxryDm = user.getCode();
		
		MicroTaskService microTaskService = new MicroTaskService();
		String rwzrDm = "";
		
		// 已发布的任务列表
		rwzrDm = "02";
		List<MicroTask> publishMicroTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		int publishMicroTaskListSize = publishMicroTaskList.size();
		
		// 已接收的任务列表
		rwzrDm = "03";
		List<MicroTask> receiveMicroTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		int receiveMicroTaskListSize = receiveMicroTaskList.size();
		
		// 已执行(已反馈)的任务列表
		rwzrDm = "04";
		List<MicroTask> feedbackMicroTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		int feedbackMicroTaskListSize = feedbackMicroTaskList.size();
		
		// 已评价的任务列表
		rwzrDm = "05";
		List<MicroTask> examMicroTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		int examMicroTaskListSize = examMicroTaskList.size();
		
		// msg
		String msg = request.getParameter("msg");
		if ("feedback_succ".equals(msg)) {
			msg = "上报成功!";
		} else if ("receive_succ".equals(msg)) {
			msg = "接收成功!";
		} else {
			msg = "";
		}
		
		request.setAttribute("publishMicroTaskListSize", publishMicroTaskListSize);
		request.setAttribute("receiveMicroTaskListSize", receiveMicroTaskListSize);
		request.setAttribute("feedbackMicroTaskListSize", feedbackMicroTaskListSize);
		request.setAttribute("examMicroTaskListSize", examMicroTaskListSize);
		request.setAttribute("msg", msg);
		
		return "show_micro_task_receive_ui";
	}
	
	/**
	 * 显示接收任务列表
	 * @return
	 */
	public String showMicroTaskReceiveTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwzxryDm = user.getCode();
		
		MicroTaskService microTaskService = new MicroTaskService();
		String rwzrDm = "";
		
		// 已发布的任务列表
		rwzrDm = "02";
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		
		request.setAttribute("microTaskList", microTaskList);
		return "show_micro_task_receive_table";
	}
	
	/**
	 * 显示任务发布通知书
	 * @return
	 */
	public String showMicroTaskReceiveReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String microTaskIdStr = request.getParameter("microTaskId");
		int id = Integer.valueOf(microTaskIdStr);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		
		// 核算机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		// 登录人员
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		
		// 分析期
		String fxqStr = "";
		fxqStr = microTask.getFxqNd() + "-" + microTask.getFxqSjq() + "--" + microTask.getFxqNd() + "-" + microTask.getFxqSjz();
		
		// 特征详情
		IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
		IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj());
		
		BigDecimal bdl = microTask.getBdl();
		
		float bdlFloat = 0.0f;
		if (bdl != null)
			bdlFloat = bdl.floatValue();
		request.setAttribute("indexFeatureDB", indexFeatureDB);
		request.setAttribute("bdlFloat", bdlFloat);
		
		request.setAttribute("microTask", microTask);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("user", user);
		request.setAttribute("fxqStr", fxqStr);
		request.setAttribute("indexFeatureDB", indexFeatureDB);
		
		return "show_micro_task_receive_report";
	}
	
	/**
	 * 接收微观任务
	 * @return
	 */
	public String receiveMicroTask() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String idStrArr[] = request.getParameterValues("microTaskId");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sjjsRq = sdf.format(date);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = null;
		for (int i = 0; i < idStrArr.length; i++) {
			int id = Integer.valueOf(idStrArr[i]);
			microTask = new MicroTask();
			microTask = microTaskService.getMicroTaskById(id);
			
			microTask.setSjjsRq(sjjsRq);
			microTask.setRwztDm("03");
			
			microTaskService.update(microTask);
		}
		
		request.setAttribute("msg", "接收成功！");
		
		return "receive_micro_task";
	}
	
	/**
	 * 显示已接收任务列表，即预反馈任务列表
	 * @return
	 */
	public String showMicroTaskToFeedbackTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwzxryDm = user.getCode();
		
		MicroTaskService microTaskService = new MicroTaskService();
		String rwzrDm = "";
		
		// 已接收的任务列表
		rwzrDm = "03";
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		
		request.setAttribute("microTaskList", microTaskList);
		return "show_micro_task_to_feedback_table";
	}
	
	/**
	 * 显示调查报告
	 * @return
	 */
	public String showMicroTaskEditSurveyReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String microTaskIdStr = request.getParameter("microTaskId");
		int id = Integer.valueOf(microTaskIdStr);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		
		// 核算机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		// 分析期
		// 分析期
		String fxqStr = "";
		fxqStr = microTask.getFxqNd() + "-" + microTask.getFxqSjq() + "--" + microTask.getFxqNd() + "-" + microTask.getFxqSjz();
		
		// 上报日期
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sjfkRq = sdf.format(date);
		
		request.setAttribute("microTask", microTask);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("fxqStr", fxqStr);
		request.setAttribute("sjfkRq", sjfkRq);
		return "show_micro_task_edit_survey_report";
	}
	
	/**
	 * 任务反馈
	 * @return
	 */
	public String feedbackMicroTask() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String rwbgDm = getRwbgDm();
		String fxqNd = request.getParameter("fxqNd");
		String fxqSjq = request.getParameter("fxqSjq");
		String fxqSjz = request.getParameter("fxqSjz");
		String nsrsbh = request.getParameter("nsrsbh");
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
		
		MicroTaskSurveyReport microTaskSurveyReport = new MicroTaskSurveyReport();
		microTaskSurveyReport.setRwbgDm(rwbgDm);
		microTaskSurveyReport.setFxqNd(fxqNd);
		microTaskSurveyReport.setFxqSjq(fxqSjq);
		microTaskSurveyReport.setFxqSjz(fxqSjz);
		microTaskSurveyReport.setNsrsbh(nsrsbh);
		microTaskSurveyReport.setFxzbDm(fxzbDm);
		microTaskSurveyReport.setTzqj(tzqj);
		microTaskSurveyReport.setJbqk(jbqk);
		microTaskSurveyReport.setDcqk(dcqk);
		microTaskSurveyReport.setCzwt(czwt);
		microTaskSurveyReport.setLscs(lscs);
		microTaskSurveyReport.setRwfbryDm(rwfbryDm);
		microTaskSurveyReport.setRwzxryDm(rwzxryDm);
		microTaskSurveyReport.setDcrqQ(dcrqQ);
		microTaskSurveyReport.setDcrqZ(dcrqZ);
		microTaskSurveyReport.setSjfkRq(sjfkRq);
		
		// 插入新的任务调查报告
		MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
		microTaskSurveyReportService.add(microTaskSurveyReport);
		
		// 更新微观任务的对应记录
		MicroTaskService microTaskService = new MicroTaskService();
		int id = Integer.valueOf(request.getParameter("id"));
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		microTask.setSjfkRq(sjfkRq);
		microTask.setRwbgDm(rwbgDm);
		microTask.setRwztDm("04");
		microTaskService.update(microTask);
		
		return "feedback_micro_task";
	}
	
	/**
	 * 获取任务报告代码（20120621001, 20120621002, ...）
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
	 * @return
	 */
	public String showMicroTaskToExamTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwzxryDm = user.getCode();
		
		MicroTaskService microTaskService = new MicroTaskService();
		String rwztDm = "";
		
		// 已反馈的任务列表
		rwztDm = "04";
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "show_micro_task_to_exam_table";
	}
	
	/**
	 * 显示任务调查报告
	 * @return
	 */
	public String showMicroTaskSurveyReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String microTaskIdStr = request.getParameter("microTaskId");
		int id = Integer.valueOf(microTaskIdStr);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		
		MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
		MicroTaskSurveyReport microTaskSurveyReport = microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm());
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		String fxqStr = "";
		fxqStr = microTaskSurveyReport.getFxqNd() + "-" + microTaskSurveyReport.getFxqSjq() + "--" + microTaskSurveyReport.getFxqNd() + "-" + microTaskSurveyReport.getFxqSjz();
		
		request.setAttribute("microTaskSurveyReport", microTaskSurveyReport);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("fxqStr", fxqStr);
		return "show_micro_task_survey_report";
	}
	
	/**
	 * 显示微观任务评价和考核界面
	 * @return
	 */
	public String showMicroTaskExamUI() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwfbryDm = user.getCode();
		
		MicroTaskService microTaskService = new MicroTaskService();
		String rwztDm = "";
		
		// 已发布的任务列表
		rwztDm = "02";
		List<MicroTask> publishMicroTaskList = microTaskService.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
		int publishMicroTaskListSize = publishMicroTaskList.size();
		
		// 已反馈的任务列表
		rwztDm = "04";
		List<MicroTask> feedbackMicroTaskList = microTaskService.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
		int feedbackMicroTaskListSize = feedbackMicroTaskList.size();
		
		// 已评价的任务列表
		rwztDm = "05";
		List<MicroTask> examMicroTaskList = microTaskService.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
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
		
		return "show_micro_task_exam_ui";
	}
	
	/**
	 * 在任务考核模块中显示任务发布列表
	 * @return
	 */
	public String showMicroTaskPublishTableInExamSection() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwfbryDm = user.getCode();
		
		// 任务状态
		String rwztDm = "02";
		
		MicroTaskService microTaskService = new MicroTaskService();
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
		
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "show_micro_task_publish_table_in_exam_section";
	}
	
	/**
	 * 在任务评价模块中显示需要评价的任务列表
	 * @return
	 */
	public String showMicroTaskToExamTableInExamSection() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwfbryDm = user.getCode();
		
		// 任务状态
		String rwztDm = "04";
		
		MicroTaskService microTaskService = new MicroTaskService();
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
		
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "show_micro_task_to_exam_table_in_exam_section";
	}
	
	/**
	 * 打开提交任务评价报告窗口
	 * @return
	 */
	public String showMicroTaskSubmitExamReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String microTaskIdStr = request.getParameter("microTaskId");
		int id = Integer.valueOf(microTaskIdStr);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		
		// 核算机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		// 评价日期
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String rwpjRq = sdf.format(date);
		
		request.setAttribute("microTask", microTask);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("rwpjRq", rwpjRq);
		
		return "show_micro_task_submit_exam_report";
	}
	
	/**
	 * 评价任务
	 * @return
	 */
	public String examMicroTask() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String microTaskIdStr = request.getParameter("microTaskId");
		int id = Integer.valueOf(microTaskIdStr);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		
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
		String bzjsRqStr = microTask.getBzjsRq();	// 标准接收日期
		Date bzjsRqDate = sdf.parse(bzjsRqStr);
		
		String sjjsRqStr = microTask.getSjjsRq().substring(0, 10);	// 实际接收日期
		Date sjjsRqDate = sdf.parse(sjjsRqStr);
		
		long diffLong = (sjjsRqDate.getTime() - bzjsRqDate.getTime()) / ((24*60*60*1000));
		int diffInt = (int)diffLong;
		
		if (diffInt < 0) {
			rwjsDf = 30;
		} else if (diffInt > 30){
			rwjsDf = 0;
		} else {
			rwjsDf = 30 - diffInt;
		}
		
		// 2. 计算任务反馈得分
		int rwfkDf = 0;
		String bzfkRqStr = microTask.getBzfkRq();	// 标准反馈日期
		Date bzfkRqDate = sdf.parse(bzfkRqStr);
		
		String sjfkRqStr = microTask.getSjfkRq();	// 实际反馈日期
		Date sjfkRqDate = sdf.parse(sjfkRqStr);
		
		diffLong = (sjfkRqDate.getTime() - bzfkRqDate.getTime()) / ((24*60*60*1000));
		
		diffInt = (int)diffLong;
		
		if (diffInt < 0) {
			rwfkDf = 30;
		} else if (diffInt > 30){
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
	 * @return
	 */
	public String finishExamTableInExamSection() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 任务状态
		String rwztDm = "05";
		
		MicroTaskService microTaskService = new MicroTaskService();
//		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByRwzt(rwztDm);
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwfbryDm = user.getCode();
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "finish_exam_table_in_exam_section";
	}
	
	/**
	 * 显示任务评价报告
	 * @return
	 */
	public String showMicroTaskExamReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String microTaskIdStr = request.getParameter("microTaskId");
		int id = Integer.valueOf(microTaskIdStr);
		
		MicroTaskService microTaskService = new MicroTaskService();
		MicroTask microTask = microTaskService.getMicroTaskById(id);
		
		// 核算机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		request.setAttribute("microTask", microTask);
		request.setAttribute("taxUnit", taxUnit);
		
		return "show_micro_task_exam_report";
	}
	
	/**
	 * 在接收界面显示已评价任务列表
	 * @return
	 */
	public String showMicroTaskFinishExamTableInReceiveSection() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)ActionContext.getContext().getSession().get("loginUser");
		String rwzxryDm = user.getCode();
		
		MicroTaskService microTaskService = new MicroTaskService();
		String rwztDm = "";
		
		// 已评价的任务列表
		rwztDm = "05";
		List<MicroTask> microTaskList = microTaskService.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);
		
		request.setAttribute("microTaskList", microTaskList);
		
		return "show_micro_task_finish_exam_table_in_receive_section";
	}
	
	/**
	 * 显示任务阈值设定界面
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
		
		DecimalFormat floatFormat = new DecimalFormat("0.00"); 	// 两位小数
		
		for (int i=0; i < analysisMicroIndexList.size(); i++) {
			calcProcess.append("(").append(i+1).append(")&nbsp;&nbsp;")		// 显示：(1)
					   .append(analysisMicroIndexList.get(i).getFxzblx().trim()).append("：")	// 显示：(1)欠缴税金 ：
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
					   		   .append("=").append(floatFormat.format(3.0 /120 * analysisMicroIndexList.get(i).getBdl().floatValue() * analysisMicroIndexList.get(i).getZbqz()))
					   		   .append("<br/>");
					
					if (i != 0)
						calcTmp.append("+");
					calcTmp.append(floatFormat.format(3.0 /120 * analysisMicroIndexList.get(i).getBdl().floatValue() * analysisMicroIndexList.get(i).getZbqz()));
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
}

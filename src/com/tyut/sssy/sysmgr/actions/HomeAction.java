package com.tyut.sssy.sysmgr.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tyut.sssy.task.domain.RiskMicroTask;
import com.tyut.sssy.task.service.RiskMicroTaskService;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.task.domain.MacroTask;
import com.tyut.sssy.task.domain.MicroTask;
import com.tyut.sssy.task.service.MacroTaskService;
import com.tyut.sssy.task.service.MicroTaskService;

/**
 * 
 * 项目名称：sssy2140431-20120817
 * 类名称：HomeAction
 * 类描述：首页显示任务提示
 * 创建人：Robin
 * 创建时间：2012-9-7 下午03:25:26
 * 修改人：
 * 修改时间：
 * 修改备注：
 * @version
 *
 */
public class HomeAction {

	/**
	 * 显示任务提示
	 * @return
	 */
	public String showTaskNotification() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user = (User)ActionContext.getContext().getSession().get("loginUser");

		//风险微观任务
		RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();

        MacroTaskService macroTaskService = new MacroTaskService();
		
		String rwztDm = "";
		
		if (user.getRoleCode().equals("02")) {
			// 局领导
			String rwfbryDm = user.getCode();
			
			// 【微观】已反馈的任务列表
			rwztDm = "04";
			List<RiskMicroTask> feedbackMicroTaskList = riskMicroTaskService.getRiskMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
			int feedbackMicroTaskListSize = feedbackMicroTaskList.size();
			
			// 【总/分量】已执行(已反馈)的任务列表
			rwztDm = "04";
			List<MacroTask> feedbackMacroTaskList = macroTaskService.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
			int feedbackMacroTaskListSize = feedbackMacroTaskList.size();
			
			request.setAttribute("feedbackMicroTaskListSize", feedbackMicroTaskListSize);
			request.setAttribute("feedbackMacroTaskListSize", feedbackMacroTaskListSize);
		} else if (user.getRoleCode().equals("03")){
			// 税收管理员
			String rwzxryDm = user.getCode();
			
			// 【微观】已发布的任务列表
			rwztDm = "02";
			List<RiskMicroTask> publishRiskMicroTaskList = riskMicroTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);
			int publishRiskMicroTaskListSize = publishRiskMicroTaskList.size();
			
			// 【微观】已评价的任务列表
			rwztDm = "05";
			List<RiskMicroTask> examMicroTaskList = riskMicroTaskService.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);
			int examRiskMicroTaskListSize = examMicroTaskList.size();
			
			request.setAttribute("publishRiskMicroTaskListSize", publishRiskMicroTaskListSize);
			request.setAttribute("examRiskMicroTaskListSize", examRiskMicroTaskListSize);
		} else if (user.getRoleCode().equals("05")) {
			// 税务所
			String rwzxryDm = user.getCode();
			
			// 【总/分量】已发布的任务列表
			rwztDm = "02";
			List<MacroTask> publishMacroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);
			int publishMacroTaskListSize = publishMacroTaskList.size();
			
			// 【总/分量】已评价的任务列表
			rwztDm = "05";
			List<MacroTask> examMacroTaskList = macroTaskService.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwztDm);
			int examMacroTaskListSize = examMacroTaskList.size();
			
			request.setAttribute("publishMacroTaskListSize", publishMacroTaskListSize);
			request.setAttribute("examMacroTaskListSize", examMacroTaskListSize);
		}
		
		return "show_task_notification";
	}
	
}

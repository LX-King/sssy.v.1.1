package com.tyut.sssy.infosearch.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.task.domain.MacroTask;
import com.tyut.sssy.task.domain.MicroTask;
import com.tyut.sssy.task.service.MacroTaskService;
import com.tyut.sssy.task.service.MicroTaskService;
import com.tyut.sssy.task.service.TaskStateService;

/**
 * 
 * 项目名称：sssy2140431-20120817
 * 类名称：TaskSearchAction
 * 类描述：任务查询
 * 创建人：Robin
 * 创建时间：2012-9-7 上午10:34:38
 * 修改人：
 * 修改时间：
 * 修改备注：
 * @version
 *
 */
public class TaskSearchAction {

	/**
	 * 显示微观任务查询条件选择界面
	 * @return
	 */
	public String showMicroTaskSearchConditionUI() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_micro_task_search_condition_ui";
	}
	
	/**
	 * 微观任务查询列表
	 * @return
	 */
	public String microTaskSearchTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String swjgDm = request.getParameter("swjgDm");
		String swjgMc = "";
		if ("0".equals(swjgDm)) {
			swjgDm = "%";
			swjgMc = "全部";
		} else {
			TaxUnitService taxUnitService = new TaxUnitService();
			swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
		}
		
		
		String rwztDm = request.getParameter("rwztDm");
		String rwztMc = "";
		if ("".equals(rwztDm)) {
			rwztDm = "%";
			rwztMc = "全部";
		} else {
			TaskStateService taskStateService = new TaskStateService();
			rwztMc = taskStateService.getTaskStateById(rwztDm).getRwztMc();
		}
		
		MicroTaskService microTaskService = new MicroTaskService();
		List<MicroTask> microTaskList = microTaskService.getListBySwjgAndRwzt(swjgDm, rwztDm);
		
		request.setAttribute("microTaskList", microTaskList);
		request.setAttribute("swjgMc", swjgMc);
		request.setAttribute("rwztMc", rwztMc);
		return "micro_task_search_table";
	}
	
	/**
	 * 显示总/分量查询界面
	 * @return
	 */
	public String showMacroTaskSearchConditionUI() {
		
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
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		
		return "show_macro_task_search_condition_ui";
	}
	
	/**
	 * 总/分量任务查询列表
	 * @return
	 */
	public String macroTaskSearchTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
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
		
		// 任务状态
		String rwztDm = request.getParameter("rwztDm");
		if ("".equals(rwztDm)) {
			rwztDm = "%";
		}
		
		MacroTaskService macroTaskService = new MacroTaskService();
		List<MacroTask> macroTaskList = macroTaskService.getListByZflLxAndRwzt(zflLx, swjgDm, hydlDm, qyzclxDm, rwztDm);
		
		request.setAttribute("macroTaskList", macroTaskList);
		return "macro_task_search_table";
	}
}

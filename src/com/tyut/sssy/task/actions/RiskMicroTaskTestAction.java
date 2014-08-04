package com.tyut.sssy.task.actions;

import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.domain.RiskMicroTask;
import com.tyut.sssy.task.domain.TaskTest;
import com.tyut.sssy.task.service.RiskMicroTaskService;
import com.tyut.sssy.task.service.RiskMicroTaskTestService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ClassName:RiskMicroTaskTestAction
 * Function:考核微观任务
 * Author: LiuXiang
 * Date: 13-7-13
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚
 */
public class RiskMicroTaskTestAction {

	/**
	 * 显示任务执行人员列表
	 * @return
	 */
	public String showRwzxryList() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String year = request.getParameter("year");
		String seasonPeriod = request.getParameter("seasonPeriod");
		
		String sjQ = "";
		String sjZ = "";
		String rwkhRq = "";
		if (seasonPeriod.equals("01")) {
			// 第一季度
			sjQ = year + "-" + "01-01";
			sjZ = year + "-" + "03-31";
			rwkhRq = year + "年 一季度";
		} else if (seasonPeriod.equals("02")) {
			// 第二季度
			sjQ = year + "-" + "04-01";
			sjZ = year + "-" + "06-30";
			rwkhRq = year + "年 二季度";
		} else if (seasonPeriod.equals("03")) {
			// 第三季度
			sjQ = year + "-" + "07-01";
			sjZ = year + "-" + "09-30";
			rwkhRq = year + "年 三季度";
		} else if (seasonPeriod.equals("04")) {
			// 第四季度
			sjQ = year + "-" + "10-01";
			sjZ = year + "-" + "12-31";
			rwkhRq = year + "年 四季度";
		}
		
		//  根据反馈日期时间起止获得执行人员列表
		RiskMicroTaskTestService microTaskTestService = new RiskMicroTaskTestService();
		List<User> userList = microTaskTestService.getRwzxryListByRwfkRq(sjQ, sjZ);
		
		request.setAttribute("userList", userList);
		request.setAttribute("rwkhRq", rwkhRq);
		request.setAttribute("sjQ", sjQ);
		request.setAttribute("sjZ", sjZ);
		
		return "show_risk_rwzxry_list";
	}
	
	/**
	 * 显示提交任务考核窗口
	 * @return
	 */
	public String showSubmitRiskTestReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String rwzxryDm = request.getParameter("rwzxryDm");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		
		String sjQ = request.getParameter("sjQ");
		String sjZ = request.getParameter("sjZ");
		
		String khSsq = "";
		
		String nd = sjQ.substring(0, 4);
		String rq = sjQ.substring(5);
		
		if (rq.equals("01-01")) {
			khSsq = nd + "年 一季度";
		} else if (rq.equals("04-01")) {
			khSsq = nd + "年 二季度";
		} else if (rq.equals("07-01")) {
			khSsq = nd + "年 三季度";
		} else if (rq.equals("10-01")) {
			khSsq = nd + "年 四季度";
		}
		
		String khRq = dateStr;
		
		String bkhryDm = rwzxryDm;
		UserService userService = new UserService();
		User bkhry = userService.getByCode(bkhryDm);
		
		float rwjsDf = 0.0f;
		float rwfkDf = 0.0f;
		float rwzxDf = 0.0f;
		
		RiskMicroTaskService riskMicroTaskService = new RiskMicroTaskService();
		String rwztDm = "05";
		List<RiskMicroTask> riskMicroTaskList = riskMicroTaskService.getListByRwfkRqAndRwztAndRwzxryDm(sjQ, sjZ, rwztDm, rwzxryDm);
		
		for (RiskMicroTask riskMicroTask : riskMicroTaskList) {
			rwjsDf += riskMicroTask.getRwjsDf();
			rwfkDf += riskMicroTask.getRwfkDf();
			rwzxDf += riskMicroTask.getRwzxDf();
		}
		
		int num = riskMicroTaskList.size();
		rwjsDf = rwjsDf / num;
		rwfkDf = rwfkDf / num;
		rwzxDf = rwzxDf / num;
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		request.setAttribute("bkhry", bkhry);
		request.setAttribute("khRq", khRq);
		request.setAttribute("rwjsDf", rwjsDf);
		request.setAttribute("rwfkDf", rwfkDf);
		request.setAttribute("rwzxDf", rwzxDf);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("khSsq", khSsq);
		request.setAttribute("sjQ", sjQ);
		request.setAttribute("sjZ", sjZ);
		
		return "show_submit_risk_test_report";
	}
	
	/**
	 * 提交考核报告
	 * @return
	 */
	public String submitRiskTestReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String sjQ = request.getParameter("sjQ");
		String sjZ = request.getParameter("sjZ");
		String khRq = request.getParameter("khRq");
		String khSsq = request.getParameter("khSsq");
		String bkhryDm = request.getParameter("bkhryDm");
		String khryDm = request.getParameter("khryDm");
		float rwjsDf = Float.valueOf(request.getParameter("rwjsDf"));
		float rwfkDf = Float.valueOf(request.getParameter("rwfkDf"));
		float rwzxDf = Float.valueOf(request.getParameter("rwzxDf"));
		float rwZdf = rwjsDf + rwfkDf + rwzxDf;
		String zhkhPj = request.getParameter("zhkhPj");
		String khrwLx = "wgrw";
		
		// 根据被考核人员和起止时间删除原有记录
		RiskMicroTaskTestService microTaskTestService = new RiskMicroTaskTestService();
		microTaskTestService.deleteByBkhryAndSjQAndSjZ(bkhryDm, sjQ, sjZ);
		
		String rwkhDm = getNewRwkhDm();
		
		// 添加任务考核
		TaskTest taskTest = new TaskTest();
		taskTest.setRwkhDm(rwkhDm);
		taskTest.setSjQ(sjQ);
		taskTest.setSjZ(sjZ);
		taskTest.setKhRq(khRq);
		taskTest.setKhSsq(khSsq);
		taskTest.setBkhryDm(bkhryDm);
		taskTest.setKhryDm(khryDm);
		taskTest.setRwjsDf(rwjsDf);
		taskTest.setRwfkDf(rwfkDf);
		taskTest.setRwzxDf(rwzxDf);
		taskTest.setZhkhPj(zhkhPj);
		taskTest.setRwZdf(rwZdf);
		taskTest.setKhrwLx(khrwLx);
		
		microTaskTestService.add(taskTest);
		
		request.setAttribute("msg", "考核成功！");
		return "submit_risk_test_report";
	}

	/**
	 * 获取新的任务考核代码
	 * @return
	 */
	private String getNewRwkhDm() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(date);
		
		RiskMicroTaskTestService microTaskTestService = new RiskMicroTaskTestService();
		String maxRwkhDm = microTaskTestService.getMaxRwkhDm(dateStr);
		String rwkhDm = "";
		
		if (null == maxRwkhDm) {
			// 当日无记录，插入第一条记录
			rwkhDm = dateStr + "001";
		} else {
			// 当日有记录，在其基础上加1
			int maxRwkhDmInt = Integer.valueOf("1" + maxRwkhDm.substring(8));
			int rwkhDmInt = maxRwkhDmInt + 1;
			rwkhDm = dateStr + String.valueOf(rwkhDmInt).substring(1);
		}
		
		return rwkhDm;
	}
	
	/**
	 * 显示微观任务考核操作选择界面
	 * @return
	 */
	public String showRiskMicroTaskTestUI() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = request.getParameter("msg");
		
		if ("test_succ".equals(msg)) {
			msg = "考核成功！";
		} else {
			msg = "";
		}
		
		request.setAttribute("msg", msg);
		return "show_risk_micro_task_test_ui";
	}
	
	/**
	 * 查看微观任务考核情况
	 * @return
	 */
	public String showRiskMicroTaskTestOrderTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String year = request.getParameter("year");
		String seasonPeriod = request.getParameter("seasonPeriod");
		
		String sjQ = "";
		String sjZ = "";
		String rwkhRq = "";
		if (seasonPeriod.equals("01")) {
			// 第一季度
			sjQ = year + "-" + "01-01";
			sjZ = year + "-" + "03-31";
			rwkhRq = year + "年 一季度";
		} else if (seasonPeriod.equals("02")) {
			// 第二季度
			sjQ = year + "-" + "04-01";
			sjZ = year + "-" + "06-30";
			rwkhRq = year + "年 二季度";
		} else if (seasonPeriod.equals("03")) {
			// 第三季度
			sjQ = year + "-" + "07-01";
			sjZ = year + "-" + "09-30";
			rwkhRq = year + "年 三季度";
		} else if (seasonPeriod.equals("04")) {
			// 第四季度
			sjQ = year + "-" + "10-01";
			sjZ = year + "-" + "12-31";
			rwkhRq = year + "年 四季度";
		}
		
		//  根据任务考核日期 和 任务类型 获得考核任务列表
		RiskMicroTaskTestService microTaskTestService = new RiskMicroTaskTestService();
		String khrwLx = "wgrw";
		List<TaskTest> riskTaskTestList = microTaskTestService.getTaskTestListByKhRq(sjQ, sjZ, khrwLx);
		
		request.setAttribute("riskTaskTestList", riskTaskTestList);
		request.setAttribute("rwkhRq", rwkhRq);
		
		return "show_risk_micro_task_test_order_table";
	}
	
}

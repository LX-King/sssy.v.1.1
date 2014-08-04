package com.tyut.sssy.task.service;

import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.MacroTaskSurveyReportDao;
import com.tyut.sssy.task.domain.MacroTaskSurveyReport;

public class MacroTaskSurveyReportService {

	/**
	 * 获取当日最大的任务代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxWgrwDm(String dateStr) {
		MacroTaskSurveyReportDao macroTaskSurveyReportDao = new MacroTaskSurveyReportDao();
		String maxWgrwDm = macroTaskSurveyReportDao.getMaxWgrwDm(dateStr);
		return maxWgrwDm;
	}

	public void add(MacroTaskSurveyReport macroTaskSurveyReport) {
		MacroTaskSurveyReportDao macroTaskSurveyReportDao = new MacroTaskSurveyReportDao();
		macroTaskSurveyReportDao.add(macroTaskSurveyReport);
	}

	/**
	 * 根据id获取任务报告
	 * @param rwbgDm
	 * @return
	 */
	public MacroTaskSurveyReport getSurveyReportById(String rwbgDm) {
		MacroTaskSurveyReportDao macroTaskSurveyReportDao = new MacroTaskSurveyReportDao();
		MacroTaskSurveyReport macroTaskSurveyReport = macroTaskSurveyReportDao.getSurveyReportById(rwbgDm);
		
		if (null != macroTaskSurveyReport) {
			UserService userService = new UserService();
			
			macroTaskSurveyReport.setRwfbry(userService.getByCode(macroTaskSurveyReport.getRwfbryDm()));
			macroTaskSurveyReport.setRwzxry(userService.getByCode(macroTaskSurveyReport.getRwzxryDm()));
		}
		
		return macroTaskSurveyReport;
	}

}

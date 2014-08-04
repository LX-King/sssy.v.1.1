package com.tyut.sssy.task.service;

import com.tyut.sssy.base.service.TaxPayerService;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.RiskMicroTaskSurveyReportDao;
import com.tyut.sssy.task.domain.RiskMicroTaskSurveyReport;

public class RiskMicroTaskSurveyReportService {

	/**
	 * 获取当日最大的任务代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxWgrwDm(String dateStr) {
		RiskMicroTaskSurveyReportDao microTaskSurveyReportDao = new RiskMicroTaskSurveyReportDao();
		String maxWgrwDm = microTaskSurveyReportDao.getMaxWgrwDm(dateStr);
		return maxWgrwDm;
	}

	/**
	 * 添加任务调查报告
	 * @param microTaskSurveyReport
	 */
	public void add(RiskMicroTaskSurveyReport microTaskSurveyReport) {
		RiskMicroTaskSurveyReportDao microTaskSurveyReportDao = new RiskMicroTaskSurveyReportDao();
		microTaskSurveyReportDao.add(microTaskSurveyReport);
		
	}

	/**
	 * 根据id获取任务报告
	 * @param rwbgDm
	 * @return
	 */
	public RiskMicroTaskSurveyReport getSurveyReportById(String rwbgDm) {
		RiskMicroTaskSurveyReportDao microTaskSurveyReportDao = new RiskMicroTaskSurveyReportDao();
		RiskMicroTaskSurveyReport microTaskSurveyReport = microTaskSurveyReportDao.getSurveyReportById(rwbgDm);
		
		if (null != microTaskSurveyReport) {
			TaxPayerService taxPayerService = new TaxPayerService();
			UserService userService = new UserService();
			
			microTaskSurveyReport.setTaxPayer(taxPayerService.getTaxPayerByCode(microTaskSurveyReport.getNsrsbh()));
			microTaskSurveyReport.setRwfbry(userService.getByCode(microTaskSurveyReport.getRwfbryDm()));
			microTaskSurveyReport.setRwzxry(userService.getByCode(microTaskSurveyReport.getRwzxryDm()));
		}
		
		return microTaskSurveyReport;
	}

}

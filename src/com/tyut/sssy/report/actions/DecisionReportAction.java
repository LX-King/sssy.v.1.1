package com.tyut.sssy.report.actions;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.report.domain.BigIndustryTaxCollectionSituation;
import com.tyut.sssy.report.domain.DecisionReport;
import com.tyut.sssy.report.domain.FirmTaxCollectionSituation;
import com.tyut.sssy.report.service.DecisionReportService;

/**
 * 
 * 项目名称：sssy20120713
 * 类名称：DecisionReportAction  
 * 类描述：系统决策报告action  
 * 创建人：梁斌  
 * 创建时间：2012-7-26 下午10:24:58  
 * 修改人：梁斌  
 * 修改时间：2012-7-26 下午10:24:58  
 * 修改备注：  
 * @version 
 *
 */
public class DecisionReportAction {

	/**
	 * 显示系统决策报告
	 * @return
	 */
	public String showDecisionReport() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 年度
		String year = request.getParameter("year");
		
		// 月份
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		DecisionReportService decisionReportService = new DecisionReportService();
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		String swjgDm = taxUnit.getSwjgDm();
		
		// 决策报告
		DecisionReport decisionReport = new DecisionReport();
		decisionReport = decisionReportService.getDecisionReport(year, sqQ, sqZ);
		
		// 主要行业完成税收情况
		List<BigIndustryTaxCollectionSituation> industryTaxCollectionSituationList = decisionReportService.getBigIndustryTaxCollectionSituationList(year, sqZ, swjgDm);
		
		// 重点税源大户实现税收情况
		List<FirmTaxCollectionSituation> tableList = decisionReportService.getFirmTaxCollectionSituationList(year, sqQ, sqZ, swjgDm);
		
		request.setAttribute("decisionReport", decisionReport);
		request.setAttribute("tableList", tableList);
		request.setAttribute("industryTaxCollectionSituationList", industryTaxCollectionSituationList);
		request.setAttribute("year", year);
		request.setAttribute("monthPeriod", monthPeriod);
		
		return "show_decision_report";
	}
	
	/**
	 * 决策报告生成word
	 * @return
	 */
	public String reportToWord(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 年度
		String year = request.getParameter("year");
		
		// 月份
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		DecisionReportService decisionReportService = new DecisionReportService();
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		String swjgDm = taxUnit.getSwjgDm();
		
		// 决策报告
		DecisionReport decisionReport = new DecisionReport();
		decisionReport = decisionReportService.getDecisionReport(year, sqQ, sqZ);
		
		// 主要行业完成税收情况
		List<BigIndustryTaxCollectionSituation> industryTaxCollectionSituationList = decisionReportService.getBigIndustryTaxCollectionSituationList(year, sqZ, swjgDm);
		
		// 重点税源大户实现税收情况
		List<FirmTaxCollectionSituation> tableList = decisionReportService.getFirmTaxCollectionSituationList(year, sqQ, sqZ, swjgDm);
		
		String fileName = decisionReport.getBgMc() + ".doc";
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("GB2312"), "8859_1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("decisionReport", decisionReport);
		request.setAttribute("tableList", tableList);
		request.setAttribute("industryTaxCollectionSituationList", industryTaxCollectionSituationList);
		
		return "report_to_word";
		
	}
}

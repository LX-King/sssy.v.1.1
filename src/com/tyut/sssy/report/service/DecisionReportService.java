package com.tyut.sssy.report.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTableParameter;
import com.tyut.sssy.infosearch.service.DecisionAnalysisSearchService;
import com.tyut.sssy.infosearch.service.TaxBurdenSearchService;
import com.tyut.sssy.infosearch.service.TaxCollectionSearchService;
import com.tyut.sssy.infosearch.service.TaxResourceSearchService;
import com.tyut.sssy.report.domain.BigIndustryTaxCollectionSituation;
import com.tyut.sssy.report.domain.DecisionReport;
import com.tyut.sssy.report.domain.FirmTaxCollectionSituation;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTableParameter;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTableParameter;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceSumChangeTableParameter;

public class DecisionReportService {

	/**
	 * 根据时间获取系统决策报告
	 * @param year
	 * @param sqQ
	 * @param sqZ
	 * @return
	 */
	public DecisionReport getDecisionReport(String year, String sqQ, String sqZ) {
		
		DecisionReport decisionReport = new DecisionReport();
		
		// 税务机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitListByType("H").get(0);
		
		//-- swjgDm
		String swjgDm = taxUnit.getSwjgDm();
		
		//-- bgMc, 报告名称
		String bgMc = "";
		
		//-- bl0
		String bl0 = taxUnit.getMc();
		
		//-- bl1
		String bl1 = year;
		
		//-- bl2
		String bl2 = "";
		if ("03".equals(sqZ)) {
			// 一季度
			bl2 = "一季度";
		} else if ("06".equals(sqZ)) {
			// 上半年
			bl2 = "上半年";
		} else if ("09".equals(sqZ)) {
			// 前三季度
			bl2 = "前三季度";
		} else if ("12".equals(sqZ)) {
			// 全年
			bl2 = "全年";
		}
		
		bgMc = bl1 + "年" + bl2 + bl0 + "税收决策分析报告";
		
		//-- bl3
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		
		TaxCollectionFinishTableParameter taxCollectionFinishTableParameter = new TaxCollectionFinishTableParameter();
		
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("S");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("01");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<TaxCollectionFinishTable> taxCollectionFinishTableList = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter);
		
		BigDecimal bl3 = taxCollectionFinishTableList.get(0).getA5();
		
		//-- bl4, bl5
//		String preYear = String.valueOf(Integer.valueOf(year) - 1);	// 前一年
		BigDecimal bl4 = taxCollectionFinishTableList.get(0).getA7().add(new BigDecimal(100));
		BigDecimal bl5 = taxCollectionFinishTableList.get(0).getA7();
		
		//-- bl6
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("01");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		taxCollectionFinishTableList = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter);
		
		BigDecimal bl6 = taxCollectionFinishTableList.get(0).getA5();
		
		//-- bl7, bl8
		BigDecimal bl7 = taxCollectionFinishTableList.get(0).getA7().add(new BigDecimal(100));
		BigDecimal bl8 = taxCollectionFinishTableList.get(0).getA7();
		
		//-- bl9
		ToResolveTaxChangeTableParameter toResolveTaxChangeTableParameter = new ToResolveTaxChangeTableParameter();
		toResolveTaxChangeTableParameter.setSwjg(swjgDm);
		toResolveTaxChangeTableParameter.setNd(year);
		toResolveTaxChangeTableParameter.setSqZ(sqZ);
		toResolveTaxChangeTableParameter.setNsrsbh("");
		toResolveTaxChangeTableParameter.setMoneyUnit("yuan");
		toResolveTaxChangeTableParameter.setType("none");
		toResolveTaxChangeTableParameter.setOrder("normal");
		
		BigDecimal bl9 = taxCollectionSearchService.getToResolveChangeTable(toResolveTaxChangeTableParameter).get(0).getA10();
		
		//-- bl11
		String sqZPre = "";
		BigDecimal bl9Pre = BigDecimal.valueOf(0.0f);
		if ("12".equals(sqZ)) {
			sqZPre = "09";
		} else if ("09".equals(sqZ)) {
			sqZPre = "06";
		} else if ("06".equals(sqZ)) {
			sqZPre = "03";
		} else if ("03".equals(sqZ)) {
			sqZPre = "03";
		}
		
		toResolveTaxChangeTableParameter.setSwjg(swjgDm);
		toResolveTaxChangeTableParameter.setNd(year);
		toResolveTaxChangeTableParameter.setSqZ(sqZPre);
		toResolveTaxChangeTableParameter.setNsrsbh("");
		toResolveTaxChangeTableParameter.setMoneyUnit("yuan");
		toResolveTaxChangeTableParameter.setType("none");
		toResolveTaxChangeTableParameter.setOrder("normal");
		
		bl9Pre = taxCollectionSearchService.getToResolveChangeTable(toResolveTaxChangeTableParameter).get(0).getA10();
		
		BigDecimal bl11 = bl9.subtract(bl9Pre);
		
		//-- bl12
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("04");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		BigDecimal bl12 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(4).getA5();
		BigDecimal bl12Pre = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(4).getA6();
		
		//-- bl13
		
		BigDecimal bl13 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(4).getA7().add(new BigDecimal(100));
		
		//-- bl14
		BigDecimal bl14 = bl12.subtract(bl12Pre);
		
		//-- bl15
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("04");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		BigDecimal bl15 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA5();
		BigDecimal bl15Pre = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA6();
		
		//-- bl16
		BigDecimal bl16 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA7().add(new BigDecimal(100));
		
		//-- bl17
		BigDecimal bl17 = bl15.subtract(bl15Pre);
		
		//-- bl18
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("04");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		BigDecimal bl18 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(1).getA5();
		BigDecimal bl18Pre = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(1).getA6();
		
		//-- bl19
		BigDecimal bl19 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(1).getA7().add(new BigDecimal(100));
		
		//-- bl20
		BigDecimal bl20 = bl18.subtract(bl18Pre);
		
		//-- bl21
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("04");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		BigDecimal bl21 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(3).getA5();
		BigDecimal bl21Pre = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(3).getA6();
		
		//-- bl22
		BigDecimal bl22 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(3).getA7().add(new BigDecimal(100));
		
		//-- bl23
		BigDecimal bl23 = bl21.subtract(bl21Pre);
		
		//-- bl24
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("S");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("06");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		BigDecimal bl24 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA5();
		
		//-- bl25, 26
		BigDecimal bl25 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA7().add(new BigDecimal(100));
		BigDecimal bl26 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA7();
		
		//-- bl27
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("06");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		BigDecimal bl27 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA5();
		
		//-- bl28, bl29
		BigDecimal bl28 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA7().add(new BigDecimal(100));
		BigDecimal bl29 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA7();
		
		//-- bl33
		TaxResourceMonitorTableParameter taxResourceMonitorTableParameter = new TaxResourceMonitorTableParameter();
		taxResourceMonitorTableParameter.setSsjgDm(swjgDm);
		taxResourceMonitorTableParameter.setQygm("");
		taxResourceMonitorTableParameter.setNd(year);
		taxResourceMonitorTableParameter.setSssqQ(sqQ);
		taxResourceMonitorTableParameter.setSssqZ(sqZ);
		taxResourceMonitorTableParameter.setHydlDm("");
		taxResourceMonitorTableParameter.setQyzclx("");
		taxResourceMonitorTableParameter.setXmbz("1");
		taxResourceMonitorTableParameter.setDszsbz("0");
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		
		int bl33 = taxResourceSearchService.getTaxResourceMonitorTable(taxResourceMonitorTableParameter).get(0).getA0();
		
		//-- bl34
		TaxResourceSumChangeTableParameter taxResourceSumChangeTableParameter = new TaxResourceSumChangeTableParameter();
		taxResourceSumChangeTableParameter.setNd(year);
		taxResourceSumChangeTableParameter.setSssqQ(sqQ);
		taxResourceSumChangeTableParameter.setSssqZ(sqZ);
		taxResourceSumChangeTableParameter.setNsrsbh("");
		taxResourceSumChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceSumChangeTableParameter.setJedw("0");
		taxResourceSumChangeTableParameter.setQyzclx("");
		taxResourceSumChangeTableParameter.setHydl("");
		taxResourceSumChangeTableParameter.setCy("");
		taxResourceSumChangeTableParameter.setXmbz("1");
		taxResourceSumChangeTableParameter.setDszsbz("0");
		
		BigDecimal bl34 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getB1();
		
		//-- bl35
		BigDecimal bl35 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getB2();
		
		//-- bl36
		BigDecimal bl36 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getB3();
		
		//-- bl40
		taxResourceMonitorTableParameter.setSsjgDm(swjgDm);
		taxResourceMonitorTableParameter.setQygm("");
		taxResourceMonitorTableParameter.setNd(year);
		taxResourceMonitorTableParameter.setSssqQ(sqQ);
		taxResourceMonitorTableParameter.setSssqZ(sqZ);
		taxResourceMonitorTableParameter.setHydlDm("");
		taxResourceMonitorTableParameter.setQyzclx("");
		taxResourceMonitorTableParameter.setXmbz("1");
		taxResourceMonitorTableParameter.setDszsbz("1");
		
		int bl40 = taxResourceSearchService.getTaxResourceMonitorTable(taxResourceMonitorTableParameter).get(0).getA0();;
		
		//-- bl41
		taxResourceSumChangeTableParameter.setNd(year);
		taxResourceSumChangeTableParameter.setSssqQ(sqQ);
		taxResourceSumChangeTableParameter.setSssqZ(sqZ);
		taxResourceSumChangeTableParameter.setNsrsbh("");
		taxResourceSumChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceSumChangeTableParameter.setJedw("0");
		taxResourceSumChangeTableParameter.setQyzclx("");
		taxResourceSumChangeTableParameter.setHydl("");
		taxResourceSumChangeTableParameter.setCy("");
		taxResourceSumChangeTableParameter.setXmbz("1");
		taxResourceSumChangeTableParameter.setDszsbz("1");
		
		BigDecimal bl41 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getC1();
		
		//-- bl42
		BigDecimal bl42 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getC2();
		
		//-- bl43
		BigDecimal bl43 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getC3();
		
		//-- bl44
		TaxResourceAllIndexTableParameter taxResourceAllIndexTableParameter = new TaxResourceAllIndexTableParameter();
		taxResourceAllIndexTableParameter.setSwjg(swjgDm);
		taxResourceAllIndexTableParameter.setNsrsbh("");
		taxResourceAllIndexTableParameter.setQyzclx("");
		taxResourceAllIndexTableParameter.setHy("");
		taxResourceAllIndexTableParameter.setNd(year);
		taxResourceAllIndexTableParameter.setSqQ(sqQ);
		taxResourceAllIndexTableParameter.setSqZ(sqZ);
		
		BigDecimal bl44 = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter).get(3).getA6();
		
		//-- bl47
		BigDecimal bl47 = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter).get(4).getA6();
		
		//-- bl50
		BigDecimal bl50 = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter).get(5).getA6();
		
		//-- bl53
		BigDecimal bl53 = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter).get(6).getA6();
		
		//-- bl56
		BigDecimal bl56 = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter).get(7).getA6();
		
		//-- bl59
		TaxGrowTaxResourceDevelopIndexTableParameter taxGrowTaxResourceDevelopIndexTableParameter = new TaxGrowTaxResourceDevelopIndexTableParameter();
		taxGrowTaxResourceDevelopIndexTableParameter.setNd(year);
		taxGrowTaxResourceDevelopIndexTableParameter.setSqQ(sqQ);
		taxGrowTaxResourceDevelopIndexTableParameter.setSqZ(sqZ);
		
		BigDecimal bl59 = decisionAnalysisSearchService.getTaxGrowTaxResourceDevelopIndexTable(taxGrowTaxResourceDevelopIndexTableParameter).getFzzs();
		
		//-- bl60, bl61
		String bl60 = "";
		String bl61 = "";
		if (bl59.floatValue() >= 100) {
			bl60 = "实现税源增长";
			bl61 = "增长";
		} else {
			bl60 = "实现税源下降";
			bl61 = "下降";
		}
		
		// -- bl64, bl65
		BigDecimal bl64 = BigDecimal.valueOf(0.0f);
		
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("S");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("01");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		// 除数
		BigDecimal bl64_1 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(0).getA7();
		
		taxResourceSumChangeTableParameter.setNd(year);
		taxResourceSumChangeTableParameter.setSssqQ(sqQ);
		taxResourceSumChangeTableParameter.setSssqZ(sqZ);
		taxResourceSumChangeTableParameter.setNsrsbh("");
		taxResourceSumChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceSumChangeTableParameter.setJedw("0");
		taxResourceSumChangeTableParameter.setQyzclx("");
		taxResourceSumChangeTableParameter.setHydl("");
		taxResourceSumChangeTableParameter.setCy("");
		taxResourceSumChangeTableParameter.setXmbz("1");
		
		// 被除数
		BigDecimal bl64_2 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getB2();
		
		if (bl64_2.floatValue() != 0)
			bl64 = bl64_1.divide(bl64_2, 2, BigDecimal.ROUND_HALF_EVEN);
		
		String bl65 = "";
		if (bl64.floatValue() > 1) {
			bl65 = "税收增长快于税源增长";
		} else if (bl64.floatValue() > 0 && bl64.floatValue() < 1) {
			bl65 = "税收增长慢于税源增长";
		}
		
		// -- bl66, bl67
		BigDecimal bl66 = BigDecimal.valueOf(0.0f);
		
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("S");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("05");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		// 除数
		BigDecimal bl66_1 = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter).get(2).getA7();
		
		// 被除数
		BigDecimal bl66_2 = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter).get(0).getC2();
		
		if (bl66_2.floatValue() != 0)
			bl66 = bl66_1.divide(bl66_2, 2, BigDecimal.ROUND_HALF_EVEN);
		
		String bl67 = "";
		if (bl66.floatValue() > 1) {
			bl67 = "税收增长快于税源增长";
		} else if (bl66.floatValue() > 0 && bl66.floatValue() < 1) {
			bl67 = "税收增长慢于税源增长";
		}
		
		//-- bl68
		TaxBurdenAnalysisSearchTableParameter taxBurdenAnalysisSearchTableParameter = new TaxBurdenAnalysisSearchTableParameter();
		taxBurdenAnalysisSearchTableParameter.setNd(year);
		taxBurdenAnalysisSearchTableParameter.setSssqQ(sqQ);
		taxBurdenAnalysisSearchTableParameter.setSssqZ(sqZ);
		taxBurdenAnalysisSearchTableParameter.setSwjgDm(swjgDm);
		taxBurdenAnalysisSearchTableParameter.setQyzclx("");
		taxBurdenAnalysisSearchTableParameter.setCy("");
		taxBurdenAnalysisSearchTableParameter.setHydl("");
		taxBurdenAnalysisSearchTableParameter.setSflx("0");
		
		TaxBurdenSearchService taxBurdenSearchService = new TaxBurdenSearchService();
		BigDecimal bl68 = taxBurdenSearchService.getTaxBurdenAnalysisSearchTable(taxBurdenAnalysisSearchTableParameter).get(0).getSshj1();
		
		//-- bl69
		BigDecimal bl69 = BigDecimal.valueOf(0.0f);
		
		decisionReport.setSwjgDm(swjgDm);
		decisionReport.setSqQ(sqQ);
		decisionReport.setSqZ(sqZ);
		decisionReport.setBgMc(bgMc);
		decisionReport.setBl0(bl0);
		decisionReport.setBl1(bl1);
		decisionReport.setBl2(bl2);
		decisionReport.setBl3(bl3);
		decisionReport.setBl4(bl4);
		decisionReport.setBl5(bl5);
		decisionReport.setBl6(bl6);
		decisionReport.setBl7(bl7);
		decisionReport.setBl8(bl8);
		decisionReport.setBl9(bl9);
		decisionReport.setBl11(bl11);
		decisionReport.setBl12(bl12);
		decisionReport.setBl13(bl13);
		decisionReport.setBl14(bl14);
		decisionReport.setBl15(bl15);
		decisionReport.setBl16(bl16);
		decisionReport.setBl17(bl17);
		decisionReport.setBl18(bl18);
		decisionReport.setBl19(bl19);
		decisionReport.setBl20(bl20);
		decisionReport.setBl21(bl21);
		decisionReport.setBl22(bl22);
		decisionReport.setBl23(bl23);
		decisionReport.setBl24(bl24);
		decisionReport.setBl25(bl25);
		decisionReport.setBl26(bl26);
		decisionReport.setBl27(bl27);
		decisionReport.setBl28(bl28);
		decisionReport.setBl29(bl29);
		decisionReport.setBl33(bl33);
		decisionReport.setBl34(bl34);
		decisionReport.setBl35(bl35);
		decisionReport.setBl36(bl36);
		decisionReport.setBl40(bl40);
		decisionReport.setBl41(bl41);
		decisionReport.setBl42(bl42);
		decisionReport.setBl43(bl43);
		decisionReport.setBl44(bl44);
		decisionReport.setBl47(bl47);
		decisionReport.setBl50(bl50);
		decisionReport.setBl53(bl53);
		decisionReport.setBl56(bl56);
		decisionReport.setBl59(bl59);
		decisionReport.setBl60(bl60);
		decisionReport.setBl61(bl61);
		decisionReport.setBl64(bl64);
		decisionReport.setBl65(bl65);
		decisionReport.setBl66(bl66);
		decisionReport.setBl67(bl67);
		decisionReport.setBl68(bl68);
		decisionReport.setBl69(bl69);
		
		return decisionReport;
	}

	/**
	 * 获取重点税源大户实现税收情况 排名表
	 * @param year
	 * @param sqQ
	 * @param sqZ
	 * @return
	 */
	public List<FirmTaxCollectionSituation> getFirmTaxCollectionSituationList(
			String year, String sqQ, String sqZ, String swjgDm) {
		
		// 1. 找出按“本年累计-实现税收”排名的前十名
		FirmTaxCollectionSituationService firmTaxCollectionSituationService = new FirmTaxCollectionSituationService();
		List<FirmTaxCollectionSituation> topTenRecords = firmTaxCollectionSituationService.getTopTenRecords(year, sqQ, sqZ, swjgDm);
		
		return topTenRecords;
	}

	/**
	 * 主要行业完成税收情况
	 * @param year
	 * @param sqZ
	 * @param swjgDm
	 * @return
	 */
	public List<BigIndustryTaxCollectionSituation> getBigIndustryTaxCollectionSituationList(
			String year, String sqZ, String swjgDm) {
		
		TaxCollectionFinishTableParameter taxCollectionFinishTableParameter = new TaxCollectionFinishTableParameter();
		taxCollectionFinishTableParameter.setSwjg(swjgDm);
		taxCollectionFinishTableParameter.setSjlx("R");
		taxCollectionFinishTableParameter.setNd(year);
		taxCollectionFinishTableParameter.setYf(sqZ);
		taxCollectionFinishTableParameter.setSqQ("");
		taxCollectionFinishTableParameter.setSqZ("");
		taxCollectionFinishTableParameter.setPd("月");
		taxCollectionFinishTableParameter.setFl("02");
		taxCollectionFinishTableParameter.setMoneyUnit("yuan");
		taxCollectionFinishTableParameter.setType("none");
		taxCollectionFinishTableParameter.setOrder("normal");
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<TaxCollectionFinishTable> tableList = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter);
		
		List<BigIndustryTaxCollectionSituation> industryTaxCollectionSituationList = new ArrayList<BigIndustryTaxCollectionSituation>();
		BigIndustryTaxCollectionSituation bigIndustryTaxCollectionSituation = null;
		
		for (int i=1; i < tableList.size(); i++) {
			bigIndustryTaxCollectionSituation = new BigIndustryTaxCollectionSituation();
			bigIndustryTaxCollectionSituation.setHydlMc(tableList.get(i).getXm());
			bigIndustryTaxCollectionSituation.setWcsr(tableList.get(i).getA5());
			bigIndustryTaxCollectionSituation.setZsntq(tableList.get(i).getA7().add(new BigDecimal(100)));
			bigIndustryTaxCollectionSituation.setZl(tableList.get(i).getA5().subtract(tableList.get(i).getA6()));
			
			industryTaxCollectionSituationList.add(bigIndustryTaxCollectionSituation);
		}
		
		return industryTaxCollectionSituationList;
	}
	

}

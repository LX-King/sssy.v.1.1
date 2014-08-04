package com.tyut.sssy.infosearch.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.Industry;
import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.AnalysisIndexService;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.IndustryService;
import com.tyut.sssy.base.service.TaxPayerService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionStructureAnalysisTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionStructureAnalysisTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTableParameter;
import com.tyut.sssy.infosearch.service.DecisionAnalysisSearchService;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.service.IndexFeatureDBService;
import com.tyut.sssy.utils.SessionAttributeKey;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：DecisionAnalysisSearchAction  
 * 类描述：决策分析类查询  
 * 创建人：梁斌  
 * 创建时间：2012-5-11 上午09:00:07  
 * 修改人：梁斌  
 * 修改时间：2012-5-11 上午09:00:07  
 * 修改备注：  
 * @version 
 *
 */
public class
        DecisionAnalysisSearchAction {

	String fileName;
	
	InputStream excelStream;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	/**
	 * 显示税收结构分析查询表条件界面
	 * @return
	 */
	public String showTaxCollectionStructureAnalysisTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("industryList", industryList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		
		return "show_tax_collection_structure_analysis_table_condition_ui";
	}
	
	/**
	 * 税收结构分析查询表
	 * @return
	 */
	public String taxCollectionStructureAnalysisTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String sklx = request.getParameter("sklx");		
		String xsxm = request.getParameter("xsxm");
		
		TaxCollectionStructureAnalysisTableParameter taxCollectionStructureAnalysisTableParameter = new TaxCollectionStructureAnalysisTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-01" +  " -- " + nd + "-" + sqZ;
		
		taxCollectionStructureAnalysisTableParameter.setSwjg(swjg);
		taxCollectionStructureAnalysisTableParameter.setNsrsbh(nsrsbh);
		taxCollectionStructureAnalysisTableParameter.setQyzclx(qyzclx);
		taxCollectionStructureAnalysisTableParameter.setCy(cy);
		taxCollectionStructureAnalysisTableParameter.setHy(hy);
		taxCollectionStructureAnalysisTableParameter.setSklx(sklx);
		taxCollectionStructureAnalysisTableParameter.setNd(nd);
		taxCollectionStructureAnalysisTableParameter.setSqZ(sqZ);
		taxCollectionStructureAnalysisTableParameter.setXsxm(xsxm);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxCollectionStructureAnalysisTable> tableList = decisionAnalysisSearchService.getTaxCollectionStructureAnalysisTable(taxCollectionStructureAnalysisTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
		
		// 行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("firmRegType", firmRegType);
		request.setAttribute("bigIndustry", bigIndustry);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		
		request.setAttribute("taxCollectionStructureAnalysisTableParameter", taxCollectionStructureAnalysisTableParameter);
		
		return "tax_collection_structure_analysis_table";
	}
	
	/**
	 * 税收结构分析查询表 to Excel
	 * @return
	 */
	public String taxCollectionStructureAnalysisTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String sklx = request.getParameter("sklx");		
		String xsxm = request.getParameter("xsxm");
		String nd = request.getParameter("nd");
		String sqZ = request.getParameter("sqZ");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		TaxCollectionStructureAnalysisTableParameter taxCollectionStructureAnalysisTableParameter = new TaxCollectionStructureAnalysisTableParameter();
		
		taxCollectionStructureAnalysisTableParameter.setSwjg(swjg);
		taxCollectionStructureAnalysisTableParameter.setNsrsbh(nsrsbh);
		taxCollectionStructureAnalysisTableParameter.setQyzclx(qyzclx);
		taxCollectionStructureAnalysisTableParameter.setCy(cy);
		taxCollectionStructureAnalysisTableParameter.setHy(hy);
		taxCollectionStructureAnalysisTableParameter.setSklx(sklx);
		taxCollectionStructureAnalysisTableParameter.setNd(nd);
		taxCollectionStructureAnalysisTableParameter.setSqZ(sqZ);
		taxCollectionStructureAnalysisTableParameter.setXsxm(xsxm);
		
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxCollectionStructureAnalysisTable> tableList = decisionAnalysisSearchService.getTaxCollectionStructureAnalysisTable(taxCollectionStructureAnalysisTableParameter);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0.00)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0.00"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("税收结构分析查询表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 注册类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		String qyzclxMc = "";
		if ("".equals(qyzclx)) {
			qyzclxMc = "全部";
		} else {
			qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclx).getMc();
		}
		
		// 纳税人名称
		TaxPayerService taxPayerService = new TaxPayerService();
		String nsrmc = "";
		if (!"".equals(nsrsbh)) {
			nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
		}
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		String hydlMc = "";
		if ("".equals(hy)) {
			hydlMc = "全部";
		} else {
			hydlMc = bigIndustryService.getBigIndustryById(hy).getMc();
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "注册类型： " + qyzclxMc + "，"
						 + "主要行业： " + hydlMc + "，"
						 + "纳税人编码： " + nsrsbh + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "报表期： " + reportPeriodStr ;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("分析期数据");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("基期数据");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(2);
		cell.setCellValue("税额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("比重");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("税额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("比重");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(i + 1);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getXm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA4().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				5 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				5 //last column (0-based)
				));
		// 项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 分析期数据
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				2, //first column (0-based)
				3 //last column (0-based)
				));
		
		// 基期数据
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				4, //first column (0-based)
				5 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<=5; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收结构分析查询表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税收类所有指标分析查询表条件界面
	 * @return
	 */
	public String showTaxCollectionAllIndexTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("industryList", industryList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		
		return "show_tax_collection_all_index_table_condition_ui";
	}
	
	/**
	 * 税收类所有指标分析查询表
	 * @return
	 */
	public String taxCollectionAllIndexTable() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		
		TaxCollectionAllIndexTableParameter taxCollectionAllIndexTableParameter = new TaxCollectionAllIndexTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		taxCollectionAllIndexTableParameter.setSwjg(swjg);
		taxCollectionAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxCollectionAllIndexTableParameter.setQyzclx(qyzclx);
		taxCollectionAllIndexTableParameter.setCy(cy);
		taxCollectionAllIndexTableParameter.setHy(hy);
		taxCollectionAllIndexTableParameter.setNd(nd);
		taxCollectionAllIndexTableParameter.setSqQ(sqQ);
		taxCollectionAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxCollectionAllIndexTable> tableList = decisionAnalysisSearchService.getTaxCollectionAllIndexTable(taxCollectionAllIndexTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclx.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		// 行业
		String hyMc = "";
		if (hy.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		
		// 为了显示分指标查询
		request.setAttribute("taxCollectionAllIndexTableParameter", taxCollectionAllIndexTableParameter);
		
		return "tax_collection_all_index_table";
	}
	
	/**
	 * 税收类所有指标分析查询表 to Excel
	 * @return
	 */
	public String taxCollectionAllIndexTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		TaxCollectionAllIndexTableParameter taxCollectionAllIndexTableParameter = new TaxCollectionAllIndexTableParameter();
		
		taxCollectionAllIndexTableParameter.setSwjg(swjg);
		taxCollectionAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxCollectionAllIndexTableParameter.setQyzclx(qyzclx);
		taxCollectionAllIndexTableParameter.setCy(cy);
		taxCollectionAllIndexTableParameter.setHy(hy);
		taxCollectionAllIndexTableParameter.setNd(nd);
		taxCollectionAllIndexTableParameter.setSqQ(sqQ);
		taxCollectionAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxCollectionAllIndexTable> tableList = decisionAnalysisSearchService.getTaxCollectionAllIndexTable(taxCollectionAllIndexTableParameter);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0.00)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0.00"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("税收类所有指标分析查询表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 注册类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		String qyzclxMc = "";
		if ("".equals(qyzclx)) {
			qyzclxMc = "全部";
		} else {
			qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclx).getMc();
		}
		
		// 纳税人名称
		TaxPayerService taxPayerService = new TaxPayerService();
		String nsrmc = "";
		if (!"".equals(nsrsbh)) {
			nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
		}
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		String hydlMc = "";
		if ("".equals(hy)) {
			hydlMc = "全部";
		} else {
			hydlMc = bigIndustryService.getBigIndustryById(hy).getMc();
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "注册类型： " + qyzclxMc + "，"
						 + "主要行业： " + hydlMc + "，"
						 + "纳税人编码： " + nsrsbh + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "报表期： " + reportPeriodStr ;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		
		cell = row.createCell(1);
		cell.setCellValue("分析期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("基期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("应交税金增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("应交税金变动率的比率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("分值");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);	
		
		cell = row.createCell(1);
		cell.setCellValue("应交税金数额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("应交税金数额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getXm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getA1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA4().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 分析期
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				1, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 基期
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				3, //first column (0-based)
				4 //last column (0-based)
				));
		
		// 应交税金增减额
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				5, //first column (0-based)
				5 //last column (0-based)
				));
		
		// 应交税金变动率的比率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				6, //first column (0-based)
				6 //last column (0-based)
				));
		
		// 分值
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				7, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<=7; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收类所有指标分析查询表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税收类分指标分析查询表条件界面
	 * @return
	 */
	public String showTaxCollectionSubIndexTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		// 分析指标
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		List<AnalysisIndex> analysisIndexList = analysisIndexService.getAnalysisIndexList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("industryList", industryList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("analysisIndexList", analysisIndexList);
		
		return "show_tax_collection_sub_index_table_condition_ui";
	}
	
	/**
	 * 税收类分指标分析查询表
	 * @return
	 */
	public String taxCollectionSubIndexTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		TaxCollectionAllIndexTableParameter taxCollectionAllIndexTableParameter = new TaxCollectionAllIndexTableParameter();
		String reportPeriodStr = "";
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		String fxzbDm = request.getParameter("fxzbDm");
		String fxzbLx = request.getParameter("fxzbLx");
		
		// 分指标文字描述
		IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
		IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(fxzbDm, fxzbLx);
		
		// 分指标名称
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		AnalysisIndex analysisIndex = analysisIndexService.getAnalysisIndexById(fxzbDm);
		
		taxCollectionAllIndexTableParameter.setSwjg(swjg);
		taxCollectionAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxCollectionAllIndexTableParameter.setQyzclx(qyzclx);
		taxCollectionAllIndexTableParameter.setCy(cy);
		taxCollectionAllIndexTableParameter.setHy(hy);
		taxCollectionAllIndexTableParameter.setNd(nd);
		taxCollectionAllIndexTableParameter.setSqQ(sqQ);
		taxCollectionAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxCollectionAllIndexTable> tableList = decisionAnalysisSearchService.getTaxCollectionAllIndexTable(taxCollectionAllIndexTableParameter);
		TaxCollectionAllIndexTable table = null;
		
		for (TaxCollectionAllIndexTable taxCollectionAllIndexTable : tableList) {
			if (analysisIndex.getFxzbDm().equals(taxCollectionAllIndexTable.getA9())) {
				table = taxCollectionAllIndexTable;
				break;
			}
		}
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclx.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		// 行业
		String hyMc = "";
		if (hy.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("table", table);
		request.setAttribute("indexFeatureDB", indexFeatureDB);
		
		return "tax_collection_sub_index_table";
		
		/*
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String fxzb = request.getParameter("fxzb");
		
		TaxCollectionSubIndexTableParameter taxCollectionSubIndexTableParameter = new TaxCollectionSubIndexTableParameter();
		
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		taxCollectionSubIndexTableParameter.setSwjg(swjg);
		taxCollectionSubIndexTableParameter.setNsrsbh(nsrsbh);
		taxCollectionSubIndexTableParameter.setQyzclx(qyzclx);
		taxCollectionSubIndexTableParameter.setCy(cy);
		taxCollectionSubIndexTableParameter.setHy(hy);
		taxCollectionSubIndexTableParameter.setFxzb(fxzb);
		taxCollectionSubIndexTableParameter.setNd(nd);
		taxCollectionSubIndexTableParameter.setSqQ(sqQ);
		taxCollectionSubIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxCollectionSubIndexTable> tableList = decisionAnalysisSearchService.getTaxCollectionSubIndexTable(taxCollectionSubIndexTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclxMc.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		
		// 行业
		String hyMc = "";
		if (hyMc.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		
		return "tax_collection_sub_index_table";
		*/
	}
	
	/**
	 * 显示税收增长与税源发展指数分析查询表条件界面
	 * @return
	 */
	public String showTaxGrowTaxResourceDevelopIndexTableConditionUI() {
		
		// 核算机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String unitType = "H";	// 核算机关标识
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitListByType(unitType);
		TaxUnit taxUnit = taxUnitList.get(0);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_tax_grow_tax_resource_develop_index_table_condition_ui";
	}
	
	/**
	 * 税收增长与税源发展指数分析查询
	 * @return
	 */
	public String taxGrowTaxResourceDevelopIndexTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		TaxGrowTaxResourceDevelopIndexTableParameter taxGrowTaxResourceDevelopIndexTableParameter = new TaxGrowTaxResourceDevelopIndexTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ + " -- " + nd + "-" + sqZ;
		
		taxGrowTaxResourceDevelopIndexTableParameter.setNd(nd);
		taxGrowTaxResourceDevelopIndexTableParameter.setSqQ(sqQ);
		taxGrowTaxResourceDevelopIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		TaxGrowTaxResourceDevelopIndexTable table = decisionAnalysisSearchService.getTaxGrowTaxResourceDevelopIndexTable(taxGrowTaxResourceDevelopIndexTableParameter);
		
		// 核算机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String unitType = "H";	// 核算机关标识
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitListByType(unitType);
		TaxUnit taxUnit = taxUnitList.get(0);
		
		request.setAttribute("table", table);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxGrowTaxResourceDevelopIndexTableParameter", taxGrowTaxResourceDevelopIndexTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.JCSYSS,taxGrowTaxResourceDevelopIndexTableParameter);
		
		return "tax_grow_tax_resource_develop_index_table";
	}
	
	/**
	 * 税收增长与税源发展指数分析查询 to Excel
	 * @return
	 */
	public String taxGrowTaxResourceDevelopIndexTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		TaxGrowTaxResourceDevelopIndexTableParameter taxGrowTaxResourceDevelopIndexTableParameter = new TaxGrowTaxResourceDevelopIndexTableParameter();
		taxGrowTaxResourceDevelopIndexTableParameter.setNd(nd);
		taxGrowTaxResourceDevelopIndexTableParameter.setSqQ(sqQ);
		taxGrowTaxResourceDevelopIndexTableParameter.setSqZ(sqZ);
		
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		TaxGrowTaxResourceDevelopIndexTable table = decisionAnalysisSearchService.getTaxGrowTaxResourceDevelopIndexTable(taxGrowTaxResourceDevelopIndexTableParameter);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0.00)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0.00"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("税收增长与税源发展指数分析");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String swjgMc = taxUnitService.getTaxUnitListByType("H").get(0).getMc();
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr;
						 
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		
		cell = row.createCell(0);
		cell.setCellValue("显示项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("报表期实缴税收");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("税收同比变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("税源发展指数");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("弹性分析");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第4行开始）
		row = sheet.createRow(4);
		
		cell = row.createCell(0);
		cell.setCellValue("税源发展指数");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(table.getSjss().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getZzl().floatValue());
		cell.setCellStyle(cellStyleFmt);
		
		cell = row.createCell(3);
		cell.setCellValue(table.getFzzs().floatValue());
		cell.setCellStyle(cellStyleFmt);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getTxfx().floatValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				4 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				4 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=4; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收增长与税源发展指数分析".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 税收增长与税源发展指数分析查询   指数结构
	 * @return
	 */
	public String indexStructureTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		TaxGrowTaxResourceDevelopIndexTable indexStructureTable = decisionAnalysisSearchService.getIndexStructureTable(nd, sqQ, sqZ);
		
		request.setAttribute("indexStructureTable", indexStructureTable);
		request.setAttribute("nd", nd);
		request.setAttribute("sqQ", sqQ);
		request.setAttribute("sqZ", sqZ);
		
		return "index_structure_table";
	}
	
	/**
	 * 税收增长与税源发展指数分析查询   指数结构 to Excel
	 * @return
	 */
	public String indexStructureTableToExcel() throws IOException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		TaxGrowTaxResourceDevelopIndexTable indexStructureTable = decisionAnalysisSearchService.getIndexStructureTable(nd, sqQ, sqZ);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0.0000)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0.0000"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("税收增长与税源发展指数分析查询--指数结构");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);	// 空一行				
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		
		cell = row.createCell(0);
		cell.setCellValue("指标指数");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("数值");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		
		cell = row.createCell(0);
		cell.setCellValue("营业收入增长率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getYysrzzl().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第5行
		row = sheet.createRow(5);					
		
		cell = row.createCell(0);
		cell.setCellValue("利润总额增长率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getLrzezzl().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第6行
		row = sheet.createRow(6);					
		
		cell = row.createCell(0);
		cell.setCellValue("生产经营状况指标值");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getScjyzk().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第7行
		row = sheet.createRow(7);					
		
		cell = row.createCell(0);
		cell.setCellValue("营业收入与营业成本弹性");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getYycbtx().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第8行
		row = sheet.createRow(8);					
		
		cell = row.createCell(0);
		cell.setCellValue("营业收入与营业费用成本弹性");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getYyfytx().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第9行
		row = sheet.createRow(9);					
		
		cell = row.createCell(0);
		cell.setCellValue("净资产收入率变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getJzcsrl().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第10行
		row = sheet.createRow(10);					
		
		cell = row.createCell(0);
		cell.setCellValue("毛利率变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getMllbdl().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		// 第11行
		row = sheet.createRow(11);					
		
		cell = row.createCell(0);
		cell.setCellValue("零散税收、个体税收增长率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue(indexStructureTable.getLssszzl().doubleValue());
		cell.setCellStyle(cellStyleFmt);
		
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=1; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收增长与税源发展指数分析查询--指数结构".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税源类所有指标分析查询表条件界面
	 * @return
	 */
	public String showTaxResourceAllIndexTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("industryList", industryList);
		
		return "show_tax_resource_all_index_table_condition_ui";
	}
	
	/**
	 * 税源类所有指标分析查询表
	 * @return
	 */
	public String taxResourceAllIndexTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String hy = request.getParameter("hy");
		
		TaxResourceAllIndexTableParameter taxResourceAllIndexTableParameter = new TaxResourceAllIndexTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		taxResourceAllIndexTableParameter.setSwjg(swjg);
		taxResourceAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxResourceAllIndexTableParameter.setQyzclx(qyzclx);
		taxResourceAllIndexTableParameter.setHy(hy);
		taxResourceAllIndexTableParameter.setNd(nd);
		taxResourceAllIndexTableParameter.setSqQ(sqQ);
		taxResourceAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxResourceAllIndexTable> tableList = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclx.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		// 行业
		String hyMc = "";
		if (hy.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxResourceAllIndexTableParameter", taxResourceAllIndexTableParameter);
		
		return "tax_resource_all_index_table";
	}
	
	/**
	 * 税源类所有指标分析查询表 to Excel
	 * @return
	 */
	public String taxResourceAllIndexTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		TaxResourceAllIndexTableParameter taxResourceAllIndexTableParameter = new TaxResourceAllIndexTableParameter();
		
		taxResourceAllIndexTableParameter.setSwjg(swjg);
		taxResourceAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxResourceAllIndexTableParameter.setQyzclx(qyzclx);
		taxResourceAllIndexTableParameter.setCy(cy);
		taxResourceAllIndexTableParameter.setHy(hy);
		taxResourceAllIndexTableParameter.setNd(nd);
		taxResourceAllIndexTableParameter.setSqQ(sqQ);
		taxResourceAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxResourceAllIndexTable> tableList = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0.00)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0.00"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("税源类所有指标分析查询表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 注册类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		String qyzclxMc = "";
		if ("".equals(qyzclx)) {
			qyzclxMc = "全部";
		} else {
			qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclx).getMc();
		}
		
		// 纳税人名称
		TaxPayerService taxPayerService = new TaxPayerService();
		String nsrmc = "";
		if (!"".equals(nsrsbh)) {
			nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
		}
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		String hydlMc = "";
		if ("".equals(hy)) {
			hydlMc = "全部";
		} else {
			hydlMc = bigIndustryService.getBigIndustryById(hy).getMc();
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "注册类型： " + qyzclxMc + "，"
						 + "主要行业： " + hydlMc + "，"
						 + "纳税人编码： " + nsrsbh + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "报表期： " + reportPeriodStr ;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		
		cell = row.createCell(1);
		cell.setCellValue("分析期数额/比率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("基期数额/比率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("分值");
		cell.setCellStyle(cellStyleAlignCenter);
		
		
		// 2.4 输出逐行数据（从第4行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 4;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getXm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getA1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA4().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 分析期数额/比率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				1, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 基期数额/比率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				3, //first column (0-based)
				4 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=7; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税源类所有指标分析查询表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税源类分指标分析查询表条件界面
	 * @return
	 */
	public String showTaxResourceSubIndexTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		// 分析指标
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		List<AnalysisIndex> analysisIndexList = analysisIndexService.getAnalysisIndexList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("industryList", industryList);
		request.setAttribute("analysisIndexList", analysisIndexList);
		
		return "show_tax_resource_sub_index_table_condition_ui";
	}
	
	/**
	 * 税源类分指标分析查询表
	 * @return
	 */
	public String taxResourceSubIndexTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		TaxResourceAllIndexTableParameter taxResourceAllIndexTableParameter = new TaxResourceAllIndexTableParameter();
		String reportPeriodStr = "";
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		String fxzbDm = request.getParameter("fxzbDm");
		String fxzbLx = request.getParameter("fxzbLx");
		
		// 分指标文字描述
		IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
		IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(fxzbDm, fxzbLx);
		
		// 分指标名称
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		AnalysisIndex analysisIndex = analysisIndexService.getAnalysisIndexById(fxzbDm);
		
		taxResourceAllIndexTableParameter.setSwjg(swjg);
		taxResourceAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxResourceAllIndexTableParameter.setQyzclx(qyzclx);
		taxResourceAllIndexTableParameter.setCy(cy);
		taxResourceAllIndexTableParameter.setHy(hy);
		taxResourceAllIndexTableParameter.setNd(nd);
		taxResourceAllIndexTableParameter.setSqQ(sqQ);
		taxResourceAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxResourceAllIndexTable> tableList = decisionAnalysisSearchService.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter);
		TaxResourceAllIndexTable table = null;
		
		for (TaxResourceAllIndexTable taxResourceAllIndexTable : tableList) {
			if (analysisIndex.getFxzbDm().equals(taxResourceAllIndexTable.getA9())) {
				table = taxResourceAllIndexTable;
				break;
			}
		}
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclx.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		// 行业
		String hyMc = "";
		if (hy.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("table", table);
		request.setAttribute("indexFeatureDB", indexFeatureDB);
		
		return "tax_resource_sub_index_table";
		/*
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String fxzb = request.getParameter("fxzb");
		
		TaxResourceSubIndexTableParameter taxResourceSubIndexTableParameter = new TaxResourceSubIndexTableParameter();
		
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		taxResourceSubIndexTableParameter.setSwjg(swjg);
		taxResourceSubIndexTableParameter.setNsrsbh(nsrsbh);
		taxResourceSubIndexTableParameter.setQyzclx(qyzclx);
		taxResourceSubIndexTableParameter.setCy(cy);
		taxResourceSubIndexTableParameter.setHy(hy);
		taxResourceSubIndexTableParameter.setFxzb(fxzb);
		taxResourceSubIndexTableParameter.setNd(nd);
		taxResourceSubIndexTableParameter.setSqQ(sqQ);
		taxResourceSubIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxResourceSubIndexTable> tableList = decisionAnalysisSearchService.getTaxResourceSubIndexTable(taxResourceSubIndexTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclxMc.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		
		// 行业
		String hyMc = "";
		if (hyMc.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		
		return "tax_resource_sub_index_table";
		*/
	}
	
	/**
	 * 显示税负类所有指标分析查询表条件界面
	 * @return
	 */
	public String showTaxBurdenAllIndexTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("industryList", industryList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		
		return "show_tax_burden_all_index_table_condition_ui";
	}
	
	/**
	 * 税负类所有指标分析查询表
	 * @return
	 */
	public String taxBurdenAllIndexTable() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		
		TaxBurdenAllIndexTableParameter taxBurdenAllIndexTableParameter = new TaxBurdenAllIndexTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		taxBurdenAllIndexTableParameter.setSwjg(swjg);
		taxBurdenAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxBurdenAllIndexTableParameter.setQyzclx(qyzclx);
		taxBurdenAllIndexTableParameter.setCy(cy);
		taxBurdenAllIndexTableParameter.setHy(hy);
		taxBurdenAllIndexTableParameter.setNd(nd);
		taxBurdenAllIndexTableParameter.setSqQ(sqQ);
		taxBurdenAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxBurdenAllIndexTable> tableList = decisionAnalysisSearchService.getTaxBurdenAllIndexTable(taxBurdenAllIndexTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclx.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		// 行业
		String hyMc = "";
		if (hy.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxBurdenAllIndexTableParameter", taxBurdenAllIndexTableParameter);
		
		return "tax_burden_all_index_table";
	}
	
	/**
	 * 税负类所有指标分析查询表 to Excel
	 * @return
	 */
	public String taxBurdenAllIndexTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		TaxBurdenAllIndexTableParameter taxBurdenAllIndexTableParameter = new TaxBurdenAllIndexTableParameter();
		
		taxBurdenAllIndexTableParameter.setSwjg(swjg);
		taxBurdenAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxBurdenAllIndexTableParameter.setQyzclx(qyzclx);
		taxBurdenAllIndexTableParameter.setCy(cy);
		taxBurdenAllIndexTableParameter.setHy(hy);
		taxBurdenAllIndexTableParameter.setNd(nd);
		taxBurdenAllIndexTableParameter.setSqQ(sqQ);
		taxBurdenAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxBurdenAllIndexTable> tableList = decisionAnalysisSearchService.getTaxBurdenAllIndexTable(taxBurdenAllIndexTableParameter);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0.00)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0.00"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("税负类所有指标分析查询表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 注册类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		String qyzclxMc = "";
		if ("".equals(qyzclx)) {
			qyzclxMc = "全部";
		} else {
			qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclx).getMc();
		}
		
		// 纳税人名称
		TaxPayerService taxPayerService = new TaxPayerService();
		String nsrmc = "";
		if (!"".equals(nsrsbh)) {
			nsrmc = taxPayerService.getTaxPayerByCode(nsrsbh).getNsrmc();
		}
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		String hydlMc = "";
		if ("".equals(hy)) {
			hydlMc = "全部";
		} else {
			hydlMc = bigIndustryService.getBigIndustryById(hy).getMc();
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "注册类型： " + qyzclxMc + "，"
						 + "主要行业： " + hydlMc + "，"
						 + "纳税人编码： " + nsrsbh + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "报表期： " + reportPeriodStr ;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		
		cell = row.createCell(1);
		cell.setCellValue("分析期数额/比率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("基期数额/比率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("分值");
		cell.setCellStyle(cellStyleAlignCenter);
		
		
		// 2.4 输出逐行数据（从第4行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 4;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getXm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getA1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA4().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 分析期数额/比率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				1, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 基期数额/比率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				3, //first column (0-based)
				4 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=7; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税负类所有指标分析查询表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税负类分指标分析查询表条件界面
	 * @return
	 */
	public String showTaxBurdenSubIndexTableConditionUI() {
		
		// 税务机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList = taxUnitService.getTaxUnitList();
		
		// 企业注册类型列表
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		// 产业类型列表
		IndustryService industryService = new IndustryService();
		List<Industry> industryList = industryService.getIndustryList();
		
		// 主要行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getMainIndustryList();
		
		// 分析指标
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		List<AnalysisIndex> analysisIndexList = analysisIndexService.getAnalysisIndexList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		request.setAttribute("industryList", industryList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("analysisIndexList", analysisIndexList);
		
		return "show_tax_burden_sub_index_table_condition_ui";
	}
	
	/**
	 * 税负类分指标分析查询表
	 * @return
	 */
	public String taxBurdenSubIndexTable() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		
		TaxBurdenAllIndexTableParameter taxBurdenAllIndexTableParameter = new TaxBurdenAllIndexTableParameter();
		String reportPeriodStr = "";
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		String fxzbDm = request.getParameter("fxzbDm");
		String fxzbLx = request.getParameter("fxzbLx");
		
		// 分指标文字描述
		IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
		IndexFeatureDB indexFeatureDB = indexFeatureDBService.getByCodeAndType(fxzbDm, fxzbLx);
		
		// 分指标名称
		AnalysisIndexService analysisIndexService = new AnalysisIndexService();
		AnalysisIndex analysisIndex = analysisIndexService.getAnalysisIndexById(fxzbDm);
		
		taxBurdenAllIndexTableParameter.setSwjg(swjg);
		taxBurdenAllIndexTableParameter.setNsrsbh(nsrsbh);
		taxBurdenAllIndexTableParameter.setQyzclx(qyzclx);
		taxBurdenAllIndexTableParameter.setCy(cy);
		taxBurdenAllIndexTableParameter.setHy(hy);
		taxBurdenAllIndexTableParameter.setNd(nd);
		taxBurdenAllIndexTableParameter.setSqQ(sqQ);
		taxBurdenAllIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxBurdenAllIndexTable> tableList = decisionAnalysisSearchService.getTaxBurdenAllIndexTable(taxBurdenAllIndexTableParameter);
		TaxBurdenAllIndexTable table = null;
		
		for (TaxBurdenAllIndexTable taxBurdenAllIndexTable : tableList) {
			if (analysisIndex.getFxzbDm().equals(taxBurdenAllIndexTable.getA9())) {
				table = taxBurdenAllIndexTable;
				break;
			}
		}
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclx.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		// 行业
		String hyMc = "";
		if (hy.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("table", table);
		request.setAttribute("indexFeatureDB", indexFeatureDB);
		
		return "tax_burden_sub_index_table";
		
		/*
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjg = request.getParameter("swjg");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hy = request.getParameter("hy");
		String fxzb = request.getParameter("fxzb");
		
		TaxBurdenSubIndexTableParameter taxBurdenSubIndexTableParameter = new TaxBurdenSubIndexTableParameter();
		
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sqQ = monthPeriod.substring(0, 2);
		String sqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sqQ +  " -- " + nd + "-" + sqZ;
		
		taxBurdenSubIndexTableParameter.setSwjg(swjg);
		taxBurdenSubIndexTableParameter.setNsrsbh(nsrsbh);
		taxBurdenSubIndexTableParameter.setQyzclx(qyzclx);
		taxBurdenSubIndexTableParameter.setCy(cy);
		taxBurdenSubIndexTableParameter.setHy(hy);
		taxBurdenSubIndexTableParameter.setFxzb(fxzb);
		taxBurdenSubIndexTableParameter.setNd(nd);
		taxBurdenSubIndexTableParameter.setSqQ(sqQ);
		taxBurdenSubIndexTableParameter.setSqZ(sqZ);
			
		DecisionAnalysisSearchService decisionAnalysisSearchService = new DecisionAnalysisSearchService();
		List<TaxBurdenSubIndexTable> tableList = decisionAnalysisSearchService.getTaxBurdenSubIndexTable(taxBurdenSubIndexTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjg);
		
		// 注册类型
		String qyzclxMc = "";
		if (qyzclxMc.equals("")) {
			qyzclxMc = "全部";
		} else {
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
			qyzclxMc = firmRegType.getMc();
		}
		
		
		// 行业
		String hyMc = "";
		if (hyMc.equals("")) {
			hyMc = "全部";
		} else {
			BigIndustryService bigIndustryService = new BigIndustryService();
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclxMc", qyzclxMc);
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		
		return "tax_burden_sub_index_table";
		*/
	}
	
	/**
	 * 将workbook写入InputStream
	 * @param workbook
	 * @param string
	 */
	private void workbook2InputStream(HSSFWorkbook workbook, String fileName) throws IOException{
		this.fileName = fileName;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		baos.flush();
		byte[] aa = baos.toByteArray();	
		this.excelStream = new ByteArrayInputStream(aa, 0, aa.length);
		baos.close();
	}
	
}

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

import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.Industry;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.IndustryService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.infosearch.service.TaxBurdenSearchService;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTable;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTableParameter;
import com.tyut.sssy.utils.SessionAttributeKey;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：TaxBurdenSearchAction  
 * 类描述：税负分析查询表  
 * 创建人：梁斌  
 * 创建时间：2012-5-23 上午10:01:12  
 * 修改人：梁斌  
 * 修改时间：2012-5-23 上午10:01:12  
 * 修改备注：  
 * @version 
 *
 */
public class TaxBurdenSearchAction {

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
	 * 显示税负分析查询表条件选择界面
	 * @return
	 */
	public String showTaxBurdenAnalysisSearchConditionUI() {
		
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
		
		return "show_tax_burden_analysis_search_condition_ui";
	}
	
	/**
	 * 税负分析查询表
	 * @return
	 */
	public String taxBurdenAnalysisSearchTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjgDm = request.getParameter("swjgDm");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hydl = request.getParameter("hydl");
		String sflx = request.getParameter("sflx");		
		
		TaxBurdenAnalysisSearchTableParameter taxBurdenAnalysisSearchTableParameter = new TaxBurdenAnalysisSearchTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqQ = monthPeriod.substring(0, 2);
		String sssqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sssqQ + "--" + nd + "-" + sssqZ;
		
		taxBurdenAnalysisSearchTableParameter.setNd(nd);
		taxBurdenAnalysisSearchTableParameter.setSssqQ(sssqQ);
		taxBurdenAnalysisSearchTableParameter.setSssqZ(sssqZ);
		taxBurdenAnalysisSearchTableParameter.setSwjgDm(swjgDm);
		taxBurdenAnalysisSearchTableParameter.setQyzclx(qyzclx);
		taxBurdenAnalysisSearchTableParameter.setCy(cy);
		taxBurdenAnalysisSearchTableParameter.setHydl(hydl);
		taxBurdenAnalysisSearchTableParameter.setSflx(sflx);
		
		TaxBurdenSearchService taxBurdenSearchService = new TaxBurdenSearchService();
		List<TaxBurdenAnalysisSearchTable> tableList = taxBurdenSearchService.getTaxBurdenAnalysisSearchTable(taxBurdenAnalysisSearchTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgDm);
		
		request.setAttribute("tableList", tableList);
		
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("qyzclx", qyzclx);
		request.setAttribute("cy", cy);
		request.setAttribute("hydl", hydl);
		request.setAttribute("sflx", sflx);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxBurdenAnalysisSearchTableParameter", taxBurdenAnalysisSearchTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.SFZHFX, taxBurdenAnalysisSearchTableParameter);
		
		return "tax_burden_analysis_search_table";
	}
	
	/**
	 * 税负分析查询表 to Excel
	 * @return
	 */
	public String taxBurdenAnalysisSearchTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxBurdenAnalysisSearchTableParameter taxBurdenAnalysisSearchTableParameter = new TaxBurdenAnalysisSearchTableParameter();
		
		String nd = request.getParameter("nd");
		String sssqQ = request.getParameter("sssqQ");
		String sssqZ = request.getParameter("sssqZ");
		String swjgDm = request.getParameter("swjgDm");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hydl = request.getParameter("hydl");
		String sflx = request.getParameter("sflx");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		taxBurdenAnalysisSearchTableParameter.setNd(nd);
		taxBurdenAnalysisSearchTableParameter.setSssqQ(sssqQ);
		taxBurdenAnalysisSearchTableParameter.setSssqZ(sssqZ);
		taxBurdenAnalysisSearchTableParameter.setSwjgDm(swjgDm);
		taxBurdenAnalysisSearchTableParameter.setQyzclx(qyzclx);
		taxBurdenAnalysisSearchTableParameter.setCy(cy);
		taxBurdenAnalysisSearchTableParameter.setHydl(hydl);
		taxBurdenAnalysisSearchTableParameter.setSflx(sflx);
		
		TaxBurdenSearchService taxBurdenSearchService = new TaxBurdenSearchService();
		List<TaxBurdenAnalysisSearchTable> tableList = taxBurdenSearchService.getTaxBurdenAnalysisSearchTable(taxBurdenAnalysisSearchTableParameter);
		
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
		cell.setCellValue("税负分析查询表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		String swjgMc = "";
		if ("".equals(swjgDm)) {
			swjgMc = "全部";
		} else {
			swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
		}
		
		// 注册类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		String qyzclxMc = "";
		if ("".equals(qyzclx)) {
			qyzclxMc = "全部";
		} else {
			qyzclxMc = firmRegTypeService.getFirmRegTypeById(qyzclx).getMc();
		}
		
		// 产业类型
		IndustryService industryService = new IndustryService();
		String cyMc = "";
		if ("".equals(cy)) {
			cyMc = "全部";
		} else {
			cyMc = industryService.getIndustryById(cy).getMc();
		}
		
		// 行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		String hydlMc = "";
		if ("".equals(hydl)) {
			hydlMc = "全部";
		} else {
			hydlMc = bigIndustryService.getBigIndustryById(hydl).getMc();
		}
		
		// 税负类型
		String sflxMc = "";
		if ("0".equals(sflx)) {
			sflxMc = "总体税负";
		} else if ("1".equals(sflx)) {
			sflxMc = "营业税税负";
		} else if ("2".equals(sflx)) {
			sflxMc = "企业所得税税负";
		} else if ("3".equals(sflx)) {
			sflxMc = "其他税负";
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "注册类型： " + qyzclxMc + "，"
						 + "产业类型： " + cyMc + "，"
						 + "行业： " + hydlMc + "，"
						 + "税负类型： " + sflxMc + "，"
						 + "报表期： " + reportPeriodStr;
						 
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("纳税人编码");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("纳税人名称");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("实缴税收");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("当期税负");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("同期税负");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("税负变动率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第4行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 4;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getDm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getMc().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getSshj1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getDqsf().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getTqsf().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getSfbdl().doubleValue());
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
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=5; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税负分析查询表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
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

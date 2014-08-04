package com.tyut.sssy.infosearch.actions;

import com.tyut.sssy.base.domain.*;
import com.tyut.sssy.base.service.*;
import com.tyut.sssy.infosearch.service.TaxResourceSearchService;
import com.tyut.sssy.taxresource.domain.*;
import com.tyut.sssy.utils.SessionAttributeKey;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxResourceSearchAction  
 * 类描述：税源类查询action  
 * 创建人：梁斌  
 * 创建时间：2012-5-15 上午10:13:50  
 * 修改人：梁斌  
 * 修改时间：2012-5-15 上午10:13:50  
 * 修改备注：  
 * @version 
 *
 */
public class TaxResourceSearchAction {

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
	 * 显示重点税源监测户统计表 输入条件
	 * @return
	 */
	public String showTaxResourceMonitorTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		// 行业大类
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();
		
		// 上年实缴税收款
		FirmSizeService firmSizeService = new FirmSizeService();
		List<FirmSize> firmSizeList = firmSizeService.getFirmSizeList();
		
		// 经济类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("firmSizeList", firmSizeList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		
		return "show_tax_resource_monitor_table_condition_ui";
	}
	
	/**
	 * 重点税源监测户统计表
	 * @return
	 */
	public String taxResourceMonitorTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String ssjgDm = request.getParameter("ssjgDm");
		String qygm = request.getParameter("qygm");
		String hydlDm = request.getParameter("hydlDm");
		String qyzclx = request.getParameter("qyzclx");
		String xmbz = request.getParameter("xmbz");
		String dszsbz = request.getParameter("dszsbz");
		
		TaxResourceMonitorTableParameter taxResourceMonitorTableParameter = new TaxResourceMonitorTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqQ = monthPeriod.substring(0, 2);
		String sssqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sssqQ + "--" + nd + "-" + sssqZ; 
		
		taxResourceMonitorTableParameter.setSsjgDm(ssjgDm);
		taxResourceMonitorTableParameter.setQygm(qygm);
		taxResourceMonitorTableParameter.setNd(nd);
		taxResourceMonitorTableParameter.setSssqQ(sssqQ);
		taxResourceMonitorTableParameter.setSssqZ(sssqZ);
		taxResourceMonitorTableParameter.setHydlDm(hydlDm);
		taxResourceMonitorTableParameter.setQyzclx(qyzclx);
		taxResourceMonitorTableParameter.setXmbz(xmbz);
		taxResourceMonitorTableParameter.setDszsbz(dszsbz);
			
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceMonitorTable> tableList = taxResourceSearchService.getTaxResourceMonitorTable(taxResourceMonitorTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ssjgDm);
		
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxResourceMonitorTableParameter", taxResourceMonitorTableParameter);
		
		return "tax_resource_monitor_table";
	}
	
	/**
	 * 重点税源检测户数统计表 to Excel
	 * @return
	 */
	public String taxResourceMonitorTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String ssjgDm = request.getParameter("ssjgDm");
		String nd = request.getParameter("nd");
		String sssqQ = request.getParameter("sssqQ");
		String sssqZ = request.getParameter("sssqZ");
		String qygm = request.getParameter("qygm");
		String hydlDm = request.getParameter("hydlDm");
		String qyzclx = request.getParameter("qyzclx");
		String xmbz = request.getParameter("xmbz");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		TaxResourceMonitorTableParameter taxResourceMonitorTableParameter = new TaxResourceMonitorTableParameter();
		taxResourceMonitorTableParameter.setSsjgDm(ssjgDm);
		taxResourceMonitorTableParameter.setQygm(qygm);
		taxResourceMonitorTableParameter.setNd(nd);
		taxResourceMonitorTableParameter.setSssqQ(sssqQ);
		taxResourceMonitorTableParameter.setSssqZ(sssqZ);
		taxResourceMonitorTableParameter.setHydlDm(hydlDm);
		taxResourceMonitorTableParameter.setQyzclx(qyzclx);
		taxResourceMonitorTableParameter.setXmbz(xmbz);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceMonitorTable> tableList = taxResourceSearchService.getTaxResourceMonitorTable(taxResourceMonitorTableParameter);
		
		// 2. 创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 2.1 设置对其方式
		CellStyle cellStyleAlignCenter;
		cellStyleAlignCenter = workbook.createCellStyle();
		cellStyleAlignCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 2.2  设置单元格数值类型(0)
		CellStyle cellStyleFmt;
		DataFormat format = workbook.createDataFormat();
		cellStyleFmt = workbook.createCellStyle();
		cellStyleFmt.setDataFormat(format.getFormat("0"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("重点税源检测户数统计表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ssjgDm);
		String ssjgMc = taxUnit.getMc();
		
		String condition = "管理机关： " + ssjgMc + "，"
						 + "报表期：" + reportPeriodStr;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("合计");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("国有企业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("集体企业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("股份合作企业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("联营企业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("股份公司");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("其中：国有控股");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("私营企业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("其他企业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第4行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 4;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(i + 1);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getXm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA0());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA1());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA2());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA3());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA4());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA5());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getA6());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getA7());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(10);
			cell.setCellValue(tableList.get(i).getA8());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				10 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				10 //last column (0-based)
				));
		
		// 项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<11; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("重点税源检测户数统计表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示重点税源监控企业明细表 输入条件
	 * @return
	 */
	public String showTaxResourceMonitorFirmDetailTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		// 行业大类
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();
		
		// 上年实缴税收款
		FirmSizeService firmSizeService = new FirmSizeService();
		List<FirmSize> firmSizeList = firmSizeService.getFirmSizeList();
		
		// 经济类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		List<FirmRegType> firmRegTypeList = firmRegTypeService.getFirmRegTypeList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		request.setAttribute("firmSizeList", firmSizeList);
		request.setAttribute("firmRegTypeList", firmRegTypeList);
		
		return "show_tax_resource_monitor_firm_detail_table_condition_ui";
	}
	
	/**
	 * 重点税源监控企业明细表
	 * @return
	 */
	public String taxResourceMonitorFirmDetailTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String ssjgDm = request.getParameter("ssjgDm");
		String qygm = request.getParameter("qygm");
		String hydlDm = request.getParameter("hydlDm");
		String qyzclx = request.getParameter("qyzclx");
		
		TaxResourceMonitorFirmDetailTableParameter taxResourceMonitorFirmDetailTableParameter = new TaxResourceMonitorFirmDetailTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqQ = monthPeriod.substring(0, 2);
		String sssqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-" + sssqQ + "--" + nd + "-" + sssqZ;
		
		taxResourceMonitorFirmDetailTableParameter.setSsjgDm(ssjgDm);
		taxResourceMonitorFirmDetailTableParameter.setQygm(qygm);
		taxResourceMonitorFirmDetailTableParameter.setNd(nd);
		taxResourceMonitorFirmDetailTableParameter.setSssqQ(sssqQ);
		taxResourceMonitorFirmDetailTableParameter.setSssqZ(sssqZ);
		taxResourceMonitorFirmDetailTableParameter.setHydlDm(hydlDm);
		taxResourceMonitorFirmDetailTableParameter.setQyzclx(qyzclx);
			
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceMonitorFirmDetailTable> tableList = taxResourceSearchService.getTaxResourceMonitorFirmDetailTable(taxResourceMonitorFirmDetailTableParameter);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ssjgDm);
		
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("hydl", hydlDm);
		request.setAttribute("qyzclx", qyzclx);
		request.setAttribute("qygm", qygm);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxResourceMonitorFirmDetailTableParameter", taxResourceMonitorFirmDetailTableParameter);
		
		return "tax_resource_monitor_firm_detail_table";
	}
	
	/**
	 * 重点税源监控企业明细表 to Excel
	 * @return
	 */
	public String taxResourceMonitorFirmDetailTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxResourceMonitorFirmDetailTableParameter taxResourceMonitorFirmDetailTableParameter = new TaxResourceMonitorFirmDetailTableParameter();
		
		String ssjgDm = request.getParameter("ssjgDm");
		String qygm = request.getParameter("qygm");
		try {
			qygm = URLDecoder.decode(qygm, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String nd = request.getParameter("nd");
		String sssqQ = request.getParameter("sssqQ");
		String sssqZ = request.getParameter("sssqZ");
		String hydlDm = request.getParameter("hydlDm");
		String qyzclx = request.getParameter("qyzclx");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		taxResourceMonitorFirmDetailTableParameter.setSsjgDm(ssjgDm);
		taxResourceMonitorFirmDetailTableParameter.setQygm(qygm);
		taxResourceMonitorFirmDetailTableParameter.setNd(nd);
		taxResourceMonitorFirmDetailTableParameter.setSssqQ(sssqQ);
		taxResourceMonitorFirmDetailTableParameter.setSssqZ(sssqZ);
		taxResourceMonitorFirmDetailTableParameter.setHydlDm(hydlDm);
		taxResourceMonitorFirmDetailTableParameter.setQyzclx(qyzclx);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceMonitorFirmDetailTable> tableList = taxResourceSearchService.getTaxResourceMonitorFirmDetailTable(taxResourceMonitorFirmDetailTableParameter);
		
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
		cellStyleFmt.setDataFormat(format.getFormat("0"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("重点税源监控企业明细表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ssjgDm);
		String ssjgMc = taxUnit.getMc();
		
		// 行业大类
		BigIndustryService bigIndustryService = new BigIndustryService();
		BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hydlDm);
		String hyMc = "";
		if (null == bigIndustry) {
			hyMc = "全部";
		} else {
			hyMc = bigIndustry.getMc();
		}
		
		// 经济类型
		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
		FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
		String qyzclxMc = "";
		if (null == firmRegType) {
			qyzclxMc = "全部";
		} else {
			qyzclxMc = firmRegType.getMc();
		}
		
		// 上年实缴税收款
		if ("".equals(qygm)) {
			qygm = "全部";
		}
		
		String condition = "管理机关： " + ssjgMc + "，"
						 + "报表期：" + reportPeriodStr + "，"
						 + "行业大类：" + hyMc + "，"
						 + "经济类型：" + qyzclxMc + "，"
						 + "上年实缴税款：" + qygm;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("纳税人编码");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("纳税人名称");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("管理机关");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("管理员");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("上年实缴税款");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第4行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 4;	// 从第4行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(i + 1);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getA1().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA2().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA3().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA4().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA5().trim());
			cell.setCellStyle(cellStyleAlignCenter);
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
		for (int i=0; i<6; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("重点税源监控企业明细表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
		
	}
	
	/**
	 * 显示企业基础信息查询表 输入条件
	 * @return
	 */
	public String showFirmInfoSearchTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_firm_info_search_table_condition_ui";
	}
	
	/**
	 * 企业基础信息查询表
	 * @return
	 */
	public String firmInfoSearchTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String nsrsbh = request.getParameter("nsrsbh");
		
		FirmInfoSearchTableParameter firmInfoSearchTableParameter = new FirmInfoSearchTableParameter();
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqZ = monthPeriod.substring(3);
		
		firmInfoSearchTableParameter.setNsrsbh(nsrsbh);
		firmInfoSearchTableParameter.setNd(nd);
		firmInfoSearchTableParameter.setSssqZ(sssqZ);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		FirmInfoSearchTable table = taxResourceSearchService.getFirmInfoSearchTable(firmInfoSearchTableParameter);
		
		request.setAttribute("table", table);
		request.setAttribute("firmInfoSearchTableParameter", firmInfoSearchTableParameter);
		
		return "firm_info_search_table";
	}
	
	/**
	 * 企业基础信息查询表 to Excel
	 * @return
	 */
	public String firmInfoSearchTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		FirmInfoSearchTableParameter firmInfoSearchTableParameter = new FirmInfoSearchTableParameter();
		
		String nsrsbh = request.getParameter("nsrsbh");
		String nd = request.getParameter("nd");
		String sssqZ = request.getParameter("sssqZ");
		
		firmInfoSearchTableParameter.setNsrsbh(nsrsbh);
		firmInfoSearchTableParameter.setNd(nd);
		firmInfoSearchTableParameter.setSssqZ(sssqZ);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		FirmInfoSearchTable table = taxResourceSearchService.getFirmInfoSearchTable(firmInfoSearchTableParameter);
		
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
		cellStyleFmt.setDataFormat(format.getFormat("0"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("企业基础信息查询表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);	// 空一行
		
		// 第2行
		row = sheet.createRow(2);					
		cell = row.createCell(0);
		cell.setCellValue("行列号");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("A");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("B");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("C");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("D");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue(1);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("纳税人名称");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA102());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("管理员姓名");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA110());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(0);
		cell.setCellValue(2);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("管理机关名称");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA106());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("管理员代码");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA105());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第5行
		row = sheet.createRow(5);					
		cell = row.createCell(0);
		cell.setCellValue(3);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("管理机关代码");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA105());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("被委托代征户标志");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA109());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第6行
		row = sheet.createRow(6);					
		cell = row.createCell(0);
		cell.setCellValue(5);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("代扣代缴标志");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA108());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("注册类型");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA111());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第7行
		row = sheet.createRow(7);					
		cell = row.createCell(0);
		cell.setCellValue(6);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("国有控股类型");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA112());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("产业");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA113());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第8行
		row = sheet.createRow(8);					
		cell = row.createCell(0);
		cell.setCellValue(7);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("行业大类");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA114());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("行业小类");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA115());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第9行
		row = sheet.createRow(9);					
		cell = row.createCell(0);
		cell.setCellValue(8);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("纳税人分类");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA116());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("核算形式");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA117());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第10行
		row = sheet.createRow(10);					
		cell = row.createCell(0);
		cell.setCellValue(9);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("公司类型");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA118());
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("生产经营方式");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue(table.getA119());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第11行
		row = sheet.createRow(11);					
		cell = row.createCell(0);
		cell.setCellValue(10);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("总机构组织机构代码");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA120());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				4 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<=4; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("企业基础信息查询表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
		
	}
	
	/**
	 * 显示企业经营信息表 输入条件
	 * @return
	 */
	public String showFirmRunInfoTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_firm_run_info_table_condition_ui";
	}
	
	/**
	 * 显示利润表 输入条件
	 * @return
	 */
	public String showInterestTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_interest_table_condition_ui";
	}
	
	/**
	 * 显示资产负债表 输入条件
	 * @return
	 */
	public String showAssetLiabilityTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_asset_liability_table_condition_ui";
	}
	
	/**
	 * 企业经营信息表
	 * @return
	 */
	public String firmRunInfoTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String nsrsbh = request.getParameter("nsrsbh");
		String ssjgDm = request.getParameter("ssjgDm");
		
		FirmRunInfoTableParameter firmRunInfoTableParameter = new FirmRunInfoTableParameter();
		String reportPeriodStr = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqZ = monthPeriod.substring(3);
		reportPeriodStr = nd + "-01" + "--" + nd + "-" + sssqZ; 
		
		firmRunInfoTableParameter.setNsrsbh(nsrsbh);
		firmRunInfoTableParameter.setNd(nd);
		firmRunInfoTableParameter.setSssqZ(sssqZ);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		FirmRunInfoTable table = taxResourceSearchService.getFirmRunInfoTable(firmRunInfoTableParameter);
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ssjgDm);
		
		request.setAttribute("table", table);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("ssjgDm", ssjgDm);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("taxUnit", taxUnit);
		request.setAttribute("firmRunInfoTableParameter", firmRunInfoTableParameter);
		
		return "firm_run_info_table";
	}
	
	/**
	 * 企业经营信息表 to Excel
	 * @return
	 */
	public String firmRunInfoTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		FirmRunInfoTableParameter firmRunInfoTableParameter = new FirmRunInfoTableParameter();
		
		String nsrsbh = request.getParameter("nsrsbh");
		String nd = request.getParameter("nd");
		String sssqZ = request.getParameter("sssqZ");
		String ssjgDm = request.getParameter("ssjgDm");
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		firmRunInfoTableParameter.setNsrsbh(nsrsbh);
		firmRunInfoTableParameter.setNd(nd);
		firmRunInfoTableParameter.setSssqZ(sssqZ);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		FirmRunInfoTable table = taxResourceSearchService.getFirmRunInfoTable(firmRunInfoTableParameter);
		
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
		cellStyleFmt.setDataFormat(format.getFormat("0"));
		
		// 2.3  输出表头
		// 第0行
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);			
		cell.setCellValue("企业经营信息表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ssjgDm);
		String ssjgMc = taxUnit.getMc();
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		String nsrmc = taxPayer.getNsrmc();
		
		String condition = "管理机关： " + ssjgMc + "，"
						 + "报表期：" + reportPeriodStr + "，"
						 + "纳税人编码：" + nsrsbh + "，"
						 + "纳税人名称：" + nsrmc;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);					
		cell = row.createCell(0);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("行列号");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("A");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("C");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(0);
		cell.setCellValue(1);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("主营产品累计产量（吨、千瓦时、件、平方米、立方米、小时）");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA401().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第5行
		row = sheet.createRow(5);					
		cell = row.createCell(0);
		cell.setCellValue(2);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("主营产品累计销量（吨、千瓦时、件、平方米、立方米、小时）");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA402().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第6行
		row = sheet.createRow(6);					
		cell = row.createCell(0);
		cell.setCellValue(3);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("主营产品平均价格（元）");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA403().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第7行
		row = sheet.createRow(7);					
		cell = row.createCell(0);
		cell.setCellValue(4);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("主营产品期末库存量（吨、千瓦时、件、平方米、立方米、小时）");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA404().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第8行
		row = sheet.createRow(8);					
		cell = row.createCell(0);
		cell.setCellValue(5);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("职工工资总额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA405().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第9行
		row = sheet.createRow(9);					
		cell = row.createCell(0);
		cell.setCellValue(6);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("职工人数");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA406().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第10行
		row = sheet.createRow(10);					
		cell = row.createCell(0);
		cell.setCellValue(7);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("目前经营状况");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA407().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第11行
		row = sheet.createRow(11);					
		cell = row.createCell(0);
		cell.setCellValue(8);
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("所得税征收机构");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue(table.getA408().trim());
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				2 //last column (0-based)
				));
		
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<=2; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("企业经营信息表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税源总量增减变动情况表条件界面
	 * @return
	 */
	public String showTaxResourceSumChangeTableConditionUI() {
		
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
		
		return "show_tax_resource_sum_change_table_condition_ui";
	}
	
	/**
	 * 税源总量增减变动过情况表
	 * @return
	 */
	public String taxResourceSumChangeTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjgDm = request.getParameter("swjgDm");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hydl = request.getParameter("hydl");
		String jedw = request.getParameter("jedw");
		String xmbz = request.getParameter("xmbz");
		String nsrsbh = request.getParameter("nsrsbh");
		String dszsbz = request.getParameter("dszsbz");
		
		TaxResourceSumChangeTableParameter taxResourceSumChangeTableParameter = new TaxResourceSumChangeTableParameter();
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqQ = monthPeriod.substring(0, 2);
		String sssqZ = monthPeriod.substring(3);
		
		taxResourceSumChangeTableParameter.setNd(nd);
		taxResourceSumChangeTableParameter.setSssqQ(sssqQ);
		taxResourceSumChangeTableParameter.setSssqZ(sssqZ);
		taxResourceSumChangeTableParameter.setNsrsbh(nsrsbh);
		taxResourceSumChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceSumChangeTableParameter.setJedw(jedw);
		taxResourceSumChangeTableParameter.setQyzclx(qyzclx);
		taxResourceSumChangeTableParameter.setHydl(hydl);
		taxResourceSumChangeTableParameter.setCy(cy);
		taxResourceSumChangeTableParameter.setXmbz(xmbz);
		taxResourceSumChangeTableParameter.setDszsbz(dszsbz);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceSumChangeTable> tableList = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxResourceSumChangeTableParameter", taxResourceSumChangeTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.SYZLZJ,taxResourceSumChangeTableParameter);
		
		return "tax_resource_sum_change_table";
	}
	
	/**
	 * 税源总量增减变动情况表 to Excel
	 * @return
	 */
	public String taxResourceSumChangeTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxResourceSumChangeTableParameter taxResourceSumChangeTableParameter = new TaxResourceSumChangeTableParameter();
		
		String swjgDm = request.getParameter("swjgDm");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hydl = request.getParameter("hydl");
		String jedw = request.getParameter("jedw");
		String xmbz = request.getParameter("xmbz");
		String nsrsbh = request.getParameter("nsrsbh");
		String nd = request.getParameter("nd");
		String sssqQ = request.getParameter("sssqQ");
		String sssqZ = request.getParameter("sssqZ");
		
		taxResourceSumChangeTableParameter.setNd(nd);
		taxResourceSumChangeTableParameter.setSssqQ(sssqQ);
		taxResourceSumChangeTableParameter.setSssqZ(sssqZ);
		taxResourceSumChangeTableParameter.setNsrsbh(nsrsbh);
		taxResourceSumChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceSumChangeTableParameter.setJedw(jedw);
		taxResourceSumChangeTableParameter.setQyzclx(qyzclx);
		taxResourceSumChangeTableParameter.setHydl(hydl);
		taxResourceSumChangeTableParameter.setCy(cy);
		taxResourceSumChangeTableParameter.setXmbz(xmbz);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceSumChangeTable> tableList = taxResourceSearchService.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter);
		
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
		
		cell.setCellValue("税源总量增减变动情况表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("显示项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("营业收入");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("利润总额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("产品产量");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("产品销量");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(13);
		cell.setCellValue("职工工资总额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		
		cell = row.createCell(1);
		cell.setCellValue("本期完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("同比增减%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("本期完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("同比增减%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("本期完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("同比增减%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("本期完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("同比增减%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(12);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(13);
		cell.setCellValue("本期完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(14);
		cell.setCellValue("同比增减%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(15);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getA().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getB1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getB2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getB3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getC1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getC2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getC3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getD1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getD2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getD3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(10);
			cell.setCellValue(tableList.get(i).getE1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(11);
			cell.setCellValue(tableList.get(i).getE2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(12);
			cell.setCellValue(tableList.get(i).getE3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(13);
			cell.setCellValue(tableList.get(i).getF1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(14);
			cell.setCellValue(tableList.get(i).getF2().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(15);
			cell.setCellValue(tableList.get(i).getF3().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				15 //last column (0-based)
				));
		
		// 显示项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				0 //last column (0-based)
				));
		
		// 营业收入
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				1, //first column (0-based)
				3 //last column (0-based)
				));
		
		// 利润总额
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				4, //first column (0-based)
				6 //last column (0-based)
				));
		
		// 产品产量
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				7, //first column (0-based)
				9 //last column (0-based)
				));
		
		// 产品销量
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				10, //first column (0-based)
				12 //last column (0-based)
				));
		
		// 职工工资总额
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				13, //first column (0-based)
				15 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=15; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税源总量增减变动情况表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示税源质量变动情况表 条件界面
	 * @return
	 */
	public String showTaxResourceQualityChangeTableConditionUI() {
		
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
		
		return "show_tax_resource_quality_change_table_condition_ui";
	}
	
	/**
	 * 税源质量变动情况表
	 * @return
	 */
	public String taxResourceQualityChangeTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String swjgDm = request.getParameter("swjgDm");
		String nsrsbh = request.getParameter("nsrsbh");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hydl = request.getParameter("hydl");
		String xmbz = request.getParameter("xmbz");
		
		TaxResourceQualityChangeTableParameter taxResourceQualityChangeTableParameter = new TaxResourceQualityChangeTableParameter();
			
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqQ = monthPeriod.substring(0, 2);
		String sssqZ = monthPeriod.substring(3);
		
		taxResourceQualityChangeTableParameter.setNd(nd);
		taxResourceQualityChangeTableParameter.setSssqQ(sssqQ);
		taxResourceQualityChangeTableParameter.setSssqZ(sssqZ);
		taxResourceQualityChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceQualityChangeTableParameter.setQyzclx(qyzclx);
		taxResourceQualityChangeTableParameter.setHydl(hydl);
		taxResourceQualityChangeTableParameter.setCy(cy);
		taxResourceQualityChangeTableParameter.setNsrsbh(nsrsbh);
		taxResourceQualityChangeTableParameter.setXmbz(xmbz);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceQualityChangeTable> tableList = taxResourceSearchService.getTaxResourceQualityChangeTable(taxResourceQualityChangeTableParameter);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("taxResourceQualityChangeTableParameter", taxResourceQualityChangeTableParameter);
		request.getSession().setAttribute(SessionAttributeKey.SYZLBD,taxResourceQualityChangeTableParameter);
		return "tax_resource_quality_change_table";
	}
	
	/**
	 * 税源质量变动情况表 to Excel
	 * @return
	 */
	public String taxResourceQualityChangeTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxResourceQualityChangeTableParameter taxResourceQualityChangeTableParameter = new TaxResourceQualityChangeTableParameter();
		
		String swjgDm = request.getParameter("swjgDm");
		String qyzclx = request.getParameter("qyzclx");
		String cy = request.getParameter("cy");
		String hydl = request.getParameter("hydl");
		String xmbz = request.getParameter("xmbz");
		String nsrsbh = request.getParameter("nsrsbh");
		String nd = request.getParameter("nd");
		String sssqQ = request.getParameter("sssqQ");
		String sssqZ = request.getParameter("sssqZ");
		
		taxResourceQualityChangeTableParameter.setNd(nd);
		taxResourceQualityChangeTableParameter.setSssqQ(sssqQ);
		taxResourceQualityChangeTableParameter.setSssqZ(sssqZ);
		taxResourceQualityChangeTableParameter.setSwjgDm(swjgDm);
		taxResourceQualityChangeTableParameter.setQyzclx(qyzclx);
		taxResourceQualityChangeTableParameter.setHydl(hydl);
		taxResourceQualityChangeTableParameter.setCy(cy);
		taxResourceQualityChangeTableParameter.setNsrsbh(nsrsbh);
		taxResourceQualityChangeTableParameter.setXmbz(xmbz);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<TaxResourceQualityChangeTable> tableList = taxResourceSearchService.getTaxResourceQualityChangeTable(taxResourceQualityChangeTableParameter);
		
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
		
		cell.setCellValue("税源质量变动情况表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("显示项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("资产负债率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("净资产收益率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("主营营业收入利润率");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("收入成本弹性");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(13);
		cell.setCellValue("收入费用弹性");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		
		cell = row.createCell(1);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("同比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("环比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("同比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("环比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("本期");;
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("同比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("环比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("同比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(12);
		cell.setCellValue("环比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(13);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(14);
		cell.setCellValue("同比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(15);
		cell.setCellValue("环比");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getA().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(1);
			if ("".equals(tableList.get(i).getB1().trim()) || null == tableList.get(i).getB1()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getB1().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(2);
			if ("".equals(tableList.get(i).getB2().trim()) || null == tableList.get(i).getB2()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getB2().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			if ("".equals(tableList.get(i).getB3().trim()) || null == tableList.get(i).getB3()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getB3().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			if ("".equals(tableList.get(i).getC1().trim()) || null == tableList.get(i).getC1()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getC1().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			if ("".equals(tableList.get(i).getC2().trim()) || null == tableList.get(i).getC2()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getC2().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			if ("".equals(tableList.get(i).getC3().trim()) || null == tableList.get(i).getC3()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getC3().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			if ("".equals(tableList.get(i).getD1().trim()) || null == tableList.get(i).getD1()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getD1().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			if ("".equals(tableList.get(i).getD2().trim()) || null == tableList.get(i).getD2()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getD2().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			if ("".equals(tableList.get(i).getD3().trim()) || null == tableList.get(i).getD3()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getD3().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(10);
			if ("".equals(tableList.get(i).getE1().trim()) || null == tableList.get(i).getE1()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getE1().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(11);
			if ("".equals(tableList.get(i).getE2().trim()) || null == tableList.get(i).getE2()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getE2().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(12);
			if ("".equals(tableList.get(i).getE3().trim()) || null == tableList.get(i).getE3()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getE3().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(13);
			if ("".equals(tableList.get(i).getF1().trim()) || null == tableList.get(i).getF1()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getF1().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(14);
			if ("".equals(tableList.get(i).getF2().trim()) || null == tableList.get(i).getF2()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getF2().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(15);
			if ("".equals(tableList.get(i).getF3().trim()) || null == tableList.get(i).getF3()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getF3().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				15 //last column (0-based)
				));
		
		// 显示项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				0 //last column (0-based)
				));
		
		// 资产负债率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				1, //first column (0-based)
				3 //last column (0-based)
				));
		
		// 净资产收益率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				4, //first column (0-based)
				6 //last column (0-based)
				));
		
		// 主营营业收入利润率
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				7, //first column (0-based)
				9 //last column (0-based)
				));
		
		// 收入成本弹性
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				10, //first column (0-based)
				12 //last column (0-based)
				));
		
		// 收入费用弹性
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				13, //first column (0-based)
				15 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=15; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税源质量变动情况表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 利润表
	 * @return
	 */
	public String interestTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String nsrsbh = request.getParameter("nsrsbh");
		
		InterestTableParameter interestTableParameter = new InterestTableParameter();
		String reportPeriodStr = "";
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		String cwzbgs = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-01" + "--" + nd + "-" + sssqZ;
		
		interestTableParameter.setNsrsbh(nsrsbh);
		interestTableParameter.setNd(nd);
		interestTableParameter.setSssqZ(sssqZ);
		
		cwzbgs = taxResourceSearchService.getInterestTableCwzbgs(interestTableParameter);
			
		
		List<InterestTable> tableList = taxResourceSearchService.getInterestTable(interestTableParameter);
		
		cwzbgs = cwzbgs.trim();
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("cwzbgs", cwzbgs);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("interestTableParameter", interestTableParameter);
		
		return "interest_table";
	}
	
	/**
	 * 利润表 to Excel
	 * @return
	 */
	public String interestTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		InterestTableParameter interestTableParameter = new InterestTableParameter();
		
		String nsrsbh = request.getParameter("nsrsbh");
		String nd = request.getParameter("nd");
		String sssqZ = request.getParameter("sssqZ");
		String cwzbgs = request.getParameter("cwzbgs");
		try {
			cwzbgs = URLDecoder.decode(cwzbgs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		interestTableParameter.setNsrsbh(nsrsbh);
		interestTableParameter.setNd(nd);
		interestTableParameter.setSssqZ(sssqZ);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<InterestTable> tableList = taxResourceSearchService.getInterestTable(interestTableParameter);
		
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
		
		String tableTitle = "";		// 报表名称
		String tableType = "";		// 报表类型
		if ("新版".equals(cwzbgs)) {
			tableTitle = "利润表（新）";
			tableType = "新版利润表";
		} else {
			tableTitle = "利润表（旧）";
			tableType = "旧版利润表";
		}
		
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue(tableTitle);
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		String nsrmc = taxPayer.getNsrmc();
		
		
		String condition = "纳税人编码： " + nsrsbh + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "报表所属期间： " + reportPeriodStr + "，"
						 + "报表类型： " + tableType;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("行列号");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("A");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("B");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("C");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("D");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		
		cell = row.createCell(1);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("行次");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		if ("新版".equals(cwzbgs)) {
			cell.setCellValue("本期金额");
		} else {
			cell.setCellValue("本月数");
		}
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		if ("新版".equals(cwzbgs)) {
			cell.setCellValue("上期金额");
		} else {
			cell.setCellValue("本年累计数");
		}
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getXh());
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getLr().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getLr1().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(3);
			if ("".equals(tableList.get(i).getBq().trim()) || null == tableList.get(i).getBq().trim()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getBq().trim()));
			}
			
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			if ("".equals(tableList.get(i).getLj().trim()) || null == tableList.get(i).getLj().trim()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getLj().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
		}
		
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
		workbook2InputStream(workbook, new String(tableTitle.getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 资产负债表
	 * @return
	 */
	public String assetLiabilityTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String nsrsbh = request.getParameter("nsrsbh");
		
		AssetLiabilityTableParameter assetLiabilityTableParameter = new AssetLiabilityTableParameter();
		String reportPeriodStr = "";
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		String cwzbgs = "";
		
		String nd = request.getParameter("nd");
		String monthPeriod = request.getParameter("monthPeriod");
		String sssqZ = monthPeriod.substring(3);
		
		reportPeriodStr = nd + "-01" + "--" + nd + "-" + sssqZ;
		
		assetLiabilityTableParameter.setNsrsbh(nsrsbh);
		assetLiabilityTableParameter.setNd(nd);
		assetLiabilityTableParameter.setSssqZ(sssqZ);
		
		cwzbgs = taxResourceSearchService.getAssetLiabilityTableCwzbgs(assetLiabilityTableParameter);
		
		List<AssetLiabilityTable> tableList = taxResourceSearchService.getAssetLiabilityTable(assetLiabilityTableParameter);
		
		cwzbgs = cwzbgs.trim();
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("cwzbgs", cwzbgs);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("assetLiabilityTableParameter", assetLiabilityTableParameter);
		
		return "asset_liability_table";
	}
	
	/**
	 * 资产负债表 to Excel
	 * @return
	 */
	public String assetLiabilityTableToExcel() throws IOException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		AssetLiabilityTableParameter assetLiabilityTableParameter = new AssetLiabilityTableParameter();
		
		String nsrsbh = request.getParameter("nsrsbh");
		String nd = request.getParameter("nd");
		String sssqZ = request.getParameter("sssqZ");
		String cwzbgs = request.getParameter("cwzbgs");
		try {
			cwzbgs = URLDecoder.decode(cwzbgs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		assetLiabilityTableParameter.setNsrsbh(nsrsbh);
		assetLiabilityTableParameter.setNd(nd);
		assetLiabilityTableParameter.setSssqZ(sssqZ);
		
		TaxResourceSearchService taxResourceSearchService = new TaxResourceSearchService();
		List<AssetLiabilityTable> tableList = taxResourceSearchService.getAssetLiabilityTable(assetLiabilityTableParameter);
		
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
		String tableTitle = "";		// 报表名称
		String tableType = "";		// 报表类型
		if ("新版".equals(cwzbgs)) {
			tableTitle = "资产负债表（新）";
			tableType = "新版资产负债表";
		} else {
			tableTitle = "资产负债表（旧）";
			tableType = "旧版资产负债表";
		}
		
		HSSFRow row = sheet.createRow(0);			
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue(tableTitle);
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		String nsrmc = taxPayer.getNsrmc();
		
		String condition = "纳税人编码： " + nsrsbh + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "报表所属期间： " + reportPeriodStr + "，"
						 + "报表类型： " + tableType;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("行列号");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("A");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("B");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("C");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("D");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("E");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("F");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("G");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("H");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		
		cell = row.createCell(1);
		cell.setCellValue("资产");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("行次");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("期末余额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("年初余额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("负债和所有者权益（或股东权益）");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("行次");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		if ("新版".equals(cwzbgs)) {
			cell.setCellValue("期末余额");
		} else {
			cell.setCellValue("年初数");
		}
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		if ("新版".equals(cwzbgs)) {
			cell.setCellValue("年初余额");
		} else {
			cell.setCellValue("期末数");
		}
		
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(tableList.get(i).getXh());
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getZc().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getZc1().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(3);
			if ("".equals(tableList.get(i).getZcnc().trim()) || null == tableList.get(i).getZcnc().trim()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getZcnc().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			if ("".equals(tableList.get(i).getZcqm().trim()) || null == tableList.get(i).getZcqm().trim()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getZcqm().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getFz().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getFz1().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(7);
			if ("".equals(tableList.get(i).getFznc().trim()) || null == tableList.get(i).getFznc().trim()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getFznc().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			if ("".equals(tableList.get(i).getFzqm().trim()) || null == tableList.get(i).getFzqm().trim()) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(Double.valueOf(tableList.get(i).getFzqm().trim()));
			}
			cell.setCellStyle(cellStyleFmt);
			
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				8 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				8 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<=8; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String(tableTitle.getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 将workbook写入InputStream
	 * @param workbook
	 * @param fileName
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

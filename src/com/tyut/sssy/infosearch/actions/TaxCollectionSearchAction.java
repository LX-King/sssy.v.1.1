package com.tyut.sssy.infosearch.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.tyut.sssy.base.domain.DisplayItem;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.DisplayItemService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.TaxPayerService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.infosearch.service.TaxCollectionSearchService;
import com.tyut.sssy.taxcollection.domain.FirmPayTaxChangeTable;
import com.tyut.sssy.taxcollection.domain.FirmPayTaxChangeTableParameter;
import com.tyut.sssy.taxcollection.domain.FirmTaxAccountTable;
import com.tyut.sssy.taxcollection.domain.FirmTaxAccountTableParameter;
import com.tyut.sssy.taxcollection.domain.OwnPayTaxDetailTable;
import com.tyut.sssy.taxcollection.domain.OwnPayTaxDetailTableParameter;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTableParameter;
import com.tyut.sssy.taxcollection.domain.TaxCollectionRealTimeSearchTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionStructureChangeTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionStructureChangeTableParameter;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxBalanceTable;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxBalanceTableParameter;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTable;
import com.tyut.sssy.taxcollection.domain.ToResolveTaxChangeTableParameter;
import com.tyut.sssy.utils.SessionAttributeKey;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxCollectionSearch  
 * 类描述：税收收入查询action  
 * 创建人：梁斌  
 * 创建时间：2012-5-8 下午09:42:29  
 * 修改人：梁斌  
 * 修改时间：2012-5-8 下午09:42:29  
 * 修改备注：  
 * @version 
 *
 */
public class TaxCollectionSearchAction {

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
	 * 显示税收收入完成情况表条件界面
	 * @return
	 */
	public String showTaxCollectionFinishTableConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		// 显示项目列表
		DisplayItemService displayItemService = new DisplayItemService();
		List<DisplayItem> displayItemList = displayItemService.getDisplayItemList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("displayItemList", displayItemList);
		
		return "show_tax_collection_finish_table_condition_ui";
	}
	
	/**
	 * 税收收入完成情况表
	 * @return
	 */
	public String taxCollectionFinishTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		TaxCollectionFinishTableParameter taxCollectionFinishTableParameter = new TaxCollectionFinishTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjgDm = request.getParameter("swjgDm");
			String reportPeriod = request.getParameter("reportPeriod");
			String dataType = request.getParameter("dataType");
			String moneyUnit = request.getParameter("moneyUnit");
			String displayItem = request.getParameter("displayItem");
			
			if (reportPeriod.equals("day")) {
				// 日
				String daySpanDay = request.getParameter("daySpanDay");
				
				String nd = daySpanDay.substring(0, 4);
				String yf = daySpanDay.substring(5, 7);
				String sqZ = daySpanDay.substring(8);
				String pd = "日";
				
				taxCollectionFinishTableParameter.setSwjg(swjgDm);
				taxCollectionFinishTableParameter.setSjlx(dataType);
				taxCollectionFinishTableParameter.setNd(nd);
				taxCollectionFinishTableParameter.setYf(yf);
				taxCollectionFinishTableParameter.setSqQ("");
				taxCollectionFinishTableParameter.setSqZ(sqZ);
				taxCollectionFinishTableParameter.setPd(pd);
				taxCollectionFinishTableParameter.setFl(displayItem);
				taxCollectionFinishTableParameter.setMoneyUnit(moneyUnit);
				taxCollectionFinishTableParameter.setType(type);
				taxCollectionFinishTableParameter.setOrder(order);
				reportPeriodStr = nd+ "-" + yf + "-" + sqZ;
				
			} else if (reportPeriod.equals("month")) {
				// 月
				String nd = request.getParameter("monthSpanYear");
				String yf = request.getParameter("monthSpanMonth");	// 2
				
				if (!yf.equals("10") && !yf.equals("11") && !yf.equals("12")) {
					yf = "0" + yf;
				}
				
				String pd = "月";
				
				taxCollectionFinishTableParameter.setSwjg(swjgDm);
				taxCollectionFinishTableParameter.setSjlx(dataType);
				taxCollectionFinishTableParameter.setNd(nd);
				taxCollectionFinishTableParameter.setSqQ("");
				taxCollectionFinishTableParameter.setSqZ("");
				taxCollectionFinishTableParameter.setYf(yf);
				taxCollectionFinishTableParameter.setPd(pd);
				taxCollectionFinishTableParameter.setFl(displayItem);
				taxCollectionFinishTableParameter.setMoneyUnit(moneyUnit);
				taxCollectionFinishTableParameter.setType(type);
				taxCollectionFinishTableParameter.setOrder(order);
				
				reportPeriodStr = nd + "-" + yf;
				
			} else if (reportPeriod.equals("xun")) {
				// 旬
				
				String nd = request.getParameter("xunSpanYear");
				String yf = request.getParameter("xunSpanMonth");
				if (!yf.equals("10") && !yf.equals("11") && !yf.equals("12")) {
					yf = "0" + yf;
				}
				String pd = request.getParameter("xunSpanXun");
				
				taxCollectionFinishTableParameter.setSwjg(swjgDm);
				taxCollectionFinishTableParameter.setSjlx(dataType);
				taxCollectionFinishTableParameter.setNd(nd);
				taxCollectionFinishTableParameter.setSqQ("");
				taxCollectionFinishTableParameter.setSqZ("");
				taxCollectionFinishTableParameter.setYf(yf);
				taxCollectionFinishTableParameter.setPd(pd);
				taxCollectionFinishTableParameter.setFl(displayItem);
				taxCollectionFinishTableParameter.setMoneyUnit(moneyUnit);
				taxCollectionFinishTableParameter.setType(type);
				taxCollectionFinishTableParameter.setOrder(order);
				
				reportPeriodStr = nd + "-" + yf + "-" + pd;
			}
			
		} else {
			// 排序
			String swjg = request.getParameter("swjg");
			String sjlx = request.getParameter("sjlx");
			String nd = request.getParameter("nd");
			String sqQ = request.getParameter("sqQ");
			String sqZ = request.getParameter("sqZ");
			String yf = request.getParameter("yf");
			String pd = request.getParameter("pd");
			try {
				pd = URLDecoder.decode(pd, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String fl = request.getParameter("fl");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			String moneyUnit = request.getParameter("moneyUnit");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			taxCollectionFinishTableParameter.setSwjg(swjg);
			taxCollectionFinishTableParameter.setSjlx(sjlx);
			taxCollectionFinishTableParameter.setNd(nd);
			taxCollectionFinishTableParameter.setSqQ(sqQ);
			taxCollectionFinishTableParameter.setSqZ(sqZ);
			taxCollectionFinishTableParameter.setYf(yf);
			taxCollectionFinishTableParameter.setPd(pd);
			taxCollectionFinishTableParameter.setFl(fl);
			taxCollectionFinishTableParameter.setMoneyUnit(moneyUnit);
			taxCollectionFinishTableParameter.setType(type);
			taxCollectionFinishTableParameter.setOrder(order);
			
		}
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<TaxCollectionFinishTable> tableList = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter);
		
		//税务机关
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(taxCollectionFinishTableParameter.getSwjg());
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		// 显示项目列表
		DisplayItemService displayItemService = new DisplayItemService();
		List<DisplayItem> displayItemList = displayItemService.getDisplayItemList();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("displayItemList", displayItemList);
		request.setAttribute("swjgMc", taxUnit.getMc());
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxCollectionFinishTableParameter", taxCollectionFinishTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.SSSRWC,taxCollectionFinishTableParameter);
		request.setAttribute("tableList", tableList);
		return "tax_collection_finish_table";
	}
	
	/**
	 * 税收收入完成情况表 to Excel
	 * @return
	 */
	public String taxCollectionFinishTableToExcel() throws IOException {
		
		// 1. 查询列表
 		HttpServletRequest request = ServletActionContext.getRequest();
		
		TaxCollectionFinishTableParameter taxCollectionFinishTableParameter = new TaxCollectionFinishTableParameter();
		String reportPeriodStr = "";
		String type = "none";
		String order = "normal";
		
		String swjg = request.getParameter("swjg");
		String sjlx = request.getParameter("sjlx");
		String nd = request.getParameter("nd");
		String sqQ = request.getParameter("sqQ");
		String sqZ = request.getParameter("sqZ");
		String yf = request.getParameter("yf");
		String pd = request.getParameter("pd");
		try {
			pd = URLDecoder.decode(pd, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String fl = request.getParameter("fl");
		String moneyUnit = request.getParameter("moneyUnit");
		reportPeriodStr = request.getParameter("reportPeriodStr");
		try {
			reportPeriodStr = URLDecoder.decode(reportPeriodStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		taxCollectionFinishTableParameter.setSwjg(swjg);
		taxCollectionFinishTableParameter.setSjlx(sjlx);
		taxCollectionFinishTableParameter.setNd(nd);
		taxCollectionFinishTableParameter.setSqQ(sqQ);
		taxCollectionFinishTableParameter.setSqZ(sqZ);
		taxCollectionFinishTableParameter.setYf(yf);
		taxCollectionFinishTableParameter.setPd(pd);
		taxCollectionFinishTableParameter.setFl(fl);
		taxCollectionFinishTableParameter.setMoneyUnit(moneyUnit);
		taxCollectionFinishTableParameter.setType(type);
		taxCollectionFinishTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<TaxCollectionFinishTable> tableList = taxCollectionSearchService.getFinishTable(taxCollectionFinishTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
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
		cell.setCellValue("税收收入完成情况表 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 数据类型名称
		String sjlxMc = "";
		if ("R".equals(sjlx)) {
			sjlxMc = "入库税收";
		} else if("S".equals(sjlx)) {
			sjlxMc = "实缴税收";
		}
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "数据类型： " + sjlxMc + "，"
						 + "金额单位： " + moneyUnitMc;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("年度计划");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("本月完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("累计完成");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("占计划%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(3);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("同期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("本期增长%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("累计");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("同期累计");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("累计增长%");
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
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getA8().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				9 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				9 //last column (0-based)
				));
		// 项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 年度计划
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				2, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 本月完成
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				3, //first column (0-based)
				5 //last column (0-based)
				));
		
		// 累计完成
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				6, //first column (0-based)
				8 //last column (0-based)
				));
		
		// 占计划
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				9, //first column (0-based)
				9 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<10; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收收入完成情况表".getBytes("GB2312"), "ISO-8859-1"));
		
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

	/**
	 * 显示税收收入结构变化表条件界面
	 * @return
	 */
	public String showTaxCollectionStructureChangeTableConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		// 显示项目列表
		DisplayItemService displayItemService = new DisplayItemService();
		List<DisplayItem> displayItemList = displayItemService.getDisplayItemList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("displayItemList", displayItemList);
		
		return "show_tax_collection_structure_change_table_condition_ui";
	}
	
	/**
	 * 税收收入结构变化表
	 * @return
	 */
	public String taxCollectionStructureChangeTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		
		TaxCollectionStructureChangeTableParameter taxCollectionStructureChangeTableParameter = new TaxCollectionStructureChangeTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjgDm = request.getParameter("swjgDm");
			String dataType = request.getParameter("dataType");
			String moneyUnit = request.getParameter("moneyUnit");
			String displayItem = request.getParameter("displayItem");
			
			String nd = request.getParameter("nd");
			String pd = "月";
			String yf = request.getParameter("yf");
			if (!yf.equals("10") && !yf.equals("11") && !yf.equals("12")) {
				yf = "0" + yf;
			}
			
			reportPeriodStr = nd + "-" + yf;
			
			taxCollectionStructureChangeTableParameter.setSwjg(swjgDm);
			taxCollectionStructureChangeTableParameter.setSjlx(dataType);
			taxCollectionStructureChangeTableParameter.setNd(nd);
			taxCollectionStructureChangeTableParameter.setPd(pd);
			taxCollectionStructureChangeTableParameter.setFl(displayItem);
			taxCollectionStructureChangeTableParameter.setYf(yf);
			taxCollectionStructureChangeTableParameter.setMoneyUnit(moneyUnit);
			taxCollectionStructureChangeTableParameter.setType(type);
			taxCollectionStructureChangeTableParameter.setOrder(order);
		} else {
			String swjg = request.getParameter("swjg");
			String sjlx = request.getParameter("sjlx");
			String nd = request.getParameter("nd");
			String pd = request.getParameter("pd");
			try {
				pd = URLDecoder.decode(pd, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String fl = request.getParameter("fl");
			String yf = request.getParameter("yf");
			String moneyUnit = request.getParameter("moneyUnit");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			taxCollectionStructureChangeTableParameter.setSwjg(swjg);
			taxCollectionStructureChangeTableParameter.setSjlx(sjlx);
			taxCollectionStructureChangeTableParameter.setNd(nd);
			taxCollectionStructureChangeTableParameter.setPd(pd);
			taxCollectionStructureChangeTableParameter.setFl(fl);
			taxCollectionStructureChangeTableParameter.setYf(yf);
			taxCollectionStructureChangeTableParameter.setMoneyUnit(moneyUnit);
			taxCollectionStructureChangeTableParameter.setType(type);
			taxCollectionStructureChangeTableParameter.setOrder(order);
		}
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<TaxCollectionStructureChangeTable> tableList = taxCollectionSearchService.getStructureChangeTable(taxCollectionStructureChangeTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(taxCollectionStructureChangeTableParameter.getSwjg());
		
		request.setAttribute("swjgMc", taxUnit.getMc());
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("taxCollectionStructureChangeTableParameter", taxCollectionStructureChangeTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.SSSRJGBH, taxCollectionStructureChangeTableParameter);
		request.setAttribute("tableList", tableList);
		
		return "tax_collection_structure_change_table";
	}
	
	/**
	 * 税收收入结构变化表 to Excel
	 * @return
	 */
	public String taxCollectionStructureChangeTableToExcel() throws IOException{
		
		// 1. 查询列表
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxCollectionStructureChangeTableParameter taxCollectionStructureChangeTableParameter = new TaxCollectionStructureChangeTableParameter();
		
		String swjg = request.getParameter("swjg");
		String sjlx = request.getParameter("sjlx");
		String nd = request.getParameter("nd");
		String pd = request.getParameter("pd");
		try {
			pd = URLDecoder.decode(pd, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String fl = request.getParameter("fl");
		String yf = request.getParameter("yf");
		String moneyUnit = request.getParameter("moneyUnit");
		String type = "none";
		String order = "normal";
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		taxCollectionStructureChangeTableParameter.setSwjg(swjg);
		taxCollectionStructureChangeTableParameter.setSjlx(sjlx);
		taxCollectionStructureChangeTableParameter.setNd(nd);
		taxCollectionStructureChangeTableParameter.setPd(pd);
		taxCollectionStructureChangeTableParameter.setFl(fl);
		taxCollectionStructureChangeTableParameter.setYf(yf);
		taxCollectionStructureChangeTableParameter.setMoneyUnit(moneyUnit);
		taxCollectionStructureChangeTableParameter.setType(type);
		taxCollectionStructureChangeTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<TaxCollectionStructureChangeTable> tableList = taxCollectionSearchService.getStructureChangeTable(taxCollectionStructureChangeTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
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
		cell.setCellValue("税收收入结构变化表 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 数据类型名称
		String sjlxMc = "";
		if ("R".equals(sjlx)) {
			sjlxMc = "入库税收";
		} else if("S".equals(sjlx)) {
			sjlxMc = "实缴税收";
		}
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "数据类型： " + sjlxMc + "，"
						 + "金额单位： " + moneyUnitMc;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("本期累计");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("占总量比重% ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("同期累计");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("同期占总量比重%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("比重变动率% ");
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
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				6 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				6 //last column (0-based)
				));
		// 项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<7; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收收入结构变化表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示待解税金余额表条件
	 * @return
	 */
	public String showToResolveTaxBalanceTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_to_resolve_tax_balance_table_condition_ui";
	}
	
	/**
	 * 待解税金余额表
	 * @return
	 */
	public String toResolveTaxBalanceTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		ToResolveTaxBalanceTableParameter toResolveBalanceTableParameter = new ToResolveTaxBalanceTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjgDm = request.getParameter("swjgDm");
			String moneyUnit = request.getParameter("moneyUnit");
			String nsrsbh = request.getParameter("nsrsbh");
			String xsxm = request.getParameter("xsxm");
				
			String nd = request.getParameter("nd");
			String yf = request.getParameter("yf");
			
			if (!yf.equals("10") && !yf.equals("11") && !yf.equals("12")) {
				yf = "0" + yf;
			}
			String sqZ = yf;
			
			toResolveBalanceTableParameter.setSwjgDm(swjgDm);
			toResolveBalanceTableParameter.setXsxm(xsxm);
			toResolveBalanceTableParameter.setNd(nd);
			toResolveBalanceTableParameter.setSqZ(sqZ);
			toResolveBalanceTableParameter.setNsrsbh(nsrsbh);
			toResolveBalanceTableParameter.setMoneyUnit(moneyUnit);
			toResolveBalanceTableParameter.setType(type);
			toResolveBalanceTableParameter.setOrder(order);
			
			reportPeriodStr = nd + "-" + sqZ;
		} else {
			// 排序
			String swjgDm = request.getParameter("swjgDm");
			String xsxm = request.getParameter("xsxm");
			String nd = request.getParameter("nd");
			String sqZ = request.getParameter("sqZ");
			String nsrsbh = request.getParameter("nsrsbh");
			String moneyUnit = request.getParameter("moneyUnit");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			toResolveBalanceTableParameter.setSwjgDm(swjgDm);
			toResolveBalanceTableParameter.setXsxm(xsxm);
			toResolveBalanceTableParameter.setNd(nd);
			toResolveBalanceTableParameter.setSqZ(sqZ);
			toResolveBalanceTableParameter.setNsrsbh(nsrsbh);
			toResolveBalanceTableParameter.setMoneyUnit(moneyUnit);
			toResolveBalanceTableParameter.setType(type);
			toResolveBalanceTableParameter.setOrder(order);
		}
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<ToResolveTaxBalanceTable> tableList = taxCollectionSearchService.getToResolveBalanceTable(toResolveBalanceTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(toResolveBalanceTableParameter.getSwjgDm());
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(toResolveBalanceTableParameter.getNsrsbh());
		
		request.setAttribute("swjgMc", taxUnit.getMc());
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("moneyUnit", toResolveBalanceTableParameter.getMoneyUnit());
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("toResolveBalanceTableParameter", toResolveBalanceTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.DJSJYE, toResolveBalanceTableParameter);
		request.setAttribute("tableList", tableList);
		
		return "to_resolve_tax_balance_table";
	}
	
	/**
	 * 待解税金余额表 to Excel
	 * @return
	 */
	public String toResolveTaxBalanceTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		ToResolveTaxBalanceTableParameter toResolveBalanceTableParameter = new ToResolveTaxBalanceTableParameter();
		
		String swjgDm = request.getParameter("swjgDm");
		String xsxm = request.getParameter("xsxm");
		String nd = request.getParameter("nd");
		String sqZ = request.getParameter("sqZ");
		String nsrsbh = request.getParameter("nsrsbh");
		String moneyUnit = request.getParameter("moneyUnit");
		String type = "none";
		String order = "normal";
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		toResolveBalanceTableParameter.setSwjgDm(swjgDm);
		toResolveBalanceTableParameter.setXsxm(xsxm);
		toResolveBalanceTableParameter.setNd(nd);
		toResolveBalanceTableParameter.setSqZ(sqZ);
		toResolveBalanceTableParameter.setNsrsbh(nsrsbh);
		toResolveBalanceTableParameter.setMoneyUnit(moneyUnit);
		toResolveBalanceTableParameter.setType(type);
		toResolveBalanceTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<ToResolveTaxBalanceTable> tableList = taxCollectionSearchService.getToResolveBalanceTable(toResolveBalanceTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
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
		cell.setCellValue("待解税金余额表  ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjgDm).getMc();
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		String nsrmc = "";
		
		if (null == taxPayer) {
			nsrmc = "全部";
			nsrsbh = "";
		} else {
			nsrmc = taxPayer.getNsrmc();
			nsrsbh = taxPayer.getNsrsbh();
		}
		
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "金额单位： " + moneyUnitMc + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "纳税人编码： " + nsrsbh;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目名称");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("营业税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("企业所得税 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("个人所得税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("资源税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("城市维护建设税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("房产税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("印花税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("土地使用税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("土地增值税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("车船税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(12);
		cell.setCellValue("烟叶税");
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
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getA8().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(10);
			cell.setCellValue(tableList.get(i).getA9().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(11);
			cell.setCellValue(tableList.get(i).getA10().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(12);
			cell.setCellValue(tableList.get(i).getA11().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				12 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				12 //last column (0-based)
				));
		// 项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<13; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("待解税金余额表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示待解税金变动情况表条件
	 * @return
	 */
	public String showToResolveTaxChangeTableConditionUI() {
		
		// 管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_to_resolve_tax_change_table_condition_ui";
	}
	
	/**
	 * 待解税金变动情况表
	 * @return
	 */
	public String toResolveTaxChangeTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		ToResolveTaxChangeTableParameter toResolveTaxChangeTableParameter = new ToResolveTaxChangeTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjg = request.getParameter("swjg");
			String moneyUnit = request.getParameter("moneyUnit");
			String nsrsbh = request.getParameter("nsrsbh");
			
			String nd = request.getParameter("nd");
			String yf = request.getParameter("yf");
			
			if (!yf.equals("10") && !yf.equals("11") && !yf.equals("12")) {
				yf = "0" + yf;
			}
			String sqZ = yf;
			
			reportPeriodStr = nd + "-" + yf;
			
			toResolveTaxChangeTableParameter.setSwjg(swjg);
			toResolveTaxChangeTableParameter.setNd(nd);
			toResolveTaxChangeTableParameter.setSqZ(sqZ);
			toResolveTaxChangeTableParameter.setNsrsbh(nsrsbh);
			toResolveTaxChangeTableParameter.setMoneyUnit(moneyUnit);
			toResolveTaxChangeTableParameter.setType(type);
			toResolveTaxChangeTableParameter.setOrder(order);
			
		} else {
			// 排序
			
			String swjg = request.getParameter("swjg");
			String nd = request.getParameter("nd");
			String sqZ = request.getParameter("sqZ");
			String nsrsbh = request.getParameter("nsrsbh");
			String moneyUnit = request.getParameter("moneyUnit");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			toResolveTaxChangeTableParameter.setSwjg(swjg);
			toResolveTaxChangeTableParameter.setNd(nd);
			toResolveTaxChangeTableParameter.setSqZ(sqZ);
			toResolveTaxChangeTableParameter.setNsrsbh(nsrsbh);
			toResolveTaxChangeTableParameter.setMoneyUnit(moneyUnit);
			toResolveTaxChangeTableParameter.setType(type);
			toResolveTaxChangeTableParameter.setOrder(order);
		}
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<ToResolveTaxChangeTable> tableList = taxCollectionSearchService.getToResolveChangeTable(toResolveTaxChangeTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(toResolveTaxChangeTableParameter.getSwjg());
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(toResolveTaxChangeTableParameter.getNsrsbh());
		
		request.setAttribute("swjgMc", taxUnit.getMc());
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("moneyUnit", toResolveTaxChangeTableParameter.getMoneyUnit());
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("toResolveTaxChangeTableParameter", toResolveTaxChangeTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.DJSJBD,toResolveTaxChangeTableParameter);
		request.setAttribute("tableList", tableList);
		
		return "to_resolve_tax_change_table";
	}
	
	/**
	 * 待解税金变动情况表 to Excel
	 * @return
	 */
	public String toResolveTaxChangeTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		ToResolveTaxChangeTableParameter toResolveTaxChangeTableParameter = new ToResolveTaxChangeTableParameter();
		
		String swjg = request.getParameter("swjg");
		String nd = request.getParameter("nd");
		String sqZ = request.getParameter("sqZ");
		String nsrsbh = request.getParameter("nsrsbh");
		String moneyUnit = request.getParameter("moneyUnit");
		String type = "none";
		String order = "normal";
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		toResolveTaxChangeTableParameter.setSwjg(swjg);
		toResolveTaxChangeTableParameter.setNd(nd);
		toResolveTaxChangeTableParameter.setSqZ(sqZ);
		toResolveTaxChangeTableParameter.setNsrsbh(nsrsbh);
		toResolveTaxChangeTableParameter.setMoneyUnit(moneyUnit);
		toResolveTaxChangeTableParameter.setType(type);
		toResolveTaxChangeTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<ToResolveTaxChangeTable> tableList = taxCollectionSearchService.getToResolveChangeTable(toResolveTaxChangeTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
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
		cell.setCellValue("待解税金变动情况表 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		String nsrmc = "";
		
		if (null == taxPayer) {
			nsrmc = "全部";
			nsrsbh = "";
		} else {
			nsrmc = taxPayer.getNsrmc();
			nsrsbh = taxPayer.getNsrsbh();
		}
		
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "金额单位： " + moneyUnitMc + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "纳税人编码： " + nsrsbh;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("分税种");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("待解税金期初余额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("待解税金增加");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("待解税金入库");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("待解税金期末余额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(2);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("同期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("同期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("同期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("本期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(12);
		cell.setCellValue("同期");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(13);
		cell.setCellValue("增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
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
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getA8().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(10);
			cell.setCellValue(tableList.get(i).getA9().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(11);
			cell.setCellValue(tableList.get(i).getA10().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(12);
			cell.setCellValue(tableList.get(i).getA11().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(13);
			cell.setCellValue(tableList.get(i).getA12().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				13 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				13 //last column (0-based)
				));
		
		// 分税种
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 待解税金期初余额
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				2, //first column (0-based)
				4 //last column (0-based)
				));
		
		// 待解税金增加
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				5, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 待解税金入库
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				8, //first column (0-based)
				10 //last column (0-based)
				));
		
		// 待解税金期末余额
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				11, //first column (0-based)
				13 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<14; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("待解税金变动情况表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示欠缴税金明细表条件界面
	 * @return
	 */
	public String showOwnPayTaxDetailTableConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		
		return "show_own_pay_tax_detail_table_condition_ui";
	}
	
	/**
	 * 欠缴税金明细表
	 * @return
	 */
	public String ownPayTaxDetailTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		OwnPayTaxDetailTableParameter ownPayTaxDetailTableParameter = new OwnPayTaxDetailTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjg = request.getParameter("swjg");
			String moneyUnit = request.getParameter("moneyUnit");
			String nsrsbh = request.getParameter("nsrsbh");
			String fl = request.getParameter("fl");
			
			String nd = request.getParameter("nd");
			String yf = request.getParameter("yf");
			if (!yf.equals("10") && !yf.equals("11") && !yf.equals("12")) {
				yf = "0" + yf;
			}
			String sqZ = yf;
			
			reportPeriodStr = nd + "-" + sqZ;
			
			ownPayTaxDetailTableParameter.setFl(fl);
			ownPayTaxDetailTableParameter.setSwjg(swjg);
			ownPayTaxDetailTableParameter.setNd(nd);
			ownPayTaxDetailTableParameter.setSqZ(sqZ);
			ownPayTaxDetailTableParameter.setNsrsbh(nsrsbh);
			ownPayTaxDetailTableParameter.setMoneyUnit(moneyUnit);
			ownPayTaxDetailTableParameter.setType(type);
			ownPayTaxDetailTableParameter.setOrder(order);
			
		} else {
			// 排序
			
			String fl = request.getParameter("fl");
			String swjg = request.getParameter("swjg");
			String nd = request.getParameter("nd");
			String sqZ = request.getParameter("sqZ");
			String nsrsbh = request.getParameter("nsrsbh");
			String moneyUnit = request.getParameter("moneyUnit");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			ownPayTaxDetailTableParameter.setFl(fl);
			ownPayTaxDetailTableParameter.setSwjg(swjg);
			ownPayTaxDetailTableParameter.setNd(nd);
			ownPayTaxDetailTableParameter.setSqZ(sqZ);
			ownPayTaxDetailTableParameter.setNsrsbh(nsrsbh);
			ownPayTaxDetailTableParameter.setMoneyUnit(moneyUnit);
			ownPayTaxDetailTableParameter.setType(type);
			ownPayTaxDetailTableParameter.setOrder(order);
		}
			
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<OwnPayTaxDetailTable> tableList = taxCollectionSearchService.getOwnPayTaxDetailTable(ownPayTaxDetailTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(ownPayTaxDetailTableParameter.getSwjg());
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(ownPayTaxDetailTableParameter.getNsrsbh());
		
		request.setAttribute("swjgMc", taxUnit.getMc());
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("moneyUnit", ownPayTaxDetailTableParameter.getMoneyUnit());
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("ownPayTaxDetailTableParameter", ownPayTaxDetailTableParameter);
		
		request.getSession().setAttribute(SessionAttributeKey.QJSJMX,ownPayTaxDetailTableParameter);
		request.setAttribute("tableList", tableList);
		
		return "own_pay_tax_detail_table";
	}
	
	/**
	 * 欠缴税金明细表 to Excel
	 * @return
	 */
	public String ownPayTaxDetailTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		OwnPayTaxDetailTableParameter ownPayTaxDetailTableParameter = new OwnPayTaxDetailTableParameter();
		
		String fl = request.getParameter("fl");
		String swjg = request.getParameter("swjg");
		String nd = request.getParameter("nd");
		String sqZ = request.getParameter("sqZ");
		String nsrsbh = request.getParameter("nsrsbh");
		String moneyUnit = request.getParameter("moneyUnit");
		String type = "none";
		String order = "normal";
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		ownPayTaxDetailTableParameter.setFl(fl);
		ownPayTaxDetailTableParameter.setSwjg(swjg);
		ownPayTaxDetailTableParameter.setNd(nd);
		ownPayTaxDetailTableParameter.setSqZ(sqZ);
		ownPayTaxDetailTableParameter.setNsrsbh(nsrsbh);
		ownPayTaxDetailTableParameter.setMoneyUnit(moneyUnit);
		ownPayTaxDetailTableParameter.setType(type);
		ownPayTaxDetailTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<OwnPayTaxDetailTable> tableList = taxCollectionSearchService.getOwnPayTaxDetailTable(ownPayTaxDetailTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
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
		cell.setCellValue("欠缴税金明细表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(nsrsbh);
		String nsrmc = "";
		
		if (null == taxPayer) {
			nsrmc = "全部";
			nsrsbh = "";
		} else {
			nsrmc = taxPayer.getNsrmc();
			nsrsbh = taxPayer.getNsrsbh();
		}
		
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "金额单位： " + moneyUnitMc + "，"
						 + "纳税人名称： " + nsrmc + "，"
						 + "纳税人编码： " + nsrsbh;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("期初余额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("本期增加");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("本期减少");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("期末余额 ");
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
				3, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<6; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("欠缴税金明细表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示重点企业实缴税收变化表条件选择
	 * @return
	 */
	public String showFirmPayTaxChangeTableConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
		// 行业大类列表
		BigIndustryService bigIndustryService = new BigIndustryService();
		List<BigIndustry> bigIndustryList = bigIndustryService.getBigIndustryList();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("taxUnitList", taxUnitList);
		request.setAttribute("bigIndustryList", bigIndustryList);
		
		return "show_firm_pay_tax_change_table_condition_ui";
	}
	
	/**
	 * 重点企业实缴税收变化表
	 * @return
	 */
	public String firmPayTaxChangeTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		FirmPayTaxChangeTableParameter firmPayTaxChangeTableParameter = new FirmPayTaxChangeTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjg = request.getParameter("swjg");
			String hy = request.getParameter("hy");
			String moneyUnit = request.getParameter("moneyUnit");
			String nsrsbh = request.getParameter("nsrsbh");
				
			String nd = request.getParameter("nd");
			
			String monthPeriod = request.getParameter("monthPeriod");
			String sqZ = monthPeriod.substring(3);
			
			reportPeriodStr = nd + "-01 -- " + nd + "-" + sqZ;
			
			firmPayTaxChangeTableParameter.setHy(hy);
			firmPayTaxChangeTableParameter.setSwjg(swjg);
			firmPayTaxChangeTableParameter.setNd(nd);
			firmPayTaxChangeTableParameter.setSqZ(sqZ);
			firmPayTaxChangeTableParameter.setNsrsbh(nsrsbh);
			firmPayTaxChangeTableParameter.setMoneyUnit(moneyUnit);
			firmPayTaxChangeTableParameter.setType(type);
			firmPayTaxChangeTableParameter.setOrder(order);
		} else {
			// 排序
			
			String hy = request.getParameter("hy");
			String swjg = request.getParameter("swjg");
			String nd = request.getParameter("nd");
			String sqZ = request.getParameter("sqZ");
			String nsrsbh = request.getParameter("nsrsbh");
			String moneyUnit = request.getParameter("moneyUnit");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			firmPayTaxChangeTableParameter.setHy(hy);
			firmPayTaxChangeTableParameter.setSwjg(swjg);
			firmPayTaxChangeTableParameter.setNd(nd);
			firmPayTaxChangeTableParameter.setSqZ(sqZ);
			firmPayTaxChangeTableParameter.setNsrsbh(nsrsbh);
			firmPayTaxChangeTableParameter.setMoneyUnit(moneyUnit);
			firmPayTaxChangeTableParameter.setType(type);
			firmPayTaxChangeTableParameter.setOrder(order);
		}
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<FirmPayTaxChangeTable> tableList = taxCollectionSearchService.getFirmPayTaxChangeTable(firmPayTaxChangeTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(firmPayTaxChangeTableParameter.getSwjg());
		
		BigIndustryService bigIndustryService = new BigIndustryService();
		String hyMc = "";
		if (firmPayTaxChangeTableParameter.getHy().equals("")) {
			hyMc = "全部行业";
		}else {
			BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(firmPayTaxChangeTableParameter.getHy());
			hyMc = bigIndustry.getMc();
		}
		
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = taxPayerService.getTaxPayerByCode(firmPayTaxChangeTableParameter.getNsrsbh());
		
		request.setAttribute("swjgMc", taxUnit.getMc());
		request.setAttribute("hyMc", hyMc);
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("moneyUnit", firmPayTaxChangeTableParameter.getMoneyUnit());
		request.setAttribute("taxPayer", taxPayer);
		request.setAttribute("firmPayTaxChangeTableParameter", firmPayTaxChangeTableParameter);
		
		request.setAttribute("tableList", tableList);
		
		return "firm_pay_tax_change_table";
	}
	
	/**
	 * 重点企业实缴税收变化表 to Excel
	 * @return
	 */
	public String firmPayTaxChangeTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		FirmPayTaxChangeTableParameter firmPayTaxChangeTableParameter = new FirmPayTaxChangeTableParameter();
		
		String hy = request.getParameter("hy");
		String swjg = request.getParameter("swjg");
		String nd = request.getParameter("nd");
		String sqZ = request.getParameter("sqZ");
		String nsrsbh = request.getParameter("nsrsbh");
		String moneyUnit = request.getParameter("moneyUnit");
		String type = "none";
		String order = "normal";
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		firmPayTaxChangeTableParameter.setHy(hy);
		firmPayTaxChangeTableParameter.setSwjg(swjg);
		firmPayTaxChangeTableParameter.setNd(nd);
		firmPayTaxChangeTableParameter.setSqZ(sqZ);
		firmPayTaxChangeTableParameter.setNsrsbh(nsrsbh);
		firmPayTaxChangeTableParameter.setMoneyUnit(moneyUnit);
		firmPayTaxChangeTableParameter.setType(type);
		firmPayTaxChangeTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<FirmPayTaxChangeTable> tableList = taxCollectionSearchService.getFirmPayTaxChangeTable(firmPayTaxChangeTableParameter);
		
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
		cell.setCellValue("重点企业实缴税收变化表");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		// 行业
		BigIndustryService bigIndustryService = new BigIndustryService();
		BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
		String hyMc = "";
		
		if (null == bigIndustry) {
			hyMc = "全部";
		} else {
			hyMc = bigIndustry.getMc();
		}
		
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "金额单位： " + moneyUnitMc + "，"
						 + "行业： " + hyMc;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("纳税人名称");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("本期收入");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("累计收入");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(2);
		cell.setCellValue("本期实缴");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("同期实缴");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("同比增长%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("本期实缴");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("同期实缴");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("同比增长%");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("同比增减额");
		cell.setCellStyle(cellStyleAlignCenter);
		
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
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
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA5().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA6().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getA7().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getA8().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				9 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				9 //last column (0-based)
				));
		
		// 纳税人名称
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 本期收入
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				2, //first column (0-based)
				5 //last column (0-based)
				));
		
		// 累计收入
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				6, //first column (0-based)
				9 //last column (0-based)
				));
		
		
		// 2.6 自动调整列宽
		for (int i=0; i<10; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("重点企业实缴税收变化表".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 显示企业税收台帐条件选择
	 * @return
	 */
	public String showFirmTaxAccountTableConditionUI() {
		
		//  管理机关列表
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxUnit> taxUnitList =  taxUnitService.getTaxUnitList();
		
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
		
		return "show_firm_tax_account_table_condition_ui";
	}
	
	/**
	 * 企业税收台帐
	 * @return
	 */
	public String firmTaxAccountTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String type = request.getParameter("type");
		FirmTaxAccountTableParameter firmTaxAccountTableParameter = new FirmTaxAccountTableParameter();
		String reportPeriodStr = "";
		
		// 非排序
		if (null == type) {
			if (null == type) {
				type = "none";
			}
			String order = request.getParameter("order");
			if (null == order) {
				order = "normal";
			}
			
			String swjg = request.getParameter("swjg");
			String hy = request.getParameter("hy");
			String qyzclx = request.getParameter("qyzclx");
			String moneyUnit = request.getParameter("moneyUnit");
			
			String nd = request.getParameter("nd");
			String monthPeriod = request.getParameter("monthPeriod");
			String sqZ = monthPeriod.substring(3);
			
			reportPeriodStr = nd + "-01 -- " + nd + "-" + sqZ;
			
			firmTaxAccountTableParameter.setHy(hy);
			firmTaxAccountTableParameter.setQyzclx(qyzclx);
			firmTaxAccountTableParameter.setSwjg(swjg);
			firmTaxAccountTableParameter.setNd(nd);
			firmTaxAccountTableParameter.setSqZ(sqZ);
			firmTaxAccountTableParameter.setMoneyUnit(moneyUnit);
			firmTaxAccountTableParameter.setType(type);
			firmTaxAccountTableParameter.setOrder(order);
		} else {
			// 排序
			
			String hy = request.getParameter("hy");
			String qyzclx = request.getParameter("qyzclx");
			String swjg = request.getParameter("swjg");
			String nd = request.getParameter("nd");
			String sqZ = request.getParameter("sqZ");
			String moneyUnit = request.getParameter("moneyUnit");
			type = request.getParameter("type");
			String order = request.getParameter("order");
			reportPeriodStr = request.getParameter("reportPeriodStr");
			
			firmTaxAccountTableParameter.setHy(hy);
			firmTaxAccountTableParameter.setQyzclx(qyzclx);
			firmTaxAccountTableParameter.setSwjg(swjg);
			firmTaxAccountTableParameter.setNd(nd);
			firmTaxAccountTableParameter.setSqZ(sqZ);
			firmTaxAccountTableParameter.setMoneyUnit(moneyUnit);
			firmTaxAccountTableParameter.setType(type);
			firmTaxAccountTableParameter.setOrder(order);
		}
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<FirmTaxAccountTable> tableList = taxCollectionSearchService.getFirmTaxAccountTable(firmTaxAccountTableParameter);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(firmTaxAccountTableParameter.getSwjg());
		
//		BigIndustryService bigIndustryService = new BigIndustryService();
//		BigIndustry bigIndustry = bigIndustryService.getBigIndustryById(hy);
//		
//		FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
//		FirmRegType firmRegType = firmRegTypeService.getFirmRegTypeById(qyzclx);
		
		request.setAttribute("swjgMc", taxUnit.getMc());
//		request.setAttribute("hyMc", bigIndustry.getMc());
//		request.setAttribute("qyzclxMc", firmRegType.getMc());
		request.setAttribute("reportPeriodStr", reportPeriodStr);
		request.setAttribute("moneyUnit", firmTaxAccountTableParameter.getMoneyUnit());
//		request.setAttribute("taxpayerCode", nsrsbh);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("firmTaxAccountTableParameter", firmTaxAccountTableParameter);
		
		return "firm_tax_account_table";
	}
	
	/**
	 * 企业税收台帐 to Excel
	 * @return
	 */
	public String firmTaxAccountTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		FirmTaxAccountTableParameter firmTaxAccountTableParameter = new FirmTaxAccountTableParameter();
		
		String hy = request.getParameter("hy");
		String qyzclx = request.getParameter("qyzclx");
		String swjg = request.getParameter("swjg");
		String nd = request.getParameter("nd");
		String sqZ = request.getParameter("sqZ");
		String moneyUnit = request.getParameter("moneyUnit");
		String type = "none";
		String order = "normal";
		String reportPeriodStr = request.getParameter("reportPeriodStr");
		
		firmTaxAccountTableParameter.setHy(hy);
		firmTaxAccountTableParameter.setQyzclx(qyzclx);
		firmTaxAccountTableParameter.setSwjg(swjg);
		firmTaxAccountTableParameter.setNd(nd);
		firmTaxAccountTableParameter.setSqZ(sqZ);
		firmTaxAccountTableParameter.setMoneyUnit(moneyUnit);
		firmTaxAccountTableParameter.setType(type);
		firmTaxAccountTableParameter.setOrder(order);
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		List<FirmTaxAccountTable> tableList = taxCollectionSearchService.getFirmTaxAccountTable(firmTaxAccountTableParameter);
		
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
		cell.setCellValue("企业税收台帐");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		TaxUnitService taxUnitService = new TaxUnitService();
		
		// 管理机关
		String swjgMc = taxUnitService.getTaxUnitById(swjg).getMc();
		
		// 金额单位名称
		String moneyUnitMc = "";
		if ("yuan".equals(moneyUnit)) {
			moneyUnitMc = "元";
		} else if ("wyuan".equals(moneyUnit)){
			moneyUnitMc = "万元";
		}
		
		String condition = "管理机关： " + swjgMc + "，"
						 + "报表期： " + reportPeriodStr + "，"
						 + "金额单位： " + moneyUnitMc;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(1);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("合计");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("营业税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("企业所得税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("个人所得税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(14);
		cell.setCellValue("资源税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(17);
		cell.setCellValue("调节税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(20);
		cell.setCellValue("城建税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(23);
		cell.setCellValue("房产税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(26);
		cell.setCellValue("印花税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(29);
		cell.setCellValue("土地使用税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(32);
		cell.setCellValue("土地增值税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(35);
		cell.setCellValue("车船税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(38);
		cell.setCellValue("烟叶税");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第4行
		row = sheet.createRow(4);					
		cell = row.createCell(2);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(3);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(4);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(5);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(6);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(7);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(8);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(9);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(10);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(11);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(12);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(13);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(14);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(15);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(16);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(17);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(18);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(19);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(20);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(21);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(22);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(23);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(24);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(25);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(26);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(27);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(28);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(29);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(30);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(31);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(32);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(33);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(34);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(35);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(36);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(37);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(38);
		cell.setCellValue("应缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(39);
		cell.setCellValue("实缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(40);
		cell.setCellValue("欠缴 ");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 2.4 输出逐行数据（从第5行开始）
		for (int i=0; i<tableList.size(); i++) {
			
			int rwoNum = i + 5;	// 从第5行开始
			
			row = sheet.createRow(rwoNum);
			
			cell = row.createCell(0);
			cell.setCellValue(i + 1);
			
			cell = row.createCell(1);
			cell.setCellValue(tableList.get(i).getXm().trim());
			cell.setCellStyle(cellStyleAlignCenter);
			
			cell = row.createCell(2);
			cell.setCellValue(tableList.get(i).getA11().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(3);
			cell.setCellValue(tableList.get(i).getA12().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(4);
			cell.setCellValue(tableList.get(i).getA13().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(5);
			cell.setCellValue(tableList.get(i).getA21().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(6);
			cell.setCellValue(tableList.get(i).getA22().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(7);
			cell.setCellValue(tableList.get(i).getA23().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(8);
			cell.setCellValue(tableList.get(i).getA31().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(9);
			cell.setCellValue(tableList.get(i).getA32().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(10);
			cell.setCellValue(tableList.get(i).getA33().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(11);
			cell.setCellValue(tableList.get(i).getA41().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(12);
			cell.setCellValue(tableList.get(i).getA42().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(13);
			cell.setCellValue(tableList.get(i).getA43().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(14);
			cell.setCellValue(tableList.get(i).getA51().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(15);
			cell.setCellValue(tableList.get(i).getA52().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(16);
			cell.setCellValue(tableList.get(i).getA53().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(17);
			cell.setCellValue(tableList.get(i).getA61().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(18);
			cell.setCellValue(tableList.get(i).getA62().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(19);
			cell.setCellValue(tableList.get(i).getA63().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(20);
			cell.setCellValue(tableList.get(i).getA71().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(21);
			cell.setCellValue(tableList.get(i).getA72().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(22);
			cell.setCellValue(tableList.get(i).getA73().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(23);
			cell.setCellValue(tableList.get(i).getA81().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(24);
			cell.setCellValue(tableList.get(i).getA82().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(25);
			cell.setCellValue(tableList.get(i).getA83().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(26);
			cell.setCellValue(tableList.get(i).getA91().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(27);
			cell.setCellValue(tableList.get(i).getA92().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(28);
			cell.setCellValue(tableList.get(i).getA93().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(19);
			cell.setCellValue(tableList.get(i).getA101().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(30);
			cell.setCellValue(tableList.get(i).getA102().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(31);
			cell.setCellValue(tableList.get(i).getA103().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(32);
			cell.setCellValue(tableList.get(i).getA111().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(33);
			cell.setCellValue(tableList.get(i).getA112().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(34);
			cell.setCellValue(tableList.get(i).getA113().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(35);
			cell.setCellValue(tableList.get(i).getA121().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(36);
			cell.setCellValue(tableList.get(i).getA122().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(37);
			cell.setCellValue(tableList.get(i).getA123().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(38);
			cell.setCellValue(tableList.get(i).getA131().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(39);
			cell.setCellValue(tableList.get(i).getA132().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
			cell = row.createCell(40);
			cell.setCellValue(tableList.get(i).getA133().doubleValue());
			cell.setCellStyle(cellStyleFmt);
			
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				40 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				40 //last column (0-based)
				));
		
		// 序号
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				0, //first column (0-based)
				0 //last column (0-based)
				));
		
		// 项目
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				4, //last row (0-based)
				1, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 合计
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				2, //first column (0-based)
				4 //last column (0-based)
				));
		
		// 营业税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				5, //first column (0-based)
				7 //last column (0-based)
				));
		
		// 企业所得税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				8, //first column (0-based)
				10 //last column (0-based)
				));
		
		// 个人所得税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				11, //first column (0-based)
				13 //last column (0-based)
				));
		
		// 资源税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				14, //first column (0-based)
				16 //last column (0-based)
				));
		
		// 调节税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				17, //first column (0-based)
				19 //last column (0-based)
				));
		
		// 城建税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				20, //first column (0-based)
				22 //last column (0-based)
				));
		
		// 房产税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				23, //first column (0-based)
				25 //last column (0-based)
				));
		
		// 印花税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				26, //first column (0-based)
				28 //last column (0-based)
				));
		
		// 土地使用税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				29, //first column (0-based)
				31 //last column (0-based)
				));
		
		// 土地增值税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				32, //first column (0-based)
				34 //last column (0-based)
				));
		
		// 车船税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				35, //first column (0-based)
				37 //last column (0-based)
				));
		
		// 烟叶税
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				38, //first column (0-based)
				40 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<41; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("企业税收台帐".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
	/**
	 * 查询纳税人列表
	 * @return
	 */
	public String getTaxPayerList() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxPayerService taxPayerService = new TaxPayerService();
		
		String swjgDm = request.getParameter("swjgDm");
		List<TaxPayer> taxPayerList = taxPayerService.getTaxPayerListByUnit(swjgDm);
		
		// 使用org.Json来拼写字符串
		JSONObject root = new JSONObject();
		JSONArray array = new JSONArray();
		
		// 迭代循环将每个对象obj放入数组array
		Iterator<TaxPayer> iter = taxPayerList.iterator();
		while (iter.hasNext()) {
			TaxPayer taxPayer = iter.next();
			JSONObject obj = new JSONObject();
			obj.put("nsrsbh", taxPayer.getNsrsbh());
			obj.put("nsrmc", taxPayer.getNsrmc());
			array.add(obj);
		}
		
		// 将array数组放入节点root对象中返回给Ajax调用
		root.put("taxPayerList", array);
		
		HttpServletResponse response=ServletActionContext.getResponse(); //取得response对象
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
	 
		PrintWriter out=response.getWriter();
		out.print(root.toString());
		out.close();
		
		return null;
	}
	
	/**
	 * 查询待解税金纳税人列表
	 * @return
	 */
	public String getDjTaxPayerList() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxPayerService taxPayerService = new TaxPayerService();
		
		String swjgDm = request.getParameter("swjgDm");
//		List<TaxPayer> taxPayerList = taxPayerService.getTaxPayerListByUnit(swjgDm);
		List<TaxPayer> taxPayerList = taxPayerService.getDjTaxPayerListByUnit(swjgDm);
		
		// 使用org.Json来拼写字符串
		JSONObject root = new JSONObject();
		JSONArray array = new JSONArray();
		
		// 迭代循环将每个对象obj放入数组array
		Iterator<TaxPayer> iter = taxPayerList.iterator();
		while (iter.hasNext()) {
			TaxPayer taxPayer = iter.next();
			JSONObject obj = new JSONObject();
			obj.put("nsrsbh", taxPayer.getNsrsbh());
			obj.put("nsrmc", taxPayer.getNsrmc());
			array.add(obj);
		}
		
		// 将array数组放入节点root对象中返回给Ajax调用
		root.put("taxPayerList", array);
		
		HttpServletResponse response=ServletActionContext.getResponse(); //取得response对象
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
	 
		PrintWriter out=response.getWriter();
		out.print(root.toString());
		out.close();
		
		return null;
	}
	
	/**
	 * 税收收入实时入库查询
	 * @return
	 */
	public String taxCollectionRealTimeSearchTable() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String nd = sdf.format(date);
		List<TaxCollectionRealTimeSearchTable> tableList = taxCollectionSearchService.TaxCollectionRealTimeSearchTable(nd);
		
		request.setAttribute("tableList", tableList);
		request.setAttribute("nd", nd);
		return "tax_collection_real_time_search_table";
	}
	
	/**
	 * 税收收入实时入库查询 to Excel
	 * @return
	 */
	public String taxCollectionRealTimeSearchTableToExcel() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		TaxCollectionSearchService taxCollectionSearchService = new TaxCollectionSearchService();
		String nd = request.getParameter("nd");
		List<TaxCollectionRealTimeSearchTable> tableList = taxCollectionSearchService.TaxCollectionRealTimeSearchTable(nd);
		
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
		cell.setCellValue("税收收入实时入库查询");
		cell.setCellStyle(cellStyleAlignCenter);
		
		// 第1行
		row = sheet.createRow(1);					
		cell = row.createCell(0);
		
		// 金额单位名称
		String moneyUnitMc = "元";
		
		String condition = "金额单位： " + moneyUnitMc;
		cell.setCellValue(condition);
		
		// 第2行
		row = sheet.createRow(2);	// 空一行
		
		// 第3行
		row = sheet.createRow(3);					
		cell = row.createCell(0);
		cell.setCellValue("项目");
		cell.setCellStyle(cellStyleAlignCenter);
		
		cell = row.createCell(2);
		cell.setCellValue("入库金额");
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
			cell.setCellValue(tableList.get(i).getA1().doubleValue());
			cell.setCellStyle(cellStyleFmt);
		}
		
		// 2.5  设置表头
		// 报表名称
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row (0-based)
				0, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(
				1, //first row (0-based)
				1, //last row (0-based)
				0, //first column (0-based)
				2 //last column (0-based)
				));
		
		// 项目
		
		sheet.addMergedRegion(new CellRangeAddress(
				3, //first row (0-based)
				3, //last row (0-based)
				0, //first column (0-based)
				1 //last column (0-based)
				));
		
		// 2.6 自动调整列宽
		for (int i=0; i<3; i++) {
			sheet.autoSizeColumn(i, true); //调整第i列的宽度
		}
		
		// 3. 将工作薄写入InputStream（excelStream）
		workbook2InputStream(workbook, new String("税收收入实时入库查询".getBytes("GB2312"), "ISO-8859-1"));
		
		return "table_to_excel";
	}
	
}

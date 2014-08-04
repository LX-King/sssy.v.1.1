package com.tyut.sssy.utils;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class TestPoi {

	public static void main(String[] args) throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(1);
		row.createCell(1).setCellValue(1.2);
		
		row.createCell(2).setCellValue("test");
		row.createCell(3).setCellValue(true);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
		HSSFCell dCell = row.createCell(4);
		dCell.setCellValue(new Date());
		dCell.setCellStyle(cellStyle);
		
		HSSFCell csCell =row.createCell(5); 
//		csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		csCell.setCellValue("中文测试_Chinese Words Test");  
		FileOutputStream fileOut = new FileOutputStream("d:\\test.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
}

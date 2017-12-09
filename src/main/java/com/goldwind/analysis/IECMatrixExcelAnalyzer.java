package com.goldwind.analysis;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class IECMatrixExcelAnalyzer extends BaseFileAnalyzer {

	@Override
	protected Map<String, Object> analyzer(File file) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(file);
		
		Map<String,Object> totalMap = new HashMap<String,Object>();
		
		int sheetSize = wb.getNumberOfSheets();
		for(int i = 0; i<sheetSize; i++) {
			int count = 0;
			Map<String,Object> dataMap = new HashMap<String,Object>();
			XSSFSheet sheet = wb.getSheetAt(i);
			
			int lastRowNum = sheet.getLastRowNum();
			for(int j = 0; j<lastRowNum; j++) {
				String rowname = "";
				List<String> dataList = new ArrayList<String>();
				
				XSSFRow row = sheet.getRow(j);
				if(row != null) {
					XSSFCell cell = row.getCell(0);
					if(cell != null) {
						String trim = cell.toString().trim();
						boolean matches = trim.matches("^(\\d+\\.\\d)?$");
						
						if(matches && Double.valueOf(trim) ==1) {
							count ++;
						}
					}
				}
				
				if(count == 2) {
					short lastCellNum = row.getLastCellNum();
					for(int k =0; k<lastCellNum; k++) {
						if(k == 0) {
							rowname= row.getCell(k).toString();
						}else {
							XSSFCell cell = row.getCell(k);
							dataList.add(new BigDecimal(cell.toString()).toPlainString());
						}
						
					}
					if(dataList.size()>0)
					dataMap.put(rowname, dataList);
				}
			}
			totalMap.put(sheet.getSheetName(), dataMap);
		}
		
		
		return totalMap;
	}

	@Override
	public boolean checkResults() {
		// TODO Auto-generated method stub
		return false;
	}
	

}

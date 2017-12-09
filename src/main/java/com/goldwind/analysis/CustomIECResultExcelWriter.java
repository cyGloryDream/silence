package com.goldwind.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.goldwind.utils.IEConstants;


public class CustomIECResultExcelWriter extends IecResultExcelWriter {

	public CustomIECResultExcelWriter(Map<String, Map<String, Object>> map, String[] sheetArr, String[] fileArr) {
		super(map, sheetArr, fileArr);
	}

	@Override
	public void writeResultExcel(String path) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(path +File.separator + IEConstants.IEC_RESULT_CUSTOM_FILE));
		 
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		
		 for(String sheetName : IEConstants.IEC_SHEETS) {
			  if("Site Condition".equals(sheetName)) {
				  createSiteConditionSheet(xssfWorkbook);
			  }else if(sheetName.indexOf("M=")!=-1) {
				  createMSheet(xssfWorkbook,sheetName);
			  }else if("ETM".equals(sheetName)) {
				  createETMSheet(xssfWorkbook,sheetName);
			  }
		 }
		 xssfWorkbook.write(fos);
		 fos.close();
	}

	@Override
	public void createMSheet(XSSFWorkbook xssfWorkbook, String sheetName) {
		// TODO Auto-generated method stub
				XSSFSheet m_sheet = xssfWorkbook.createSheet(sheetName);
				Map<String, Object> dataMap = map.get(IEConstants.IEC_AVALIABLE_TYPE);
				
				
				XSSFRow wt_row = m_sheet.createRow(0);
				
				boolean wt_flag = true;
				
				for(int i = 0; i < IEConstants.TI_SPEED.length; i++) {
					XSSFRow row = m_sheet.createRow(i + 1);
					
					XSSFCell speedCell = row.createCell(0);
					speedCell.setCellValue(String.valueOf(IEConstants.TI_SPEED[i]));
					
					for(int j = 0; j < sheetArr.length ; j++) {
						Map<String,Object> wt_map = (Map<String, Object>) dataMap.get(sheetName.toLowerCase());
						Map<String,Object> speed_map = (Map<String, Object>) wt_map.get(sheetArr[j]);
						if(wt_flag) {
							XSSFCell wt_cell = wt_row.createCell(j+1);
							wt_cell.setCellValue(sheetArr[j]);
						}
						XSSFCell content_cell = row.createCell(j+1);
						content_cell.setCellValue(String.valueOf(speed_map.get(String.valueOf(IEConstants.TI_SPEED[i]))));
					}
					wt_flag = false;
				}
		
		
	}
	
	@Override
	public void fillMainSheetContent(ArrayList<String> titleList,XSSFSheet sheet) {
		// TODO Auto-generated method stub
		Map<String, Object> data = map.get(IEConstants.IEC_M1_FILE);
		if(data == null) {
			data = map.get(IEConstants.IEC_M_FILE);
		}
		Map<String,Object> base_map = (Map<String,Object>)data.get(IEConstants.IEC_BASEINFO_TYPE);
		
		for(int i = 0; i<sheetArr.length; i++) {
			XSSFRow base_row = sheet.createRow(i+2);
			 for(int j = 0 ; j < titleList.size(); j++) {
				 ArrayList<String> dataList = (ArrayList<String>) base_map.get(titleList.get(j));
				 XSSFCell content_cell = base_row.createCell(j);
				if(j == 0) {
					content_cell.setCellValue(sheetArr[i]);
				}else {
					if(dataList==null &&titleList.get(j).indexOf("m=")==-1) {
						continue;
					}
					if(titleList.get(j).indexOf("m=")!=-1) {
						 Map<String, Object> mdata = map.get(IEConstants.IEC_AVALIABLE_TYPE);
						 Map<String,Object> wt_map =(Map<String,Object>) mdata.get(titleList.get(j));
						 Map<String,Object> data_map =(Map<String,Object>) wt_map.get(sheetArr[i]);
						 content_cell.setCellValue(String.valueOf(data_map.get(String.valueOf(15))));
						 
					}else {
						try {
							content_cell.setCellValue(dataList.get(i));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				 
			 }
		}
		
	}
	
	
	
	
	
	

}

package com.goldwind.analysis;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.goldwind.utils.IEConstants;




public class IecResultExcelWriter {
	public Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
	public String[] sheetArr =null;
	public List<String> fileList = null;
	
	public IecResultExcelWriter(Map<String,Map<String,Object>> map,String[] sheetArr, String[] fileArr) {
		  this.map = map;
		  this.sheetArr = sheetArr;
		  fileList = Arrays.asList(fileArr);
	 }
	
	public void writeResultExcel(String path) throws Exception {
		 
		 FileOutputStream fos = new FileOutputStream(new File(path +File.separator +IEConstants.IEC_RESULT_CLASSICS_FILE));
		 
		 XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		 
		 
		 for(String sheetName : IEConstants.IEC_SHEETS) {
			  if("Site Condition".equals(sheetName)) {
				  createSiteConditionSheet(xssfWorkbook);
			  }else if(sheetName.indexOf("M=")!=-1 && fileList.contains(sheetName + ".xlsx")) {
				  createMSheet(xssfWorkbook,sheetName);
			  }else if("ETM".equals(sheetName)) {
				  createETMSheet(xssfWorkbook,sheetName);
			  }
			 
		 }
		
		xssfWorkbook.write(fos);
		fos.close();
		 
	}
	

	//main sheet
	public void createSiteConditionSheet(XSSFWorkbook xssfWorkbook) {
		// 创建主表
		XSSFSheet mainSheet = xssfWorkbook.createSheet("Site Condition");
		
		//创建表头
		XSSFRow row1 = mainSheet.createRow(0);
		XSSFRow row2 = mainSheet.createRow(1);
		
		//存储title名称
		ArrayList<String> titleList = new ArrayList<String>();
		
		int totalCellIndex = 0;
		
		for(int i = 0; i < IEConstants.MAIN_TITLES.length; i++) {
			
			if(IEConstants.MAIN_TITLES[i].equals(IEConstants.IEC_WEIBULL_TITLE)) {
				for(int weiBull_index = 0; weiBull_index < IEConstants.WEIBULL_TITLE.length ;weiBull_index++) {
					//设置威布尔son_title
					XSSFCell weiBullSonCell = row2.createCell(totalCellIndex); 
					weiBullSonCell.setCellValue(IEConstants.WEIBULL_TITLE[weiBull_index]);
					totalCellIndex++;
					
					titleList.add(IEConstants.WEIBULL_TITLE[weiBull_index]);
					
					//设置威布尔main_title
					XSSFCell weiBullCell = row1.createCell(totalCellIndex-1);
					weiBullCell.setCellValue(IEConstants.MAIN_TITLES[i]);
					
					
				}
				mainSheet.addMergedRegion(new CellRangeAddress(0, 0, i,totalCellIndex-1));
				
			}else if(IEConstants.MAIN_TITLES[i].equals(IEConstants.IEC_FIFTEEN_TITLE)) {
				for(int m_index = 0; m_index <fileList.size(); m_index++){
					XSSFCell mSonCell = row2.createCell(totalCellIndex);
					mSonCell.setCellValue(fileList.get(m_index).replace(".xlsx", "").toLowerCase());
					totalCellIndex++;
					
					titleList.add(fileList.get(m_index).replace(".xlsx", "").toLowerCase());
					
					XSSFCell mCell = row1.createCell(totalCellIndex-1);
					mCell.setCellValue(IEConstants.MAIN_TITLES[i]);
				}
				mainSheet.addMergedRegion(new CellRangeAddress(0, 0, i+1,totalCellIndex-1));
			}else {
				XSSFCell cell_general = row1.createCell(totalCellIndex);
				totalCellIndex++;
				cell_general.setCellValue(IEConstants.MAIN_TITLES[i]);
				titleList.add(IEConstants.MAIN_TITLES[i]);
				
				//普通单元格合并上下两行
				mainSheet.addMergedRegion(new CellRangeAddress(0, 1, totalCellIndex-1, totalCellIndex-1));
			}
		}
		fillMainSheetContent(titleList,mainSheet);
	}
	
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
						 Map<String, Object> mdata = map.get(titleList.get(j).toUpperCase()+".xlsx");
						 if(mdata == null)
							 mdata = map.get(IEConstants.IEC_M_FILE);
						 String[] wtspeed_15 = (String[]) mdata.get("wtspeed_15");
						 content_cell.setCellValue(wtspeed_15[i]);
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

	public void createMSheet(XSSFWorkbook xssfWorkbook,String sheetName) {
		// TODO Auto-generated method stub
		XSSFSheet m_sheet = xssfWorkbook.createSheet(sheetName);
		Map<String, Object> dataMap = map.get(sheetName + ".xlsx");
		
		String[][] mtemp = (String[][]) dataMap.get("mtemp");
		
		XSSFRow wt_row = m_sheet.createRow(0);
		
		boolean wt_flag = true;
		
		for(int i = 0; i < IEConstants.TI_SPEED.length; i++) {
			XSSFRow row = m_sheet.createRow(i + 1);
			
			XSSFCell speedCell = row.createCell(0);
			speedCell.setCellValue(String.valueOf(IEConstants.TI_SPEED[i]));
			
			for(int j = 0; j < sheetArr.length ; j++) {
				if(wt_flag) {
					XSSFCell wt_cell = wt_row.createCell(j+1);
					wt_cell.setCellValue(sheetArr[j]);
				}
				XSSFCell content_cell = row.createCell(j+1);
				content_cell.setCellValue(mtemp[j][i]);
			}
			wt_flag = false;
		}
		
	}
	
	public void createETMSheet(XSSFWorkbook xssfWorkbook,String sheetName) {
		Map<String, Object> etm_map = map.get(IEConstants.IEC_ETM_RESULT_TYPE);
		
		XSSFSheet etm_sheet = xssfWorkbook.createSheet(sheetName);
		XSSFRow wt_row = etm_sheet.createRow(0);
		
		boolean wt_flag = true;
		
		for(int i = 0; i < IEConstants.TI_SPEED.length; i++) {
			XSSFRow row = etm_sheet.createRow(i + 1);
			
			XSSFCell speedCell = row.createCell(0);
			speedCell.setCellValue(String.valueOf(IEConstants.TI_SPEED[i]));
			
			for(int j = 0; j < sheetArr.length ; j++) {
				if(wt_flag) {
					XSSFCell wt_cell = wt_row.createCell(j+1);
					wt_cell.setCellValue(sheetArr[j]);
				}
				XSSFCell content_cell = row.createCell(j+1);
				Map<String,Object> speedMap = (Map<String, Object>) etm_map.get(sheetArr[j]);
				content_cell.setCellValue((String)speedMap.get(IEConstants.TI_SPEED[i]));
			}
			wt_flag = false;
		}
		
		
	}
	
}

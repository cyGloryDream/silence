package com.goldwind.analysis;



import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.goldwind.utils.IEConstants;

public class IecMAnalyzer extends BaseFileAnalyzer {
	//风机湍流分析
	private String[][] mtemp = null;
	
	//15米湍流
	private String[] WindSpeedarr_15 = null;
	
	//基础信息map
	private static Map<String,Object> map =new HashMap<String,Object>();
	
	//titles
	private List<String> list = new ArrayList<String>();
	
	
	
	

	@Override
	protected  Map<String,Object> analyzer(File file) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(file);
		
		init(wb.getSheetAt(0));
		
		//解析sheet
		for(int i = 0; i < sheetarr.length; i++) {
			//获取对应风机sheet
			XSSFSheet sheet = wb.getSheet(sheetarr[i]);
			
			int turbulenceindex = 3;
			
			for(int j = 0; j < sheet.getLastRowNum(); j++) {
				if (sheet.getRow(j) == null) {
					continue;
				}
				if (sheet.getRow(j).getLastCellNum() <= 0) {
					continue;
				}
				if (sheet.getRow(j).getCell(1).toString().trim()
						.equals("湍流分析:")||sheet.getRow(j).getCell(1).toString().trim()
						.equals("Turbulence analysis:")) {
					turbulenceindex += j;
					break;
				}
			}
			ArrayList<String> turbulenceList = new ArrayList<String>();
			
			boolean speedFillFlag = true;
			
			for (int j = turbulenceindex; j < sheet.getLastRowNum(); j++) {
				if (sheet.getRow(j) == null) {//数据行为null，结束 
					break;
				}
				if (sheet.getRow(j).getLastCellNum() <= 0) {//数据行长度为0，结束
					break;
				}
				//数据行对应的列值为空，结束
				if (sheet.getRow(j).getCell(3).toString().trim().equals("")) {
					break;
				}
				if (speedFillFlag) {
					double speed = Double.parseDouble(sheet.getRow(j).getCell(1).toString().trim());
					int index = intIndexInArr((int)speed, IEConstants.TI_SPEED);
					for (int k = 0; k < index; k++) {
						turbulenceList.add("");
					}
					speedFillFlag = false;
				}
				//将数据添加到集合中
				turbulenceList.add(sheet.getRow(j).getCell(3).toString().trim());
			}
			mtemp[i] = speedListToArray(turbulenceList);//集合转数组，然后赋值给临时二维数组对应的列
		}
		int speed15Index = intIndexInArr(15, IEConstants.TI_SPEED);
		String[] turbulencem1 = new String[mtemp.length];//提取m1中 15m/s 的有效湍流强度
		for (int i = 0; i < turbulencem1.length; i++) {
			if (mtemp[i].length <= speed15Index) {
				throw new Exception("格式化错误！");
			}
			WindSpeedarr_15[i] = mtemp[i][speed15Index];
		}
		
		Map<String,Object> datamap = new HashMap<String,Object>();
		datamap.put("mtemp", mtemp);
		datamap.put("wtspeed_15",WindSpeedarr_15);
		datamap.put(IEConstants.IEC_BASEINFO_TYPE, map);
		
		mtemp = new String[sheetarr.length][];
		WindSpeedarr_15 = new String[sheetarr.length];
		
		return datamap;
			
	}
	
	
	//初始化风机名称
	public void init(XSSFSheet xssfSheet){
		//获取行数
		int rows = xssfSheet.getLastRowNum();
		//数据开始行
		int startrow = 1;
		
		//获取表格从哪一行开始，由于表格中有表头，应再下移一行才是真正的数据
		for (int i = 0; i < rows; i++) {
			if (xssfSheet.getRow(i).getLastCellNum() > 5) {
				startrow += i;
				break;
			}
		}
		//所有的风机对应的sheet名称
		ArrayList<String> sheetList = new ArrayList<String>();
		for (int i = startrow; i < rows; i++) {
			if (xssfSheet.getRow(i) != null) {
				if (xssfSheet.getRow(i).getLastCellNum() > 0) {
					if (!xssfSheet.getRow(i).getCell(0).toString()
							.equals("")) {
						sheetList.add(xssfSheet.getRow(i).getCell(0)
								.toString());
					} else {
						break;
					}
				}
			}
		}
		sheetarr = ListToArray(sheetList);
		
		initWtBaseInfo(xssfSheet);
		
		mtemp = new String[sheetarr.length][];
		WindSpeedarr_15 = new String[sheetarr.length];
	}
	
	
	public void initWtBaseInfo(XSSFSheet xssfSheet) {
		//获取title栏index
		int titleRow = 0;
		int rows = xssfSheet.getLastRowNum();
		for (int i = 0; i < rows; i++) {
			if (xssfSheet.getRow(i).getLastCellNum() > 1) {
				titleRow = i;
				break;
			}
		}
		int titleCells = xssfSheet.getRow(titleRow).getLastCellNum();
		//存储result.xlsx表在该表中的对应10列的列数
		int[] columns = new int[10];
		
		list.add("Site Label");
		
		for (int i = 0; i < titleCells; i++) {
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("X")) {
				columns[1] = i;
				list.add("Easting(m)");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("Y")) {
				columns[2] = i;
				list.add("Northing(m)");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("Z")) {
				columns[3] = i;
				list.add("Elevation(m)");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("H")) {
				columns[4] = i;
				list.add("Hub Height(m)");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("A (米/秒)")||xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("A (m/s)")) {
				columns[5] = i;
				list.add("A");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("K")) {
				columns[6] = i;
				list.add("K");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("空气密度(千克/立方米)")||xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("Air density (kg/m^3)")) {
				columns[7] = i;
				list.add("Mean Air Density(kg/m3)");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("最大入流角绝对值(度)")||xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("Absolute maximum inflow angle (deg)")) {
				columns[8] = i;
				list.add("Max Inflow Angle(deg)");
			}
			if (xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("平均风切变指数")||xssfSheet.getRow(titleRow).getCell(i).toString().trim()
					.equals("Mean wind shear exponent")) {
				columns[9] = i;
				list.add("Wind Shear Exponent");
			}
		}
		Arrays.sort(columns);
		
		//根据列提取数据
		for (int i = 0; i < columns.length; i++) {
			ArrayList<String> arrayList = new ArrayList<String>();
			for (int j = titleRow + 1; j < rows; j++) {
				if (xssfSheet.getRow(j) != null
						&& xssfSheet.getRow(j).getLastCellNum() > 0) {
					if (xssfSheet.getRow(j).getCell(columns[i])
							.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						if (xssfSheet.getRow(j).getCell(columns[i])
								.getNumericCellValue() > 100000) {//对经纬度数据处理，消除科学计数法数据
							arrayList.add(df.format(
									xssfSheet.getRow(j)
											.getCell(columns[i])
											.getNumericCellValue())
									.toString());
						} else {
							arrayList.add(xssfSheet.getRow(j)
									.getCell(columns[i]).toString());
						}
					} else {
						arrayList.add(xssfSheet.getRow(j)
								.getCell(columns[i]).toString());
					}
				}
			}
			map.put(list.get(i), arrayList);
		}
	}
	
	//获取一个整数在一个整数数组的下标
	public static int intIndexInArr(int speed , int[] speedArr){
		int index = -1;
		for (int i=0;i<speedArr.length;i++) {
			if (speed == speedArr[i]) {
				index = i;
				break;
			}
		}
		return index;
	}


	@Override
	public boolean checkResults() {
		// TODO Auto-generated method stub
		return false;
	}
	
}

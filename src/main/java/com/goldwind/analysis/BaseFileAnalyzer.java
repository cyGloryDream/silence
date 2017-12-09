package com.goldwind.analysis;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.goldwind.utils.IEConstants;

public abstract class BaseFileAnalyzer implements CheckAnalyzer {
	
	// 存储所有风机对应的sheet表名
	public  String[] sheetarr = null;    
	
	public  DecimalFormat df = new DecimalFormat("0");// 数字格式化，读取excel时大数字会变成科学计数法，该变量可以将其格式化为数字
	
	//解析文件
	public File analyzerFile = null;
	
	
	
	public Map<String,Object> excute() {
			try {
				return analyzer(analyzerFile);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
	
	//子类根据需求重写解析方法
	 abstract Map<String,Object> analyzer(File file) throws Exception;
	
	
	
	//初始化wb
	public void initFile(File file) {
		analyzerFile = file;
	}
	
	//集合转数组
	public static String[] ListToArray(ArrayList<String> arrayList) {
		String[] array = new String[arrayList.size()];
		for (int i = 0; i <array.length; i++) {
			array[i] = arrayList.get(i);
		}
		return array;
	}
	
	public static String[] speedListToArray(ArrayList<String> arrayList) {
		String[] array = new String[IEConstants.TI_SPEED.length];
		for (int i = 0; i < arrayList.size(); i++) {
			array[i] = arrayList.get(i);
		}
		for(int j = 0; j< array.length; j++) {
			if(array[j] == null) {
				array[j] = "";
			}
		}
		return array;
	}
		
	
	public static BaseFileAnalyzer analyzerFactory(String fileName) throws Exception {
		if(fileName.indexOf("M=")!=-1) {
			return new IecMAnalyzer();
			
		}else if(IEConstants.IEC_MATRIX_FILE.equals(fileName)) {
			return new IECMatrixExcelAnalyzer();
			
		}else if(IEConstants.IEC_MATRIXTURB_FILE.equals(fileName)) {
			return new IecMatrixTxtAnalyzer();
			
		}else {
			throw new Exception("文件名称不匹配");
		}
		
	}
		
		

}

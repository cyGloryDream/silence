package com.goldwind.utils;

public interface IEConstants {
	
	//风速数组
	int[] TI_SPEED ={2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
	
	// result表中site label 的表头
	String[] MAIN_TITLES = { "Site Label", "Easting(m)",
			"Northing(m)", "Elevation(m)", "Turbine", "Hub Height(m)","Weibull Parameters","Annual Long Term Mean Wind Speed(m/s)", 
			"Effective Turbulence Intensity at 15m/s","Mean Air Density(kg/m3)",
			"Max Inflow Angle(deg)","Mean Inflow Angle(deg)",
			"Wind Shear Exponent", "50-year EWS_10min(m/s)",
			"50-year EWS_3s(m/s)" };
	
	String IEC_WEIBULL_TITLE = "Weibull Parameters";
	String IEC_FIFTEEN_TITLE = "Effective Turbulence Intensity at 15m/s";
	
	String[] WEIBULL_TITLE = {"A", "K"};
	String[] MSON_TITLES = {"m=1", "m=4", "m=8", "m=10",};
	
	String[] IEC_FILE_NAMES = {"M=1.xlsx","M=4.xlsx","M=8.xlsx","M=10.xlsx","matrix.xlsx","matrix_turb.txt"};
	
	//结果文件sheets
	String[] IEC_SHEETS = {"Site Condition", "M=1", "M=4", "M=8", "M=10", "ETM"};
	
	//上传文件名
	String IEC_M_FILE = "M.xlsx";
	String IEC_M1_FILE = "M=1.xlsx";
	String IEC_M4_FILE = "M=4.xlsx";
	String IEC_M8_FILE = "M=8.xlsx";
	String IEC_M10_FILE = "M=10.xlsx";
	//风频文件
	String IEC_MATRIX_FILE = "matrix.xlsx";  
	//湍流文件
	String IEC_MATRIXTURB_FILE = "matrix_turb.txt";
	
	//结果文件
	String IEC_RESULT_CLASSICS_FILE = "result.xlsx";
	String IEC_RESULT_CUSTOM_FILE = "result_custom.xlsx";
	
	//文件类型
	String IEC_M_TYPE = "m";
	String IEC_M1_TYPE = "m=1";
	String IEC_M4_TYPE =   "m=4";   
	String IEC_M8_TYPE =   "m=8";
	String IEC_M10_TYPE =  "m=10";
	String IEC_MATRIX_TYPE = "matrix";
	String IEC_MATRIXTURB_TYPE = "matrix_turb";
	String IEC_AVALIABLE_TYPE = "iec_avaliable";
	String IEC_ETM_RESULT_TYPE = "etm_result";
	String IEC_BASEINFO_TYPE = "wt_base_info";
	
	
	//错误提醒
	String IEC_DIR_ERROR = "IEC文件结构未生成！";
	String IEC_FILE_ERROR = "文件不存在！";

	

}

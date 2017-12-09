package com.goldwind.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goldwind.analysis.AnalyzerPool;
import com.goldwind.analysis.BaseFileAnalyzer;
import com.goldwind.analysis.CustomIECResultExcelWriter;
import com.goldwind.analysis.CustomMatrixCalc;
import com.goldwind.analysis.IECMatrixExcelAnalyzer;
import com.goldwind.analysis.IecMAnalyzer;
import com.goldwind.analysis.IecMatrixTxtAnalyzer;
import com.goldwind.analysis.IecResultExcelWriter;
import com.goldwind.analysis.MatrixCalc;
import com.goldwind.service.IecService;
import com.goldwind.utils.IEConstants;


@Service
public class IecServiceImpl implements IecService {
	
	@Value("${system.storePath}")
	private String storePath;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}

	@Override
	public void dealUploadFile(MultipartFile file,String fileType,String... content) throws Exception {
		 String filePath = generateFileDir(fileType,content);
		 File destFile =  new File(storePath+filePath);
		 if(!destFile.getParentFile().exists()) {
			 destFile.getParentFile().mkdirs();
		 }
		 file.transferTo(destFile);
	}
	
	public static String generateFileDir(String fileType,String ...content) {
		//2017-11-17/sessionId/fileName
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		StringBuffer pathBuffer = new StringBuffer();
		
		String format = sdf.format(new Date());
		pathBuffer.append(File.separator + format);
		
		for(String path:content) {
			pathBuffer.append(File.separator + path);
		}
		
		switch (fileType) {
		case IEConstants.IEC_M_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_M_FILE);
			break;
			
		case IEConstants.IEC_M1_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_M1_FILE);
			break;
			
		case IEConstants.IEC_M4_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_M4_FILE);
			break;
		
		case IEConstants.IEC_M8_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_M8_FILE);
			break;
		
		case IEConstants.IEC_M10_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_M10_FILE);
			break;
			
		case IEConstants.IEC_MATRIX_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_MATRIX_FILE);
			break;
		
		case IEConstants.IEC_MATRIXTURB_TYPE:
			pathBuffer.append(File.separator + IEConstants.IEC_MATRIXTURB_FILE);
			break;

		default:
			return "";
		}
		
		return pathBuffer.toString();
	}
	
	
	@Override
	public  Map<String,Object> startCalc(String sessionId,String varible1, Boolean varible2,String fileList) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String userPath = sessionId ;
		
		String format = sdf.format(new Date());
		
		//处理要解析的文件
		String[] split = fileList.toUpperCase().split(",");
		for(int i=0; i<split.length; i++) {
			split[i]=split[i]+".xlsx";
		}
		
		List<String> mfileList = Arrays.asList(split);
		
		File file =new File(storePath + File.separator + format +  File.separator + sessionId );
		if(!file.exists()) {
			map.put("flag", false);
			map.put("message", IEConstants.IEC_DIR_ERROR);
			return map;
		}
		//解析器池
		AnalyzerPool pool = new AnalyzerPool();
		
		Map<String,Map<String,Object>> datamap = new HashMap<String,Map<String,Object>>();
		String[] sheetArr = null;
		
		System.out.println("-------------------提取数据文件---------------------");
		for(String fileName:IEConstants.IEC_FILE_NAMES) {
			if(fileName.indexOf("M=")!=-1 && !mfileList.contains(fileName))
				continue;
			File iec_file = new File(storePath + File.separator + format + File.separator + userPath + File.separator + fileName);
			if(!iec_file.exists()) {
				map.put("flag", false);
				map.put("message", fileName + IEConstants.IEC_FILE_ERROR);
				return map;
			}
			
			BaseFileAnalyzer analyzer = null;
			
			if((analyzer=pool.getAnalyzer(fileName)) == null) {
				analyzer = BaseFileAnalyzer.analyzerFactory(fileName);
				pool.loadAnalyzer(fileName, analyzer);
			}
			 analyzer.initFile(iec_file);
			 
			 Map<String, Object> data = analyzer.excute();
			 if(sheetArr == null)
				 sheetArr = analyzer.sheetarr;
			 
			 datamap.put(fileName, data);
		}
		
		
		//处理矩阵文件数据
		System.out.println("-------------------处理矩阵文件---------------------");
		MatrixCalc mc = new MatrixCalc(sheetArr);
		Map<String, Object> etm_result = mc.caclMatrixFile(datamap, Double.valueOf(varible1), varible2);
		
		datamap.put("etm_result", etm_result);
		
		
		System.out.println("-------------------生成结果文件---------------------");
		IecResultExcelWriter iecWriter = new IecResultExcelWriter(datamap,sheetArr,split);
		iecWriter.writeResultExcel(storePath+ File.separator + format + File.separator +userPath);
		
		map.put("flag", true);
		
		return map;
	}
	
	
	//checkFile
	@Override
	public boolean checkFileExists(String userPath,String fileName) {
		String format = sdf.format(new Date());
		File file = new File(storePath + File.separator+ format + File.separator + userPath + File.separator + fileName);
		if(file.exists()) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> startCustomCalc(String sessionId, String varible1, Boolean varible2)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String userPath = sessionId ;
		
		String format = sdf.format(new Date());
		
		File file =new File(storePath + File.separator + format +  File.separator + sessionId );
		if(!file.exists()) {
			map.put("flag", false);
			map.put("message", IEConstants.IEC_DIR_ERROR);
			return map;
		}
		
		Map<String,Map<String,Object>> dataMap = new HashMap<String,Map<String,Object>>();
		
		String[] sheetArr = null;
		
		System.out.println("/******************** 自定义-参数文件读取  ********************/");
		
		BaseFileAnalyzer mAnalyzer = new IecMAnalyzer();
		
		File m_file = new File(storePath + File.separator + format + File.separator + userPath + File.separator + IEConstants.IEC_M_FILE);
		if(!m_file.exists()) {
			map.put("flag", false);
			map.put("message", IEConstants.IEC_M_TYPE + IEConstants.IEC_FILE_ERROR);
			return map;
		}
			
		mAnalyzer.initFile( m_file);
		
		dataMap.put(IEConstants.IEC_M_FILE, mAnalyzer.excute());
		
		sheetArr = mAnalyzer.sheetarr;
		
		BaseFileAnalyzer iec_matrix = new IECMatrixExcelAnalyzer(); 	//风频
		File matrix_file = new File(storePath + File.separator + format + File.separator + userPath + File.separator + IEConstants.IEC_MATRIX_FILE);
		if(!matrix_file.exists()) {
			map.put("flag", false);
			map.put("message", IEConstants.IEC_MATRIX_TYPE + IEConstants.IEC_FILE_ERROR);
			return map;
		}
			
		iec_matrix.initFile(matrix_file);
		dataMap.put(IEConstants.IEC_MATRIX_FILE, iec_matrix.excute());
		
		BaseFileAnalyzer iec_matrix_turb = new IecMatrixTxtAnalyzer();	//湍流
		File matrix_turb_file = new File(storePath + File.separator + format + File.separator + userPath + File.separator + IEConstants.IEC_MATRIXTURB_FILE);
		if(!matrix_turb_file.exists()) {
			map.put("flag", false);
			map.put("message", IEConstants.IEC_MATRIXTURB_TYPE + IEConstants.IEC_FILE_ERROR);
			return map;
		}
		iec_matrix_turb.initFile(matrix_turb_file);
		
		dataMap.put(IEConstants.IEC_MATRIXTURB_FILE, iec_matrix_turb.excute());
		
		System.out.println("/******************** 自定义-矩阵文件处理  ********************/");
		CustomMatrixCalc custom_matrixc_calc = new CustomMatrixCalc(sheetArr);
		
		Map<String, Object> custom_avaliable = custom_matrixc_calc.customCalcMatrixFile(dataMap, Double.valueOf(varible1), varible2);
		
		Map<String, Object> matrix_map = custom_matrixc_calc.caclMatrixFile(dataMap, Double.valueOf(varible1), varible2);
		
		dataMap.put(IEConstants.IEC_AVALIABLE_TYPE, custom_avaliable);
		dataMap.put(IEConstants.IEC_ETM_RESULT_TYPE, matrix_map);
		
		
		System.out.println("/******************** 自定义-结果文件生成  ********************/");
		IecResultExcelWriter writer = new CustomIECResultExcelWriter(dataMap, sheetArr, IEConstants.MSON_TITLES);
		writer.writeResultExcel(storePath+ File.separator + format + File.separator +userPath);
		
		map.put("flag", true);
		
		return map;
	}
	
	
	

}

package com.goldwind.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.goldwind.utils.IEConstants;

public class MatrixCalc {
	public String[] sheetArr;
	public MatrixCalc(String[] sheetArr) {
		this.sheetArr = sheetArr;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> caclMatrixFile(Map<String,Map<String,Object>> map,double filterVarible,boolean flag){
		System.out.println("-----------------用户选项:"+filterVarible+"  "+flag+"-----------------");
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		Map<String,Object> martrix_turb = (Map<String, Object>) map.get(IEConstants.IEC_MATRIXTURB_FILE);
		Map<String,Object> matrix = (Map<String, Object>) map.get(IEConstants.IEC_MATRIX_FILE);
		
		
		for(String wt_name : sheetArr) {
			Map<Integer,Object> sheetMap = new HashMap<Integer,Object>();
			
			Map<String,Object> turb_wtMap = (Map<String,Object>) martrix_turb.get(wt_name);
			Map<String,Object> matrix_wtMap = (Map<String, Object>) matrix.get(wt_name);
			ArrayList<String>  wspeedList= (ArrayList<String>) matrix_wtMap.get(Double.valueOf(IEConstants.TI_SPEED[0]).toString());
			for(int i = 0; i<wspeedList.size(); i++) {
				boolean clearFlag = false;
				for(int speed : IEConstants.TI_SPEED) {
					ArrayList<String> wtsList= (ArrayList<String>)matrix_wtMap.get(Double.valueOf(speed).toString());
					String speedStr = wtsList.get(i);
					
					//置零标志 当当前数据为0，且用户选择置零时触发
					if(clearFlag) {
						wtsList.set(i, "");
					}else {
						if(!StringUtils.isEmpty(speedStr)&&Double.valueOf(speedStr)!=0) {
							//用户选择小于指定大小的风速置零
							if(filterVarible != -1) {
								if(Double.valueOf(wtsList.get(i)) < filterVarible) {
									wtsList.set(i,"");
									if(flag) {
										clearFlag = true;
									}
								}else {
									String[] wts_arr =(String[]) turb_wtMap.get(String.valueOf(speed));
									wtsList.set(i, wts_arr[i+1]);
								}
							}else {
								String[] wts_arr =(String[]) turb_wtMap.get(String.valueOf(speed));
								wtsList.set(i, wts_arr[i+1]);
							}
							
						}else {
							//用户选择置零操作
							wtsList.set(i, "");
							if(flag) {
								clearFlag = true;
							}
						}
					}
				}
			}
			
			for(int speed : IEConstants.TI_SPEED) {
				ArrayList<String> slist = (ArrayList<String>) matrix_wtMap.get(Double.valueOf(speed).toString());
				String max = Collections.max(slist);
				
				sheetMap.put(speed, max);
			}
			dataMap.put(wt_name, sheetMap);
			
		}
		//取风机风速中，每一行的最大值
		
		return dataMap;
	}

}

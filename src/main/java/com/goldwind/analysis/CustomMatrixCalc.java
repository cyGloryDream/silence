package com.goldwind.analysis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.goldwind.utils.IEConstants;

public class CustomMatrixCalc extends MatrixCalc {

	public CustomMatrixCalc(String[] sheetArr) {
		super(sheetArr);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> customCalcMatrixFile(Map<String,Map<String,Object>> map,double filterVarible,boolean flag){
		Map<String,Object> result = new HashMap<String,Object>();
		
		dealDataMap(map, filterVarible, flag);
		
		for(String m : IEConstants.MSON_TITLES) {
			String mtype = m.replace("m=", "");
			Map<String, Object> m_avaliable = calcDataMap(map,Double.valueOf(mtype));
			result.put(m, m_avaliable);
		}
		
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public void dealDataMap(Map<String,Map<String,Object>> map,double filterVarible,boolean flag){
		Map<String,Object> martrix_turb = (Map<String, Object>) map.get(IEConstants.IEC_MATRIXTURB_FILE);
		Map<String,Object> matrix = (Map<String, Object>) map.get(IEConstants.IEC_MATRIX_FILE);
		
		for(String wt_name : sheetArr) {
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
						if(Double.valueOf(speedStr)!=0) {
							//用户选择小于指定大小的风速置零
							if(filterVarible != -1) {
								if(Double.valueOf(wtsList.get(i)) < filterVarible) {
									wtsList.set(i,"");
									if(flag) {
										clearFlag = true;
									}
								}
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
		}
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> calcDataMap(Map<String, Map<String, Object>> map,double mType) {
		Map<String,Object> wt_results = new HashMap<String,Object>();
		
		Map<String,Object> martrix_turb = (Map<String, Object>) map.get(IEConstants.IEC_MATRIXTURB_FILE);
		Map<String,Object> matrix = (Map<String, Object>) map.get(IEConstants.IEC_MATRIX_FILE);
		
		for(String wt_name : sheetArr) {
			Map<String,Object>  wt_avaliable = new HashMap<String,Object>();
			
			Map<String,Object> turb_wtMap = (Map<String,Object>) martrix_turb.get(wt_name);
			Map<String,Object> matrix_wtMap = (Map<String, Object>) matrix.get(wt_name);
			
			for(int speed : IEConstants.TI_SPEED) {
				double var1 = 0;
				double var2 = 0;
				double result = 0;
				ArrayList<String> wtsList= (ArrayList<String>)matrix_wtMap.get(Double.valueOf(speed).toString());
				String[] wts_arr =(String[]) turb_wtMap.get(String.valueOf(speed));
				for(int i = 0; i<wtsList.size(); i++) {
					if(!StringUtils.isEmpty(wtsList.get(i))) {
						var1 += Double.valueOf(wtsList.get(i)) * Math.pow(Double.valueOf(wts_arr[i+1]), mType);
						var2 += Double.valueOf(wtsList.get(i));
					}
				}
				result = Math.pow(var1/var2,1/mType);
				if(Double.isNaN(result)) {
					result = 0.000;
				}
				BigDecimal b_result = new BigDecimal(result);
				
				BigDecimal setScale = b_result.setScale(3, BigDecimal.ROUND_HALF_UP);
					
				wt_avaliable.put(String.valueOf(speed), setScale.doubleValue());
				
			}
			wt_results.put(wt_name, wt_avaliable);
		}
		return wt_results;
		
	}

	
}

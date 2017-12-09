package com.goldwind.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.jasper.tagplugins.jstl.core.ForEach;


public class IecMatrixTxtAnalyzer extends BaseFileAnalyzer {
	String name = "";
	//iec矩阵标志
	boolean iec_matrix_flag = false;
	
	//矩阵读取标志
	boolean iec_matrix_read_flag = false;
	
	Map<String,Object> dataMap = new HashMap<String,Object>();

	@Override
	protected Map<String, Object> analyzer(File file) throws Exception {
		// TODO Auto-generated method stub
		Reader reader= new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		
		Map<String,Object> totalMap = new HashMap<String,Object>();
		
		
		Stream<String> lines = br.lines();
		lines.forEach(new Consumer<String>() {
			@Override
			public void accept(String content) {
				//key:风速段   value:风速数组
				if(content.indexOf("标签")>=0 ){
					if(iec_matrix_flag) {
						iec_matrix_flag = false;
						
						totalMap.put(name, dataMap);
						dataMap = new HashMap<String,Object>();
					}
					name = content.substring(content.indexOf("=")+1, content.length()).trim();
						
				}
				
				if("*** IEC湍流信息".equals(content.trim())) {
					iec_matrix_flag = true;
				}
				
				if(iec_matrix_flag){
					if(!"".equals(content.trim())) {
						String beginNo = content.substring(0, 2).trim();
						
						boolean matches = beginNo.matches("[0-9]+");
						
						if(matches&&Integer.valueOf(beginNo) == 1) {
							iec_matrix_read_flag = true;
						}
					}
				}
				if(iec_matrix_read_flag) {
					if("".equals(content.trim())) {
						iec_matrix_read_flag = false;
						return;
					}
					content = content.replaceAll("\\s+", " ");
					content=content.startsWith(" ")?content.substring(1,content.length()):content;
					String[] split = content.split(" ");
					
					dataMap.put(split[0].trim(), split);
				}
			}
		});
		totalMap.put(name, dataMap);
		reader.close();
		br.close();
		return totalMap;
	}

	@Override
	public boolean checkResults() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}

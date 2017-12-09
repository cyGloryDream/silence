package com.goldwind.analysis;

import java.util.HashMap;

public class AnalyzerPool extends HashMap {
	
	//装载解析器
	public void loadAnalyzer(String key,Object obj){
		if(key.indexOf("M=")!= -1) {
			key = "manalyzer";
		}
		super.put(key, obj);
		
	}
	
	public BaseFileAnalyzer getAnalyzer(String key) {
		if(key.indexOf("M=")!=-1) {
			key = "manalyzer";
		}
		return (BaseFileAnalyzer) super.get(key);
	}

}

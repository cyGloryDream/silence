package com.goldwind.service;


import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import scala.annotation.varargs;


public interface IecService {
	void dealUploadFile(MultipartFile file,String fileType,String ...content) throws Exception;

	Map<String,Object> startCalc(String sessionId,String varible1, Boolean varible2,String fileList) throws Exception;

	boolean checkFileExists(String string,String fileName);
	
	Map<String,Object> startCustomCalc(String sessionId,String varible1, Boolean varible2) throws Exception;

}

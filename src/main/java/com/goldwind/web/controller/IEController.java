package com.goldwind.web.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goldwind.service.IecService;
import com.goldwind.utils.IEConstants;


@Controller
public class IEController {
	
	@Autowired
	private IecService iecService;
	
	
	@Value("${system.storePath}")
	private String storePath;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}
	
	@RequestMapping(value="/")
	public String iecIndex(HttpServletRequest request,Model model) {
		return "iec_index";
		
	}
	
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> uploadFile(@RequestParam MultipartFile file,@RequestParam(value="fileType",required=true) String fileType,HttpServletRequest request){
			Map<String,Object> map = new HashMap<String,Object>();
			
			try {
				iecService.dealUploadFile(file, fileType,request.getSession().getId());
				map.put("message", "success");
				map.put("fileType", fileType);
				map.put("fileName", file.getOriginalFilename());
				
			} catch (Exception e) {
				e.printStackTrace();
				map.put("message", "error");
			}
			
			return map;
	}
	
	@RequestMapping(value="/calc",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> startCalc(@RequestParam(value = "varible1",required=true)String varible1,
													  @RequestParam(value = "varible2",required=true)Boolean varible2,
													  @RequestParam(value = "fileList",required=true)String fileList,
													  HttpServletRequest request){
		Map<String,Object> map =new HashMap<String,Object>();
		
		try {
			Map<String, Object> calcMap = iecService.startCalc(request.getSession().getId(),varible1,varible2,fileList);
			String flag = (boolean)calcMap.get("flag")?"success":(String) calcMap.get("message");
			map.put("message", flag);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "IEC计算失败，请检查所上传的文件!");
		}
		
		return map;
	}
	
	@RequestMapping(value= "/checkFile",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkFile(@RequestParam(name="type",required=true)String type,HttpServletRequest request){
			Map<String,Object> map = new HashMap<String,Object>();
			String session_id = request.getSession().getId();
			String fileName = type.equals("classic")?IEConstants.IEC_RESULT_CLASSICS_FILE:IEConstants.IEC_RESULT_CUSTOM_FILE;
			boolean flag = iecService.checkFileExists(session_id,fileName);
			
			map.put("flag", flag);
			
			return map;
		
	}
	
	@RequestMapping(value = "/download",method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,@RequestParam(name="type",required=true)String type) throws IOException {
		String fileName = type.equals("classic")?IEConstants.IEC_RESULT_CLASSICS_FILE:IEConstants.IEC_RESULT_CUSTOM_FILE;
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			  fileName = URLEncoder.encode(fileName, "UTF-8");
		    } else {
		      fileName = new String(fileName.replace(" ", "_").getBytes("UTF-8"), "ISO8859-1");
		    }
		    response.setContentType("application/force-download");
		    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
		    

		    Object out = response.getOutputStream();
		    
		    String format = sdf.format(new Date());
		    
		    FileInputStream is = new FileInputStream(storePath + File.separator + format + File.separator +request.getSession().getId()
		    		 + File.separator + fileName);
		    
		    int len = 0;
		    byte[] b = new byte[8192];
		    while ((len = is.read(b)) != -1) {
		      ((OutputStream)out).write(b, 0, len);
		    }
		    ((OutputStream)out).flush();
		    
		    is.close();
		    ((OutputStream)out).close();
		
	}
	
	@RequestMapping(value = "/custom_calc")
	public @ResponseBody Map<String,Object> customCalc(@RequestParam(value = "varible1",required=true)String varible1,
			  										   @RequestParam(value = "varible2",required=true)Boolean varible2,
			  										   HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			Map<String, Object> customCalc = iecService.startCustomCalc(request.getSession().getId(), varible1, varible2);
			String flag = (boolean)customCalc.get("flag")?"success":(String) customCalc.get("message");
			map.put("message", flag);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "IEC计算失败，请检查所上传的文件!");
		}
		
		return map;
	}
	
}

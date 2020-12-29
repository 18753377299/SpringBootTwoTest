package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.ExportService;

/**
 * @function： 用于导出导入文件，对文件进行操作
 * @param 
 * @return
 * @date: 20200522 
 * @author lqk
 */
@RestController
@RequestMapping("/export")
public class ExportApi {
	
	@Autowired
	private ExportService exportService;
	
	
	@RequestMapping(value="/exportWordFile")
	public String exportWordFile() {
		String path = exportService.exportWordFile();
		return path;
	}
	/**
	 * @function： 导入excel文件
	 * @param 
	 * @return
	 * @date: 20200522 
	 * @author lqk
	 */
	
	
	
}

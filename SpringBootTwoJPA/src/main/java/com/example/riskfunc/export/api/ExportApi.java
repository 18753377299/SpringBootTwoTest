package com.example.riskfunc.export.api;

import com.example.common.annotation.method.KthLog;
import com.example.riskfunc.export.service.facade.ExportService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @function： 用于导出导入文件，对文件进行操作
 * @param 
 * @return
 * @date: 20200522 
 * @author lqk
 */
@RestController
@RequestMapping("/export")
@Api(value="用于数据的导入导出")
public class ExportApi {
	
	@Autowired
	private ExportService exportService;
	
	@KthLog("这是日志内容")
	@GetMapping(value="/exportWordFile")
//	@ApiImplicitParam(dataType = "",name="",value="",required=true)
	public String exportWordFile() {
		/*if(true){
			throw new CustomException("自定义的异常信息");
		}*/
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

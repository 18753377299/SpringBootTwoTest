package com.example.test;

import com.example.common.utils.SpringUtils;
import com.example.riskfunc.export.service.facade.ExportService;
import io.swagger.annotations.Api;
import org.junit.Test;

@Api("Spring工具类测试")
public class SpringUtilsTest extends DemoTestSuper{

     @Test
     public void testSpring(){
//        ExportService exportService = SpringUtils.getBean(ExportService.class);
         ExportService exportService = SpringUtils.getBean("exportService");
         exportService.exportWordFile();
     }


}

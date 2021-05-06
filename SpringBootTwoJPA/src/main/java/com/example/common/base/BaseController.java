package com.example.common.base;


import com.example.common.utils.SpringUtils;
import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;

@Api(value="Controller中的基础方法")
public class BaseController {

     public  static HttpServletRequest getRequest(){
         return SpringUtils.getServletRequest();
     }



}

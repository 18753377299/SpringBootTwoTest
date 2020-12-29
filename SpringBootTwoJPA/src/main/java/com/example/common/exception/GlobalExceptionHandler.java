package com.example.common.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.common.exception.ReturnInfo.ResultEntity;
import com.example.vo.AjaxResult;

import lombok.extern.slf4j.Slf4j;


/*统一异常处理: 
 * 进行校验的时候，如果不进行统异常处理，那么springboot的校验信息就不会输出来，输出的仍旧是原始报错信息*/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
//	@ExceptionHandler(ValidationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handle(ValidationException exception) {
//		String errorMessage = "";
//        if(exception instanceof ConstraintViolationException){
//            ConstraintViolationException exs = (ConstraintViolationException) exception;
//
//            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
//            for (ConstraintViolation<?> item : violations) {
//                //打印验证不通过的信息
//                System.out.println(item.getMessage());
//                errorMessage = item.getMessage();
//            }
//        }
//        return errorMessage;
//    }
	
	@ExceptionHandler
    public AjaxResult handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        logger.info("Restful Http请求发生异常...");

        if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            logger.info("修改返回状态值为200");
            res.setStatus(HttpStatus.OK.value());
        }

        if (e instanceof NullPointerException) {
            logger.error("代码00：" + e.getMessage(), e);
            return AjaxResult.error("发生空指针异常："+ e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            logger.error("代码01：" + e.getMessage(), e);
            return AjaxResult.error("请求参数类型不匹配："+ e.getMessage());
        } else if (e instanceof SQLException) {
            logger.error("代码02：" + e.getMessage(), e);
            return AjaxResult.error("数据库访问异常："+ e.getMessage());
        } else if (e instanceof ValidationException) {
            logger.error("代码03：" + e.getMessage(), e);
            return AjaxResult.error("方法校验异常："+ e.getMessage());
        } else if (e instanceof ArithmeticException) {
            logger.error("代码03-1：" + e.getMessage(), e);
            return AjaxResult.error("算数运算异常："+ e.getMessage());
        } else if (e instanceof RuntimeException) {
            logger.error("代码04：" + e.getMessage(), e);
            return AjaxResult.error("运行时异常异常："+ e.getMessage());
        }else {
            logger.error("代码99：" + e.getMessage(), e);
            return AjaxResult.error("服务器代码发生异常,请联系管理员:"+ e.getMessage());
        }
        
        
    }
	
	
}

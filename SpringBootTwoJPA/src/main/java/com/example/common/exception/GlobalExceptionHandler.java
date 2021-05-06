package com.example.common.exception;

import com.example.common.exception.customException.CustomException;
import com.example.common.exception.enumConst.EnumExcepConst;
import com.example.vo.AjaxResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Set;


/*统一异常处理: 
 * 进行校验的时候，如果不进行统异常处理，
 * 那么springboot的校验信息就不会输出来，输出的仍旧是原始报错信息*/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
//	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	@ExceptionHandler
    public AjaxResult handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        log.info("Restful Http请求发生异常...");

        if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            log.info("修改返回状态值为200");
            res.setStatus(HttpStatus.OK.value());
        }

        if (e instanceof NullPointerException) {
            log.error("代码："+ EnumExcepConst.NullConst.getCode()+ e.getMessage(), e);
            return AjaxResult.error(EnumExcepConst.NullConst.getName()+ e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            log.error("代码01：" + e.getMessage(), e);
            return AjaxResult.error("请求参数类型不匹配："+ e.getMessage());
        } else if (e instanceof SQLException) {
            log.error("代码02：" + e.getMessage(), e);
            return AjaxResult.error("数据库访问异常："+ e.getMessage());
        }
        else if (e instanceof ValidationException) {
            log.error("代码03：" + e.getMessage(), e);
            return AjaxResult.error("方法校验异常："+ e.getMessage());
        }
        else if (e instanceof ArithmeticException) {
            log.error("代码03-1：" + e.getMessage(), e);
            return AjaxResult.error("算数运算异常："+ e.getMessage());
        } else if (e instanceof RuntimeException) {
            log.error("代码04：" + e.getMessage(), e);
            return AjaxResult.error("运行时异常异常："+ e.getMessage());
        }else {
            log.error("代码99：" + e.getMessage(), e);
            return AjaxResult.error("服务器代码发生异常,请联系管理员:"+ e.getMessage());
        }
    }

    @ApiOperation(value = "自定义异常处理方法")
    @ExceptionHandler(CustomException.class)
    public AjaxResult handle(CustomException e) {
        return AjaxResult.error(EnumExcepConst.CustomConst.getName()+":"+ e.getMessage());
    }

    @ApiOperation(value = "参数校验异常处理方法")
    @ExceptionHandler(ValidationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult paramValidateException(ValidationException e) {
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                //打印验证不通过的信息e
                log.error(EnumExcepConst.ParamValidateConst.getCode()+ ":{}",item.getMessage());
                System.out.println(item.getMessage());
                return AjaxResult.error(item.getMessage());
            }
        }
        return AjaxResult.error(EnumExcepConst.ParamValidateConst.getName()+":"+ e.getMessage());
    }


}

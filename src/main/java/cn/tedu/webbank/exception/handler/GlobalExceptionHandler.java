package cn.tedu.webbank.exception.handler;

import cn.tedu.webbank.exception.ServiceException;
import cn.tedu.webbank.web.JsonResult;
import cn.tedu.webbank.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.StringJoiner;

/**
 * @ClassName GlobalExceptionHandler
 * @Version 1.0
 * @Description 統一異常處理類
 * @Date 2022/10/20、上午2:38
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e){
        log.debug("統一處理異常ServiceException:{}",e.getMessage());
        return JsonResult.fail(e);
    }
    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e){
        log.error("統一處理未知異常【{}】，將向客戶端響應：{}", e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        String message = "伺服器忙線中，請稍候！！";
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,message);
    }

    @ExceptionHandler
    public JsonResult handlerBindException(BindException e){
        log.debug("綁定異常處理");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringJoiner  stringJoiner= new StringJoiner(";", "異常", "！！！");
        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            stringJoiner.add(defaultMessage);
        }
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST,stringJoiner.toString());
    }

    @ExceptionHandler
    public JsonResult handleBadCredentials(BadCredentialsException e) {
        log.error("統一處理憑證錯誤得異常【{}】，將向客戶端響應：{}", e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        String message = "帳號或密碼錯誤，請重新登入！";
        return JsonResult.fail(ServiceCode.ERR_JWT_INVALID, message);
    }
}

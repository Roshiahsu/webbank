package cn.tedu.webbank.web;

import cn.tedu.webbank.exception.ServiceException;
import lombok.Data;

/**
 * @ClassName JsonResult
 * @Version 1.0
 * @Description JsonResult回傳方法
 * @Date 2022/10/20、上午2:42
 */
@Data
public class JsonResult {
    /**
     * 狀態碼
     */
    private Integer statusCode;
    /**
     * 錯誤訊息
     */
    private String message;
    /**
     * 響應資料
     */
    private Object data;

    /**
     * 響應ok，不帶資料的回傳
     * @return
     */
    public static JsonResult ok(){
        return ok(null);
    }
    /**
     * 響應ok，回傳查詢資料
     * @return
     */
    public static JsonResult ok(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.statusCode=ServiceCode.OK;
        jsonResult.data=data;
        return jsonResult;
    }

    /**
     * 響應異常
     * @param e
     * @return
     */
    public static JsonResult fail(ServiceException e){
        return fail(e.getStatusCode(),e.getMessage());
    }

    /**
     * 響應異常
     * @param statusCode
     * @param message
     * @return
     */
    public static JsonResult fail(Integer statusCode,String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.statusCode=statusCode;
        jsonResult.message=message;
        return jsonResult;
    }
}

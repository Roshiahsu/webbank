package cn.tedu.webbank.exception;

/**
 * @ClassName ServiceException
 * @Version 1.0
 * @Description 自定義異常類
 * @Date 2022/10/20、上午2:34
 */
public class ServiceException extends RuntimeException{


    /**
     * 異常狀態碼，
     * 拋異常的時候傳入
     */
    private Integer statusCode;

    public ServiceException(Integer statusCode,String message) {
        super(message);
        this.statusCode=statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

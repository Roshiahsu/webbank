package cn.tedu.webbank.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserLoginDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、下午7:21
 */
@Data
public class UserLoginDTO implements Serializable {
    /**
     * 用戶名稱
     */
    private String username;
    /**
     * 用戶密碼
     */
    private String password;
    /**
     * 身分證號，登入帳號
     */
    private String identityNumber;
}

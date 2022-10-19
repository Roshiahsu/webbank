package cn.tedu.webbank.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、上午1:28
 */
@Data
public class User implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 身分證號
     */
    private String identityNumber;
    /**
     * 使用者姓名
     */
    private String username;
    /**
     * 使用者密碼
     */
    private String password;
    /**
     * 帳號餘額
     */
    private Long balance;
    /**
     * 登入時間
     */
    private LocalDateTime loginTime;
    /**
     * 登出時間
     */
    private LocalDateTime logoutTime;


}

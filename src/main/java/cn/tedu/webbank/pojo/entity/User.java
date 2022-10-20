package cn.tedu.webbank.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     * 身分證號
     */
    @ApiModelProperty(value = "身份證號")
    private String identityNumber;
    /**
     * 使用者姓名
     */
    @ApiModelProperty(value = "使用者名稱")
    private String username;
    /**
     * 使用者密碼
     */
    @ApiModelProperty(value = "密碼")
    private String password;
    /**
     * 帳號餘額
     */
    @ApiModelProperty(value = "帳號餘額")
    private Long balance;
    /**
     * 登入時間
     */
    private LocalDateTime loginTime;
    /**
     * 登出時間
     */
    private LocalDateTime logoutTime;

    @ApiModelProperty(value = "帳號是否啟用")
    private Integer enable;

}

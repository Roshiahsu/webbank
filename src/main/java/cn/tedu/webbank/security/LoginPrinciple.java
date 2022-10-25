package cn.tedu.webbank.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LoginPrinciple
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/25、下午10:47
 */
@Data
public class LoginPrinciple implements Serializable {
    @ApiModelProperty(value = "使用者Id")
    private Long id;
    /**
     * 用戶名稱
     */
    @ApiModelProperty(value = "使用者名稱")
    private String username;
    /**
     * 身分證號，登入帳號
     */
    @ApiModelProperty(value = "使用者分份證號")
    private String identityNumber;

    public LoginPrinciple(Long id, String username, String identityNumber) {
        this.id = id;
        this.username = username;
        this.identityNumber = identityNumber;
    }
}

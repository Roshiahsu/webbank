package cn.tedu.webbank.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UserLoginVO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、下午7:43
 */
@Data
public class UserLoginVO implements Serializable {

    private Long id;
    /**
     * 用戶名稱
     */
    @ApiModelProperty(value = "使用者名稱")
    private String username;
    /**
     * 用戶密碼
     */
    @ApiModelProperty(value = "密碼")
    private String password;
    /**
     * 身分證號，登入帳號
     */
    @ApiModelProperty(value = "身分證號")
    private String identityNumber;
    /**
     * 是否啟用
     */
    @ApiModelProperty(value = "帳號是否啟用")
    private Integer enable;

    /**
     * 權限
     */
    @ApiModelProperty(value = "權限")
    private List<String> permissions;
}

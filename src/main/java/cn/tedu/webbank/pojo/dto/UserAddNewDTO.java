package cn.tedu.webbank.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @ClassName UserAddNewDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、上午1:24
 */
@Data
@ApiModel("新增用戶訊息DTO")
public class UserAddNewDTO implements Serializable {

    /**
     * 用戶名稱
     */
    @ApiModelProperty("用戶名稱")
    private String username;
    /**
     * 用戶密碼
     */
    @ApiModelProperty("用戶密碼")
    private String password;
    /**
     * 身分證號，登入帳號
     */
    @ApiModelProperty("身分證號，登入帳號")
    @Pattern(regexp = "[A-Za-z][12]\\d{8}")
    private String identityNumber;


}

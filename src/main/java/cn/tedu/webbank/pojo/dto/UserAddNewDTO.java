package cn.tedu.webbank.pojo.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * @ClassName UserAddNewDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、上午1:24
 */
@Data
public class UserAddNewDTO implements Serializable {

    /**
     * 用戶名稱
     */
    private String username;
    /**
     * 用戶密碼
     */
    private String password;
    /**
     * 身分證號
     */
    private String identityNumber;


}

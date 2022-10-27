package cn.tedu.webbank.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName LoginLog
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/26、下午8:25
 */
@Data
public class LoginLog implements Serializable {

    /**
     * ID
     */
    private Long id;
    /**
     * 用戶id
     */
    private Long userId;
    /**
     * 登入時間
     */
    private LocalDateTime loginTime;
    /**
     * 登出時間
     */
    private LocalDateTime logoutTime;
}

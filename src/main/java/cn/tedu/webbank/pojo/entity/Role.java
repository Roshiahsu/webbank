package cn.tedu.webbank.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Role
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/27、上午1:45
 */
@Data
public class Role implements Serializable {

    private Long id;
    /**
     * 用戶id
     */
    private Long userId;
    /**
     * 用戶身份
     */
    private Long roleId;

}

package cn.tedu.webbank.web;

import cn.tedu.webbank.pojo.entity.LoginLog;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Utils
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/26、下午9:37
 */
@Data
public class Utils {

    /**
     * 新增用戶登入時間
     * @param id 用戶id
     * @return
     */
    public static LoginLog createdLocalDateTime(Long id){
        LocalDateTime currentTime = LocalDateTime.now();
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(id);
        loginLog.setLoginTime(currentTime);

        return loginLog;
    }
}

package cn.tedu.webbank.mapper;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、上午1:27
 */
@Repository
public interface UserMapper {
    /**
     * 插入使用者訊息
     * @param user 用戶詳情
     * @return
     */
    int insert(User user);

    /**
     * 使用用戶名統計數量
     * @param username
     * @return
     */
    int countByUsername(String username);
}

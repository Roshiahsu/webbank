package cn.tedu.webbank.mapper;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.pojo.vo.UserLoginVO;
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
     * 使用UserAddNewDTO統計數量
     * @param userAddNewDTO
     * @return
     */
    int countByUserAddNewDTO(UserAddNewDTO userAddNewDTO);

    /**
     * 使用用戶名查詢用戶資料
     * @param username
     * @return
     */
    UserLoginVO getByUsername(String username);

    /**
     * 根據用戶名統計數量
     * @param username
     * @return
     */
    int countByUsername(String username);
}

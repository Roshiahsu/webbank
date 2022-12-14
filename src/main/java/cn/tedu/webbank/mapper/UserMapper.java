package cn.tedu.webbank.mapper;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.entity.LoginLog;
import cn.tedu.webbank.pojo.entity.Role;
import cn.tedu.webbank.pojo.entity.Transaction;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.pojo.vo.UserLoginVO;
import cn.tedu.webbank.pojo.vo.UserTransactionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 插入使用者登入或登出時間
     * @param loginLog
     * @return
     */
    int insertLoginLog(LoginLog loginLog);

    /**
     * 插入交易資料表
     * @param transaction
     * @return
     */
    int insertTransactionInfo(Transaction transaction);

    /**
     * 添加用戶身份
     * @param role 資料詳情
     * @return
     */
    int insertUserRole(Role role);

    /**
     * 用戶存錢
     * @param user 用戶詳情
     * @return
     */
    int update(User user);

    /**
     * 使用id獲取用戶訊息
     * @param id 用戶id
     * @return
     */
    User getByID(Long id);

    /**
     * 使用id獲取交易列表
     * @param userId 用戶id
     * @return
     */
    List<UserTransactionInfoVO> listTransactionInfoById(Long userId);

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

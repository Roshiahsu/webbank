package cn.tedu.webbank.service;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.dto.UserLoginDTO;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.pojo.vo.UserTransactionInfoVO;
import cn.tedu.webbank.security.LoginPrinciple;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName IUserService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/19、下午11:54
 */

@Transactional
public interface IUserService {

    /**
     * 新增用戶
     * @param userAddNewDTO 用戶註冊資料
     */
    void insert(UserAddNewDTO userAddNewDTO);

    /**
     * 用戶登入
     * @param userLoginDTO 登入詳情
     * @return
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 用戶存錢
     * @param money 存放金額
     * @param loginPrinciple
     * @return
     */
    User deposit(Long money, LoginPrinciple loginPrinciple);

    /**
     * 用戶領錢
     * @param money 領取金額
     * @param loginPrinciple
     * @return
     */
    User cashOut(Long money, LoginPrinciple loginPrinciple);

    /**
     * 查詢交易紀錄
     * @param loginPrinciple
     * @return
     */
    List<UserTransactionInfoVO> transactionInfo(LoginPrinciple loginPrinciple);

    void passwordUpdate(String password,LoginPrinciple loginPrinciple);
}

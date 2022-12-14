package cn.tedu.webbank.service.Impl;

import cn.tedu.webbank.exception.ServiceException;
import cn.tedu.webbank.mapper.UserMapper;
import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.dto.UserLoginDTO;
import cn.tedu.webbank.pojo.entity.LoginLog;
import cn.tedu.webbank.pojo.entity.Role;
import cn.tedu.webbank.pojo.entity.Transaction;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.pojo.vo.UserTransactionInfoVO;
import cn.tedu.webbank.security.AdminDetails;
import cn.tedu.webbank.security.LoginPrinciple;
import cn.tedu.webbank.service.IUserService;
import cn.tedu.webbank.util.JwtUtils;
import cn.tedu.webbank.web.ServiceCode;
import cn.tedu.webbank.web.Utils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/19、下午11:54
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * 新增使用者(註冊)
     * 1.查詢用戶是否已經註冊
     * 2.將收到的註冊資料複製到User
     * 3.將註冊資料寫入
     */
    @Override
    public void insert(UserAddNewDTO userAddNewDTO) {
        log.debug("開始insert使用者");
        /*查詢用戶是否已註冊*/
        int count = userMapper.countByUserAddNewDTO(userAddNewDTO);
        if (count > 0) {
            String message = "用戶名或身份證號已註冊";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        /*將DTO封裝成entity*/
        User user = new User();
        BeanUtils.copyProperties(userAddNewDTO, user);
        //密碼加密
        String encode = passwordEncoder.encode(userAddNewDTO.getPassword());
        user.setPassword(encode);
        //設定餘額
        user.setBalance(0L);
        //設定帳號是否可用
        user.setEnable(1);
        //設定用戶身份
        user.setRoleId(1);
        /*寫入用戶資料*/
        int row = userMapper.insert(user);
        if (row != 1) {
            String message = "資料寫入錯誤";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        log.debug("寫入完成");
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        log.debug("開始登入業務");
        String account = userLoginDTO.getIdentityNumber();
        String password = userLoginDTO.getPassword();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(account, password);
        log.debug("封裝數據authentication:{}", authentication);
        //獲取驗證完結果集
        Authentication loginResult = authenticationManager.authenticate(authentication);
        log.debug("獲取的用戶訊息>>>{}", loginResult);

        //獲取principal轉換成自定義類
        AdminDetails adminDetails = (AdminDetails) loginResult.getPrincipal();

        //獲取權限
        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();
        log.debug("獲取的用戶權限>>>{}", authorities);

        //獲取id
        Long id = adminDetails.getId();
        log.debug("獲取的用戶id>>>{}", id);

        //獲取username
        String username = adminDetails.getUsername();
        log.debug("獲取的用戶名>>>{}", username);

        //獲取的identityNumber
        String identityNumber = adminDetails.getIdentityNumber();
        log.debug("獲取的身分證>>>{}", identityNumber);

        //將權限轉換成JSON類型
        String authoritiesString = JSON.toJSONString(authorities);
        log.debug("管理員權限轉換成json{}", authoritiesString);

        //生成claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("id", id);
        claims.put("authorities", authoritiesString);
        claims.put("identityNumber", identityNumber);

        /*新增登入時間*/
        LoginLog loginLog = Utils.createdLocalDateTime(id);
        int row = userMapper.insertLoginLog(loginLog);
        if (row != 1) {
            String message = "伺服器忙碌中請稍候";
            log.debug("資料更新錯誤!!");
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        return JwtUtils.generate(claims);
    }

    @Override
    public User deposit(Long money, LoginPrinciple loginPrinciple) {
        log.debug("存款金額>>>{},用戶列表>>>{}", money, loginPrinciple);
        //獲取用戶id
        Long id = loginPrinciple.getId();
        //根據id獲取到的用戶訊息
        User user = userMapper.getByID(id);
        Long balance = user.getBalance();
        log.debug("用戶詳情>>>{}", user);

        balance = balance + money;
        user.setBalance(balance);

        /*將資料寫入交易資料表*/
        {
            Transaction transaction = new Transaction(id, "1", money, LocalDateTime.now());
            int row = userMapper.insertTransactionInfo(transaction);
            updateError(row);
        }

        /*更新用戶資料*/
        int row = userMapper.update(user);
        log.debug("受影響行數>>>{}", row);
        updateError(row);
        return user;
    }

    @Override
    public User cashOut(Long money, LoginPrinciple loginPrinciple) {
        log.debug("領錢金額>>>{},用戶列表>>>{}", money, loginPrinciple);
        //獲取用戶id
        Long id = loginPrinciple.getId();
        //根據id獲取到的用戶訊息
        User user = userMapper.getByID(id);
        Long balance = user.getBalance();
        log.debug("用戶詳情>>>{}", user);

        if (balance < money) {
            String message = "餘額不足";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        balance = balance - money;
        user.setBalance(balance);

        /*將資料寫入交易資料表*/
        {
            Transaction transaction = new Transaction(id, "2", money, LocalDateTime.now());
            int row = userMapper.insertTransactionInfo(transaction);
            updateError(row);
        }
        /*更新用戶資料*/
        int row = userMapper.update(user);
        log.debug("受影響行數>>>{}", row);
        updateError(row);
        return user;
    }

    /**
     * 如果Update回傳結果不是1則拋異常
     * @param row
     */
    public void updateError(int row){
        if (row != 1) {
            String message = "伺服器忙碌中請稍候";
            log.debug("資料更新錯誤!!");
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    @Override
    public List<UserTransactionInfoVO> transactionInfo(LoginPrinciple loginPrinciple) {
        Long id = loginPrinciple.getId();
        List<UserTransactionInfoVO> transactionInfo = userMapper.listTransactionInfoById(id);

        return transactionInfo;
    }

    @Override
    public void passwordUpdate(String password, LoginPrinciple loginPrinciple) {
        log.debug("開始更新密碼");
        Long id = loginPrinciple.getId();
        User user = userMapper.getByID(id);

        String encode = passwordEncoder.encode(password);

        user.setPassword(encode);

        int row = userMapper.update(user);
        if (row != 1) {
            String message = "伺服器忙碌中請稍候";
            log.debug("資料更新錯誤!!");
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }
}

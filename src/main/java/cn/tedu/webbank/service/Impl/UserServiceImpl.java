package cn.tedu.webbank.service.Impl;

import cn.tedu.webbank.exception.ServiceException;
import cn.tedu.webbank.mapper.UserMapper;
import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.dto.UserLoginDTO;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.pojo.vo.UserLoginVO;
import cn.tedu.webbank.security.AdminDetails;
import cn.tedu.webbank.service.IUserService;
import cn.tedu.webbank.util.JwtUtils;
import cn.tedu.webbank.web.ServiceCode;
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

import java.rmi.server.ServerCloneException;
import java.util.Collection;
import java.util.HashMap;
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

    @Override
    public void insert(UserAddNewDTO userAddNewDTO) {
        log.debug("開始insert使用者");

        //改名
        int count = userMapper.countByUserAddNewDTO(userAddNewDTO);
        if(count>0){
            String message = "用戶名或身份證號已註冊";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddNewDTO,user);
        String encode = passwordEncoder.encode(userAddNewDTO.getPassword());
        user.setPassword(encode);
        user.setBalance(0L);
        user.setEnable(1);

        int row = userMapper.insert(user);
        if(row!=1){
            String message = "資料寫入錯誤";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("寫入完成");
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        log.debug("開始登入業務");
        String account = userLoginDTO.getIdentityNumber();
        String password = userLoginDTO.getPassword();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(account,password);
        log.debug("封裝數據authentication:{}",authentication);
        //獲取驗證完結果集
        Authentication loginResult = authenticationManager.authenticate(authentication);
        log.debug("獲取的用戶訊息>>>{}",loginResult);
        //獲取principal轉換成自定義類
        AdminDetails adminDetails = (AdminDetails) loginResult.getPrincipal();
        //獲取權限
        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();
        log.debug("獲取的用戶權限>>>{}",authorities);
        //獲取id
        Long id = adminDetails.getId();
        log.debug("獲取的用戶id>>>{}",id);
        //獲取username
        String username = adminDetails.getUsername();
        log.debug("獲取的用戶名>>>{}",username);

        //將權限轉換成JSON類型
        String authoritiesString = JSON.toJSONString(authorities);
        log.debug("管理員權限轉換成json{}",authoritiesString);
        //生成claims
        Map<String,Object> claims =new HashMap<>();
        claims.put("username",username);
        claims.put("id",id);
        claims.put("authorities",authoritiesString);

        return JwtUtils.generate(claims);
    }
}

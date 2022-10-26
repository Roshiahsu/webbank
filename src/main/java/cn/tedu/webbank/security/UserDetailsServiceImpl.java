package cn.tedu.webbank.security;

import cn.tedu.webbank.exception.ServiceException;
import cn.tedu.webbank.mapper.UserMapper;
import cn.tedu.webbank.pojo.vo.UserLoginVO;
import cn.tedu.webbank.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserDetailsServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、下午7:59
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("{},spring security 自動getByUsername:{} 查詢用戶詳情",getClass().getName(),username);
        UserLoginVO userLogin = userMapper.getByUsername(username);

        if(userLogin !=null){
            log.debug("查詢到匹配的管理員訊息:{}",userLogin);
            List<String> permissions = userLogin.getPermissions();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
            AdminDetails adminDetails = new AdminDetails(
                    userLogin.getId(),
                    userLogin.getIdentityNumber(),
                    userLogin.getUsername(),
                    userLogin.getPassword(),
                    userLogin.getEnable()==1,
                    authorities
            );
            log.debug("userDetails:{}",adminDetails);
            return adminDetails;
        }
        return null;
    }
}

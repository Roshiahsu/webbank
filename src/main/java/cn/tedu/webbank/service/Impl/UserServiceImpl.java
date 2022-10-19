package cn.tedu.webbank.service.Impl;

import cn.tedu.webbank.exception.ServiceException;
import cn.tedu.webbank.mapper.UserMapper;
import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.service.IUserService;
import cn.tedu.webbank.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.ServerCloneException;

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
    private UserMapper userMapper;

    @Override
    public void insert(UserAddNewDTO userAddNewDTO) {
        log.debug("開始insert使用者");

        int count = userMapper.countByUsername(userAddNewDTO.getUsername());
        if(count>0){
            String message = "用戶名已被佔用";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddNewDTO,user);
        user.setBalance(0L);

        int row = userMapper.insert(user);
        if(row!=1){
            String message = "資料寫入錯誤";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("寫入完成");
    }
}

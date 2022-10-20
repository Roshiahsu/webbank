package cn.tedu.webbank.service;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.dto.UserLoginDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName IUserService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/19、下午11:54
 */


public interface IUserService {

    @Transactional
    void insert(UserAddNewDTO userAddNewDTO);

    String login(UserLoginDTO userLoginDTO);

}

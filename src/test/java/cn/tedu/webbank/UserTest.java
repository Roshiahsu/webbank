package cn.tedu.webbank;

import cn.tedu.webbank.mapper.UserMapper;
import cn.tedu.webbank.pojo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName UserTest
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、上午2:09
 */
@SpringBootTest
@Slf4j
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertTest(){
        User user = new User();
        user.setUsername("tom");
        user.setIdentityNumber("a123456789");
        user.setBalance(123456789L);
        int row = userMapper.insert(user);
        log.debug("新增完成");
    }
    @Test
    public void countByUsernameTest(){
        int count = userMapper.countByUsername("tom");
        log.debug("查詢到使用者>>>{}",count);
    }
}

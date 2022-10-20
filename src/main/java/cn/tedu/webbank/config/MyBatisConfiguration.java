package cn.tedu.webbank.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfiguration
 * @Version 1.0
 * @Description mybatis相關設定
 * @Date 2022/10/20、上午2:25
 */
@Configuration
@MapperScan("cn.tedu.webbank.mapper")
public class MyBatisConfiguration {
}

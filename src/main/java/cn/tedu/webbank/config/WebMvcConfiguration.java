package cn.tedu.webbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfiguration
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/27、下午11:43
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 設置被跨域的路徑,/**表示所有路徑
                .allowedOriginPatterns("*")//設置請求域名訪問跨域資源,*表示允許任何方法源
                .allowedMethods("*")//設置請求方式，*表示允許任何請求方式 get or post
                .allowedHeaders("*")//設置請求頭,*表示允許任何請求頭
                .allowCredentials(true)//允許發送cookie
                .maxAge(3600); //設置允許時間 單位ms
    }
}

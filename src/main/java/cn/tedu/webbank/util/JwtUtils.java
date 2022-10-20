package cn.tedu.webbank.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName JwtUtils
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、下午10:27
 */
@Slf4j
public class JwtUtils {

    /**
     * 密鑰
     */
    private static final String SECRET_KEY = "c8763";
    /**
     * 過期時間
     */
    private static final Long EXPIRED_IN_MINUTE= 24L*60;

    private JwtUtils() {
    }

    /**
     * 生成jwt
     * @param claims
     * @return
     */
    public static String generate(Map<String,Object> claims){
        //過期時間
        Date expired =new Date(System.currentTimeMillis()+60*1000*EXPIRED_IN_MINUTE);
        //生成jwt
        log.debug("開始生成jwt");
        String jwt=Jwts.builder()
                .setHeaderParam("type","jwt")
                .setHeaderParam("alg","HS256")
                .setClaims(claims)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();

        log.debug("生成JWT數據：{}",jwt);

        return jwt;
    }

    /**
     * 解析JWT
     * @param jwt
     * @return
     */

    public static Claims parseJwt(String jwt){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(jwt).getBody();
    }

}

package cn.tedu.webbank.filter;

import cn.tedu.webbank.security.LoginPrinciple;
import cn.tedu.webbank.util.JwtUtils;
import cn.tedu.webbank.web.JsonResult;
import cn.tedu.webbank.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.ContentHandler;
import java.util.List;

/**
 * @ClassName JwtAuthorizationFilter
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/25、下午10:13
 */
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("啟動過濾器!!!");
        //清除上下文
        SecurityContextHolder.clearContext();
        log.debug("清除上下文");

        log.debug("獲取JWT");
        //從請求頭獲取jwt
        String jwt = request.getHeader("Authorization");

        if(!StringUtils.hasText(jwt)){
            //不帶有jwt的請求直接放行
            log.debug("不包含jwt，直接放行");
            filterChain.doFilter(request,response);
            return;
        }

        Claims claims =null;

        try {
            claims = JwtUtils.parseJwt(jwt);
        } catch (ExpiredJwtException e) {
            log.debug("解析JWT失敗，JWT過期:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，登錄訊息已過期，請重新登入!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_EXPIRED, errorMessage);
            return;
        }catch (MalformedJwtException e){
            log.debug("解析JWT失敗，JWT數據有誤:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，獲取登入訊息失敗!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_INVALID, errorMessage);
            return;
        }catch (SignatureException e){
            log.debug("解析JWT失敗，密鑰解析失敗:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，獲取登入訊息失敗!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_INVALID, errorMessage);
            return;
        }catch (Throwable e){
            log.debug("解析JWT失敗，錯誤詳情:{},{}",e.getClass().getName(),e.getMessage());
            String errorMessage= "登入失敗，獲取登入訊息失敗!!";
            jwtExceptionResponse(response,ServiceCode.ERR_JWT_INVALID, errorMessage);
            return;
        }
        //獲取用戶訊息
        Object id = claims.get("id");
        log.debug("獲取用戶id>>>{}",id);
        Object username = claims.get("username");
        log.debug("獲取用戶名>>>{}",username);
        Object identityNumber = claims.get("identityNumber");
        log.debug("獲取用戶身分證號>>>{}",identityNumber);
        Object authoritiesString = claims.get("authorities");
        //將authorities從JSON格式轉換成List
        List<SimpleGrantedAuthority> authorities = JSON.parseArray(authoritiesString.toString(),SimpleGrantedAuthority.class);
        //封裝用戶訊息
        LoginPrinciple loginPrinciple = new LoginPrinciple(Long.parseLong(id.toString()), username.toString(), identityNumber.toString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginPrinciple,null,authorities);

        log.debug("將用戶訊息放入上下文");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        //將用戶訊息加入上下文
        securityContext.setAuthentication(authentication);


        filterChain.doFilter(request,response);

    }

    private void jwtExceptionResponse(HttpServletResponse response, Integer serviceCode,String errorMessage) throws IOException {
        JsonResult jsonResult = JsonResult.fail(serviceCode, errorMessage);
        String jsonResultString = JSON.toJSONString(jsonResult);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(jsonResultString);
    }
}

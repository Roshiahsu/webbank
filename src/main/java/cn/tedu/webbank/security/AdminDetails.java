package cn.tedu.webbank.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName AdminDetails
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/20、下午8:29
 */
@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdminDetails extends User {

    private Long id;
    /**
     * 身分證號，登入帳號
     */
    private String identityNumber;

    public AdminDetails(
            Long id,
            String identityNumber,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.id= id;
        this.identityNumber=identityNumber;
    }
}

package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;   // 사용자 권한정보 : GrantedAuthority는 Spring Security에서 사용하는 역할(Role)을 나타내기 위해 사용되는 인터페이스입니다. 이 필드는 Collection 타입을 사용하여 다중 권한을 표현할 수 있습니다.
    private final String nickname;


    public AuthUser(Long id, String email, UserRole role, String nickname) {
        this.id = id;
        this.email = email;
        /**
         * Spring Security에서 권한을 나타내는 GrantedAuthority 객체를 생성하여 해당 역할을 권한으로 사용합니다. List.of()를 사용하여 단일 권한을 가진 리스트로 사용자 권한을 초기화 합니다.
         * SimpleGrantedAuthority 클래스: GrantedAuthority를 구현한 클래스입니다. 이 클래스는 문자열 기반의 권한을 표현합니다. 예를 들어 "ROLE_ADMIN"과 같은 문자열로 역할을 정의하여 이를 권한으로 관리합니다.
         */
        this.authorities = List.of(new SimpleGrantedAuthority(role.name()));
        this.nickname = nickname;
    }
}

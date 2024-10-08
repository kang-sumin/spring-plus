package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String nickname;

    public User(String email, String password, UserRole userRole, String nickname) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    private User(Long id, String email, UserRole userRole, String nickname) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getAuthorities()   // getAuthorities() 메서드를 통해서 authUser 객체에서 사용자의 권한 목록을 가져옵니다.
                        .stream()   // stream() 메서드를 통해 리스트를 스트림으로 변환합니다.
                        .map(GrantedAuthority::getAuthority)    // GrantedAuthority의 getAuthority() 메서드를 통해 권한을 문자열(String)으로 반환하여 가져옵니다. ex) ROLE_USER -> "ROLE_USER"
                        .map(UserRole::of)  // 권한 이름("ROLE_USER")이 Enum UserRole 클래스의 상수에 해당하는지(Enum 값과 일치한지) 확인하고 UserRole로 변환합니다.
                        .findFirst()    // 스트림 조건에 맞는 첫 번째 요소를 반환합니다. Optional<UserRole> 타입으로 반환됩니다.
                        .orElseThrow(() -> new InvalidRequestException("UserRole not found")),  //Optional 객체가 null일 경우 예외를 발생시킵니다.
                authUser.getNickname());
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }

    // 유지보수 차원에서 추가된 nickname 변경 메서드 추가
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}

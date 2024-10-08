package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    /**
     * @AuthenticationPrincipal 어노테이션을 사용하여 컨트롤러에서 AuthUser 객체를 받아올 수 있습니다.
     * @AuthenticationPrincipal 을 사용하면 Spring Security에서 제공하는 기본적인 인증이된 사용자 정보(AuthUser)를 추출할 수 있기때문에 WebConfig 클래스의 HandlerMethodArgumentResolver를 통해 AuthUserArgumentResolver라는 커스텀 리졸버로 추가하지 않아도 됩니다.
     * AuthUserArgumentResolver는 Spring Security를 사용하지 않을때 인증된 사용자의 정보를 @Auth 어노테이션을 통해 컨트롤러 메서드 인자에 간단하게 주입할 수 있는 리졸버 역할을 합니다.
     * @AuthenticationPrincipal을 사용하면 Spring Security에서 제공하는 기본적인 사용자 정보 추출 기능(Spring Security의 SecurityContextHolder에서 인증된 사용자 정보를 꺼낼 수 있음)을 사용할 수 있습니다. 이 경우 WebConfig에서 HandlerMethodArgumentResolver를 별도로 등록하지 않아도 됩니다. 즉, Spring Security의 기본 기능을 활용하여 더 간편하게 사용자 정보를 주입받을 수 있습니다.
     * 하지만 Spring Security를 사용한다 하여 WebConfig가 필요 없는것은 아닙니다. 서비스에서 커스텀해야할 맞춤형 로직이 필요할 경우 WebConfig의 HandlerMethdodArgumentResolver를 사용할 수 있습니다.
     * 예를 들어 사용자 인증 정보에서 추가적인 변환 등의 로직은 커스텀 리졸버(AuthUserArgumentResolver)를 정의하여 WebConfig에 등록하여 사용할 수 있습니다.
     */
    @PutMapping("/users")
    public void changePassword(@AuthenticationPrincipal AuthUser authUser, @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }
}

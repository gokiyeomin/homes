package team.gokiyeonmin.imacheater.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.user.dto.req.UserUpdateRequest;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserResponse;
import team.gokiyeonmin.imacheater.domain.user.service.UserService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public UserResponse getSimpleUser(
            @UserId Long userId
    ) {
        return userService.getSimpleUser(userId);
    }

    @PutMapping("/api/users")
    public UserResponse updateSimpleUser(
            @UserId Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return userService.updateSimpleUser(userId, request);
    }
}

package team.gokiyeonmin.imacheater.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserSimpleResponse;
import team.gokiyeonmin.imacheater.domain.user.service.UserService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public UserSimpleResponse getSimpleUser(
            @UserId Long userId
    ) {
        return userService.getSimpleUser(userId);
    }
}

package team.gokiyeonmin.imacheater.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserSimpleResponse;
import team.gokiyeonmin.imacheater.domain.user.service.UserService;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetail;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/user")
    public UserSimpleResponse getSimpleUser(
            @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return userService.getSimpleUser(userDetail);
    }
}

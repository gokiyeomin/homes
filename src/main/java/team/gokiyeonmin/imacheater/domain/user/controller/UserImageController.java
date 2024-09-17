package team.gokiyeonmin.imacheater.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserImageResponse;
import team.gokiyeonmin.imacheater.domain.user.service.UserImageService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;

@RestController
@RequiredArgsConstructor
public class UserImageController {

    private final UserImageService userImageService;

    @GetMapping("/api/users/image")
    public UserImageResponse getUserImage(
            @UserId Long userId
    ) {
        return userImageService.getUserImage(userId);
    }

    @PutMapping("/api/users/image")
    public UserImageResponse updateUserImage(
            @UserId Long userId,
            @RequestParam("image") MultipartFile image
    ) {
        return userImageService.updateUserImage(userId, image);
    }
}

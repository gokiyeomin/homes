package team.gokiyeonmin.imacheater.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.gokiyeonmin.imacheater.domain.user.dto.res.UserImageResponse;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.entity.UserImage;
import team.gokiyeonmin.imacheater.domain.user.event.DeleteUploadedImageEvent;
import team.gokiyeonmin.imacheater.domain.user.repository.UserImageRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.util.ImageUtil;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserImageRepository userImageRepository;
    private final ImageUtil imageUtil;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public UserImage createUserImage(User user, String url) {
        UserImage userImage = UserImage.builder()
                .user(user)
                .url(url)
                .build();

        return userImageRepository.save(userImage);
    }

    @Transactional(readOnly = true)
    public UserImageResponse getUserImage(Long userId) {
        String url = userImageRepository.findByUser_Id(userId)
                .map(UserImage::getUrl)
                .orElse(null);

        return new UserImageResponse(url);
    }

    @Transactional
    public UserImageResponse updateUserImage(Long userId, MultipartFile image) {
        String uploadedImageUrl = imageUtil.uploadImage("user", image);

        // 이미지는 사용자마다 1개 무조건 생성 -> 이미지가 없다면, 유저가 없다.
        UserImage userImage = userImageRepository.findByUser_Id(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        userImage.updateUrl(uploadedImageUrl);

        publisher.publishEvent(new DeleteUploadedImageEvent(uploadedImageUrl));

        return new UserImageResponse(uploadedImageUrl);
    }
}

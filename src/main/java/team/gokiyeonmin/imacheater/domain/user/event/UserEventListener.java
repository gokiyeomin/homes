package team.gokiyeonmin.imacheater.domain.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.gokiyeonmin.imacheater.global.util.ImageUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final ImageUtil imageUtil;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void deleteUploadedImage(DeleteUploadedImageEvent event) {
        String uploadImageUrl = event.getUrl();
        log.info("이미지 삭제 이벤트: {}", uploadImageUrl);

        imageUtil.deleteImage(uploadImageUrl);
    }
}

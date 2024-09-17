package team.gokiyeonmin.imacheater.domain.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.gokiyeonmin.imacheater.global.util.S3Util;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final S3Util s3Util;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void rollbackUploadedImage(RollbackUploadedImageEvent event) {
        String uploadImageUrl = event.getUrl();
        log.info("이미지 삭제 이벤트: {}", uploadImageUrl);

        s3Util.deleteImage(uploadImageUrl);
    }

    @Async
    @TransactionalEventListener
    public void deleteUploadedImage(DeleteUploadedImageEvent event) {
        String uploadImageUrl = event.getUrl();
        log.info("이미지 삭제 이벤트: {}", uploadImageUrl);

        s3Util.deleteImage(uploadImageUrl);
    }
}

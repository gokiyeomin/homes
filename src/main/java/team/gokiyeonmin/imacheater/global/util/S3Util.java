package team.gokiyeonmin.imacheater.global.util;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.io.InputStream;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Slf4j
public class S3Util {

    public static final String USER_IMAGE_FOLDER = "user";
    public static final String ITEM_IMAGE_FOLDER = "item";

    private final S3Operations s3Operations;
    private final String bucket;

    public S3Util(
            S3Operations s3Operations,
            @Value("${spring.cloud.aws.s3.bucket}") String bucket
    ) {
        this.s3Operations = s3Operations;
        this.bucket = bucket;
    }

    public String uploadImage(String folder, MultipartFile file) {
        log.info("S3에 이미지 업로드: {}", file.getOriginalFilename());

        if (file.getContentType() == null) {
            throw new BusinessException(ErrorCode.MISSING_CONTENT_TYPE);
        }

        final String imageType = getImageType(file.getContentType());
        final String imageName = folder + "/" + generateImageName(imageType);
        final ObjectMetadata metadata = new ObjectMetadata.Builder()
                .contentType(file.getContentType())
                .build();


        try (final InputStream inputStream = file.getInputStream()) {
            S3Resource resource = s3Operations.upload(bucket, imageName, inputStream, metadata);
            log.info("S3 이미지 업로드 성공: {}", resource.getURL());
            return resource.getURL().toString();
        } catch (Exception e) {
            log.error("S3 이미지 업로드 실패: {}", e.getMessage());
            throw new BusinessException(ErrorCode.S3_UPLOAD_ERROR);
        }
    }

    public void deleteImage(String imageUrl) {
        log.info("S3 이미지 삭제: {}", imageUrl);

        try {
            s3Operations.deleteObject(getS3Url(imageUrl));
            log.info("S3 이미지 삭제 성공: {}", imageUrl);
        } catch (Exception e) {
            log.error("S3 이미지 삭제 실패: {}", e.getMessage());
            throw new BusinessException(ErrorCode.S3_DELETE_ERROR);
        }
    }

    private String getImageType(String contentType) {
        String[] split = contentType.split("/");
        if (!split[0].equals("multipart")) {
            throw new BusinessException(ErrorCode.ILLEGAL_CONTENT_TYPE);
        }
        return split[1];
    }

    private String generateImageName(String imageType) {
        return UUID.randomUUID() + "." + imageType;
    }

    private String getS3Url(String imageUrl) {
        String[] split = imageUrl.split("/");

        return "s3://" + bucket + "/" + Stream.of(split).skip(3).reduce((a, b) -> a + "/" + b)
                .orElseThrow(() -> new BusinessException(ErrorCode.ILLEGAL_S3_URL));
    }
}

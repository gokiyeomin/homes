package team.gokiyeonmin.imacheater.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 기본 예외가 존재하지만, 이걸 분리한 이유는 프론트에서 에러를 편하게 처리하기 위함입니다.<br>
 * 예를 들면, 400 에러가 발생했을 때, 프론트에서 message만 출력하면 바로 사용자에게 보여줄 수 있습니다. <br>
 * <br>
 * 코드 네이밍 규칙 <br>
 * 앞 3자리는 HttpStatus 코드를 따르고, XYZ 형태로 구성합니다. <br>
 * X: 0__(기본), 1__(유저), 2__(매물), 3__(채팅), <br>
 * YZ: 00~99 (코드 순서대로 부여) <br>
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400 Bad Request
    ILLEGAL_ARGUMENT(400_000, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    MISSING_TOKEN(400_001, HttpStatus.BAD_REQUEST, "토큰이 누락되었습니다."),
    MISSING_REQUEST_PARAMETER(400_002, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    MISSING_CONTENT_TYPE(400_003, HttpStatus.BAD_REQUEST, "컨텐츠 타입이 누락되었습니다."),
    ILLEGAL_CONTENT_TYPE(400_004, HttpStatus.BAD_REQUEST, "잘못된 컨텐츠 타입입니다."),
    ILLEGAL_S3_URL(400_005, HttpStatus.BAD_REQUEST, "S3 URL이 잘못되었습니다."),
    ALREADY_EXISTS_USERNAME(400_100, HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    ALREADY_EXISTS_NICKNAME(400_101, HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_USER(400_102, HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),

    // 401 Unauthorized
    ILLEGAL_TOKEN(401_000, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401_001, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(401_002, HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    MALFORMED_TOKEN(401_003, HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    UNKNOWN_TOKEN(401_004, HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),

    // 403 Forbidden
    ACCESS_DENIED(403_000, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // 404 Not Found
    NOT_FOUND_USER_ROLE(400_100, HttpStatus.NOT_FOUND, "사용자 권한이 존재하지 않습니다."),

    // 405 Method Not Allowed

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500_000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 요청 중 오류가 발생했습니다."),

    // 502 Bad Gateway
    EXTERNAL_SERVER_ERROR(502_000, HttpStatus.BAD_GATEWAY, "서버 외부 요청 중 오류가 발생했습니다."),
    S3_UPLOAD_ERROR(502_001, HttpStatus.BAD_GATEWAY, "S3 이미지 업로드 중 오류가 발생했습니다."),
    S3_DELETE_ERROR(502_002, HttpStatus.BAD_GATEWAY, "S3 이미지 삭제 중 오류가 발생했습니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}

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
    MISSING_REQUEST_PARAMETER(400_001, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    ALREADY_EXISTS_USERNAME(400_100, HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    ALREADY_EXISTS_NICKNAME(400_101, HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),

    // 401 Unauthorized


    // 403 Forbidden

    // 404 Not Found
    NOT_FOUND_USER_ROLE(400_100, HttpStatus.NOT_FOUND, "사용자 권한이 존재하지 않습니다."),

    // 405 Method Not Allowed

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(500_000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 요청 중 오류가 발생했습니다."),

    // 502 Bad Gateway
    EXTERNAL_SERVER_ERROR(502_000, HttpStatus.BAD_GATEWAY, "서버 외부 요청 중 오류가 발생했습니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}

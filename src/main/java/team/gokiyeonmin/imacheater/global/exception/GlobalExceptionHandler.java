package team.gokiyeonmin.imacheater.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("Missing Servlet Request Parameter Exception: {}", e.getMessage());
        System.out.println("Missing Servlet Request Parameter Exception: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestPartExceptionHandler(MissingServletRequestPartException e) {
        log.error("Missing Servlet Request Part Exception: {}", e.getMessage());
        System.out.println("Missing Servlet Request Part Exception: " + e.getMessage());
        System.out.println("e.getDetailMessageArguments() = " + Arrays.toString(e.getDetailMessageArguments()));
        final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("Method Argument Not Valid Exception: {}", e.getMessage());
        System.out.println("Method Argument Not Valid Exception: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER, e.getBindingResult());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("Illegal Argument Exception: {}", e.getMessage());
        System.out.println("Illegal Argument Exception: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException e) {
        log.error("Business Exception: {}", e.getMessage());
        System.out.println("Business Exception: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> tokenExceptionHandler(TokenException e) {
        log.error("Token Exception: {}", e.getMessage());
        System.out.println("Token Exception: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();
        log.error("HttpMediaTypeNotSupportedException: {}", e.getMessage());
//        System.out.println("HttpMediaTypeNotSupportedException: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED);
        return ResponseEntity.badRequest().body(response);
    }
}

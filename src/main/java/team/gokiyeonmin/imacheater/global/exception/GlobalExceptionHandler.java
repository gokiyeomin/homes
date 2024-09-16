package team.gokiyeonmin.imacheater.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER, e.getBindingResult());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT);
        return ResponseEntity.badRequest().body(response);
    }
}

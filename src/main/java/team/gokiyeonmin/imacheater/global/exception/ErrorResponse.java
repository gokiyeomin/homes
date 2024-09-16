package team.gokiyeonmin.imacheater.global.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
    private final List<FieldError> errors;

    @Builder
    private ErrorResponse(final HttpStatus httpStatus, final int code, final String message, final List<FieldError> errors) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(message)
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(FieldError.of(bindingResult))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}

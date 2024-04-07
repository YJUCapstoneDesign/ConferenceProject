package team.broadcast.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.exception.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .code("INVALID_REQUEST")
                .reason(errorMessage)
                .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }

    /**
     * @param e - 커스텀 예외를 바인딩
     * @return body로
     */

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        CustomErrorCode errorCode = e.getCustomErrorCode();
        ErrorResponse errorResponse = errorCode.getErrorResponse();

        return ResponseEntity
                .status(HttpStatus.valueOf(errorResponse.getStatus()))
                .body(errorResponse);
    }
}

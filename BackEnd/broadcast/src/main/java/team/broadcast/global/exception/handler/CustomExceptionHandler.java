package team.broadcast.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.broadcast.global.exception.CustomErrorCode;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.exception.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {


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

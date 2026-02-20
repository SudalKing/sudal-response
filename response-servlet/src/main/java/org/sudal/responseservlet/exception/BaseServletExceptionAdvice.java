package org.sudal.responseservlet.exception;

import lombok.extern.slf4j.Slf4j;
import org.sudal.responsecore.model.exception.BusinessException;
import org.sudal.responsecore.model.exception.ErrorObject;
import org.sudal.responsecore.model.response.RestApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public abstract class BaseServletExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<RestApiResponse<Void>> handlerBusinessException(BusinessException ex) {
        ErrorObject error = ex.getErrorObject();
        log.warn("[BusinessException] Code: {}, Message: {}", error.getCode(), ex.getMessage());

        return ResponseEntity
                .status(error.getStatus())
                .body(RestApiResponse.of(ex.getMessage(), error.getStatus(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<RestApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(400)
                .body(RestApiResponse.badRequest(message));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<RestApiResponse<Void>> handleException(Exception ex) {
        log.error("[UnhandledException]", ex);
        return ResponseEntity
                .status(500)
                .body(RestApiResponse.error("시스템 내부 오류가 발생했습니다."));
    }
}

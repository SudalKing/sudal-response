package org.sudal.responsereactive.exception;

import lombok.extern.slf4j.Slf4j;
import org.sudal.responsecore.model.exception.BusinessException;
import org.sudal.responsecore.model.response.RestApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class BaseReactiveExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    protected Mono<ResponseEntity<RestApiResponse<Void>>> handleBusinessException(BusinessException ex) {
        return Mono.just(ResponseEntity.status(ex.getErrorObject().getStatus())
                .body(RestApiResponse.of(ex.getMessage(), ex.getErrorObject().getStatus(), null)));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    protected Mono<ResponseEntity<RestApiResponse<Void>>> handleBindException(WebExchangeBindException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Mono.just(ResponseEntity.status(400).body(RestApiResponse.badRequest(message)));
    }
}

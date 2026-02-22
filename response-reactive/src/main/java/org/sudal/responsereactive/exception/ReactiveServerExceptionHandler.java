package org.sudal.responsereactive.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.sudal.responsecore.model.exception.BusinessException;
import org.sudal.responsecore.model.response.RestApiResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveServerExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ReactiveServerExceptionHandler(ErrorAttributes errorAttributes,
                                          WebProperties.Resources resources,
                                          ApplicationContext applicationContext,
                                          ServerCodecConfigurer codecConfigurer
    ) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
        this.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(@NonNull ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);

        int status = 500;
        RestApiResponse<Void> body;

        if (error instanceof BusinessException be) {
            status = be.getErrorObject().getStatus();
            body = RestApiResponse.of(be.getMessage(), status, null);
            log.warn("[BusinessException] {}", be.getMessage());
        } else {
            body = RestApiResponse.error("시스템 내부 오류가 발생했습니다.");
            log.error("[UnhandledException]", error);
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }
}

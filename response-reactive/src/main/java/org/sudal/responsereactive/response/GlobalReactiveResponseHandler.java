package org.sudal.responsereactive.response;

import org.sudal.responsecore.model.response.RestApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class GlobalReactiveResponseHandler extends ResponseBodyResultHandler {

    public GlobalReactiveResponseHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver) {
        super(writers, resolver);
        setOrder(0);
    }

    @Override
    public boolean supports(HandlerResult result) {
        return checkType(result.getReturnTypeSource());
    }

    private boolean checkType(MethodParameter returnType) {
        Class<?> type = returnType.getParameterType();
        return RestApiResponse.class.isAssignableFrom(type) ||
                Mono.class.isAssignableFrom(type) ||
                Flux.class.isAssignableFrom(type);
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Object body = result.getReturnValue();

        if (body instanceof Mono<?> monoBody) {
            return monoBody.flatMap(actualBody -> {
                applyStatus(exchange, actualBody);
                return super.handleResult(exchange, result);
            });
        }

        applyStatus(exchange, body);
        return super.handleResult(exchange, result);
    }

    private void applyStatus(ServerWebExchange exchange, Object body) {
        if (body instanceof RestApiResponse<?> apiResponse) {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(apiResponse.getStatus()));
        }
    }
}

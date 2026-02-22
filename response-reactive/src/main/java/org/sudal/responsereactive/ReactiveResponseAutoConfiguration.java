package org.sudal.responsereactive;

import org.sudal.responsereactive.exception.DefaultReactiveExceptionAdvice;
import org.sudal.responsereactive.exception.ReactiveServerExceptionHandler;
import org.sudal.responsereactive.response.GlobalReactiveControllerResponseHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Import({
        DefaultReactiveExceptionAdvice.class,
        GlobalReactiveControllerResponseHandler.class
})
public class ReactiveResponseAutoConfiguration {

    @Bean
    public GlobalReactiveControllerResponseHandler globalReactiveResponseHandler(
            ServerCodecConfigurer serverCodecConfigurer,
            RequestedContentTypeResolver requestedContentTypeResolver
    ) {
        return new GlobalReactiveControllerResponseHandler(
                serverCodecConfigurer.getWriters(),
                requestedContentTypeResolver
        );
    }

    @Bean
    @Order(-1)
    public ReactiveServerExceptionHandler serverReactiveExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties webProperties,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        return new ReactiveServerExceptionHandler(
                errorAttributes,
                webProperties.getResources(),
                applicationContext,
                serverCodecConfigurer
        );
    }
}

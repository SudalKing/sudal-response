package org.sudal.responsereactive;

import org.sudal.responsereactive.exception.DefaultReactiveExceptionAdvice;
import org.sudal.responsereactive.exception.ServerReactiveExceptionHandler;
import org.sudal.responsereactive.response.GlobalReactiveResponseHandler;
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
        GlobalReactiveResponseHandler.class
})
public class ReactiveResponseAutoConfiguration {

    @Bean
    public GlobalReactiveResponseHandler globalReactiveResponseHandler(
            ServerCodecConfigurer serverCodecConfigurer,
            RequestedContentTypeResolver requestedContentTypeResolver
    ) {
        return new GlobalReactiveResponseHandler(
                serverCodecConfigurer.getWriters(),
                requestedContentTypeResolver
        );
    }

    @Bean
    @Order(-1)
    public ServerReactiveExceptionHandler serverReactiveExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties webProperties,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        return new ServerReactiveExceptionHandler(
                errorAttributes,
                webProperties.getResources(),
                applicationContext,
                serverCodecConfigurer
        );
    }
}

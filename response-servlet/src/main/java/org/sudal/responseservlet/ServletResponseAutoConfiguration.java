package org.sudal.responseservlet;

import org.sudal.responseservlet.exception.DefaultServletExceptionAdvice;
import org.sudal.responseservlet.response.GlobalServletResponseAdvice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
        DefaultServletExceptionAdvice.class,
        GlobalServletResponseAdvice.class
})
public class ServletResponseAutoConfiguration {
}

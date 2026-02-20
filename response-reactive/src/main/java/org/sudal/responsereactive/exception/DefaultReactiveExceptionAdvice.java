package org.sudal.responsereactive.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ConditionalOnMissingBean(annotation = RestControllerAdvice.class)
public class DefaultReactiveExceptionAdvice extends BaseReactiveExceptionAdvice{
}

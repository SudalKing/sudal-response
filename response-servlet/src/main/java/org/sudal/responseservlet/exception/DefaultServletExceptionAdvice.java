package org.sudal.responseservlet.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ConditionalOnMissingBean(annotation = RestControllerAdvice.class)
public class DefaultServletExceptionAdvice extends BaseServletExceptionAdvice{
}

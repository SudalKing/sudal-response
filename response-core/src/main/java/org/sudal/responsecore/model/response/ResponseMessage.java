package org.sudal.responsecore.model.response;

import lombok.Getter;

import static java.net.HttpURLConnection.*;

@Getter
public enum ResponseMessage {
    OK(HTTP_OK, "OK", "성공") // 200
    , CREATE(HTTP_CREATED, "CREATED", "생성") // 201
    , NO_CONTENT(HTTP_NO_CONTENT, "NO_CONTENT", "반영") // 204

    , REDIRECT(HTTP_MOVED_TEMP, "REDIRECT", "리다이렉트")

    , BAD_REQUEST(HTTP_BAD_REQUEST, "BAD_REQUEST", "잘못된 요청") // 400
    , UNAUTHORIZED(HTTP_UNAUTHORIZED, "UNAUTHORIZED", "인증되지 않음") // 401
    , FORBIDDEN(HTTP_FORBIDDEN, "FORBIDDEN", "접근 불가") // 403
    , METHOD_NOT_FOUND(HTTP_NOT_FOUND, "NOT_FOUND", "지원하지 않는 기능") // 404
    , METHOD_NOT_ALLOWED(HTTP_BAD_METHOD, "NOT_ALLOWED", "지원하지 않는 메소드") // 405

    , INTERNAL_ERROR(HTTP_INTERNAL_ERROR,"INTERNAL_ERROR", "서버 에러") // 500
    ;

    private final int statusCode;
    private final String message;
    private final String description;

    ResponseMessage(final int statusCode,
                    final String message,
                    final String description
    ) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }
}

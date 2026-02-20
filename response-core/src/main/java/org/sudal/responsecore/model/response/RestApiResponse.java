package org.sudal.responsecore.model.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RestApiResponse<T> {
    private String message;
    private int status;
    private T data;

    private RestApiResponse() {}

    private RestApiResponse(final String message,
                            final int status,
                            final T data
    ) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static <T> RestApiResponse<T> of(final String message, final int status, T data) {
        return new RestApiResponse<>(message, status, data);
    }

    // Success 200
    public static <T> RestApiResponse<T> success() {
        return build(ResponseMessage.OK, null);
    }
    public static <T> RestApiResponse<T> success(T data) {
        return build(ResponseMessage.OK, data);
    }
    public static RestApiResponse<Void> success(String message) {
        return build(ResponseMessage.OK, null, message);
    }
    public static <T> RestApiResponse<T> success(T data, String message) {
        return build(ResponseMessage.OK, data, message);
    }

    // Read 200
    public static <T> RestApiResponse<T> read(T data) {
        return build(ResponseMessage.OK, data);
    }

    // Create 201
    public static <T> RestApiResponse<T> create() {
        return build(ResponseMessage.CREATE, null);
    }
    public static <T> RestApiResponse<T> create(T data) {
        return build(ResponseMessage.CREATE, data);
    }
    public static RestApiResponse<Void> create(String message) {
        return build(ResponseMessage.CREATE, null, message);
    }

    // Delete 204
    public static <T> RestApiResponse<T> delete() {
        return build(ResponseMessage.NO_CONTENT, null);
    }

    // Bad Request 400
    public static <T> RestApiResponse<T> badRequest() {
        return build(ResponseMessage.BAD_REQUEST, null);
    }
    public static RestApiResponse<Void> badRequest(String message) {
        return build(ResponseMessage.BAD_REQUEST, null, message);
    }
    public static <T> RestApiResponse<T> badRequest(T data, String message) {
        return build(ResponseMessage.BAD_REQUEST, data, message);
    }
    public static <T> RestApiResponse<T> badRequest(ResponseMessage responseMessage) {
        return build(responseMessage, null);
    }

    // Unauthorized 401
    public static <T> RestApiResponse<T> unAuthorize(String message) {
        return build(ResponseMessage.UNAUTHORIZED, null, message);
    }

    // Forbidden 403
    public static <T> RestApiResponse<T> forbidden(String message) {
        return build(ResponseMessage.FORBIDDEN, null, message);
    }

    // Error 500
    public static <T> RestApiResponse<T> error() {
        return build(ResponseMessage.INTERNAL_ERROR, null);
    }
    public static <T> RestApiResponse<T> error(String message) {
        return build(ResponseMessage.INTERNAL_ERROR, null, message);
    }
    public static <T> RestApiResponse<T> error(T data, String message) {
        return build(ResponseMessage.INTERNAL_ERROR, data, message);
    }

    private static <T> RestApiResponse<T> build(ResponseMessage responseMessage, T data) {
        return new RestApiResponse<>(responseMessage.getMessage(), responseMessage.getStatusCode(), data);
    }

    private static <T> RestApiResponse<T> build(ResponseMessage responseMessage, T data, String message) {
        return new RestApiResponse<>(message, responseMessage.getStatusCode(), data);
    }
}

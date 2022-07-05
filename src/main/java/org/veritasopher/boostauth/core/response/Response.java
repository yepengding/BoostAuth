package org.veritasopher.boostauth.core.response;

import lombok.Data;
import org.veritasopher.boostauth.core.dictionary.ResponseCode;

import java.io.Serializable;

/**
 * Response Util
 *
 * @param <T> data class
 * @author Yepeng Ding
 */
@Data
public class Response<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    private Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseCode.SUCCESS.getValue(), "ok", data);
    }

    public static <T> Response<T> success(String message) {
        return new Response<T>(ResponseCode.SUCCESS.getValue(), message, null);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<T>(ResponseCode.SUCCESS.getValue(), message, data);
    }

    public static <T> Response<T> failure(int code, String message) {
        return new Response<T>(code, message, null);
    }

    public static <T> Response<T> failure(String message) {
        return failure(ResponseCode.FAILURE.getValue(), message);
    }

    public String toString() {
        return "{code:\"" + code + "\", message:\"" + message + "\", data:\"" + (data != null ? data.toString() : null) + "\"}";
    }

}

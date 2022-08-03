package org.veritasopher.boostauth.core.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.veritasopher.boostauth.core.exception.type.AuthenticationException;
import org.veritasopher.boostauth.core.exception.type.AuthorizationException;
import org.veritasopher.boostauth.core.exception.type.BadRequestException;
import org.veritasopher.boostauth.core.exception.type.InternalException;
import org.veritasopher.boostauth.core.response.Response;

import java.util.Objects;


/**
 * Global Exception Handler
 *
 * @author Yepeng Ding
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Authentication Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public Response<String> authenticationExceptionHandler(AuthenticationException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }

    /**
     * Authorization Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Response<String> authorizationExceptionHandler(AuthorizationException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }

    /**
     * Expired Token Exception Handler
     *
     * @param e Expired Token exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(value = TokenExpiredException.class)
    public Response<String> expiredTokenExceptionHandler(TokenExpiredException e) {
        logger.error(e.getMessage());
        return Response.failure("Token has expired.");
    }

    /**
     * JWT Verification Exception Handler
     *
     * @param e JWT Verification exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(value = JWTVerificationException.class)
    public Response<String> jwtVerificationExceptionHandler(JWTVerificationException e) {
        logger.error(e.getMessage());
        return Response.failure("Token is invalid.");
    }

    /**
     * BindException Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public Response<String> bindExceptionHandler(BindException e) {
        logger.error(e.getMessage());
        return Response.failure(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * MethodArgumentNotValid Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        return Response.failure(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * Http Request Method Not Supported Exception Handler
     *
     * @param e Http Request Method Not Supported Exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Response<String> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getMessage());
    }

    /**
     * Bad Request Exception Handler
     *
     * @param e Bad Request Exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = BadRequestException.class)
    public Response<String> badRequestExceptionHandler(BadRequestException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }

    /**
     * Http Message Not Readable Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Response<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        logger.error(e.getMessage());
        return Response.failure("Not readable body.");
    }

    /**
     * Internal Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = InternalException.class)
    public Response<String> internalExceptionHandler(InternalException e) {
        logger.error(e.getMessage(), e);
        return Response.failure(e.getCode(), e.getMessage());
    }

    /**
     * Runtime Exception Handler
     *
     * @param e runtime exception
     * @return response
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response<String> errorHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.failure("Internal Error.");
    }


}

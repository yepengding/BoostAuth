package org.veritasopher.boostauth.core.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.veritasopher.boostauth.core.response.Response;

import java.util.Objects;
import java.util.Optional;


/**
 * Global Exception Handler
 *
 * @author Yepeng Ding
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * System Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseBody
    @ExceptionHandler(value = SystemException.class)
    public Response<String> systemExceptionHandler(SystemException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }

    /**
     * MethodArgumentNotValid Exception Handler
     *
     * @param e exception
     * @return response
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage());
        return Response.failure(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * Expired Token Exception Handler
     *
     * @param e Expired Token exception
     * @return response
     */
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
    @ResponseBody
    @ExceptionHandler(value = JWTVerificationException.class)
    public Response<String> jwtVerificationExceptionHandler(JWTVerificationException e) {
        logger.error(e.getMessage());
        return Response.failure("Token is invalid.");
    }

    /**
     * Internal Exception Handler
     *
     * @param e exception
     * @return response
     */
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
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response<String> errorHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.failure("Internal Error.");
    }


}

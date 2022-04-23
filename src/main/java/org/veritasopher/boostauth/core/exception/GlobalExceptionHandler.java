package org.veritasopher.boostauth.core.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.veritasopher.boostauth.core.response.Response;


/**
 * Global Exception Handler
 *
 * @author Yepeng Ding
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = SystemException.class)
    public Response<String> systemExceptionHandler(SystemException e) {
        logger.error(e.getMessage());
        return Response.failure(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = TokenExpiredException.class)
    public Response<String> expiredJwtExceptionHandler(TokenExpiredException e) {
        logger.error(e.getMessage());
        return Response.failure("Token has expired.");
    }

    @ResponseBody
    @ExceptionHandler(value = JWTVerificationException.class)
    public Response<String> expiredJwtExceptionHandler(JWTVerificationException e) {
        logger.error(e.getMessage());
        return Response.failure("Token is invalid.");
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response<String> errorHandler(Exception e) {
        logger.error(e.getMessage());
        return Response.failure("Internal Error.");
    }


}

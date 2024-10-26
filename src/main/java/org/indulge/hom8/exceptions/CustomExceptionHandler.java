package org.indulge.hom8.exceptions;

import org.indulge.hom8.constants.Regex;
import org.indulge.hom8.dtos.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;


@RestControllerAdvice
public record CustomExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BaseResponse<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new BaseResponse<>(false, HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleInvalidRequestException(InvalidRequestException exception) {
        return new BaseResponse<>(false, HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public BaseResponse<Object> handleDuplicateException(DuplicateException exception) {
        return new BaseResponse<>(false, HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(DataProcessingException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public BaseResponse<Object> handleDataProcessingException(DataProcessingException exception) {
        return new BaseResponse<>(false, HttpStatus.EXPECTATION_FAILED, exception.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public BaseResponse<Object> handleUnAuthorizedException(UnAuthorizedException exception) {
        return new BaseResponse<>(false, HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleWebExchangeBindingException(WebExchangeBindException exception) {
        List<String> list = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new BaseResponse<>(false, HttpStatus.BAD_REQUEST, list.toString().replaceAll(Regex.SQUARE_BRACKETS, ""));
    }
}

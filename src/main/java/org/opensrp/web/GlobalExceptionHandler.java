package org.opensrp.web;

import org.opensrp.web.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.opensrp.web.Constants.DEFAULT_EXCEPTION_HANDLER_MESSAGE;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.toString());

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> exceptionHandler(HttpMessageNotReadableException exception) {
        logger.error("HttpMessageNotReadableException occurred : ", exception);
        return buildErrorResponseForBadRequest(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<?> exceptionHandler(MissingServletRequestParameterException exception) {
        logger.error("MissingServletRequestParameterException occurred : ", exception);
        return buildErrorResponseForBadRequest(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<?> exceptionHandler(RuntimeException exception) {
        logger.error("Runtime Exception occurred : ", exception);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<?> exceptionHandler(Exception exception) {
        logger.error("Exception occurred : ", exception);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseDto<Object> buildErrorResponse(HttpStatus status) {
        ResponseDto<Object> dto = new ResponseDto<>().makeFailureResponse(status);
        dto.setData(null);
        dto.setMessage(DEFAULT_EXCEPTION_HANDLER_MESSAGE);
        return dto;
    }

    public ResponseDto<Object> buildErrorResponseForBadRequest(HttpStatus status) {
        ResponseDto<Object> dto = new ResponseDto<>().makeFailureResponse(status);
        dto.setData(null);
        dto.setMessage("");
        return dto;
    }
}
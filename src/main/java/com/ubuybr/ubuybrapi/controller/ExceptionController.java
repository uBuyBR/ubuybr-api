package com.ubuybr.ubuybrapi.controller;

import com.ubuybr.ubuybrapi.exception.NotFoundException;
import com.ubuybr.ubuybrapi.exception.ProductNotAvailable;
import com.ubuybr.ubuybrapi.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(final NotFoundException ex) {
        log.error("NotFoundException", ex);
        return new ErrorResponse(ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotAvailable.class)
    public ErrorResponse handleNotAvailableException(final NotFoundException ex) {
        log.error("ProductNotAvailable", ex);
        return new ErrorResponse(ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }
}

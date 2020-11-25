package com.intranet.project.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IntranetExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException e){
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e){
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerErrorExceptionDefault(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

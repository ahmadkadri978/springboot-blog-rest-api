package com.springboot.blog.exeption;

import com.springboot.blog.payload.ErrorDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundExeption.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundExeption exception , WebRequest webRequest ){
        ErrorDetails errorDetails =new ErrorDetails(new Date() , exception.getMessage(),webRequest.getDescription(false) , HttpStatus.NOT_FOUND );
        return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogApiException exception ,WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage(),
                webRequest.getDescription(false) , HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails , HttpStatus.BAD_REQUEST);

    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception ,WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage(),
//                webRequest.getDescription(false) , HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
    //customize validation error responce
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String , String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            String message = error.getDefaultMessage();
            errors.put(fieldName , message);

        });
        return new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception , WebRequest webRequest ){
        ErrorDetails errorDetails =new ErrorDetails(new Date() , exception.getMessage(),webRequest.getDescription(false),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorDetails , HttpStatus.UNAUTHORIZED);
    }
}

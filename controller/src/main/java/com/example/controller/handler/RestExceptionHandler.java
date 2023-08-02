package com.example.controller.handler;

import com.example.service.validator.UserInfoInvalidException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.example.service.util.ConstContainer.*;

@RestControllerAdvice(basePackages = PATH_TO_REST)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserInfoInvalidException.class)
    public ResponseEntity<ErrorResponseForUserInfo> handleValidationException(UserInfoInvalidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        List<String> fullErrorMessage = new ArrayList<>();
        for (ObjectError error : errors) {
            fullErrorMessage.add(error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new ErrorResponseForUserInfo(VALIDATION_FAILED, fullErrorMessage));
    }

    @Data
    @AllArgsConstructor
    private static class ErrorResponseForUserInfo {
        private String message;
        private List<String> errors;

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Data
    @AllArgsConstructor
    private static class ErrorResponse {
        private int status;
        private String error;
        private String message;

    }
}

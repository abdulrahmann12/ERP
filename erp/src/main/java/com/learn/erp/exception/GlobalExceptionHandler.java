package com.learn.erp.exception;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // === Common Utility === //
    private ResponseEntity<BasicResponse> buildErrorResponse(Exception ex, WebRequest request, HttpStatus status) {
        BasicResponse response = new BasicResponse(  ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<BasicResponse> buildErrorResponse(String message, WebRequest request, HttpStatus status) {
        BasicResponse response = new BasicResponse(  message, request.getDescription(false));
        return new ResponseEntity<>( response, status);
    }
    
    @ExceptionHandler(MailSendingException.class)
    public ResponseEntity<BasicResponse> handleMailException(MailSendingException ex, HttpServletRequest request) {

        BasicResponse response = new BasicResponse( ex.getMessage(),request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // === Authentication & Security Exceptions === //
    
    @ExceptionHandler({ 
        AuthenticationCredentialsNotFoundException.class,
        BadCredentialsException.class,
        AuthenticationException.class
    })
    public ResponseEntity<BasicResponse> handleAuthenticationExceptions(Exception ex, WebRequest request) {
        String message = (ex instanceof BadCredentialsException) ? Messages.BAD_CREDENTIALS :
                         (ex instanceof AuthenticationException) ? Messages.AUTH_FAILED :
                         ex.getMessage();
        return buildErrorResponse(message, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BasicResponse> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return buildErrorResponse(Messages.ACCESS_DENIED, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<BasicResponse> handleJwtExpired(ExpiredJwtException ex, WebRequest request) {
        return buildErrorResponse(Messages.SESSION_EXPIRED, request, HttpStatus.BAD_REQUEST);
    }


    // === Business Exceptions === //

    @ExceptionHandler({
        ResourceNotFoundException.class,
        InvalidConfirmationCodeException.class,
        InvalidTokenException.class,
        InvalidResetCodeException.class,
        InvalidCurrentPasswordException.class,
        UserNotFoundException.class,
        CategoryNotFoundException.class,
        ProductNotFoundException.class,
        EmailAlreadyExistsException.class,
        InsufficientStockException.class,
        DepartmentNotFoundException.class,
        DuplicateResourceException.class,
        EmployeeNotFoundException.class,
        AlreadyCheckedOutException.class,
        LeaveRequestNotFoundException.class,
        SalaryNotFoundException.class,
        BonusNotFoundException.class,
        PayrollAlreadyExistsException.class,
        PayrollNotFoundException.class,
        SupplierNotFoundException.class,
        CustomerNotFoundException.class,
        SaleNotFoundException.class,
        
        
    })
    public ResponseEntity<BasicResponse> handleNotFoundBusinessExceptions(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }


    // === Validation Exceptions === //

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BasicResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildErrorResponse(errorMessage, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BasicResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BasicResponse> handleSpringJsonParseException(
            HttpMessageNotReadableException ex) {
    	BasicResponse error = new BasicResponse(Messages.INVALID_DATA);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BasicResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return buildErrorResponse(Messages.REQUEST_NOT_SUPPORTED, request, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BasicResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String firstError = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElse("Validation failed");
                return buildErrorResponse(firstError, request, HttpStatus.BAD_REQUEST);   
                
    }

    // === Fallback Exceptions === //

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BasicResponse> handleRuntime(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse> handleAll(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

}
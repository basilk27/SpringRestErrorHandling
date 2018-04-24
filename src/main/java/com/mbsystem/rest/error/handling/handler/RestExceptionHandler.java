package com.mbsystem.rest.error.handling.handler;

import com.mbsystem.rest.error.handling.dto.ErrorDetail;
import com.mbsystem.rest.error.handling.dto.ValidationError;
import com.mbsystem.rest.error.handling.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException( ResourceNotFoundException rnfe, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp( Instant.now().toEpochMilli());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return handleExceptionInternal(rnfe, errorDetail, null, HttpStatus.NOT_FOUND, request);

    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setDetail( ex.getMessage() );
        errorDetail.setDeveloperMessage( ex.getClass().getName() );
        errorDetail.setStatus( HttpStatus.BAD_REQUEST.value() );
        errorDetail.setTitle( "Validation Failed" );
        errorDetail.setTimestamp( Instant.now().toEpochMilli() );

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for ( FieldError fieldError : fieldErrors ) {
            //List<ValidationError> validationErrorList = errorDetail.getErrorMap().get(fieldError.getField());
            List< ValidationError > validationErrorList = errorDetail.getErrorMap().computeIfAbsent( fieldError.getField(), k -> new ArrayList<>() );

            /*
            if ( null == validationErrorList ) {
                validationErrorList = new ArrayList<>();
                errorDetail.getErrorMap().put( fieldError.getField(), validationErrorList );
            }

 */
            ValidationError validationError = new ValidationError();

            validationError.setCode( fieldError.getCode() );
            validationError.setMessage( messageSource.getMessage( fieldError, null ) );
            validationErrorList.add( validationError );
        }

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp( Instant.now().toEpochMilli() );
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }
}

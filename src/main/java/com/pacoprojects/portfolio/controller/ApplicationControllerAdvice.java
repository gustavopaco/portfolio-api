package com.pacoprojects.portfolio.controller;

import com.pacoprojects.portfolio.constants.Messages;
import com.pacoprojects.portfolio.exception.ExceptionObject;
import com.pacoprojects.portfolio.exception.RecordNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLDataException;
import java.sql.SQLException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    @ExceptionHandler(value = {RecordNotFoundException.class})
    protected ResponseEntity<ExceptionObject> handleRecordNotFound(RecordNotFoundException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<ExceptionObject> handleResponseStatusException(ResponseStatusException exception) {
        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(exception.getReason())
                .code(exception.getStatusCode().value())
                .build(), exception.getStatusCode());
    }

    @ExceptionHandler(value = {RestClientException.class})
    protected ResponseEntity<ExceptionObject> handleRestClientException(RestClientException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(exception.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        StringBuilder message = new StringBuilder();
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> message.append(error.getDefaultMessage()).append("\n"));
            message.delete(message.length() - 1, message.length());
            if (LOG.isErrorEnabled()) {
                LOG.error(message.toString(), exception);
            }
        }
        return ResponseEntity.badRequest()
                .body(ExceptionObject
                        .builder()
                        .message(message.toString())
                        .code(status.value())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        StringBuilder message = new StringBuilder();
        if (exception instanceof HttpMessageNotReadableException readableException) {
            message.append(Messages.INVALID_REQUEST_BODY).append(readableException.getMessage());
        } else {
            message.append(exception.getMessage());
        }
        if (LOG.isErrorEnabled()) {
            LOG.error(message.toString(), exception);
        }
        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(message.toString())
                .code(statusCode.value())
                .build(), headers, statusCode);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, SQLException.class, TransactionSystemException.class})
    protected ResponseEntity<ExceptionObject> handleDataIntegrityException(Exception exception) {
        StringBuilder message = new StringBuilder();
        if (exception instanceof DataIntegrityViolationException dataIntegrityViolationException) {
            message.append(Messages.INTERNAL_DATA_INTEGRITY_ERROR).append(dataIntegrityViolationException.getCause().getCause().getMessage());
        } else if (exception instanceof TransactionSystemException transactionSystemException) {
            Throwable rootCause = transactionSystemException.getRootCause();
            if (rootCause instanceof ConstraintViolationException constraintViolationException) {
                message.append(Messages.INTERNAL_CONSTRAINT_VIOLATION_ERROR);
                constraintViolationException.getConstraintViolations()
                        .forEach(constraintViolation -> message.append(constraintViolation.getMessage()).append("\n"));
                message.delete(message.length() - 1, message.length());
            } else if (rootCause instanceof SQLDataException sqlDataException) {
                message.append(Messages.INTERNAL_SQL_ERROR).append(sqlDataException.getCause().getCause().getMessage());
            }
        } else if (exception instanceof SQLException sqlException) {
            message.append(Messages.INTERNAL_SQL_ERROR).append(sqlException.getCause().getCause().getMessage());
        }
        if (LOG.isErrorEnabled()) {
            LOG.error(message.toString(), exception);
        }
        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(message.toString())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

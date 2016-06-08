package org.springframework.webflow.samples.booking.rest;

import static java.util.stream.Collectors.joining;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author mszarlinski on 2016-06-08.
 */
@ControllerAdvice
public class ValidationExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationExceptionInfo handleValidationError(final MethodArgumentNotValidException ex) {
        return ValidationExceptionInfo.from(ex);
    }

    /**
     * This is just a String value wrapper.
     */
    private static class ValidationExceptionInfo {
        private final String message;

        private static ValidationExceptionInfo from(final MethodArgumentNotValidException ex) {
            return new ValidationExceptionInfo(
                ex.getBindingResult().getFieldErrors()
                    .stream()
                    //TODO: method reference, Tomcat issue
                    .map(new Function<FieldError, String>() {
                        @Override
                        public String apply(final FieldError fieldError) {
                            return fieldError.getDefaultMessage();
                        }
                    })
                    .collect(joining(", ")));
        }

        private ValidationExceptionInfo(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

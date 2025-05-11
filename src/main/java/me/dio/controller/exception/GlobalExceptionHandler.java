package me.dio.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDetails> handleBusinessException(IllegalArgumentException businessException) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Business Exception",
                businessException.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionDetails> handleNotFoundException(NoSuchElementException notFoundException) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                "Resource ID not found."
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionDetails> handleUnexpectedException(Throwable unexpectedException) {
        String message = "Unexpected server error, see the logs.";
        logger.error(message, unexpectedException);
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected Error",
                message
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ExceptionDetails {
        private LocalDateTime timestamp;
        private Integer status;
        private String title;
        private String detail;

        public ExceptionDetails(LocalDateTime timestamp, Integer status, String title, String detail) {
            this.timestamp = timestamp;
            this.status = status;
            this.title = title;
            this.detail = detail;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public Integer getStatus() {
            return status;
        }

        public String getTitle() {
            return title;
        }

        public String getDetail() {
            return detail;
        }
    }
}
package ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.dto.ErrorDto;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound.NotFoundObjectException;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid.NotValidObjectException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> notFoundObjectExceptionHandler(NotFoundObjectException notFoundObjectException) {
        log.info("notFoundObjectExceptionHandler() - Handling NotFoundException");
        log.debug("notFoundObjectExceptionHandler() - Handling NotFoundException. Details: " + notFoundObjectException);
        ErrorDto errorMessage = new ErrorDto(
                notFoundObjectException.getCode().toString(),
                notFoundObjectException.getMessage(),
                notFoundObjectException.getStackTrace());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> notValidObjectExceptionHandler(NotValidObjectException notValidObjectException) {
        log.info("notValidObjectExceptionHandler() - Handling NotValidObjectException");
        log.debug("notValidObjectExceptionHandler() - Handling NotFoundException. Details: " + notValidObjectException);
        ErrorDto errorMessage = new ErrorDto(
                notValidObjectException.getCode().getErrorCode(),
                notValidObjectException.getMessage(),
                notValidObjectException.getStackTrace());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}

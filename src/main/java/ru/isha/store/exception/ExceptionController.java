package ru.isha.store.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController  {
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Exception e)   {
        log.error("Request: " + request.getRequestURL() + " raised " + e);
        return "404";
    }

  @ExceptionHandler(ResourceNotFoundException.class)
    public String handleError404(HttpServletRequest request, ResourceNotFoundException e)   {
        log.error("Request: " + request.getRequestURL() + " raised " + e);
        return "404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleNotAuthenticatedException(AccessDeniedException ex) {
    }


}

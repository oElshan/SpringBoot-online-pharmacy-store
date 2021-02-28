package ru.isha.store.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

//@Controller
@ControllerAdvice(basePackages = "ru.isha.store.rest")
public class RestExceptions {

    private static final Logger log = LoggerFactory.getLogger(RestExceptions.class);

//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorRestDTO otherExceptions(Exception e, WebRequest request) {
//        ErrorRestDTO response = new ErrorRestDTO("Error url",h);
//        return response;
//    }
}

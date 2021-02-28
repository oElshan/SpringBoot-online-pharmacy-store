package ru.isha.store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.isha.store.exception.RestNoHandlerFoundException;
import ru.isha.store.rest.exception.ErrorRestDTO;

import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
public class ExceptionController  {
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Exception e) throws  RestNoHandlerFoundException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/rest")) {
            request.setAttribute("exc",e);
            return  "forward:/rest-not-found";
        }
        log.error("Request: " + request.getRequestURL() + " raised " + e);

        return "404";
    }

    @RequestMapping(value = "/rest-not-found", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorRestDTO handleRestError404(HttpServletRequest request) {
        Exception e = (Exception) request.getAttribute("exc");
        log.error("Request: " + request.getRequestURI()+ " raised " + e);
            return  new ErrorRestDTO("Bad request!",HttpStatus.NOT_FOUND);
    }

}

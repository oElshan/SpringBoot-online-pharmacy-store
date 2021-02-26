package ru.isha.store.rest.exception;


import org.springframework.http.HttpRequest;
import org.springframework.security.access.AccessDeniedException;

public class RestExceptions {


    public ErrorRestDTO acsessDenite(AccessDeniedException e, HttpRequest request) {
        return new ErrorRestDTO("Access is denied",request.getURI().toString(),"");
    }

}

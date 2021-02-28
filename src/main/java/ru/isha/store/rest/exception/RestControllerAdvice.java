package ru.isha.store.rest.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;

@Controller
@ControllerAdvice(basePackages = "ru.isha.store.rest")
public class RestControllerAdvice {

    @Autowired
    private MessageSource messageSource;
    private static final Logger log = LoggerFactory.getLogger(RestControllerAdvice.class);

    private static final String ARGUMENT_VALIDATION_CODE = "Error.Validation.Parameter";
    private static final String NOT_EXIST_CODE = "NotExist";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public RestErrorResponse processValidationError(MethodArgumentNotValidException e, WebRequest request) {
        BindingResult result = e.getBindingResult();
        String objectName = result.getObjectName();
        List<FieldError> fieldErrors = result.getFieldErrors();
        RestErrorResponse response = createLocalizedResponse(ARGUMENT_VALIDATION_CODE, objectName, fieldErrors, request);
        log.warn(response.toString());
        return response;
    }

    private RestErrorResponse createLocalizedResponse(String localizationCode, String entityType,
                                                      List<FieldError> fieldErrors, WebRequest request)
    {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedMessage = messageSource.getMessage(localizationCode, null, currentLocale);
        RestErrorResponse response = new RestErrorResponse(localizedMessage, entityType, request);
        return resolveFieldErrors(response, fieldErrors);
    }

    private RestErrorResponse resolveFieldErrors(RestErrorResponse response, List<FieldError> fieldErrors) {
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveErrorMessage(fieldError);
            response.addFieldError(fieldError.getField(), localizedErrorMessage);
        }
        return response;
    }

    private String resolveErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            if (fieldErrorCodes != null)
                localizedErrorMessage = fieldErrorCodes[0];
        }
        return localizedErrorMessage;
    }
}

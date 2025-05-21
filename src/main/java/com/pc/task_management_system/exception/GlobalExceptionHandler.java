package com.pc.task_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> hanldeUsernameAlreadyExistsException(UsernameAlreadyExistsException usernameAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usernameAlreadyExistsException.getLocalizedMessage());
    }

    @ExceptionHandler(EmailIdAlreadyExistsException.class)
    public ResponseEntity<String> hanldeEmailIdAlreadyExistsException(EmailIdAlreadyExistsException emailIdAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailIdAlreadyExistsException.getLocalizedMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usernameNotFoundException.getLocalizedMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handlebadCredentialsException(BadCredentialsException badCredentialsException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(badCredentialsException.getLocalizedMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(runtimeException.getLocalizedMessage());
    }
}

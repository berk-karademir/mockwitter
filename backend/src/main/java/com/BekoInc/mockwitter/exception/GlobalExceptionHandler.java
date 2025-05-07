package com.BekoInc.mockwitter.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MockwitterException.class)
    public ResponseEntity<MockwitterErrorResponse> handleMockwitterException(MockwitterException e) {
        MockwitterErrorResponse response = new MockwitterErrorResponse(
                e.getMessage(),
                e.getHttpStatus().value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response,e.getHttpStatus());
}

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<CustomExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(new CustomExceptionResponse(ex.getMessage()));
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<CustomExceptionResponse> handleNotFoundException(NotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new CustomExceptionResponse(ex.getMessage()));
//    }
//
//    @ExceptionHandler(AuthorizationException.class)
//    public ResponseEntity<CustomExceptionResponse> handleAuthorizationException(AuthorizationException ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(new CustomExceptionResponse(ex.getMessage()));
//    }
//
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<MockwitterErrorResponse> handleAuthenticationException(AuthenticationException ex) {
//        MockwitterErrorResponse response = new MockwitterErrorResponse(
//                "You must be authenticated",
//                HttpStatus.UNAUTHORIZED.value(),
//                LocalDateTime.now()
//        );
//
//        log.warn("Unauthorized access attempt detected!");
//
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<MockwitterErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
//        MockwitterErrorResponse response = new MockwitterErrorResponse(
//                "User not found in db.",
//                HttpStatus.UNAUTHORIZED.value(),
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }

//    @ExceptionHandler(MockwitterException.class)
//    public ResponseEntity<MockwitterErrorResponse> handleMockwitterException(MockwitterException ex) {
//        MockwitterErrorResponse response = new MockwitterErrorResponse(
//                ex.getMessage(),
//                ex.getHttpStatus().value(),
//                LocalDateTime.now()
//        );
//
//        log.error("Mockwitter error occurred: {}", ex.getMessage());
//
//        return new ResponseEntity<>(response, ex.getHttpStatus());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        log.error("Unexpected API error has occurred!");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected API error has occurred");
//    }
}

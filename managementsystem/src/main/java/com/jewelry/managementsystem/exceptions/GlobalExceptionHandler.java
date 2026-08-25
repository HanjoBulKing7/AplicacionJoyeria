package com.jewelry.managementsystem.exceptions;

import com.jewelry.managementsystem.security.response.MessageResponse;
import com.stripe.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /// Handling empty resources
    @ExceptionHandler( EmptyResourceException.class)
    public ResponseEntity<ErrorResponse> handleEmpty (EmptyResourceException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                );
        /// Set up the Response Entity and return it
        return  ResponseEntity.status(HttpStatus.OK).body(errorResponse);

    }

    ///  Non-existing items
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound (ResourceNotFound ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /// Duplicate items
    @ExceptionHandler ( DuplicateResourceException.class )
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException (DuplicateResourceException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    ///  Method Argument Valid Exception @Valid -> DTO LAYER
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    ///  HIBERNATE LAYER
    @ExceptionHandler ( ConstraintViolationException.class )
    public ResponseEntity<Map<String, String>> handleConstraintViolationException (ConstraintViolationException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>( errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        MessageResponse response = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShoppingCartException.class)
    public ResponseEntity<ErrorResponse> handleShoppingCartException(ShoppingCartException ex) {
         ErrorResponse  errorResponse = new ErrorResponse(
                 ex.getMessage(),
                 HttpStatus.CONFLICT.value(),
                 LocalDateTime.now()
         );
         return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleJWTExcepetion(TokenException jwrEx){

        ErrorResponse errorResponse = new ErrorResponse(
                jwrEx.getError(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<MessageResponse> handleStripeException(StripeException e) {

        HttpStatus status = switch (e) {
            case CardException ex          -> HttpStatus.PAYMENT_REQUIRED;  // 402
            case InvalidRequestException ex -> HttpStatus.BAD_REQUEST;       // 400
            case AuthenticationException ex -> HttpStatus.UNAUTHORIZED;      // 401
            case RateLimitException ex     -> HttpStatus.TOO_MANY_REQUESTS;  // 429
            default                        -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return ResponseEntity.status(status)
                .body(new MessageResponse(e.getMessage()));
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<MessageResponse> handleOptimisticLocking(ObjectOptimisticLockingFailureException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT) // 409
                .body(new MessageResponse("Someone just bought this item. Please review your cart."));
    }
}

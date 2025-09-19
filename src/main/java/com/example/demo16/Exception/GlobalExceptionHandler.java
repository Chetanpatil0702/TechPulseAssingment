package com.example.demo16.Exception;

import com.example.demo16.Responce.GenericErrorResponse;
import com.example.demo16.Responce.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DuplicateResourseException.class)
    public ResponseEntity<GenericResponse> handleBadRequest(DuplicateResourseException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> handleNotFound(ResourceNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(false, ex.getMessage()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        GenericErrorResponse response = new GenericErrorResponse(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




    // --- 401: Unauthorized / Access Denied ---
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<GenericResponse> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GenericResponse(false, "Unauthorized: You don’t have permission to perform this action."));
    }

    // --- 409: Conflict (duplicate / DB constraint) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> errors = Map.of(
                "database", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new GenericErrorResponse(errors));
    }

    // --- 500: Unexpected Internal Server Error ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleUnexpectedExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericResponse(false, "Something went wrong. Please try again later."));
    }

    // 🔹 Handle JWT Expired Token
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Token Expired");
        body.put("message", "Your JWT token has expired, please login again.");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    // 🔹 Handle Invalid JWT Signature
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, Object>> handleSignatureException(SignatureException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Invalid Token Signature");
        body.put("message", "JWT signature does not match.");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    // 🔹 Handle Malformed or Unsupported JWT
    @ExceptionHandler({MalformedJwtException.class, UnsupportedJwtException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, Object>> handleInvalidJwt(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Invalid Token");
        body.put("message", "The provided JWT token is invalid.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}

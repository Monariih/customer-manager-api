package com.lucas_monari.customer_manager_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata validações do DTO (ex: CPF inválido, campos em branco) -> 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Trata regras de negócio como "CPF já cadastrado" -> 409 Conflict
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Trata erros genéricos, incluindo a indisponibilidade do serviço externo e cliente inexistente
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        HttpStatus status;

        // Define o status HTTP correto baseado na mensagem
        if (ex.getMessage().contains("Cliente inexistente")) {
            status = HttpStatus.NOT_FOUND; // 404
        } else if (ex.getMessage().contains("indisponível") || ex.getMessage().contains("Erro ao consultar")) {
            status = HttpStatus.SERVICE_UNAVAILABLE; // 503
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        }

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(status).body(error);
    }

    public record ErrorResponse(LocalDateTime timestamp, int status, String error) {}
}
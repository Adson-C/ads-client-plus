package com.ads.clien.plus.exception.handler;

import com.ads.clien.plus.dto.error.ErrorResponseDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.BusinessexceptionAds;
import com.ads.clien.plus.exception.IntegrationException;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceHandler {
    @ExceptionHandler(NotFoundExceptionAds.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundExceptionAds(NotFoundExceptionAds ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build());
    }
    @ExceptionHandler(BadReqequestExceptionAds.class)
    public ResponseEntity<ErrorResponseDTO> badRequestExceptionads(BadReqequestExceptionAds ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> badRequestExceptionads(MethodArgumentNotValidException m) {
        // percorrendo os erros default message do MethodArgumentNotValidException

        Map<String, String> messages = new HashMap<>();

        m.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            // salva os erros
            messages.put(fieldName, defaultMessage);
        });
        // retorna os erros DTO
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                .message(Arrays.toString(messages.entrySet().toArray()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    // DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> badRequestExceptionads(DataIntegrityViolationException d) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                .message(d.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponseDTO> IntegrationExceptionException(IntegrationException d) {
        String errorMessage = d.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseDTO.builder()
                .message(d.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build());
    }

    @ExceptionHandler(BusinessexceptionAds.class)
    public ResponseEntity<ErrorResponseDTO> badRequestExceptionads(BusinessexceptionAds ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.CONFLICT)
                .statusCode(HttpStatus.CONFLICT.value())
                .build());
    }
}

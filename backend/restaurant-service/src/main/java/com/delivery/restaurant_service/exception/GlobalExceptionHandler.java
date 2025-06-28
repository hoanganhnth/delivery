package com.delivery.restaurant_service.exception;

import com.delivery.restaurant_service.payload.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Lỗi không tìm thấy
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(0, null, ex.getMessage()));
    }

    // Lỗi chung khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleAll(Exception ex) {
        ex.printStackTrace(); // log lỗi để debug
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(0, null, "Đã xảy ra lỗi nội bộ."));
    }
}

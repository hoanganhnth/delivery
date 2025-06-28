package com.delivery.restaurant_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MenuItemResponse {
    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters

}

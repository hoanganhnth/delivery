package com.delivery.restaurant_service.dto.request;

import java.math.BigDecimal;

public class UpdateMenuItemRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String status; // AVAILABLE, SOLD_OUT, DISCONTINUED

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

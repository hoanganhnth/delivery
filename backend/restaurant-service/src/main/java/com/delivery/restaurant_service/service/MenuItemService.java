package com.delivery.restaurant_service.service;

import com.delivery.restaurant_service.dto.request.CreateMenuItemRequest;
import com.delivery.restaurant_service.dto.request.UpdateMenuItemRequest;
import com.delivery.restaurant_service.dto.response.MenuItemResponse;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(CreateMenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, UpdateMenuItemRequest request);

    void deleteMenuItem(Long id);

    List<MenuItemResponse> getItemsByRestaurant(Long restaurantId);

    List<MenuItemResponse> getAvailableItems(Long restaurantId);
}

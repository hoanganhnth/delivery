package com.delivery.restaurant_service.service.impl;

import com.delivery.restaurant_service.dto.request.CreateMenuItemRequest;
import com.delivery.restaurant_service.dto.request.UpdateMenuItemRequest;
import com.delivery.restaurant_service.dto.response.MenuItemResponse;
import com.delivery.restaurant_service.entity.MenuItem;
import com.delivery.restaurant_service.exception.ResourceNotFoundException;
import com.delivery.restaurant_service.mapper.MenuItemMapper;
import com.delivery.restaurant_service.repository.MenuItemRepository;
import com.delivery.restaurant_service.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Override
    public MenuItemResponse createMenuItem(CreateMenuItemRequest request) {
        MenuItem item = menuItemMapper.toEntity(request);
        MenuItem saved = menuItemRepository.save(item);
        return menuItemMapper.toResponse(saved);
    }

    @Override
    public MenuItemResponse updateMenuItem(Long id, UpdateMenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));
        menuItemMapper.updateEntityFromDto(request, item);
        MenuItem updated = menuItemRepository.save(item);
        return menuItemMapper.toResponse(updated);
    }

    @Override
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public List<MenuItemResponse> getItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemResponse> getAvailableItems(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndStatus(restaurantId, MenuItem.Status.AVAILABLE)
                .stream()
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());
    }
}

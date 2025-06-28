package com.delivery.restaurant_service.service;

import com.delivery.restaurant_service.dto.request.CreateRestaurantRequest;
import com.delivery.restaurant_service.dto.request.UpdateRestaurantRequest;
import com.delivery.restaurant_service.dto.response.RestaurantResponse;
import com.delivery.restaurant_service.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(CreateRestaurantRequest restaurant);

    RestaurantResponse updateRestaurant(Long id, UpdateRestaurantRequest restaurant);

    void deleteRestaurant(Long id);

    RestaurantResponse getRestaurantById(Long id);

    List<RestaurantResponse> getAllRestaurants();

    List<RestaurantResponse> findByName(String keyword);
}

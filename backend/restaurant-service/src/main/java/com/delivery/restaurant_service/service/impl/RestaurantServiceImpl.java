package com.delivery.restaurant_service.service.impl;

import com.delivery.restaurant_service.dto.request.CreateRestaurantRequest;
import com.delivery.restaurant_service.dto.request.UpdateRestaurantRequest;
import com.delivery.restaurant_service.dto.response.RestaurantResponse;
import com.delivery.restaurant_service.entity.Restaurant;
import com.delivery.restaurant_service.exception.ResourceNotFoundException;
import com.delivery.restaurant_service.mapper.RestaurantMapper;
import com.delivery.restaurant_service.repository.RestaurantRepository;
import com.delivery.restaurant_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    // Gọi hàm isOpen trước khi trả về đối tượng Restaurant để đảm bảo trạng thái mở/đóng được cập nhật.
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Override
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantMapper.toEntity(request);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(saved);
    }

    @Override
    public RestaurantResponse updateRestaurant(Long id, UpdateRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        restaurantMapper.updateEntityFromDto(request, restaurant); // cập nhật các trường từ DTO
        Restaurant updated = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(updated);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return restaurantMapper.toResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        List<Restaurant> list = restaurantRepository.findAll();
        return list.stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponse> findByName(String keyword) {

        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(keyword);
        return restaurants.stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }
}

package com.delivery.restaurant_service.service;

import com.delivery.restaurant_service.dto.request.CreateRestaurantRequest;
import com.delivery.restaurant_service.dto.response.RestaurantResponse;
import com.delivery.restaurant_service.entity.Restaurant;
import com.delivery.restaurant_service.exception.ResourceNotFoundException;
import com.delivery.restaurant_service.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;
    private CreateRestaurantRequest createRequest;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test Street");
        restaurant.setCreatorId(1L);

        createRequest = new CreateRestaurantRequest();
        createRequest.setName("Test Restaurant");
        createRequest.setAddress("123 Test Street");
    }

    @Test
    void createRestaurant_ShouldReturnRestaurantResponse_WhenValidRequest() {
        // Given
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        // When
        RestaurantResponse response = restaurantService.createRestaurant(createRequest, 1L, "RESTAURANT_OWNER");

        // Then
        assertNotNull(response);
        assertEquals("Test Restaurant", response.getName());
        assertEquals("123 Test Street", response.getAddress());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void getRestaurantById_ShouldReturnRestaurant_WhenExists() {
        // Given
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // When
        RestaurantResponse response = restaurantService.getRestaurantById(1L);

        // Then
        assertNotNull(response);
        assertEquals("Test Restaurant", response.getName());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void getRestaurantById_ShouldThrowException_WhenNotFound() {
        // Given
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () ->
                restaurantService.getRestaurantById(1L));

        verify(restaurantRepository).findById(1L);
    }

    @Test
    void findByName_ShouldReturnMatchingRestaurants() {
        // Given
        List<Restaurant> restaurants = Arrays.asList(restaurant);
        when(restaurantRepository.findByNameContainingIgnoreCase("Test")).thenReturn(restaurants);

        // When
        List<RestaurantResponse> responses = restaurantService.findByName("Test");

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Test Restaurant", responses.get(0).getName());
        verify(restaurantRepository).findByNameContainingIgnoreCase("Test");
    }

    @Test
    void deleteRestaurant_ShouldDeleteSuccessfully_WhenUserIsOwner() {
        // Given
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // When
        restaurantService.deleteRestaurant(1L, 1L);

        // Then
        verify(restaurantRepository).findById(1L);
        verify(restaurantRepository).delete(restaurant);
    }

    @Test
    void deleteRestaurant_ShouldThrowException_WhenUserNotOwner() {
        // Given
        restaurant.setCreatorId(2L); // Different owner
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // When & Then
        assertThrows(AccessDeniedException.class, () ->
                restaurantService.deleteRestaurant(1L, 1L));

        verify(restaurantRepository).findById(1L);
        verify(restaurantRepository, never()).delete(any());
    }
}
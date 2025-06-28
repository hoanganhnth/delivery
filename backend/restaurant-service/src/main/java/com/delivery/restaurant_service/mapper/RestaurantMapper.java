package com.delivery.restaurant_service.mapper;

import com.delivery.restaurant_service.dto.request.CreateRestaurantRequest;
import com.delivery.restaurant_service.dto.request.UpdateRestaurantRequest;
import com.delivery.restaurant_service.dto.response.RestaurantResponse;
import com.delivery.restaurant_service.entity.Restaurant;
import org.mapstruct.*;

import java.time.LocalTime;
@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant toEntity(CreateRestaurantRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateRestaurantRequest request, @MappingTarget Restaurant restaurant);

    @Mapping(target = "open", expression = "java(isOpen(restaurant.getOpeningHour(), restaurant.getClosingHour()))")
    RestaurantResponse toResponse(Restaurant restaurant);

    default boolean isOpen(LocalTime opening, LocalTime closing) {
        LocalTime now = LocalTime.now();
        return now.isAfter(opening) && now.isBefore(closing);
    }
}

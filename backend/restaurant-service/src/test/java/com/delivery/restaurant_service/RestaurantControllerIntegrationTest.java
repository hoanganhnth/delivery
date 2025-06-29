package com.delivery.restaurant_service;

import com.delivery.restaurant_service.common.constants.ApiPathConstants;
import com.delivery.restaurant_service.common.constants.HttpHeaderConstants;
import com.delivery.restaurant_service.dto.request.CreateRestaurantRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test example - này sẽ test cả controller + service + repository
 * Cần cấu hình database test và các dependencies
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional // Rollback sau mỗi test
class RestaurantControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void createRestaurant_ShouldPersistToDatabase_WhenValidRequest() throws Exception {
		// Given
		CreateRestaurantRequest request = new CreateRestaurantRequest();
		request.setName("Integration Test Restaurant");
		request.setAddress("123 Test Street");
		request.setPhone("0123456789");
//		request.setDescription("Test restaurant for integration testing");

		// When & Then
		mockMvc.perform(post(ApiPathConstants.RESTAURANTS)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaderConstants.X_USER_ID, "1")
						.header(HttpHeaderConstants.X_ROLE, "RESTAURANT_OWNER")
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(1))
				.andExpect(jsonPath("$.data.name").value("Integration Test Restaurant"))
				.andExpect(jsonPath("$.data.address").value("123 Test Street"));

		// Có thể thêm assertion để check database
		// Có thể test GET để verify data đã được lưu
	}

	@Test
	void getRestaurant_ShouldReturnRestaurant_WhenExists() throws Exception {
		// Given - tạo restaurant trước
		CreateRestaurantRequest createRequest = new CreateRestaurantRequest();
		createRequest.setName("Test Restaurant");
		createRequest.setAddress("Test Address");

		// Create restaurant first
		String createResponse = mockMvc.perform(post("/api/restaurants")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaderConstants.X_USER_ID, "1")
						.header(HttpHeaderConstants.X_ROLE, "RESTAURANT_OWNER")
						.content(objectMapper.writeValueAsString(createRequest)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		// Extract ID from response (hoặc dùng TestEntityManager nếu có)
		// Long restaurantId = extractIdFromResponse(createResponse);

		// When & Then - Get the created restaurant
		mockMvc.perform(get("/api/restaurants/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(1))
				.andExpect(jsonPath("$.data.name").value("Test Restaurant"));
	}
}
package in.astik.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;
import in.astik.service.FoodService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("FoodController Integration Tests")
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodService foodService;

    private FoodResponse sampleFoodResponse;

    @BeforeEach
    void setUp() {
        sampleFoodResponse = FoodResponse.builder()
                .id("1")
                .name("Pizza")
                .description("Delicious pizza")
                .price(250.0)
                .category("Italian")
                .imageUrl("/images/pizza.jpg")
                .build();
    }

    @Test
    @DisplayName("Should get all foods successfully")
    void testGetAllFoods() throws Exception {
        // Arrange
        List<FoodResponse> foods = Arrays.asList(sampleFoodResponse);
        when(foodService.getAllFoods()).thenReturn(foods);

        // Act & Assert
        mockMvc.perform(get("/api/foods")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Pizza"));
    }

    @Test
    @DisplayName("Should get food by id successfully")
    void testGetFoodById() throws Exception {
        // Arrange
        when(foodService.getFood("1")).thenReturn(sampleFoodResponse);

        // Act & Assert
        mockMvc.perform(get("/api/foods/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    @DisplayName("Should return 404 when food not found")
    void testGetFoodNotFound() throws Exception {
        // Arrange
        when(foodService.getFood("999")).thenThrow(new RuntimeException("Food not found"));

        // Act & Assert
        mockMvc.perform(get("/api/foods/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Should delete food successfully")
    void testDeleteFood() throws Exception {
        // Arrange
        when(foodService.deleteFood("1")).thenReturn("Food deleted successfully");

        // Act & Assert
        mockMvc.perform(delete("/api/foods/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Food deleted successfully"));
    }
}

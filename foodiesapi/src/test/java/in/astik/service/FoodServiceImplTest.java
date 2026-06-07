package in.astik.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import in.astik.entity.FoodEntity;
import in.astik.exception.ResourceNotFoundException;
import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;
import in.astik.mapper.FoodMapper;
import in.astik.repository.FoodRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("FoodService Unit Tests")
class FoodServiceImplTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private StorageService storageService;

    @Mock
    private FoodMapper foodMapper;

    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private FoodServiceImpl foodService;

    private FoodEntity sampleFood;
    private FoodRequest sampleFoodRequest;
    private FoodResponse sampleFoodResponse;

    @BeforeEach
    void setUp() {
        sampleFood = FoodEntity.builder()
                .id("1")
                .name("Pizza")
                .description("Delicious pizza")
                .price(250.0)
                .category("Italian")
                .imageUrl("/images/pizza.jpg")
                .build();

        sampleFoodRequest = new FoodRequest();
        sampleFoodRequest.setName("Pizza");
        sampleFoodRequest.setDescription("Delicious pizza");
        sampleFoodRequest.setPrice(250.0);
        sampleFoodRequest.setCategory("Italian");

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
    @DisplayName("Should add food successfully")
    void testAddFoodSuccess() {
        // Arrange
        when(storageService.uploadFile(mockFile)).thenReturn("/images/pizza.jpg");
        when(foodMapper.toEntity(sampleFoodRequest)).thenReturn(sampleFood);
        when(foodRepository.save(sampleFood)).thenReturn(sampleFood);
        when(foodMapper.toResponse(sampleFood)).thenReturn(sampleFoodResponse);

        // Act
        FoodResponse result = foodService.addFood(sampleFoodRequest, mockFile);

        // Assert
        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(storageService, times(1)).uploadFile(mockFile);
        verify(foodRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should fail adding food when file upload fails")
    void testAddFoodFileUploadFailure() {
        // Arrange
        when(storageService.uploadFile(mockFile)).thenThrow(new RuntimeException("Upload failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> foodService.addFood(sampleFoodRequest, mockFile));
        verify(foodRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should get all foods successfully")
    void testGetAllFoodsSuccess() {
        // Arrange
        List<FoodEntity> foodList = Arrays.asList(sampleFood);
        when(foodRepository.findAll()).thenReturn(foodList);
        when(foodMapper.toResponse(sampleFood)).thenReturn(sampleFoodResponse);

        // Act
        List<FoodResponse> result = foodService.getAllFoods();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
        verify(foodRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no foods exist")
    void testGetAllFoodsEmptyList() {
        // Arrange
        when(foodRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<FoodResponse> result = foodService.getAllFoods();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(foodRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get food by id successfully")
    void testGetFoodByIdSuccess() {
        // Arrange
        when(foodRepository.findById("1")).thenReturn(Optional.of(sampleFood));
        when(foodMapper.toResponse(sampleFood)).thenReturn(sampleFoodResponse);

        // Act
        FoodResponse result = foodService.getFood("1");

        // Assert
        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(foodRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("Should throw exception when food not found by id")
    void testGetFoodByIdNotFound() {
        // Arrange
        when(foodRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> foodService.getFood("nonexistent"));
        verify(foodRepository, times(1)).findById("nonexistent");
    }

    @Test
    @DisplayName("Should delete food successfully")
    void testDeleteFoodSuccess() {
        // Arrange
        when(foodRepository.findById("1")).thenReturn(Optional.of(sampleFood));
        when(storageService.deleteFile("/images/pizza.jpg")).thenReturn(true);

        // Act
        String result = foodService.deleteFood("1");

        // Assert
        assertEquals("Food deleted successfully", result);
        verify(foodRepository, times(1)).deleteById("1");
        verify(storageService, times(1)).deleteFile("/images/pizza.jpg");
    }

    @Test
    @DisplayName("Should not delete food when image deletion fails")
    void testDeleteFoodImageDeletionFails() {
        // Arrange
        when(foodRepository.findById("1")).thenReturn(Optional.of(sampleFood));
        when(storageService.deleteFile("/images/pizza.jpg")).thenReturn(false);

        // Act
        String result = foodService.deleteFood("1");

        // Assert
        assertEquals("Food deleted successfully", result);
        verify(foodRepository, never()).deleteById("1");
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent food")
    void testDeleteFoodNotFound() {
        // Arrange
        when(foodRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> foodService.deleteFood("nonexistent"));
        verify(storageService, never()).deleteFile(any());
    }

    @Test
    @DisplayName("Should upload file successfully")
    void testUploadFileSuccess() {
        // Arrange
        when(storageService.uploadFile(mockFile)).thenReturn("/images/pizza.jpg");

        // Act
        String result = foodService.upload(mockFile);

        // Assert
        assertEquals("/images/pizza.jpg", result);
        verify(storageService, times(1)).uploadFile(mockFile);
    }

    @Test
    @DisplayName("Should delete food image successfully")
    void testDeleteFoodImageSuccess() {
        // Arrange
        when(storageService.deleteFile("/images/pizza.jpg")).thenReturn(true);

        // Act
        boolean result = foodService.deleteFoodImage("/images/pizza.jpg");

        // Assert
        assertTrue(result);
        verify(storageService, times(1)).deleteFile("/images/pizza.jpg");
    }
}

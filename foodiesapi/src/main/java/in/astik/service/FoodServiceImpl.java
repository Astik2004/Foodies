package in.astik.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.astik.entity.FoodEntity;
import in.astik.exception.ResourceNotFoundException;
import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;
import in.astik.mapper.FoodMapper;
import in.astik.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepo;
    private final StorageService storageService;
    private final FoodMapper foodMapper;

    @Override
    public FoodResponse addFood(FoodRequest foodRequest, MultipartFile file) {
    	log.info("Adding new food item: {}", foodRequest.getName());
    	try {
    		String fileUrl = storageService.uploadFile(file);
    		log.debug("File uploaded successfully: {}", fileUrl);
    		
    		FoodEntity foodEntity = foodMapper.toEntity(foodRequest);
    		foodEntity.setImageUrl(fileUrl);
    		FoodEntity savedFood = foodRepo.save(foodEntity);
    		log.info("Food item saved with id: {}", savedFood.getId());
    		
    		return foodMapper.toResponse(savedFood);
    	} catch (Exception e) {
    		log.error("Error adding food item: {}", foodRequest.getName(), e);
    		throw new RuntimeException("Failed to add food item", e);
    	}
    }

	@Override
	public String upload(MultipartFile file) {
		return storageService.uploadFile(file);
	}

	@Override
	public List<FoodResponse> getAllFoods() {
		log.info("Fetching all foods from database");
		try {
			List<FoodEntity> foodEntities = foodRepo.findAll();
			log.debug("Retrieved {} food items", foodEntities.size());
			return foodEntities.stream()
                .map(foodMapper::toResponse)
                .collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error fetching all foods", e);
			throw e;
		}
	}

	@Override
	@Cacheable(value = "food", key = "#id")
	public FoodResponse getFood(String id) {
		log.info("Fetching food with id: {}", id);
		try {
			FoodEntity foodEntity = foodRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
			log.debug("Successfully retrieved food: {}", id);
			return foodMapper.toResponse(foodEntity);
		} catch (ResourceNotFoundException e) {
			log.warn("Food not found for id: {}", id);
			throw e;
		}
	}

	@Override
	public boolean deleteFoodImage(String imgurl) { 
		return storageService.deleteFile(imgurl);
    }

	@Override
	@CacheEvict(value = "food", key = "#id")
	public String deleteFood(String id) {
		log.info("Deleting food item with id: {}", id);
		try {
		    FoodEntity food = foodRepo.findById(id)
		            .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));

		    boolean isImageDeleted = storageService.deleteFile(food.getImageUrl());
		    log.debug("Image deletion status: {} for id: {}", isImageDeleted, id);
		    
		    if (isImageDeleted) {
		    	foodRepo.deleteById(id);
		    	log.info("Food item deleted successfully with id: {}", id);
		    }
		    return "Food deleted successfully";
		} catch (ResourceNotFoundException e) {
			log.warn("Food not found for deletion with id: {}", id);
			throw e;
		} catch (Exception e) {
			log.error("Error deleting food item with id: {}", id, e);
			throw e;
		}
	}
}

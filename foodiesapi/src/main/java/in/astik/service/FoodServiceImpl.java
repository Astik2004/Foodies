package in.astik.service;

import java.util.List;
import java.util.stream.Collectors;

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
    	log.info("Inside addFood");
    	String fileUrl = storageService.uploadFile(file);
    	FoodEntity foodEntity = foodMapper.toEntity(foodRequest);
    	foodEntity.setImageUrl(fileUrl);
    	FoodEntity savedFood = foodRepo.save(foodEntity);
    	return foodMapper.toResponse(savedFood);
    }

	@Override
	public String upload(MultipartFile file) {
		return storageService.uploadFile(file);
	}

	@Override
	public List<FoodResponse> getAllFoods() {
		log.info("Inside getAllFoods");
		List<FoodEntity> foodEntities = foodRepo.findAll();
		return foodEntities.stream()
                .map(foodMapper::toResponse)
                .collect(Collectors.toList());
	}

	@Override
	public FoodResponse getFood(String id) {
		log.info("Inside getFood");
		FoodEntity foodEntity = foodRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
		return foodMapper.toResponse(foodEntity);
	}

	@Override
	public boolean deleteFoodImage(String imgurl) { 
		return storageService.deleteFile(imgurl);
    }

	@Override
	public String deleteFood(String id) {
		log.info("Inside deleteFood");
	    FoodEntity food = foodRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));

	    boolean isImageDeleted = storageService.deleteFile(food.getImageUrl());
	    
	    if (isImageDeleted) {
	    	foodRepo.deleteById(id);
	    }
	    return "Food deleted successfully";
	}
}

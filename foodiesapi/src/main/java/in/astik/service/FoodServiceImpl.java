package in.astik.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.astik.entity.FoodEntity;
import in.astik.exception.FileStorageException;
import in.astik.exception.FoodNotFoundException;
import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;
import in.astik.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepo;

    private final String uploadDir = "upload/";

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    @Override
    public FoodResponse addFood(FoodRequest foodRequest, MultipartFile file) 
    {
    	log.info("Inside addFood");
    	String filePath = upload(file);
    	FoodEntity foodEntity = convertToEntity(foodRequest);
    	foodEntity.setImageUrl(filePath);
    	FoodEntity savedFood = foodRepo.save(foodEntity);
    	return convertToResponse(savedFood);
    }

    private FoodEntity convertToEntity(FoodRequest request) {
        return FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
    }

    private FoodResponse convertToResponse(FoodEntity entity) {
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(entity.getCategory())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    @Override
    public String upload(MultipartFile file)
    {    
    	if (file == null || file.isEmpty()) 
    		{
    			throw new FileStorageException("No file uploaded or file is empty");
    		}
    	try {
    		Files.createDirectories(Paths.get(uploadDir));
    		String originalName = file.getOriginalFilename();
    		if (originalName == null || !originalName.matches(".*\\.(png|jpg|jpeg|webp|gif)$")) {
    			throw new FileStorageException("Invalid file type. Allowed: png, jpg, jpeg, webp, gif");
    		}

    		String fileName = System.currentTimeMillis() + "_" + originalName;

    		Path path = Paths.get(uploadDir + fileName);
    		Files.write(path, file.getBytes());
    		return "http://localhost:8080/images/" + fileName;
    	} catch (IOException e) {
    		throw new FileStorageException("Error saving file: " + file.getOriginalFilename(), e);
    	}
}

	@Override
	public List<FoodResponse> getAllFoods() {
		log.info("Inside getAllFoods");
		List<FoodEntity> foodEntities = foodRepo.findAll();
		return foodEntities.stream().map(entity->convertToResponse(entity)).collect(Collectors.toList());
	}

	@Override
	public FoodResponse getFood(String id) {
		log.info("Inside getFood");
		FoodEntity foodEntity = foodRepo.findById(id).orElseThrow(()->new RuntimeException("Food not found"));
		return convertToResponse(foodEntity);
	}

	@Override
	public boolean deleteFoodImage(String imgurl) { 
		String fileName="";
		try {
	         fileName = imgurl.substring(imgurl.lastIndexOf("/") + 1);
	
	        Path path = Paths.get(uploadDir + fileName);
	
	        if (!Files.exists(path)) {
	            throw new FileStorageException("Image not found: " + fileName);
	        }
	
	        Files.delete(path);
	        return true;
	
	    } catch (IOException e) {
	        throw new FileStorageException("Error deleting file: " + fileName, e);
	    }
}

	@Override
	public String deleteFood(String id) {
		log.info("Inside deleteFood");
	    FoodEntity food = foodRepo.findById(id)
	            .orElseThrow(() -> new FoodNotFoundException("Food not found with id: " + id));

	    boolean  isImageDeleted = deleteFoodImage(food.getImageUrl());
	    
	    if(isImageDeleted) {
	    	foodRepo.deleteById(id);
	    }
	    return "Food deleted successfully";

	}
}

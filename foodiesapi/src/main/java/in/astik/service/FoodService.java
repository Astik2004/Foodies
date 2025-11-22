package in.astik.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;

public interface FoodService {

	String upload(MultipartFile file);
	FoodResponse addFood(FoodRequest foodRequest,MultipartFile file);
	List<FoodResponse> getAllFoods();
	FoodResponse getFood(String id);
	boolean deleteFoodImage(String fileName);
	public String deleteFood(String id);
}

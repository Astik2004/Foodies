package in.astik.controller;


import java.awt.Taskbar.Feature;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.astik.entity.FoodEntity;
import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;
import in.astik.service.FoodService;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("*")
public class FoodController {
	
	@Autowired
	private final FoodService foodService;
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<FoodResponse> addFood(@RequestPart("food") String foodRequest, @RequestPart("file") MultipartFile file) 
			throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		FoodRequest foodReq = objectMapper.readValue(foodRequest, FoodRequest.class);
		FoodResponse res=foodService.addFood(foodReq, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
	
	@GetMapping()
	public ResponseEntity<List<FoodResponse>>getAllFoods(){
		List<FoodResponse>foods=foodService.getAllFoods();
		return ResponseEntity.ok(foods);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FoodResponse>getFood(@PathVariable String id){
		FoodResponse food=foodService.getFood(id);
		return ResponseEntity.ok(food);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>deleteFood(@PathVariable String id){
		String message=foodService.deleteFood(id);
		return ResponseEntity.ok(message);
	}
}

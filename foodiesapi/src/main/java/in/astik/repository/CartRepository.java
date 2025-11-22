package in.astik.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.astik.entity.CartEntity;

public interface CartRepository extends MongoRepository<CartEntity, String>{
	Optional<CartEntity>findByUserId(String userId);
	
	void deleteByUserId(String userId);
}

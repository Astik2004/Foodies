package in.astik.mapper;

import in.astik.entity.FoodEntity;
import in.astik.io.FoodRequest;
import in.astik.io.FoodResponse;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodEntity toEntity(FoodRequest request) {
        if (request == null) {
            return null;
        }
        return FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
    }

    public FoodResponse toResponse(FoodEntity entity) {
        if (entity == null) {
            return null;
        }
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(entity.getCategory())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}

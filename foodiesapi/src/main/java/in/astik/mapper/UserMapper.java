package in.astik.mapper;

import in.astik.entity.UserEntity;
import in.astik.io.UserRequest;
import in.astik.io.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }
        return UserEntity.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();
    }

    public UserResponse toResponse(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .build();
    }
}

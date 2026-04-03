package in.astik.mapper;

import in.astik.entity.CartEntity;
import in.astik.io.CartResponse;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartResponse toResponse(CartEntity cart) {
        if (cart == null) {
            return null;
        }
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cart.getItems())
                .build();
    }
}

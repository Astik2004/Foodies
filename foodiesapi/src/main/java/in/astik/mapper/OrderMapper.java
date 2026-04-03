package in.astik.mapper;

import in.astik.entity.OrderEntity;
import in.astik.io.OrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    
    public OrderEntity toEntity(OrderRequest request) {
        if (request == null) {
            return null;
        }
        return OrderEntity.builder()
                .userId(request.getUserId())
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderItems(request.getOrderItems())
                .build();
    }
}

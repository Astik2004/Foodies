package in.astik.service;

import com.razorpay.Order;
import in.astik.entity.OrderEntity;
import in.astik.io.OrderRequest;
import in.astik.io.OrderResponse;
import in.astik.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest orderRequest) {
        OrderEntity newOrder=convertToEntity(orderRequest);
        newOrder=orderRepository.save(newOrder);
        return null;
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        return OrderEntity.builder()
                .userId(request.getUserId())
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderItems(request.getOrderItems())
                .build();
    }
}

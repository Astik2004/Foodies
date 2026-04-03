package in.astik.service;

import org.springframework.stereotype.Service;

import in.astik.entity.OrderEntity;
import in.astik.io.OrderRequest;
import in.astik.io.OrderResponse;
import in.astik.mapper.OrderMapper;
import in.astik.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest orderRequest) {
        log.info("Inside createOrderWithPayment");
        OrderEntity newOrder = orderMapper.toEntity(orderRequest);
        newOrder = orderRepository.save(newOrder);
        // Assuming current logic returns null or needs unimplemented functionality.
        return null;
    }
}

package in.astik.service;

import in.astik.io.OrderRequest;
import in.astik.io.OrderResponse;

public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest orderRequest);

}

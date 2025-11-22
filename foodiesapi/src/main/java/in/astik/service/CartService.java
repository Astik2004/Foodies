package in.astik.service;

import in.astik.io.CartRequest;
import in.astik.io.CartResponse;

public interface CartService {
	CartResponse addToCart(CartRequest request);
	CartResponse getCart();
	void clearCart();
	CartResponse decreaseQtyFromCart(CartRequest request);
	void deleteProductFromCart(String foodId);
}

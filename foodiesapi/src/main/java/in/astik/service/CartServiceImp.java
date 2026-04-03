package in.astik.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import in.astik.entity.CartEntity;
import in.astik.exception.ResourceNotFoundException;
import in.astik.io.CartRequest;
import in.astik.io.CartResponse;
import in.astik.mapper.CartMapper;
import in.astik.repository.CartRepository;
import in.astik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImp implements CartService {
	
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final CartMapper cartMapper;

	@Override
	public CartResponse addToCart(CartRequest request) {
		log.info("Inside AddToCart");
		String loggedInUserId = userService.findByUserId();
		
		CartEntity cart = cartRepository.findByUserId(loggedInUserId)
                .orElseGet(() -> new CartEntity(loggedInUserId, new HashMap<>()));
		
		Map<String, Integer> cartItems = cart.getItems();
		cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(), 0) + 1);
		cart.setItems(cartItems);
		cart = cartRepository.save(cart);
		
		return cartMapper.toResponse(cart);
	}

	@Override
	public CartResponse getCart() {
		log.info("Inside getCart");
		String loggedInUserId = userService.findByUserId();
		CartEntity cart = cartRepository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null, loggedInUserId, new HashMap<>()));
		return cartMapper.toResponse(cart);
	}

	@Override
	public void clearCart() {
		log.info("Inside clearCart");
		String loggedInUserId = userService.findByUserId();
		cartRepository.deleteByUserId(loggedInUserId);
	}

	@Override
	public CartResponse decreaseQtyFromCart(CartRequest request) {
		log.info("Inside removeFromCart()");
		String loggedInUserId = userService.findByUserId();
		CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Is Not Found"));
		
		Map<String, Integer> cartItems = entity.getItems();
		if (cartItems.containsKey(request.getFoodId())) {
			int currentQty = cartItems.get(request.getFoodId());
			if (currentQty > 1) {
				cartItems.put(request.getFoodId(), currentQty - 1);
			} else {
				cartItems.remove(request.getFoodId());
			}
			entity.setItems(cartItems);
			entity = cartRepository.save(entity);
		}
		return cartMapper.toResponse(entity);
	}

	@Override
	public void deleteProductFromCart(String foodId) {
		log.info("Inside deleteProductFromCart()");
		String loggedInUserId = userService.findByUserId();
		CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Is Not Found"));
		
		Map<String, Integer> cartItems = entity.getItems();
		if (cartItems.containsKey(foodId)) {
			cartItems.put(foodId, 0); // or remove depending on intent, keeping logic identical
			entity.setItems(cartItems);
		    cartRepository.save(entity);		
		}
	}
}

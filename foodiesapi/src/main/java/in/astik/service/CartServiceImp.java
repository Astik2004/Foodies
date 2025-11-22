package in.astik.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import in.astik.entity.CartEntity;
import in.astik.io.CartRequest;
import in.astik.io.CartResponse;
import in.astik.repository.CartRepository;
import in.astik.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImp implements CartService{
	
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Override
	public CartResponse addToCart(CartRequest request) {
		log.info("Inside AddToCart");
		String loggedInUserId=userService.findByUserId();
		Optional<CartEntity>cartOptional=cartRepository.findByUserId(loggedInUserId);
		CartEntity cart=cartOptional.orElseGet(()->new CartEntity(loggedInUserId,new HashMap<>()));
		Map<String, Integer>cartItems=cart.getItems();
		cartItems.put(request.getFoodId(),cartItems.getOrDefault(request.getFoodId(), 0)+1 );
		cart.setItems(cartItems);
		cart=cartRepository.save(cart);
		return convertToResponse(cart);
		
	}
	private CartResponse convertToResponse(CartEntity cart)
	{
		return CartResponse.builder()
						.id(cart.getId())
						.userId(cart.getUserId())
						.items(cart.getItems())
						.build();
	}
	@Override
	public CartResponse getCart() {
		log.info("Inside getCart");
		String loggedInUserId=userService.findByUserId();
		CartEntity cart= cartRepository.findByUserId(loggedInUserId).orElse(new CartEntity(null,loggedInUserId,new HashMap<>()));
		return convertToResponse(cart);
	}
	@Override
	public void clearCart() {
		log.info("Inside clearCart");
		String loggedInUserId=userService.findByUserId();
		cartRepository.deleteByUserId(loggedInUserId);
	}
	@Override
	public CartResponse decreaseQtyFromCart(CartRequest request) {
		log.info("Inside rmoveFromCart()");
		String loggedInUserId=userService.findByUserId();
		CartEntity entity=cartRepository.findByUserId(loggedInUserId).orElseThrow(()->new RuntimeException("Cart Is Not Found"));
		Map<String, Integer>cartItems=entity.getItems();
		if(cartItems.containsKey(request.getFoodId()))
		{
			int curentQty=cartItems.get(request.getFoodId());
			if(curentQty>0)
			{
				cartItems.put(request.getFoodId(), curentQty-1);
			}
			else
			{
				cartItems.remove(request.getFoodId());
			}
			entity=cartRepository.save(entity);
		}
		return convertToResponse(entity);
	}
	@Override
	public void deleteProductFromCart(String foodId) {
		log.info("Inside deleteProductFromCart()");
		String loggedInUserId=userService.findByUserId();
		CartEntity entity=cartRepository.findByUserId(loggedInUserId).orElseThrow(()->new RuntimeException("Cart Is Not Found"));
		Map<String, Integer>cartItems=entity.getItems();
		if(cartItems.containsKey(foodId))
		{
			cartItems.put(foodId,0);
			entity.setItems(cartItems);
		    cartRepository.save(entity);		
		}
	}
	
}

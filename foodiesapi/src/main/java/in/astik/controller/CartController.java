package in.astik.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import in.astik.io.CartRequest;
import in.astik.io.CartResponse;
import in.astik.service.CartService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {	
	private final CartService cartService;

	@PostMapping
	public ResponseEntity<?>addToCart(@RequestBody CartRequest request)
	{
		String foodId=request.getFoodId();
		if(foodId==null || foodId.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FoodId not Found");
		}
		CartResponse response=cartService.addToCart(request);
		return ResponseEntity.ok().body(response);
	}
	@GetMapping
	public ResponseEntity<CartResponse> getCart()
	{
		CartResponse response=cartService.getCart();
		return ResponseEntity.ok(response);
	}
	@DeleteMapping
	public ResponseEntity<?>clearCart()
	{
		cartService.clearCart();
		return ResponseEntity.ok("Cart Deleted");
	}
	@PostMapping("/remove")
	public ResponseEntity<CartResponse>decreaseQtyFromCart(@RequestBody CartRequest req)
	{
		String foodId=req.getFoodId();
		if(foodId==null || foodId.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FoodId not found");
		}
		CartResponse res=cartService.decreaseQtyFromCart(req);
		return ResponseEntity.ok().body(res);
	}
	@DeleteMapping("/{foodId}")
	public ResponseEntity<?>deleteProductFromCart(@PathVariable String foodId)
	{
		cartService.deleteProductFromCart(foodId);
		return ResponseEntity.ok("Product Removed From Cart");
	}
}

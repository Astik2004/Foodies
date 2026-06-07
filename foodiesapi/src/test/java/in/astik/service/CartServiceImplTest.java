package in.astik.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import in.astik.entity.CartEntity;
import in.astik.exception.ResourceNotFoundException;
import in.astik.io.CartRequest;
import in.astik.io.CartResponse;
import in.astik.mapper.CartMapper;
import in.astik.repository.CartRepository;
import in.astik.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("CartService Unit Tests")
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImp cartService;

    private CartEntity sampleCart;
    private CartRequest sampleCartRequest;
    private CartResponse sampleCartResponse;
    private String userId = "user123";

    @BeforeEach
    void setUp() {
        Map<String, Integer> items = new HashMap<>();
        items.put("food1", 2);
        items.put("food2", 1);

        sampleCart = CartEntity.builder()
                .userId(userId)
                .items(items)
                .build();

        sampleCartRequest = CartRequest.builder()
                .foodId("food1")
                .build();

        sampleCartResponse = CartResponse.builder()
                .userId(userId)
                .items(items)
                .build();
    }

    @Test
    @DisplayName("Should add food to cart successfully")
    void testAddToCartSuccess() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(sampleCart));
        when(cartRepository.save(any(CartEntity.class))).thenReturn(sampleCart);
        when(cartMapper.toResponse(sampleCart)).thenReturn(sampleCartResponse);

        // Act
        CartResponse result = cartService.addToCart(sampleCartRequest);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should create new cart when user has no cart")
    void testAddToCartNewCart() {
        // Arrange
        CartRequest request = CartRequest.builder().foodId("food3").build();
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(CartEntity.class))).thenReturn(sampleCart);
        when(cartMapper.toResponse(any())).thenReturn(sampleCartResponse);

        // Act
        CartResponse result = cartService.addToCart(request);

        // Assert
        assertNotNull(result);
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should get cart successfully")
    void testGetCartSuccess() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(sampleCart));
        when(cartMapper.toResponse(sampleCart)).thenReturn(sampleCartResponse);

        // Act
        CartResponse result = cartService.getCart();

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(cartRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return empty cart when user has no cart")
    void testGetCartEmpty() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        CartResponse emptyResponse = CartResponse.builder()
                .userId(userId)
                .items(new HashMap<>())
                .build();
        when(cartMapper.toResponse(any())).thenReturn(emptyResponse);

        // Act
        CartResponse result = cartService.getCart();

        // Assert
        assertNotNull(result);
        verify(cartRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should clear cart successfully")
    void testClearCartSuccess() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);

        // Act
        cartService.clearCart();

        // Assert
        verify(cartRepository, times(1)).deleteByUserId(userId);
    }

    @Test
    @DisplayName("Should decrease quantity from cart successfully")
    void testDecreaseQtyFromCartSuccess() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(sampleCart));
        when(cartRepository.save(any())).thenReturn(sampleCart);
        when(cartMapper.toResponse(any())).thenReturn(sampleCartResponse);

        // Act
        CartResponse result = cartService.decreaseQtyFromCart(sampleCartRequest);

        // Assert
        assertNotNull(result);
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should remove item from cart when quantity becomes 1")
    void testDecreaseQtyRemovesItem() {
        // Arrange
        CartEntity cartWithOneItem = CartEntity.builder()
                .userId(userId)
                .items(new HashMap<>(Map.of("food1", 1)))
                .build();

        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartWithOneItem));
        when(cartRepository.save(any())).thenReturn(cartWithOneItem);
        when(cartMapper.toResponse(any())).thenReturn(sampleCartResponse);

        // Act
        CartResponse result = cartService.decreaseQtyFromCart(sampleCartRequest);

        // Assert
        assertNotNull(result);
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when cart not found for decrease qty")
    void testDecreaseQtyCartNotFound() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, 
            () -> cartService.decreaseQtyFromCart(sampleCartRequest));
    }

    @Test
    @DisplayName("Should delete product from cart successfully")
    void testDeleteProductFromCartSuccess() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(sampleCart));
        when(cartRepository.save(any())).thenReturn(sampleCart);

        // Act
        cartService.deleteProductFromCart("food1");

        // Assert
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when cart not found for delete product")
    void testDeleteProductCartNotFound() {
        // Arrange
        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
            () -> cartService.deleteProductFromCart("food1"));
    }

    @Test
    @DisplayName("Should not save when product not in cart")
    void testDeleteProductNotInCart() {
        // Arrange
        CartEntity cart = CartEntity.builder()
                .userId(userId)
                .items(new HashMap<>(Map.of("food2", 1)))
                .build();

        when(userService.findByUserId()).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        // Act
        cartService.deleteProductFromCart("food1");

        // Assert
        verify(cartRepository, never()).save(any());
    }
}

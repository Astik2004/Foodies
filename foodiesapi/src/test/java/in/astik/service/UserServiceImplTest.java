package in.astik.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import in.astik.entity.UserEntity;
import in.astik.exception.DuplicateResourceException;
import in.astik.exception.ResourceNotFoundException;
import in.astik.io.UserRequest;
import in.astik.io.UserResponse;
import in.astik.mapper.UserMapper;
import in.astik.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImp userService;

    private UserEntity sampleUser;
    private UserRequest sampleUserRequest;
    private UserResponse sampleUserResponse;

    @BeforeEach
    void setUp() {
        sampleUser = UserEntity.builder()
                .id("user1")
                .email("test@example.com")
                .password("hashedPassword123")
                .name("John Doe")
                .build();

        sampleUserRequest = new UserRequest();
        sampleUserRequest.setEmail("test@example.com");
        sampleUserRequest.setPassword("plainPassword123");
        sampleUserRequest.setName("John Doe");

        sampleUserResponse = UserResponse.builder()
                .id("user1")
                .email("test@example.com")
                .name("John Doe")
                .build();
    }

    @Test
    @DisplayName("Should register user successfully")
    void testRegisterUserSuccess() {
        // Arrange
        when(userRepository.existsByEmail(sampleUserRequest.getEmail())).thenReturn(false);
        when(userMapper.toEntity(sampleUserRequest)).thenReturn(sampleUser);
        when(passwordEncoder.encode(sampleUserRequest.getPassword())).thenReturn("hashedPassword123");
        when(userRepository.save(any(UserEntity.class))).thenReturn(sampleUser);
        when(userMapper.toResponse(sampleUser)).thenReturn(sampleUserResponse);

        // Act
        UserResponse result = userService.registerUser(sampleUserRequest);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).existsByEmail(sampleUserRequest.getEmail());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when email already registered")
    void testRegisterUserDuplicateEmail() {
        // Arrange
        when(userRepository.existsByEmail(sampleUserRequest.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> userService.registerUser(sampleUserRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void testFindByUserIdSuccess() {
        // Arrange
        when(authenticationFacade.getAuthentication()).thenReturn(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        "test@example.com", null));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        // Act
        String result = userService.findByUserId();

        // Assert
        assertEquals("user1", result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should throw exception when user not found by email")
    void testFindByUserIdNotFound() {
        // Arrange
        when(authenticationFacade.getAuthentication()).thenReturn(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        "nonexistent@example.com", null));
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.findByUserId());
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("Should encrypt password on registration")
    void testPasswordEncryption() {
        // Arrange
        when(userRepository.existsByEmail(sampleUserRequest.getEmail())).thenReturn(false);
        when(userMapper.toEntity(sampleUserRequest)).thenReturn(sampleUser);
        when(passwordEncoder.encode(sampleUserRequest.getPassword())).thenReturn("encrypted");
        when(userRepository.save(any(UserEntity.class))).thenReturn(sampleUser);
        when(userMapper.toResponse(sampleUser)).thenReturn(sampleUserResponse);

        // Act
        userService.registerUser(sampleUserRequest);

        // Assert
        verify(passwordEncoder, times(1)).encode(sampleUserRequest.getPassword());
    }
}

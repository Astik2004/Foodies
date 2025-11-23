package in.astik.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import in.astik.io.UserRequest;
import in.astik.io.UserResponse;
import in.astik.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void registerUserTest_Success() {
        UserRequest req = new UserRequest();
        req.setName("TestUser2");
        req.setEmail("testUser2@gmail.com");
        req.setPassword("1234");
        
        UserResponse res = userService.registerUser(req);
        
        assertNotNull(res);
        assertEquals(userRepository.findByEmail(req.getEmail()).get().getId(), res.getId());
        assertEquals("TestUser2", res.getName());
        assertEquals("testUser2@gmail.com", res.getEmail());
    }

    @Test
    void registerUserTest_EmailAlreadyExists() {
        UserRequest req = new UserRequest();
        req.setName("TestUser1");
        req.setEmail("testUser1@gmail.com");
        req.setPassword("1234");
        
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(req)
        );
        
        assertEquals("Email already registered", exception.getMessage());
    }

}

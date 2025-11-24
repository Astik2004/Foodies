package in.astik.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendMail()
    {
        redisTemplate.opsForValue().set("email","astik@gmail.com");
        Object email=redisTemplate.opsForValue().get("email");
        assertEquals("astik@gmail.com",email);
    }
}

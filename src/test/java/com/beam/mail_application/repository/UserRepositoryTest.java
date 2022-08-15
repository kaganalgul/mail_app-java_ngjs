package com.beam.mail_application.repository;

import com.beam.mail_application.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void set() {
        User user = new User()
                .setName("Elon")
                .setLastname("Musk")
                .setMailAddress("elon.musk@beamteknoloji.com")
                .setPassword("tesla");

        userRepository.save(user);
    }
}
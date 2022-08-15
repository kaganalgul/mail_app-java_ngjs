package com.beam.mail_application.service;

import com.beam.mail_application.dto.AuthenticationResponse;
import com.beam.mail_application.model.User;
import com.beam.mail_application.repository.UserRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    private final PasswordEncoder passwordEncoder;

    public static final String SESSION_ACCOUNT = "account";

    public AuthenticationResponse login(String mailAddress, String password) {

        Optional<User> optionalUser = userRepository.findByMailAddress(mailAddress);

        if (optionalUser.isEmpty()) {
            return new AuthenticationResponse()
                    .setCode(-1);
        } else {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new AuthenticationResponse()
                        .setCode(1)
                        .setUser(user);
            } else {
                return new AuthenticationResponse()
                        .setCode(0);
            }
        }

    }

    public Boolean register(String name, String lastname, String mailAddress, String password) {
        if (userRepository.findByMailAddress(mailAddress).orElse(null) == null) {
            User user = new User()
                    .setName(name)
                    .setLastname(lastname)
                    .setMailAddress(mailAddress)
                    .setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            log.info("User created");
            return true;
        } else {
            log.info("User is not created");
            return false;
        }
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    public User updateUsername(User user, String newMailAddress) {
        user.setMailAddress(newMailAddress);
        userRepository.save(user);
        return user;
    }

    public Boolean delete(String id) {
        DeleteResult deleteResult = mongoTemplate.remove(query(where("_id").is(id)), "User");

        if (deleteResult.getDeletedCount() == 1) {
            log.info("User Deleted");
            return true;
        }
        log.info("User is not Deleted");
        return false;
    }
}

package com.beam.mail_application.repository;

import com.beam.mail_application.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByMailAddress(String mailAddress);

    Optional<User> findByMailAddressAndPassword(String mailAddress, String password);
}

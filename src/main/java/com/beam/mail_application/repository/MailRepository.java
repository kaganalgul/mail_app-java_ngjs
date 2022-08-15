package com.beam.mail_application.repository;

import com.beam.mail_application.model.Mail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MailRepository extends MongoRepository<Mail, String> {

    public List<Mail> findBySender(String sender);




}

package com.beam.mail_application.repository;

import com.beam.mail_application.model.Mail;
import com.beam.mail_application.model.SendMail;
import com.beam.mail_application.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SendMailRepository extends MongoRepository<SendMail, String> {

    public List<SendMail> findByReceiver(String receiverMailAddress);
    public Optional<SendMail> findByMailId(String id);

    public Optional<SendMail> findByReceiverAndMail(String receiver, String id);
}

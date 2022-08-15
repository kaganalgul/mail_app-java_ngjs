package com.beam.mail_application.repository;

import com.beam.mail_application.model.Mail;
import com.beam.mail_application.model.SendMail;
import com.beam.mail_application.model.Status;
import com.beam.mail_application.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class SendMailRepositoryTest {

    @Autowired
    private SendMailRepository sendMailRepository;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void sendManyMail(){
        Optional<User> sender = userRepository.findById("db83c604-d70b-43a5-a2c5-37ce5a5aabf4");
        Optional<User> reciver1 = userRepository.findById("d62ef4ea-a033-48fc-a775-c9343cff7386");
        Optional<User> reciver2 = userRepository.findById("0f4337ff-8acb-4640-9e8a-bf58e31270ca");
        Mail mail = new Mail()
                .setTitle("Say Hello!")
                .setContent("Hello")
                .setSender(sender.get().getMailAddress())
                .setReceiverList(Arrays.asList(reciver2.get().getMailAddress(),reciver1.get().getMailAddress()));
        SendMail sendMail = new SendMail()
                .setMail(mail)
                .setReceiver(reciver1.get().getMailAddress())
                .setStatus(Status.RECEIVED);
        SendMail sendMail1 = new SendMail()
                .setMail(mail)
                .setReceiver(reciver2.get().getMailAddress())
                .setStatus(Status.RECEIVED);
        mailRepository.save(mail);
        sendMailRepository.saveAll(Arrays.asList(sendMail,sendMail1));
    }

    @Test
    public void replyMail(){
        Optional<Mail> parentMail = mailRepository.findById("4f0c6c73-b160-4679-bb6f-03672aa91093");
        Optional<User> sender = userRepository.findById("b3284065-f315-42e7-b86e-38921b585e1f");
        Optional<User> reciver = userRepository.findById("a65e7263-53ce-4dc8-9fec-0a0ed3f6be7d");
        Mail mail = new Mail()
                .setParent(parentMail.get())
                .setContent("Hi Esin")
                .setTitle("Hi")
                .setSender(sender.get().getMailAddress());
        SendMail sendMail = new SendMail()
                .setMail(mail)
                .setReceiver(reciver.get().getMailAddress())
                .setStatus(Status.RECEIVED);
        mailRepository.save(mail);
        sendMailRepository.save(sendMail);
    }
}

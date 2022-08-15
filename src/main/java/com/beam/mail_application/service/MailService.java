package com.beam.mail_application.service;

import com.beam.mail_application.dto.SendMailRequest;
import com.beam.mail_application.model.Mail;
import com.beam.mail_application.model.SendMail;
import com.beam.mail_application.model.Status;
import com.beam.mail_application.model.User;
import com.beam.mail_application.repository.MailRepository;
import com.beam.mail_application.repository.SendMailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Receiver;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.beam.mail_application.service.UserService.SESSION_ACCOUNT;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final MailRepository mailRepository;

    private final SendMailRepository sendMailRepository;

    public void send(String title, String content, String file, List<String> receiverList, HttpSession session) {
        User user = (User)session.getAttribute(SESSION_ACCOUNT);
        String sender = user.getMailAddress();
        Mail mail = new Mail()
                .setTitle(title)
                .setContent(content)
                .setFile(file)
                .setSender(sender)
                .setReceiverList(receiverList);
        mailRepository.save(mail);

        for (String receiver : receiverList) {
            SendMail sendMail = new SendMail()
                    .setReceiver(receiver)
                    .setMail(mail);

            sendMailRepository.save(sendMail);
        }
    }

    public List<SendMail> list(String mail) {
        List<SendMail> sendMails = sendMailRepository.findByReceiver(mail);
        //List<Mail> mails = new ArrayList<>();
        //for (SendMail item : sendMails) {
        //    Optional<Mail> receivedMail = mailRepository.findById(item.getMail().getId());
        //    mails.add(receivedMail.get());
        //}

        //List<Object> inbox = Stream.concat(mails.stream(), sendMails.stream()).collect(Collectors.toList());
        return sendMails;
    }

    public void delete(HttpSession session, String id) {

        User user = (User) session.getAttribute(SESSION_ACCOUNT);
        String receiver = user.getMailAddress();
        Optional<SendMail> mail = sendMailRepository.findByReceiverAndMail(receiver, id);

        if (mail.get().getStatus() == Status.DELETED) {
            Optional<SendMail> sendMail = sendMailRepository.findByMailId(id);
            mailRepository.deleteById(id);
            sendMailRepository.delete(sendMail.get());
        } else {
            mail.get().setStatus(Status.DELETED);
            sendMailRepository.save(mail.get());
        }
    }

    public void sendSpam(String id) {
        Optional<SendMail> mail = sendMailRepository.findById(id);

        mail.get().setStatus(Status.SPAM);
        sendMailRepository.save(mail.get());
    }

    public Mail find(String id) {
        Optional<SendMail> sendMail = sendMailRepository.findByMailId(id);
        Optional<Mail> mail = mailRepository.findById(id);
        sendMail.get().setStatus(Status.READ);
        sendMailRepository.save(sendMail.get());
        return mail.get();
    }

    public List<Mail> findBySender(String sender) {
        List<Mail> sendbox = mailRepository.findBySender(sender);

        return sendbox;
    }

    public void reply(String id, String sender , SendMailRequest request, HttpSession session) {
        Optional<Mail> mail = mailRepository.findById(id);
        List<String> receiverList = mail.get().getReceiverList();
        receiverList.add(mail.get().getSender());
        receiverList.remove(sender);
        send(request.getTitle(), request.getContent(), request.getFile(), receiverList, session);
        mailRepository.save(mail.get());
    }
}

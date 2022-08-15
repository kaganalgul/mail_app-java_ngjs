package com.beam.mail_application.controller;

import com.beam.mail_application.dto.SendMailRequest;
import com.beam.mail_application.model.Mail;
import com.beam.mail_application.model.SendMail;
import com.beam.mail_application.model.Status;
import com.beam.mail_application.model.User;
import com.beam.mail_application.repository.MailRepository;
import com.beam.mail_application.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.beam.mail_application.service.UserService.SESSION_ACCOUNT;

@RequiredArgsConstructor
@RestController
@RequestMapping("mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("send-mail")
    public void send(@RequestBody SendMailRequest request, HttpSession session) {
        mailService.send(request.getTitle(), request.getContent(), request.getFile(), request.getReceiverList(), session);
    }

    @GetMapping("inbox")
    public List<SendMail> list(HttpSession session)
    {
        Object obj = session.getAttribute(SESSION_ACCOUNT);
        User user = (User) obj;
        String mail = user.getMailAddress();
        return mailService.list(mail);
    }

    @GetMapping("sendbox")
    public List<Mail> listSendBox(HttpSession session)
    {
        User user = (User) session.getAttribute(SESSION_ACCOUNT);
        return mailService.findBySender(user.getMailAddress());
    }

    @DeleteMapping("delete")
    public boolean delete(HttpSession session, @RequestParam String id)
    {
        mailService.delete(session, id);
        return true;
    }

    @GetMapping("send-spam")
    public String sendSpamForm() {
        return "send-spam";
    }

    @PostMapping("send-spam")
    public void sendSpam(@RequestParam String id)
    {
        mailService.sendSpam(id);
    }

    @GetMapping("mail-content/{id}")
    public Mail read(@PathVariable String id) {
        Mail mail = mailService.find(id);
        return mail;
    }

    @PostMapping("reply")
    public void reply(@RequestParam String id, @RequestParam String sender, @RequestBody SendMailRequest request, HttpSession session)
    {
        mailService.reply(id, sender, request, session);
    }
}

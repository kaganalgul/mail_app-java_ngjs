package com.beam.mail_application.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static com.beam.mail_application.service.UserService.SESSION_ACCOUNT;

@Controller
public class PageController {
    @GetMapping(value = {"/inbox", "/send-mail", "/trash", "/mail-content", "/reply"}, produces = {MediaType.TEXT_HTML_VALUE})
    public String home(HttpSession session) {
        if (session.getAttribute(SESSION_ACCOUNT) == null) {
            return "redirect:/login";
        } else {
            return "index";
        }
    }

    @GetMapping(value = {"/","/login","/register", "/delete"}, produces = {MediaType.TEXT_HTML_VALUE})
    public String login() {
        return "index";
    }

}

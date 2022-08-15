package com.beam.mail_application.controller;

import com.beam.mail_application.dto.AuthenticationRequest;
import com.beam.mail_application.dto.AuthenticationResponse;
import com.beam.mail_application.model.User;
import com.beam.mail_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.beam.mail_application.service.UserService.SESSION_ACCOUNT;

@RequiredArgsConstructor
@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("login")
    public AuthenticationResponse login(HttpSession session, @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = userService.login(request.getMailAddress(), request.getPassword());

        if (response.getCode() == 1){
            session.setAttribute(SESSION_ACCOUNT, response.getUser());
        }

        return response;
    }

    @ResponseBody
    @PostMapping("register")
    public void register(@RequestBody User user)
    {
        userService.register(user.getName(), user.getLastname(), user.getMailAddress(), user.getPassword());
    }

    @ResponseBody
    @PostMapping("update-password")
    public User updatePassword(@RequestParam String id, @RequestParam String newPassword) {
        User user = userService.findById(id);
        userService.updatePassword(user, newPassword);

        return user;
    }

    @GetMapping("update-mail-address")
    public String updateUsernameForm() {
        return "update-mail-address";
    }

    @ResponseBody
    @PostMapping("update-mail-address")
    public User updateUsername(@RequestParam String id, @RequestParam String newMailAddress) {
        User user = userService.findById(id);
        userService.updateUsername(user, newMailAddress);

        return user;
    }

    @GetMapping("delete")
    public Boolean delete(@RequestParam String id) {
        return userService.delete(id);
    }


    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_ACCOUNT);
        return "redirect:/login";
    }
}

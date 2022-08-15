package com.beam.mail_application.dto;

import com.beam.mail_application.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationResponse {

    /**
     * 1 = SUCCESS
     * 0 -> PASSWORD DOES NOT MATCH
     * -1 -> USERNAME NOT FOUND
     * */
    private int code;
    private User user;
}

package com.beam.mail_application.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationRequest {
    private String mailAddress;
    private String password;
}

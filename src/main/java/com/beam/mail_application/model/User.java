package com.beam.mail_application.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Accessors(chain = true)
@Document("User")
@TypeAlias("User")
public class User extends Base{
    private String name;
    private String lastname;
    private String mailAddress;
    private String password;
}

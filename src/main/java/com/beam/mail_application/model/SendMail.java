package com.beam.mail_application.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Accessors(chain = true)
@TypeAlias("SendMail")
@Document("SendMail")
public class SendMail extends Base {
    @DBRef
    private Mail mail;
    private String receiver;
    private Status status = Status.RECEIVED;
}

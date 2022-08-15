package com.beam.mail_application.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;

import javax.sound.midi.Receiver;
import java.util.*;

@Data
@Accessors(chain = true)
@TypeAlias("Mail")
@Document("Mail")
public class Mail extends Base {
    private String title;
    private String content;
    private String file;
    @DBRef
    private Mail parent;
    private Date createDate = new Date();
    private String sender;
    private List<String> receiverList;
}

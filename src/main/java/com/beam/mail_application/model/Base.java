package com.beam.mail_application.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Base {

    private String id = UUID.randomUUID().toString();


}

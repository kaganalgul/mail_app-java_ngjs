package com.beam.mail_application.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class SendMailRequest {
    private String content;
    private String title;
    private String file;
    private List<String> receiverList;
}

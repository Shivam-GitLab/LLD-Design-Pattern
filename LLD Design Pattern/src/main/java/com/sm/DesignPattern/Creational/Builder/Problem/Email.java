package com.sm.DesignPattern.Creational.Builder.Problem;

import lombok.Getter;

import java.util.List;

@Getter
public class Email {
    private final String to;
    private final String subject;
    private final String body;
    private String cc;
    private String bcc;
    private List<String> attachments;

    public Email(String to, String subject, String body, String cc, String bcc, List<String> attachments) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.cc = cc;
        this.bcc = bcc;
        this.attachments = attachments;
    }

    // Getters only – no setters (immutability)

    // some might prefer just subject and body
    public Email(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    // some might prefer subject, body and cc
    public Email(String to, String subject, String body, String cc) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.cc = cc;

    }

    // some might prefer subject, body, cc and attachments
    public Email(String to, String subject, String body, String cc, List<String> attachments) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.cc = cc;
        this.attachments = attachments;
    }

    // some might prefer subject, body, attachments but not cc
    public Email(String to, String subject, String body, List<String> attachments) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }


}
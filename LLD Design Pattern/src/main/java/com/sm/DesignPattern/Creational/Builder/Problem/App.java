package com.sm.DesignPattern.Creational.Builder.Problem;

import static java.lang.constant.ConstantDescs.NULL;

public class App {
    public static void main(String[] args) {
        Email email = new Email(
                "contact@saitm.org",
                "Request for course on SpringBoot",
                "Hi Shubha, ......"
        );

        System.out.println(email.getTo());
        System.out.println(email.getSubject());
        System.out.println(email.getBody());
        System.out.println(email.getCc());
        System.out.println(email.getBcc());
        System.out.println(email.getAttachments());
    }
}

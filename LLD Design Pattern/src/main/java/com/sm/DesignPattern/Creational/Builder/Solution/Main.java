package com.sm.DesignPattern.Creational.Builder.Solution;

public class Main {
    public static void main(String[] args) {
        EmailBuilder builder = new EmailBuilder();
        EmailBuilder builder2 = new EmailBuilder();
        Email email = builder
                .setTo("contact@nailyourinterview.org")
                .setSubject("Request for Java Multithreading Content")
                .setBody("Hi Shubha, ....")
                .build();

        Email email2 = builder2
                .setTo("contact@nailyourinterview.org")
                .setSubject("Request for Multithreading Content")
                .build();


        // email is immutable because it does not have setters and the attributes are private.
        System.out.println(email.getTo());
        System.out.println(email.getSubject());
       System.out.println(email.getBody());

//        System.out.println(email.getCc());
        System.out.println("==============");
        System.out.println(email2.getSubject());
        System.out.println(email2.getBody());
    }
}

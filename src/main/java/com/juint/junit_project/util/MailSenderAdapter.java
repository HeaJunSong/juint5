package com.juint.junit_project.util;

public class MailSenderAdapter implements MailSender {
    // private Mail mail;

    // public MailSenderAdapter(Mail mail) {
    // this.mail = new Mail();
    // }

    @Override
    public boolean send() {
        // return mail.sendMail();
        return true;
    }

}

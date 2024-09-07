package com.airepur.service;


import com.airepur.utils.Config;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailService {
    private static final String appPwd = Config.appPwd;
    private JavaMailSenderImpl mailSender = null;

    private void inicializarMail(){
        // Configurar el envío de correo
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setProtocol("smtp");
        mailSender.setUsername("contact.airepur@gmail.com");
        mailSender.setPassword(appPwd);

        // Configurar propiedades adicionales
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.required", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.quitwait", "false");
    }
    public String recoverPass(String email, String pwd) {
        String answer = "ok";
        inicializarMail();
        // Enviar el correo
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("New Password");
            helper.setTo(email);
            helper.setText(pwd);
            helper.setFrom("contact.airepur@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    public String deleteUser(String email, String text) {
        String answer = "ok";

        inicializarMail();
        // Enviar el correo
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Your accout has been deleted. For more details contact us.");
            helper.setTo(email);
            helper.setText(text);
            helper.setFrom("contact.airepur@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    public String blockUser(String email) {
        String answer = "ok";

        inicializarMail();
        // Enviar el correo
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Your accout has been suspended. For more details contact us.");
            helper.setTo(email);
            helper.setText("You broke the rules.");
            helper.setFrom("contact.airepur@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    public String deletePost(String email) {
        String answer = "ok";

        inicializarMail();
        // Enviar el correo
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("One of your posts has been deleted. For more details contact us.");
            helper.setTo(email);
            helper.setText("Be careful with your actions.");
            helper.setFrom("contact.airepur@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }
}

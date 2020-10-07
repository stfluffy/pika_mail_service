package com.pika.mailservice.service.impl;

import com.pika.mailservice.model.Story;
import com.pika.mailservice.model.Subscriber;
import com.pika.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;

/**
 * @author Modenov D.A
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void commentsMailDelivery(List<Story> stories, List<Subscriber> subscribers, String subject) {

        subscribers.forEach(subscriber -> sendEmail(stories, subscriber, subject));

    }

    @Async
    public void sendEmail(List<Story> stories, Subscriber subscriber, String subject) {
        try {
            Context context = new Context();
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            context.setVariables(new HashMap<String, Object>() {{
                put("stories", stories);
                put("name", subscriber.getName());
            }});

            helper.setTo(subscriber.getEmail());
            helper.setFrom(email);
            helper.setSubject(subject);
            helper.setText(templateEngine.process("mailComments", context), true);
            emailSender.send(message);

            log.info("Сообщение отправлено на  адрес: " + subscriber.getEmail());
        } catch (Exception exception) {
            log.error("Сообщение не доставлено на адрес: " + subscriber.getEmail(), exception);
        }
    }

}

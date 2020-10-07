package com.pika.mailservice.service;

import com.pika.mailservice.model.Story;
import com.pika.mailservice.model.Subscriber;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface MailService {

    /**
     * Асинхронно отправляет почту.
     *
     * @param stories     список историй для отправки.
     * @param subscribers список подписчиков.
     * @param subject     тема сообщения.
     */
    void commentsMailDelivery(List<Story> stories, List<Subscriber> subscribers, String subject);

}

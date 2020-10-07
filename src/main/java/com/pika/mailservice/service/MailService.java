package com.pika.mailservice.service;

import com.pika.mailservice.model.Story;
import com.pika.mailservice.model.Subscriber;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface MailService {

    /**
     *
     * @param stories
     * @param subscribers
     * @param subject
     */
    void commentsMailDelivery(List<Story> stories, List<Subscriber> subscribers, String subject);

}

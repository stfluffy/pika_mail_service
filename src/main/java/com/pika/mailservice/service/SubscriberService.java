package com.pika.mailservice.service;

import com.pika.mailservice.dto.SubscribeDto;
import com.pika.mailservice.model.Subscriber;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface SubscriberService {

    /**
     *
     * @param subscribeDto
     */
    Subscriber subscribe(SubscribeDto subscribeDto);

    /**
     *
     * @param email
     */
    boolean unsubscribe(String email);

    /**
     *
     * @param email
     * @return
     */
    boolean checkEmail(String email);

    /**
     *
     * @return
     */
    List<Subscriber> getActiveSubscribers();

    /**
     *
     * @param subscriberId
     * @return
     */
    Subscriber getById(Long subscriberId);

    /**
     *
     * @return
     */
    List<Subscriber> findAll();


}

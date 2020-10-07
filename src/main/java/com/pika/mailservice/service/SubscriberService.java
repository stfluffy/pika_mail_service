package com.pika.mailservice.service;

import com.pika.mailservice.dto.SubscribeDto;

/**
 * @author Modenov D.A
 */

public interface SubscriberService {

    /**
     *
     * @param subscribeDto
     */
    void subscribeToEmail(SubscribeDto subscribeDto);

    /**
     *
     * @param email
     */
    void unsubscribe(String email);

    /**
     *
     * @param email
     * @return
     */
    boolean checkEmail(String email);

}

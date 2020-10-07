package com.pika.mailservice.service.impl;

import com.pika.mailservice.dto.SubscribeDto;
import com.pika.mailservice.model.Subscriber;
import com.pika.mailservice.repository.SubscriberRepository;
import com.pika.mailservice.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author Modenov D.A
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Override
    public void subscribeToEmail(SubscribeDto subscribeDto) {
        if (Objects.isNull(subscribeDto)) {
            return;
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setName(subscribeDto.getName());
        subscriber.setEmail(subscribeDto.getEmail());
        subscriber.setSubscriptionDate(ZonedDateTime.now());
        subscriber.setActive(Boolean.TRUE);
        subscriberRepository.save(subscriber);

    }

    @Override
    public void unsubscribe(String email) {
        Subscriber getFromDb = subscriberRepository.findByEmail(email);

        if (Objects.isNull(getFromDb)) {
            return;
        }

        getFromDb.setActive(Boolean.FALSE);
        subscriberRepository.save(getFromDb);
    }

    @Override
    public boolean checkEmail(String email) {
        return subscriberRepository.existsByEmail(email);
    }

}

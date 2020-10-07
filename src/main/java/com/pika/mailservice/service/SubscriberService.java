package com.pika.mailservice.service;

import com.pika.mailservice.dto.SubscribeDto;
import com.pika.mailservice.model.Subscriber;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface SubscriberService {

    /**
     * Создание новой подписки на рассылку на почту.
     *
     * @param subscribeDto форма с полями: имя и email.
     */
    Subscriber subscribe(SubscribeDto subscribeDto);

    /**
     * Отписка от рассылки на почту.
     *
     * @param email на которой отправляется почта.
     */
    boolean unsubscribe(String email);

    /**
     * Проверка существует ли подписчик с данной почтой.
     *
     * @param email для проверки.
     * @return true если почта существует, false если нет.
     */
    boolean checkEmail(String email);

    /**
     * Список подписчиков с активной подпиской на рассылку.
     *
     * @return список пользователей.
     */
    List<Subscriber> getActiveSubscribers();

    /**
     * Получение подписчика по id.
     *
     * @param subscriberId идентификатор подписчика.
     * @return подписчик.
     */
    Subscriber getById(Long subscriberId);

    /**
     * Список всех пользователей.
     *
     * @return список пользователей.
     */
    List<Subscriber> findAll();


}

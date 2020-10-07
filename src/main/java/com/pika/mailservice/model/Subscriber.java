package com.pika.mailservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author Modenov D.A
 */

@Entity
@Table(name = "subscriber")
@Data
public class Subscriber {

    /**
     * Идентификатор подписчика.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Имя подписчика.
     */
    @Column(name = "name")
    @NotNull
    private String name;

    /**
     * Электронная почта подписчика.
     */
    @Column(name = "email", unique = true)
    @Email
    private String email;

    /**
     * Время создания подписки.
     */
    @Column(name = "subscription_date")
    private ZonedDateTime subscriptionDate;

    /**
     * Активна ли подписка на рассылку.
     */
    @Column(name = "active")
    private boolean active;

}

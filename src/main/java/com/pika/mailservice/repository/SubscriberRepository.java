package com.pika.mailservice.repository;

import com.pika.mailservice.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Modenov D.A
 */

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    List<Subscriber> findByActive(boolean isActive);

    Subscriber findByEmail(String email);

    boolean existsByEmail(String email);

}

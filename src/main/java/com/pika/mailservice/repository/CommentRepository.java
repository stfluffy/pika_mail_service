package com.pika.mailservice.repository;

import com.pika.mailservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Modenov D.A
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}

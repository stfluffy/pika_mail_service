package com.pika.mailservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Modenov D.A
 */

@Entity
@Table(name = "сomment")
@Data
public class Comment {

    /**
     * Идентификатор истории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Ссылка на историю.
     */
    @ManyToOne
    @JoinColumn(name = "story_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Story story;

    /**
     * Идентификатор истории.
     */
    @Column(name = "author")
    private String author;

    /**
     * Идентификатор истории.
     */
    @Column(name = "rating")
    private Integer rating;

    /**
     * Идентификатор истории.
     */
    @Column(name = "link")
    private String link;

    /**
     * Идентификатор истории.
     */
    @Column(name = "text")
    private String text;
}

package com.pika.mailservice.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Modenov D.A
 */

@Entity
@Table(name = "story")
@Data
public class Story {

    /**
     * Идентификатор истории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Заголовок истории.
     */
    @Column(name = "title")
    private String title;

    /**
     * Ссылка на историю.
     */
    @Column(name = "link")
    private String link;

    /**
     * Комментарии к истории.
     */
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Comment> comments;

}

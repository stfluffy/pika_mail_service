package com.pika.mailservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Modenov D.A
 */

@Data
public class CommentDto {

    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private StoryDto story;

    private String author;

    private Integer rating;

    private String link;

    private String text;
}

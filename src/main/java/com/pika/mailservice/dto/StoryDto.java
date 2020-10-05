package com.pika.mailservice.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Modenov D.A
 */

@Data
public class StoryDto {

    private Long id;

    private String title;

    private String link;

    private List<CommentDto> comments;

}

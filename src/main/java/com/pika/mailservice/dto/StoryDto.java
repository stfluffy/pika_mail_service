package com.pika.mailservice.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Modenov D.A
 */

@Data
public class StoryDto {

    private Long id;

    private String title;

    private String link;

    private ZonedDateTime zonedDateTime;

    private List<CommentDto> comments;
}

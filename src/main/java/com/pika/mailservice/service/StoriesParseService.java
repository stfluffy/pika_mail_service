package com.pika.mailservice.service;

import com.pika.mailservice.dto.StoryDto;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface StoriesParseService {

    List<StoryDto> parseStories();
}

package com.pika.mailservice.service;

import com.pika.mailservice.model.Story;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface StoriesParseService {

    /**
     * Получение историй с комментариями, после парсинга страницы.
     *
     * @return список историй.
     */
    List<Story> parseStories();

}

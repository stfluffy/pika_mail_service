package com.pika.mailservice.service;

import com.pika.mailservice.dto.CommentDto;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface CommentParseService {

    /**
     * Парсинг комментариев.
     *
     * @param url   ссылка на страницу с комментариями.
     * @param limit лимит на количество комментариев.
     * @return список комментариев после парсинга.
     */
    List<CommentDto> parseComments(String url, Integer limit);
}

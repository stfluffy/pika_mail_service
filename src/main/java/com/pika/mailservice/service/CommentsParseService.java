package com.pika.mailservice.service;

import com.pika.mailservice.model.Comment;
import com.pika.mailservice.model.Story;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface CommentsParseService {

    /**
     * Получение страниы с историей из параметра истории,
     * и парсинг комментариев к ней.
     *
     * @param story        полученная после парсинга.
     * @param commentLimit лимит на количество комментариев.
     * @return список комментариев после парсинга.
     */
    List<Comment> parseComments(Story story, Integer commentLimit);

}

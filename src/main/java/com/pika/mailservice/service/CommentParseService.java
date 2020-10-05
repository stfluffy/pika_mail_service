package com.pika.mailservice.service;

import com.pika.mailservice.dto.CommentDto;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * @author Modenov D.A
 */

public interface CommentParseService {

    /**
     *
     * @param url
     * @param limit
     * @return
     */
    List<CommentDto> parseComments(String url, Integer limit);
}

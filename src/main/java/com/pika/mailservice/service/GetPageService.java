package com.pika.mailservice.service;

import org.jsoup.nodes.Document;

/**
 * @author Modenov D.A
 */

public interface GetPageService {

    Document getDocumentFromUrl(String url);
}

package com.pika.mailservice.service;

import org.jsoup.nodes.Document;

/**
 * @author Modenov D.A
 */

public interface GetPageService {

    /**
     * Получение документа по ссылке состоящего из элементов.
     *
     * @param url ссылка на станицу.
     * @return документ с элементами страницы
     */
    Document getDocumentFromUrl(String url);

}

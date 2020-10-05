package com.pika.mailservice.service.impl;

import com.pika.mailservice.service.GetPageService;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author Modenov D.A
 */

@Service
@PropertySource("classpath:parser.properties")
@Log4j2
public class GetPageServiceImpl implements GetPageService {

    @Value("${get.page.by.url.timeout}")
    private Integer timeout;

    @Override
    public Document getDocumentFromUrl(String url) {
        try {
            return Jsoup.connect(url).timeout(timeout).get();
        } catch (Exception exception) {
            log.error("Ошибка подключения по url: ", exception);
            return null;
        }
    }
}

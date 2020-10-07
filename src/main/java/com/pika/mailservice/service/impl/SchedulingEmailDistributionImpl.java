package com.pika.mailservice.service.impl;

import com.pika.mailservice.model.Story;
import com.pika.mailservice.model.Subscriber;
import com.pika.mailservice.service.CommentsParseService;
import com.pika.mailservice.service.MailService;
import com.pika.mailservice.service.StoriesParseService;
import com.pika.mailservice.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Modenov D.A
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class SchedulingEmailDistributionImpl {

    private final CommentsParseService commentsParseService;

    private final StoriesParseService storiesParseService;

    private final SubscriberService subscriberService;

    private final MailService mailService;

    @Value("${parse.page.comments.limit}")
    private Integer commentLimit;

    /**
     * Создание задачи выполняющей в течение всего времени работы программы отправку почту,
     * по установленному графику в application.properties
     */
    @Scheduled(cron = "${scheduling.email.distribution}")
    public void sendMails() {
        log.info("Запуск email рассылки в " + ZonedDateTime.now());
        buildTasks();
        log.info("Остановка email рассылки в " + ZonedDateTime.now());
    }

    /**
     * Конструирование задачи для дальнейшего выполнения
     * <p>
     * Происходит загрузка всех подписчиков для рассылки, получение историй,
     * и отправка на почту.
     */
    private void buildTasks() {
        List<Subscriber> subscribers = subscriberService.getActiveSubscribers();
        List<Story> stories = prepareStories();
        mailService.commentsMailDelivery(stories, subscribers, "Ого! Опять новые комментарии с Пикабу...");
    }

    /**
     * Подготовка историй для задачи.
     * <p>
     * Парсинг последних историй и комментариев.
     *
     * @return список историй с комментариями.
     */
    private List<Story> prepareStories() {
        List<Story> stories = storiesParseService.parseStories();

        for (Story story : stories) {
            story.setComments(commentsParseService.parseComments(story, commentLimit));
        }

        return stories;
    }
}

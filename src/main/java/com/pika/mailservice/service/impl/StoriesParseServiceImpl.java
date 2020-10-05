package com.pika.mailservice.service.impl;

import com.pika.mailservice.dto.StoryDto;
import com.pika.mailservice.service.CommentParseService;
import com.pika.mailservice.service.GetPageService;
import com.pika.mailservice.service.StoriesParseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Modenov D.A
 */

@Service
@PropertySource("classpath:parser.properties")
@Log4j2
@RequiredArgsConstructor
public class StoriesParseServiceImpl implements StoriesParseService {

    private final GetPageService pageService;

    private final CommentParseService commentService;

    @Value("${page.pikabu.best.url}")
    private String pikabuBestUrl;

    @Value("${parse.page.stories.limit}")
    private Integer storiesLimit;

    @Value("${parse.page.comments.limit}")
    private Integer commentsLimit;

    @Override
    public List<StoryDto> parseStories() {
        Document document = pageService.getDocumentFromUrl(pikabuBestUrl);

        if (Objects.isNull(document)) {
            return Collections.emptyList();
        }

        Elements elements = getStoriesElements(document);

        return getStories(elements);
    }

    /**
     * Получение элементов из раздела "истории",
     * и выделение элементов отражающих заголовок.
     *
     * @param document полученный после получения страницы
     * @return отсортированные элементы
     */
    private Elements getStoriesElements(Document document) {
        return document.select(".stories-feed__container")
                .select(".story__title");
    }

    /**
     * Получение списка историй.
     * <p>
     * Так же устанавливается лимит на количество историй.
     *
     * @param elements отсортированные элементы через метод {@link StoriesParseServiceImpl#getStoriesElements}.
     * @return список историй после парсинга.
     */
    private List<StoryDto> getStories(Elements elements) {
        return elements.stream()
                .map(this::parseStory)
                .limit(storiesLimit)
                .collect(Collectors.toList());
    }

    /**
     * Создание истории.
     * <p>
     * Метод парсит элементы связанные с историей,
     * а затем получает комментарии к этой истории с помощью парсера комментариев.
     * <p>
     * Так же устанавливается лимит на количество комментариев.
     *
     * @param element отсортированный элемент через метод {@link StoriesParseServiceImpl#getStoriesElements}.
     * @return возвращает созданную историю.
     */
    private StoryDto parseStory(Element element) {
        StoryDto story = new StoryDto();

        story.setTitle(element.text());

        story.setLink(element.select(".story__title-link").attr("href"));

        story.setComments(commentService.parseComments(story.getLink(), commentsLimit));

        return story;
    }
}

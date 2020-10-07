package com.pika.mailservice.service.impl;

import com.pika.mailservice.model.Story;
import com.pika.mailservice.repository.StoryRepository;
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

import java.time.ZonedDateTime;
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

    private final StoryRepository storyRepository;

    @Value("${page.pikabu.best.url}")
    private String pikabuBestUrl;

    @Value("${parse.page.stories.limit}")
    private Integer storiesLimit;

    @Override
    public List<Story> parseStories() {
        Document document = pageService.getDocumentFromUrl(pikabuBestUrl);

        if (Objects.isNull(document)) {
            return Collections.emptyList();
        }

        Elements elements = getStoriesElements(document);

        List<Story> stories = getStories(elements);

        return saveToDb(stories);
    }

    /**
     * Сохранение историй в бд.
     *
     * @param stories для сохранения.
     * @return истории после загрузки в бд.
     */
    private List<Story> saveToDb(List<Story> stories) {
        return storyRepository.saveAll(stories);
    }

    /**
     * Получение элементов из раздела "истории",
     * и выделение элементов отражающих заголовок.
     *
     * @param document полученный после получения страницы.
     * @return отсортированные элементы.
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
    private List<Story> getStories(Elements elements) {
        return elements.stream()
                .map(this::parseStory)
                .limit(storiesLimit)
                .collect(Collectors.toList());
    }

    /**
     * Создание истории.
     * <p>
     * Метод парсит элементы связанные с историей.
     * <p>
     *
     * @param element отсортированный элемент через метод {@link StoriesParseServiceImpl#getStoriesElements}.
     * @return возвращает созданную историю.
     */
    private Story parseStory(Element element) {
        Story story = new Story();

        story.setTitle(element.text());

        story.setLink(element.select(".story__title-link").attr("href"));

        story.setParseDate(ZonedDateTime.now());

        return story;
    }

}

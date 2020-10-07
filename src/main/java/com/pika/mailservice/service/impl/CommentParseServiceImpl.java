package com.pika.mailservice.service.impl;

import com.pika.mailservice.model.Comment;
import com.pika.mailservice.model.Story;
import com.pika.mailservice.repository.CommentRepository;
import com.pika.mailservice.service.CommentParseService;
import com.pika.mailservice.service.GetPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Modenov D.A
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentParseServiceImpl implements CommentParseService {

    private final GetPageService pageService;

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> parseComments(Story story, Integer commentLimit) {

        if (Objects.isNull(story)) {
            return Collections.emptyList();
        }
        Document document = pageService.getDocumentFromUrl(story.getLink());

        if (Objects.isNull(document)) {
            return Collections.emptyList();
        }

        Elements elements = getCommentRatingElements(document);

        List<Comment> comments = getComments(elements, story);

        return saveToDb(sortCommentByRating(comments, commentLimit));
    }

    private List<Comment> saveToDb(List<Comment> comments) {
        return commentRepository.saveAll(comments);
    }

    /**
     * Получение элементов из раздела "комментарии",
     * и выделение элементов отражающих рейтинг комментария.
     *
     * @param document полученный после получения страницы.
     * @return отсортированные элементы.
     */
    private Elements getCommentRatingElements(Document document) {
        return document.select(".page-story__comments")
                .select(".comment__rating-count");
    }

    /**
     * Получение комментариев.
     *
     * @param elements отсортированные элементы через метод {@link CommentParseServiceImpl#getCommentRatingElements}.
     * @return список комментариев конвертированных в CommentDto.
     */
    private List<Comment> getComments(Elements elements, Story story) {
        return elements.stream()
                .map(element -> parseComment(element, story))
                .collect(Collectors.toList());
    }

    /**
     * Сортировка комментариев по рейтингу.
     *
     * @param comments     список комментариев.
     * @param commentLimit лимит на количество комментариев, после сортировки.
     * @return список сортированных комментариев
     */
    private List<Comment> sortCommentByRating(List<Comment> comments, Integer commentLimit) {
        return comments.stream()
                .sorted(Comparator.comparingInt(Comment::getRating).reversed())
                .limit(commentLimit)
                .collect(Collectors.toList());
    }

    /**
     * Создание комментария.
     *
     * @param element отсортированный элемент через метод {@link CommentParseServiceImpl#getCommentRatingElements}.
     * @return возвращает созданный комментарий.
     */
    private Comment parseComment(Element element, Story story) {
        Comment comment = new Comment();

        if (NumberUtils.isCreatable(element.text())) {
            comment.setRating(Integer.parseInt(element.text()));
        } else {
            comment.setRating(0);
        }

        comment.setAuthor(element.parent()
                .select(".comment__user")
                .attr("data-name"));

        comment.setLink(element.parent()
                .select(".comment__tool")
                .select("[data-role=link]")
                .attr("href"));

        comment.setText(element.parent().parent().select(".comment__content").text());

        comment.setParseDate(ZonedDateTime.now());

        comment.setStory(story);

        return comment;
    }

}

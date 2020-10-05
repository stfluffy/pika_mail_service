package com.pika.mailservice.service.impl;

import com.pika.mailservice.dto.CommentDto;
import com.pika.mailservice.service.CommentParseService;
import com.pika.mailservice.service.GetPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

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

    @Override
    public List<CommentDto> parseComments(String url, Integer commentLimit) {
        Document document = pageService.getDocumentFromUrl(url);

        if (Objects.isNull(document)) {
            return Collections.emptyList();
        }

        Elements elements = getCommentRatingElements(document);

        List<CommentDto> comments = getComments(elements);

        return sortCommentByRating(comments, commentLimit);
    }

    /**
     * Получение элемента с разделом комментариев и выделение элементов отражающих рейтинг.
     *
     * @param document полученный после получения страницы.
     * @return отсортированные элементы.
     */
    private Elements getCommentRatingElements(Document document) {
        return document.select(".page-story__comments")
                .select(".comment__rating-count");
    }

    /**
     * ????????
     * @param elements --
     * @return --
     */
    private List<CommentDto> getComments(Elements elements) {
        return elements.stream()
                .map(CommentParseServiceImpl::parseComment)
                .collect(Collectors.toList());
    }

    /**
     * ????
     * @param comments --
     * @param commentLimit --
     * @return --
     */
    private List<CommentDto> sortCommentByRating(List<CommentDto> comments, Integer commentLimit) {
        return comments.stream()
                .sorted(Comparator.comparingInt(CommentDto::getRating).reversed())
                .limit(commentLimit)
                .collect(Collectors.toList());
    }

    /**
     * Создание комментария после сортировки методом getCommentRatingElements.
     *
     * @param element отсортированный элемент.
     * @return возвращает созданный комментарий.
     */
    private static CommentDto parseComment(Element element) {
        CommentDto comment = new CommentDto();

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
                .select("[aria-label=Ссылка на комментарий]")
                .attr("href"));

        comment.setText(element.parent().parent().select(".comment__content").text());

        return comment;
    }
}

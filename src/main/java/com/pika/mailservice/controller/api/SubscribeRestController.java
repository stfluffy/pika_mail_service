package com.pika.mailservice.controller.api;

import com.pika.mailservice.dto.SubscribeDto;
import com.pika.mailservice.model.Subscriber;
import com.pika.mailservice.service.SubscriberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Modenov D.A
 */

@RestController
@RequestMapping("/api/subscribers")
@RequiredArgsConstructor
public class SubscribeRestController {

    private final SubscriberService subscriberService;

    /**
     *
     * @return
     */
    @ApiOperation(value = "Поиск подписчика по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь найден"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    @Schema(implementation = Subscriber.class)
    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> getById(@ApiParam(value = "Идентификатор") @PathVariable("id") Long id) {
        Subscriber subscriber = subscriberService.getById(id);

        return subscriber != null ? ResponseEntity.ok(subscriber) : ResponseEntity.notFound().build();
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "Список подписчиков получающих рассылку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователи найдены"),
            @ApiResponse(code = 404, message = "Пользователи не найдены")
    })
    @Schema(implementation = Subscriber.class)
    @GetMapping
    public ResponseEntity<List<Subscriber>> findActiveSubscribers() {
        List<Subscriber> subscribers = subscriberService.getActiveSubscribers();

        return subscribers != null ? ResponseEntity.ok(subscribers) : ResponseEntity.notFound().build();
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "Список всех подписчиков")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователи найдены"),
            @ApiResponse(code = 404, message = "Пользователи не найдены")
    })
    @Schema(implementation = Subscriber.class)
    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> findAll() {
        List<Subscriber> subscribers = subscriberService.findAll();

        return subscribers != null ? ResponseEntity.ok(subscribers) : ResponseEntity.notFound().build();
    }

    /**
     *
     * @param subscribeDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "Подписка на рассылку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен"),
            @ApiResponse(code = 400, message = "Невалидные данные")
    })
    @Schema(implementation = SubscribeDto.class)
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@ApiParam(value = "Данные для подписки")
                                            @Valid @RequestBody SubscribeDto subscribeDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(e -> errors.append(e.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (subscriberService.checkEmail(subscribeDto.getEmail())) {
            ResponseEntity.badRequest().body("Subscriber exist");
        }

        Subscriber subscriber = subscriberService.subscribe(subscribeDto);

        return subscriber != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     *
     * @param email
     * @return
     */
    @ApiOperation(value = "Отписка от рассылки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Email не найден"),
    })
    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam("email") String email) {
        return subscriberService.unsubscribe(email) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

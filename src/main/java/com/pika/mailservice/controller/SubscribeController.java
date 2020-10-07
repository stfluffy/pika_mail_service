package com.pika.mailservice.controller;

import com.pika.mailservice.dto.SubscribeDto;
import com.pika.mailservice.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author Modenov D.A
 */

@Controller
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscriberService subscriberService;

    @GetMapping
    public String subscribeView(Model model) {
        model.addAttribute("error", "");
        return "subscribeView";
    }

    @PostMapping("/confirm")
    public String subscribe(@Valid SubscribeDto subscribeDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Некорректные данные");
            return "subscribeView";
        }

        if (subscriberService.checkEmail(subscribeDto.getEmail())) {
            model.addAttribute("error", "Подписка уже существует");
            return "subscribeView";
        }

        subscriberService.subscribe(subscribeDto);

        model.addAttribute("error", "");

        return "redirect:/";
    }

    @GetMapping("/unsubscribe")
    public String unsubscribe(Model model) {
        model.addAttribute("error", "");
        return "unsubscribe";
    }

    @PostMapping("/unsubscribe/confirm")
    public String unsubscribe(@RequestParam("email") String email, Model model) {

        if (subscriberService.checkEmail(email)) {
            subscriberService.unsubscribe(email);
            return "redirect:/";
        }

        model.addAttribute("error", "Неверный Email");

        return "unsubscribe";
    }

}

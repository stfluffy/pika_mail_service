package com.pika.mailservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Modenov D.A
 */

@Data
public class SubscribeDto {

    @Schema(description = "Имя получателя")
    @NotNull
    private String name;

    @Schema(description = "Почта получателя")
    @Email
    @NotNull
    private String email;

}

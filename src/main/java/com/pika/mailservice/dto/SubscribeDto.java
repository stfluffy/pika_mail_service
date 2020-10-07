package com.pika.mailservice.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Modenov D.A
 */

@Data
public class SubscribeDto {

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

}

package com.cinema.booking.order;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateOrderRequestData {

    @NotNull
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @NotBlank(message = "Email is required")
    @Email
    private String email;

}

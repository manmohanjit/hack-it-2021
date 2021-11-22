package com.cinema.booking.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class CreateOrderRequestData {

    @NotEmpty(message = "Items is required")
    @Size(min = 1, max = 10)
    private List<Long> items;
    
}

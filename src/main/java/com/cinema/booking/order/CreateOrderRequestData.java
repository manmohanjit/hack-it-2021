package com.cinema.booking.order;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateOrderRequestData {

    @NotEmpty(message = "Items is required")
    @Size(min = 1, max = 10)
    private List<Long> items;

}

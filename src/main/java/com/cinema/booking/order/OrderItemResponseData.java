package com.cinema.booking.order;

import com.cinema.booking.category.CategoryResponseData;
import com.cinema.booking.seat.SeatResponseData;
import lombok.Data;

@Data
public class OrderItemResponseData {
    private Long id;
    private CategoryResponseData category;
    private SeatResponseData seat;
}

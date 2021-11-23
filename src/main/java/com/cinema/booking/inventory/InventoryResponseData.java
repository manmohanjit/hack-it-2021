package com.cinema.booking.inventory;

import com.cinema.booking.category.CategoryResponseData;
import com.cinema.booking.seat.SeatResponseData;
import lombok.Data;

@Data
public class InventoryResponseData {

    private Long id;
    private CategoryResponseData category;
    private SeatResponseData seat;
    private InventoryStatus status;
    private Boolean enabled;

}

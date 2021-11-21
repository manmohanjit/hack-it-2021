package com.cinema.booking.order;

import com.cinema.booking.category.CategoryResponseData;
import com.cinema.booking.inventory.InventoryResponseData;
import com.cinema.booking.seat.SeatResponseData;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderItemResponseData {
    private CategoryResponseData category;
    private SeatResponseData seat;
}

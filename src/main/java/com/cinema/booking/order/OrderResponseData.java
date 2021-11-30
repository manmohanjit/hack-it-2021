package com.cinema.booking.order;

import lombok.Data;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderResponseData {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;
    private String name;
    private String email;
    private Long showId;
    Set<OrderItemResponseData> items = new HashSet<>();
}

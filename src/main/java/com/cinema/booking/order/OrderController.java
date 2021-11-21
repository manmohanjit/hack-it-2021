package com.cinema.booking.order;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // TODO: 404 Error handling
    // TODO: UUID Error handling
    @GetMapping(path = "{orderId}")
    public OrderResponseData getOrder(@PathVariable("orderId") String orderId) {
        return orderService
                .findOrder(orderId)
                .orElseThrow(() -> new IllegalStateException("Unable to find order by id "+orderId));
    }
}

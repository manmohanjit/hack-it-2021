package com.cinema.booking.order;

import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final InventoryService inventoryService;

    @PostMapping
    public OrderResponseData createOrder(@Valid CreateOrderRequestData createOrderRequestData) {
        return orderService
                .createOrder(createOrderRequestData);
    }

    // TODO: 404 Error handling
    // TODO: UUID Error handling
    @GetMapping(path = "{orderId}")
    public OrderResponseData getOrder(@PathVariable("orderId") String orderId) {
        return orderService
                .findOrder(orderId)
                .orElseThrow(() -> new IllegalStateException("Unable to find order by id "+orderId));
    }

    @PatchMapping(path = "{orderId}")
    public OrderResponseData updateOrder(@PathVariable("orderId") String orderId, @Valid UpdateOrderRequestData updateOrderRequestData) {
        return orderService
                .updateOrderDetails(orderId, updateOrderRequestData)
                .orElseThrow(() -> new IllegalStateException("Unable to find order by id "+orderId));
    }

    @DeleteMapping(path = "{orderId}")
    public OrderResponseData cancelOrder(@PathVariable("orderId") String orderId) {
        return orderService
                .cancelOrder(orderId)
                .orElseThrow(() -> new IllegalStateException("Unable to find order by id "+orderId));
    }

    @PostMapping(path = "{orderId}/complete")
    public OrderResponseData completeOrder(@PathVariable("orderId") String orderId) {
        return orderService
                .completeOrder(orderId)
                .orElseThrow(() -> new IllegalStateException("Unable to find order by id "+orderId));
    }
}

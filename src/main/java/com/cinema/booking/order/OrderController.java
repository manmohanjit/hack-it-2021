package com.cinema.booking.order;

import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryService;
import com.cinema.booking.inventory.InventoryUnavailableException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return orderService
                    .createOrder(createOrderRequestData);
        } catch (InventoryUnavailableException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Some of the selected seats are unavailable.");
        }
    }

    // TODO: UUID Error handling
    @GetMapping(path = "{orderId}")
    public OrderResponseData getOrder(@PathVariable("orderId") String orderId) {
        return orderService
                .findOrder(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find order with id "+orderId));
    }

    @PatchMapping(path = "{orderId}")
    public OrderResponseData updateOrder(@PathVariable("orderId") String orderId, @Valid UpdateOrderRequestData updateOrderRequestData) {
        try {
            return orderService
                    .updateOrderDetails(orderId, updateOrderRequestData)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find order with id "+orderId));
        } catch (InvalidOrderStateException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @DeleteMapping(path = "{orderId}")
    public OrderResponseData cancelOrder(@PathVariable("orderId") String orderId) {
        try {
            return orderService
                    .cancelOrder(orderId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find order with id "+orderId));
        } catch (InvalidOrderStateException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @PostMapping(path = "{orderId}/complete")
    public OrderResponseData completeOrder(@PathVariable("orderId") String orderId) {
        try {
            OrderResponseData order = orderService
                    .completeOrder(orderId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find order with id "+orderId));

            orderService.sendOrderMail(orderId);

            return order;
        } catch (InvalidOrderStateException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}

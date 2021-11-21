package com.cinema.booking.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Optional<OrderResponseData> findOrder(String id) {
        Optional<Order> order = orderRepository.findById(UUID.fromString(id));

        if(order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(OrderMapper.INSTANCE.fromOrder(order.get()));
    }
}

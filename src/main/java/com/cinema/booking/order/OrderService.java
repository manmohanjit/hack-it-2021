package com.cinema.booking.order;

import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryRepository;
import com.cinema.booking.inventory.InventoryStatus;
import com.cinema.booking.inventory.InventoryUnavailableException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;


    public Optional<OrderResponseData> findOrder(String id) {
        return orderRepository
                .findById(UUID.fromString(id))
                .map(OrderMapper.INSTANCE::fromOrder);
    }

    @Transactional
    public OrderResponseData createOrder(CreateOrderRequestData createOrderRequestData) {
        Order order = new Order();

        List<Inventory> items = inventoryRepository
                .findAllByIdInAndStatus(createOrderRequestData.getItems(), InventoryStatus.AVAILABLE);

        if(items.size() != createOrderRequestData.getItems().size()) {
            throw new IllegalStateException("Some of the seats selected are unavailable");
        }

        items.forEach(new Consumer<Inventory>() {
            @Override
            public void accept(Inventory inventory) {
                inventory.setStatus(InventoryStatus.RESERVED);

                order.addItem(inventory);
            }
        });

        orderRepository.save(order);
        inventoryRepository.saveAll(items);

        return OrderMapper.INSTANCE.fromOrder(order);
    }


    public OrderResponseData updateOrderDetails(Order order, UpdateOrderRequestData updateOrderRequestData) throws InvalidOrderStateException {
        if(order.getStatus() != OrderStatus.INITIAL) {
            throw new InvalidOrderStateException(order, "Order is in invalid state");
        }

        order.setName(updateOrderRequestData.getName());
        order.setEmail(updateOrderRequestData.getEmail());
        order.setStatus(OrderStatus.PENDING);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return OrderMapper.INSTANCE.fromOrder(order);
    }

    public Optional<OrderResponseData> updateOrderDetails(String id, UpdateOrderRequestData updateOrderRequestData) throws InvalidOrderStateException {
        Optional<Order> order = orderRepository.findById(UUID.fromString(id));

        if(order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(updateOrderDetails(order.get(), updateOrderRequestData));
    }

    @Transactional
    public OrderResponseData cancelOrder(Order order) throws InvalidOrderStateException {
        if(order.getStatus() != OrderStatus.INITIAL && order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException(order, "Order is in invalid state");
        }

        order.setStatus(OrderStatus.EXPIRED);
        order.setUpdatedAt(LocalDateTime.now());

        List<Inventory> items = inventoryRepository
                .findAllByIdIn(order.getItems().stream().map(Inventory::getId).toList());

        items.forEach(new Consumer<Inventory>() {
            @Override
            public void accept(Inventory inventory) {
                inventory.setStatus(InventoryStatus.AVAILABLE);
            }
        });

        orderRepository.save(order);
        inventoryRepository.saveAll(items);

        return OrderMapper.INSTANCE.fromOrder(order);
    }

    @Transactional
    public Optional<OrderResponseData> cancelOrder(String id) throws InvalidOrderStateException {
        Optional<Order> order = orderRepository.findById(UUID.fromString(id));

        if(order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(cancelOrder(order.get()));
    }


    @Transactional
    public OrderResponseData completeOrder(Order order) throws InvalidOrderStateException {
        if(order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException(order, "Order is in invalid state");
        }

        order.setStatus(OrderStatus.COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());

        List<Inventory> items = inventoryRepository
                .findAllByIdIn(order.getItems().stream().map(Inventory::getId).toList());

        items.forEach(new Consumer<Inventory>() {
            @Override
            public void accept(Inventory inventory) {
                inventory.setStatus(InventoryStatus.SOLD);
            }
        });

        orderRepository.save(order);
        inventoryRepository.saveAll(items);

        return OrderMapper.INSTANCE.fromOrder(order);
    }

    @Transactional
    public Optional<OrderResponseData> completeOrder(String id) throws InvalidOrderStateException {
        Optional<Order> order = orderRepository.findById(UUID.fromString(id));

        if(order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(completeOrder(order.get()));
    }
}

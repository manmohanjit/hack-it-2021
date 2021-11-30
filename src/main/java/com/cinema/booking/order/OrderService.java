package com.cinema.booking.order;

import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryRepository;
import com.cinema.booking.inventory.InventoryStatus;
import com.cinema.booking.inventory.InventoryUnavailableException;
import com.cinema.booking.movie.Movie;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import com.cinema.booking.show.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final ShowRepository showRepository;
    private JavaMailSender emailSender;


    /**
     * Find an order by order ID
     *
     * @param orderId
     */
    public Optional<OrderResponseData> findOrder(String orderId) {
        return orderRepository
                .findById(UUID.fromString(orderId))
                .map(OrderMapper.INSTANCE::fromOrder);
    }

    /**
     * Create an order via request DataTransfer Object
     *
     * @param createOrderRequestData
     */
    @Transactional
    public OrderResponseData createOrder(CreateOrderRequestData createOrderRequestData) throws InventoryUnavailableException {
        Order order = new Order();

        Show show = showRepository.findById(createOrderRequestData.getShowId())
                .orElseThrow(InventoryUnavailableException::new);

        order.setShow(show);

        List<Inventory> items = inventoryRepository
                .findAllByShowIdAndIdInAndStatus(show.getId(), createOrderRequestData.getItems(), InventoryStatus.AVAILABLE);

        if (items.size() != createOrderRequestData.getItems().size()) {
            throw new InventoryUnavailableException();
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

    /**
     * Send out an non-blocking (async) email for a given order by order ID
     *
     * @param orderId
     */
    @Async
    public void sendOrderMail(String orderId) {
        Optional<Order> orderData = orderRepository.findById(UUID.fromString(orderId));

        if (orderData.isEmpty() || orderData.get().getStatus() != OrderStatus.COMPLETED) {
            return;
        }

        Order order = orderData.get();

        String seatsList = order.getItems().stream().map(Inventory::getSeat).map(Seat::getLabel).collect(Collectors.joining(", "));

        Optional<Show> showData = order.getItems()
                .stream()
                .findFirst()
                .map(Inventory::getShow);

        Optional<Movie> movieData = showData.map(Show::getMovie);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@example.com");
        message.setTo(order.getEmail());
        message.setSubject("Your order for " + (movieData.isPresent() ? movieData.get().getTitle() : "your show") + " has been processed!");

        List<String> body = new ArrayList<>();
        body.add("Hello " + order.getName() + ",\n\n");
        body.add("Your booking details are as below:\n\n");
        movieData.ifPresent(movie -> body.add("Movie: " + movie.getTitle() + "\n"));
        showData.ifPresent(show -> body.add("Date: " + show.getStartsAt() + "\n"));
        if (!seatsList.isEmpty()) {
            body.add("Seats: " + seatsList + "\n");
        }

        body.add("Reference: " + order.getId().toString() + "\n\n");
        body.add("Thank you and see you soon!");

        message.setText(String.join("", body));

        emailSender.send(message);
    }

    /**
     * Update order details using a DataTransferObject by order entity
     *
     * @param order
     * @param updateOrderRequestData
     */
    public OrderResponseData updateOrderDetails(Order order, UpdateOrderRequestData updateOrderRequestData) throws InvalidOrderStateException {
        if (order.getStatus() != OrderStatus.INITIAL) {
            throw new InvalidOrderStateException(order, "Order is in invalid state");
        }

        order.setName(updateOrderRequestData.getName());
        order.setEmail(updateOrderRequestData.getEmail());
        order.setStatus(OrderStatus.PENDING);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return OrderMapper.INSTANCE.fromOrder(order);
    }

    /**
     * Update order details using a DataTransferObject by order ID
     *
     * @param orderId
     * @param updateOrderRequestData
     */
    public Optional<OrderResponseData> updateOrderDetails(String orderId, UpdateOrderRequestData updateOrderRequestData) throws InvalidOrderStateException {
        Optional<Order> order = orderRepository.findById(UUID.fromString(orderId));

        if (order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(updateOrderDetails(order.get(), updateOrderRequestData));
    }
    /**
     * Cancel an order by order entity
     *
     * @param order
     */
    @Transactional
    public OrderResponseData cancelOrder(Order order) throws InvalidOrderStateException {
        if (order.getStatus() != OrderStatus.INITIAL && order.getStatus() != OrderStatus.PENDING) {
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

    /**
     * Cancel an order by order ID
     *
     * @param orderId
     */
    @Transactional
    public Optional<OrderResponseData> cancelOrder(String orderId) throws InvalidOrderStateException {
        Optional<Order> order = orderRepository.findById(UUID.fromString(orderId));

        if (order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(cancelOrder(order.get()));
    }


    /**
     * Complete an order by order entity
     *
     * @param order
     */
    @Transactional
    public OrderResponseData completeOrder(Order order) throws InvalidOrderStateException {
        if (order.getStatus() != OrderStatus.PENDING) {
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

    /**
     * Complete an order by order ID
     *
     * @param orderId
     */
    @Transactional
    public Optional<OrderResponseData> completeOrder(String orderId) throws InvalidOrderStateException {
        Optional<Order> order = orderRepository.findById(UUID.fromString(orderId));

        if (order.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(completeOrder(order.get()));
    }
}

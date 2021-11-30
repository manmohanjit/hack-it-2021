package com.cinema.booking.order;

import com.cinema.booking.category.Category;
import com.cinema.booking.hall.Hall;
import com.cinema.booking.hall.HallRepository;
import com.cinema.booking.inventory.Inventory;
import com.cinema.booking.inventory.InventoryRepository;
import com.cinema.booking.inventory.InventoryStatus;
import com.cinema.booking.inventory.InventoryUnavailableException;
import com.cinema.booking.movie.Movie;
import com.cinema.booking.movie.MovieRepository;
import com.cinema.booking.seat.Seat;
import com.cinema.booking.show.Show;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Show show;
    private List<Inventory> inventoryList;

    /**
     * Create the seed data to be used for each test case
     */
    @BeforeEach
    void setUp() {
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        Hall hall = new Hall("Test Hall", "<svg />");
        hall.addSeat(new Seat(hall, "A-1"));
        hall.addSeat(new Seat(hall, "A-2"));
        hall.addSeat(new Seat(hall, "A-3"));
        hall.addSeat(new Seat(hall, "A-4"));
        hall.addSeat(new Seat(hall, "A-5"));

        hallRepository.save(hall);

        Movie movie = new Movie("Test", "Test Body");

        Category category = new Category(movie, "Cat", 5000, "black");
        movie.addCategory(category);

        Show show = new Show(movie, hall, LocalDateTime.now(), LocalDateTime.now());
        movie.addShow(show);

        movieRepository.save(movie);

        this.inventoryList = show
                .getHall()
                .getSeats()
                .stream()
                .map(seat -> new Inventory(show, category, seat, InventoryStatus.AVAILABLE, true))
                .toList();
        inventoryRepository.saveAll(this.inventoryList);

        this.show = show;
    }

    /**
     * Create order test, covers repeated orders, invalid request data and testing for successful creation
     *
     * @throws Exception
     */
    @Test
    void createOrder() throws Exception {
        // No data, should error out
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Build our order request data
        CreateOrderRequestData createOrderRequestData = new CreateOrderRequestData();
        createOrderRequestData.setShowId(show.getId());
        createOrderRequestData.setItems(inventoryList.stream().limit(3).map(i -> i.getId()).toList());

        // Build our order request
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequestData));


        // First time purchasing seats should be successful
        String resultBody = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderResponseData orderResponseDataTest = objectMapper.readValue(resultBody, OrderResponseData.class);

        Assertions.assertEquals(orderResponseDataTest.getStatus(), OrderStatus.INITIAL);

        // Second time should error out since it's seats taken
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    /**
     * Find order test
     *
     * @throws Exception
     */
    @Test
    void findOrder() throws Exception {
        // Build our order request
        OrderResponseData orderResponseData = createTestOrder();

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/orders/" + orderResponseData.getId());

        // Expect HTTP 2XX response
        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Update order test
     *
     * @throws Exception
     */
    @Test
    void updateOrder() throws Exception {
        // Create a test order
        OrderResponseData orderResponseData = createTestOrder();

        // Build our update order request data
        UpdateOrderRequestData updateOrderRequestData = new UpdateOrderRequestData();
        updateOrderRequestData.setName("Example");
        updateOrderRequestData.setEmail("john@example.com");

        // Build our update order request
        RequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/orders/" + orderResponseData.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateOrderRequestData));


        // Expect a successful response
        String resultBody = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderResponseData orderResponseDataTest = objectMapper.readValue(resultBody, OrderResponseData.class);

        Assertions.assertEquals(orderResponseDataTest.getId(), orderResponseData.getId());
        Assertions.assertEquals(orderResponseDataTest.getStatus(), OrderStatus.PENDING);
    }

    /**
     * Complete order test
     *
     * @throws Exception
     */
    @Test
    void completeOrder() throws Exception {
        // Seed some order data
        OrderResponseData orderResponseData = createTestOrder();
        updateTestOrder(orderResponseData);

        // Build our complete order request
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders/" + orderResponseData.getId() + "/complete");

        // Expect a successful response
        String resultBody = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderResponseData orderResponseDataTest = objectMapper.readValue(resultBody, OrderResponseData.class);

        // Additional assertions
        Assertions.assertEquals(orderResponseDataTest.getId(), orderResponseData.getId());
        Assertions.assertEquals(orderResponseDataTest.getStatus(), OrderStatus.COMPLETED);
    }

    /**
     * Expire order test
     *
     * @throws Exception
     */
    @Test
    void expireOrder() throws Exception {
        // Seed some order data
        OrderResponseData orderResponseData = createTestOrder();
        updateTestOrder(orderResponseData);

        // Build our expire order request
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/orders/" + orderResponseData.getId());

        // Expect a successful response
        String resultBody = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderResponseData orderResponseDataTest = objectMapper.readValue(resultBody, OrderResponseData.class);

        // Additional assertions
        Assertions.assertEquals(orderResponseDataTest.getId(), orderResponseData.getId());
        Assertions.assertEquals(orderResponseDataTest.getStatus(), OrderStatus.EXPIRED);
    }

    private void updateTestOrder(OrderResponseData orderResponseData) throws InvalidOrderStateException {
        UpdateOrderRequestData updateOrderRequestData = new UpdateOrderRequestData();
        updateOrderRequestData.setName("Example");
        updateOrderRequestData.setEmail("john@example.com");

        orderService.updateOrderDetails(orderResponseData.getId().toString(), updateOrderRequestData);
    }

    private OrderResponseData createTestOrder() throws InventoryUnavailableException {
        // Build our order request
        CreateOrderRequestData createOrderRequestData = new CreateOrderRequestData();
        createOrderRequestData.setShowId(show.getId());
        createOrderRequestData.setItems(inventoryList.stream().limit(3).map(i -> i.getId()).toList());

        // Create order using service class
        return orderService.createOrder(createOrderRequestData);
    }
}